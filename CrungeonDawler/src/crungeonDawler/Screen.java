package crungeonDawler;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Screen extends JPanel implements KeyListener,MouseListener,ActionListener{

	public static enum GameState{MAIN_MENU, PLAYING, GAMEOVER, MENU, LOADING, STARTUP}
	public static GameState gameState;

	private static boolean[] keyPressed = new boolean[525];
	private static boolean[] mousePressed = new boolean[3];
	private static boolean[] jButtonPressed = new boolean[3];

	public static int frameWidth;
	public static int frameHeight;
	public static final long secInNanosec = 1000000000L;
	public static final long milisecInNanosec = 1000000000L;

	private final int GAME_FPS = 30;
	private final long GAME_UPDATE_PERIOD = secInNanosec / GAME_FPS;
	private long lastTime;


	private Game game;

	public Screen(){
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.setBackground(Color.black);
		this.addKeyListener(this);
		this.addMouseListener(this);
        
        gameState = GameState.STARTUP;
        
        Thread gameThread = new Thread() {
            @Override
            public void run(){
                GameLoop();
            }
        };
        gameThread.start();
	}

	//things that actually do things

	@Override
	public void mousePressed(MouseEvent e) {
		mousePressed[e.getButton()-1] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mousePressed[e.getButton()-1] = false;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keyPressed[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keyPressed[e.getKeyCode()] = false;
	}

	@Override
	public void mouseClicked(MouseEvent e) { }
	@Override
	public void mouseEntered(MouseEvent e) { }
	@Override
	public void mouseExited(MouseEvent e) { }
	@Override
	public void keyTyped(KeyEvent e) { }
	BufferedImage backg=null;
    private void Initialize(){
		//eyyyy, um so a card layout
    	this.setLayout(new GridBagLayout());
    	GridBagConstraints Titleconst = new GridBagConstraints();
	    	Titleconst.gridx=2;
	    	Titleconst.gridy=2;
	    	Titleconst.gridwidth=3;
	    	Titleconst.ipady=200;
	    	Titleconst.fill = GridBagConstraints.HORIZONTAL;
		
		GridBagConstraints Highscoresconst = new GridBagConstraints();
			Highscoresconst.gridx=2;
			Highscoresconst.gridy=3;
			Highscoresconst.gridwidth=3;
			Highscoresconst.weighty=0.4;
			Titleconst.fill = GridBagConstraints.BOTH;
			
		GridBagConstraints NewGameconst = new GridBagConstraints();
			NewGameconst.gridx=2;
			NewGameconst.gridy=4;
			NewGameconst.ipadx=30;
			NewGameconst.ipady=30;
			NewGameconst.weightx=0.3;
			
		GridBagConstraints Continueconst = new GridBagConstraints();
			Continueconst.gridx=3;
			Continueconst.gridy=4;
			Continueconst.ipadx=30;
			Continueconst.ipady=30;
			
		GridBagConstraints Exitconst = new GridBagConstraints();
			Exitconst.gridx=4;
			Exitconst.gridy=4;
			Exitconst.weightx=0.3;
			Exitconst.weighty=0.5;
			Exitconst.ipadx=30;
			Exitconst.ipady=30;
		
		JLabel  Title    = new JLabel ("Crungeon Dawler", SwingConstants.CENTER);
			Title.setForeground(Color.white);
			Title.setFont(new Font("Times New Roman",0,64));
			Title.setOpaque(false);
		JPanel  Highscores = new JPanel ();
		JButton NewGame    = new JButton("NewGame");
			NewGame.addActionListener(this);
			NewGame.setActionCommand("0");
			NewGame.setForeground(Color.white);
			NewGame.setFont(new Font("Times New Roman",0,24));
			NewGame.setBorderPainted(false); 
			NewGame.setContentAreaFilled(false); 
			NewGame.setFocusPainted(false); 
			NewGame.setOpaque(false);
		JButton Continue   = new JButton("Continue");
			Continue.setActionCommand("1");
			Continue.addActionListener(this);
			Continue.setForeground(Color.white);
			Continue.setFont(new Font("Times New Roman",0,24));
			Continue.setBorderPainted(false); 
			Continue.setContentAreaFilled(false); 
			Continue.setFocusPainted(false); 
			Continue.setOpaque(false);
		JButton Exit       = new JButton("Exit");
			Exit.setActionCommand("2");
			Exit.addActionListener(this);
			Exit.setForeground(Color.white);
			Exit.setFont(new Font("Times New Roman",0,24));
			Exit.setBorderPainted(false); 
			Exit.setContentAreaFilled(false); 
			Exit.setFocusPainted(false);
			Exit.setOpaque(false);
		
		this.add(Title,Titleconst);
		this.add(Highscores,Highscoresconst);
		this.add(NewGame,NewGameconst);
		this.add(Continue,Continueconst);
		this.add(Exit,Exitconst);
		
		for(int i=0;i<2;i++){
			GridBagConstraints Buffer = new GridBagConstraints();
			Buffer.gridx=1+4*i;
			Buffer.weightx=0.2;
			this.add(new JLabel(),Buffer);
		}
    }

    private void LoadContent(){
    	try{
			backg=ImageIO.read(new File("backgroundmainmenu.jpg"));
		}catch(Exception E){
			System.out.println("backgroundimagenotfound");
		}
    	Toolkit toolkit = Toolkit.getDefaultToolkit();
    	Image image = toolkit.getImage("cursor.png");
    	Cursor c = toolkit.createCustomCursor(image , new Point(this.getX(), 
    	           this.getY()), "img");
    	this.setCursor (c);
    }
	
	private void GameLoop() {
		long startupTime = 0, lastStartupTime = System.nanoTime();

		long startTime, timeTaken, timeLeft;

		while(true) {
			startTime = System.nanoTime();

			switch (gameState) {
			case PLAYING:
				game.UpdateGame(mousePosition());
				lastTime = System.nanoTime();
				break;
			case GAMEOVER:
				//...
				break;
			case MAIN_MENU:
				if(jButtonPressed[0]){
					game=new Game();
					gameState=GameState.PLAYING;
					jButtonPressed[0]=false;
				}
				if(jButtonPressed[1]){
					//Continue so not for a while
					jButtonPressed[1]=false;
				}
				if(jButtonPressed[2]){
					System.exit(0);
					jButtonPressed[2]=false;
				}
				repaint();
				break;
			case LOADING:
				Initialize();
				LoadContent();
				gameState = GameState.MAIN_MENU;
				break;
			case STARTUP:
				if(this.getWidth() > 1 && startupTime > secInNanosec/4)
				{
					frameWidth = this.getWidth();
					frameHeight = this.getHeight();
					gameState = GameState.LOADING;
				}
				else
				{
					startupTime += System.nanoTime() - lastStartupTime;
					lastStartupTime = System.nanoTime();
				}
				break;
			}
			repaint();
			timeTaken = System.nanoTime() - startTime;
			timeLeft = (GAME_UPDATE_PERIOD - timeTaken) / milisecInNanosec;
			if (timeLeft < 10) 
				timeLeft = 10; 
			try {
				Thread.sleep(timeLeft);
			} catch (InterruptedException ex) { }
		}
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;        
		super.paintComponent(g2d);        
		Draw(g2d);
	}

	public void Draw(Graphics2D g2d) {
		switch (gameState)
		{
		case PLAYING:
			game.Draw(g2d, mousePosition());
			break;
		case MENU:
			game.DrawMenu(g2d,mousePosition());
			break;
		case GAMEOVER:
			game.DrawGameOver(g2d, mousePosition());
			break;
		case MAIN_MENU:
			g2d.drawImage(backg, 0, 0, this.getWidth(), this.getHeight(), null);
			break;
		}
	}
    private void newGame(){
        lastTime = System.nanoTime();
        game = new Game();
    }
	private Point mousePosition() {
		try{
			Point mp = this.getMousePosition();

			if(mp != null)
				return this.getMousePosition();
			else
				return new Point(0, 0);
		}
		catch (Exception e)
		{
			return new Point(0, 0);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		try{
			jButtonPressed[Integer.parseInt(e.getActionCommand())]=true;
		}catch(Exception E){
			System.out.println(e.getActionCommand());
		}
	}
}