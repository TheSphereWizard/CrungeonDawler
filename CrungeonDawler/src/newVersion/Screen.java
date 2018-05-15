package newVersion;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Screen extends JPanel implements KeyListener,MouseListener,ActionListener{
	public static enum GameState{MAIN_MENU, PLAYING, GAMEOVER, MENU, LOADING, STARTUP}
	public static GameState gameState;

	private static boolean[] keyPressed = new boolean[525];
	private static boolean[] mousePressed = new boolean[3];
	private static boolean[] buttonPressed = new boolean[9];

	public static int frameWidth;
	public static int frameHeight;
	public static final long secInNanosec = 1000000000L;
	public static final long milisecInNanosec = 1000000000L;

	private final int GAME_FPS = 10;
	private final long GAME_UPDATE_PERIOD = secInNanosec / GAME_FPS;
	private long lastTime;

	private ArrayList<Component> menuUI = new ArrayList<Component>();
	static final int MENU_BUTTON_NEWGAME  = 0;
	static final int MENU_BUTTON_CONTINUE = 1;
	static final int MENU_BUTTON_EXIT     = 2;

	private ArrayList<Component> gameUI = new ArrayList<Component>();
	static final int ACTION_BUTTON_1  = 3;
	static final int ACTION_BUTTON_2  = 4;
	static final int ACTION_BUTTON_3  = 5;
	static final int ACTION_BUTTON_4  = 6;
	static final int INVENTORY_BUTTON = 7;
	static final int MAP_BUTTON       = 8;

	Game game;

	public Screen(){
		//KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		//manager.addKeyEventDispatcher(this.new MyDispatcher());
		this.setFocusable(true);
		this.setDoubleBuffered(true);
		this.setBackground(Color.black);
		this.addMouseListener(this);
		this.addKeyListener(this);

		gameState = GameState.STARTUP;
		Thread gameThread = new Thread() {
			public void run(){
				GameLoop();
			}
		};
		gameThread.start();

	}

	/*****************************************************************/
	/* Keep track of inputs                                          */
	/*****************************************************************/
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
	public void mousePressed(MouseEvent e) {
		mousePressed[e.getButton()-1] = true;
	}
	public void mouseReleased(MouseEvent e) {
		mousePressed[e.getButton()-1] = false;
	}
	public void keyPressed(KeyEvent e) {
		keyPressed[e.getKeyCode()] = true;
	}
	public void keyReleased(KeyEvent e) {
		keyPressed[e.getKeyCode()] = false;
	}
	public void actionPerformed(ActionEvent e) {
		try{
			buttonPressed[Integer.parseInt(e.getActionCommand())]=true;
		}catch(Exception E){
			System.out.println(e.getActionCommand());
		}
	}
	/*private class MyDispatcher implements KeyEventDispatcher {
		public boolean dispatchKeyEvent(KeyEvent e) {
			if (e.getID() == KeyEvent.KEY_PRESSED) {
				keyPressed[e.getKeyCode()] = true;
			}
			if (e.getID() == KeyEvent.KEY_RELEASED) {
				keyPressed[e.getKeyCode()] = false;
			}
			return false;
		}
	}*/

	/*****************************************************************/
	/* Main loop                                                     */
	/*****************************************************************/

	private void GameLoop() {
		long startupTime = 0, lastStartupTime = System.nanoTime();

		long startTime, timeTaken, timeLeft;

		while(true) {
			startTime = System.nanoTime();
			switch (gameState) {
			case PLAYING:
				game.updateGame(mousePosition(), mousePressed, keyPressed, buttonPressed);
				break;
			case GAMEOVER:
				break;
			case MAIN_MENU:
				newGame();
				if(buttonPressed[MENU_BUTTON_NEWGAME]){
					newGame();
					buttonPressed[MENU_BUTTON_NEWGAME] = false;
				}
				if(buttonPressed[MENU_BUTTON_CONTINUE]){
					System.out.println("doesn't work yet");
					buttonPressed[MENU_BUTTON_CONTINUE] = false;
				}
				if(buttonPressed[MENU_BUTTON_EXIT]){
					System.exit(0);
					buttonPressed[MENU_BUTTON_EXIT] = false;
				}
				break;
			case LOADING:
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

	/*****************************************************************/
	/* Graphical Stuff                                               */
	/*****************************************************************/

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;        
		draw(g2d);
	}
	public void draw(Graphics2D g2d) {
		switch (gameState)
		{
		case PLAYING:
			game.draw(g2d);
			break;
		case MENU:
			break;
		case GAMEOVER:
			break;
		case MAIN_MENU:
			break;
		}
	}
	/*****************************************************************/
	/* Starting a game                                               */
	/*****************************************************************/
	private void newGame(){
		lastTime = System.nanoTime();
		game=new Game(this.getWidth(),this.getHeight());
		gameState=GameState.PLAYING;
	}
	/*****************************************************************/
	/* Unused methods                                                */
	/*****************************************************************/

	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void keyTyped(KeyEvent arg0) {}
}
