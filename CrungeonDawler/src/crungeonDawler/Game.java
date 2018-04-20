package crungeonDawler;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Game {
	Level currentLevel;
	Player player;
	public ArrayList<Entity> allEntities =new ArrayList<Entity>();
	final int TILE_SIZE = 16;
	private Image tileSet;
	private Image decorationSet;

	public void DrawMenu(Graphics2D g2d, Point mousePosition) {
		Draw(g2d,mousePosition);
		g2d.translate(-Screen.frameWidth/2,-Screen.frameHeight/2);//in future don't translate
		g2d.setColor(new Color(0,0,0,128));
		g2d.fillRect(0,0,Screen.frameWidth,Screen.frameHeight);
		g2d.setColor(new Color(75,75,75,255));
		g2d.fillRect(Screen.frameWidth/3,Screen.frameHeight/3,Screen.frameWidth/3,Screen.frameHeight/3);
	}
	public static final int pixelTileWidth =16;
	public static final int renderdist = 20;
	public void Draw(Graphics2D g2d, Point mousePosition) {
		BufferedImage dungeon = new BufferedImage(pixelTileWidth*renderdist*2,pixelTileWidth*renderdist*2,BufferedImage.TYPE_4BYTE_ABGR); 
		for(int x=Math.max(player.getX()/pixelTileWidth-renderdist,0);x<currentLevel.width&&x<player.getX()/pixelTileWidth+renderdist+1;x++){
			for(int y=Math.max(player.getY()/pixelTileWidth-renderdist,0);y<currentLevel.height&&y<player.getY()/pixelTileWidth+renderdist+1;y++){
				dungeon.getGraphics().drawImage(getImageFromTileID(currentLevel.levellayout[x][y]),x*pixelTileWidth-player.getX()+dungeon.getWidth()/2,y*pixelTileWidth-player.getY()+dungeon.getHeight()/2, pixelTileWidth, pixelTileWidth,null);
			}
		}
		dungeon.getGraphics().drawImage(getVisible(), 0, 0, null);
		for(Entity e : allEntities){
			if(Math.abs(e.getX()-player.getX())<renderdist&& Math.abs(e.getY()-player.getY())<renderdist){
				dungeon.getGraphics().drawImage(e.getSprite(), (e.getX()-player.getX())+dungeon.getWidth()/2, (e.getY()-player.getY())+dungeon.getHeight()/2,e.getWidth(),e.getHeight(), null);
			}
		}
		g2d.drawImage(dungeon, (Screen.frameWidth-dungeon.getWidth())/2,(Screen.frameHeight-dungeon.getHeight())/2,null);
	}
	public BufferedImage getVisible() {
		BufferedImage dungeon = new BufferedImage(pixelTileWidth*renderdist*2,pixelTileWidth*renderdist*2,BufferedImage.TYPE_4BYTE_ABGR); 
		Graphics g = dungeon.getGraphics();
		g.setColor(new Color(128,128,128,128));
		g.fillRect(0, 0, dungeon.getWidth(), dungeon.getHeight());
		g.setColor(Color.white);
		for(double theta=0;theta<Math.PI*2;theta+=Math.PI*2/20){
			
			double xrange = Math.cos(theta)*player.lengthOfLineOfSight;
			double yrange = Math.sin(theta)*player.lengthOfLineOfSight;
			double m = Math.tan(theta);
			ArrayList<double[]> p = new ArrayList<double[]>();
			//find x line intersection, 
			//y=m(x-h)+k
			for(int i=0;Math.abs(i)<Math.abs(xrange);i+=Math.signum(xrange)){
				p.add(new double[]{player.x+i,(m*(i)+player.y)});
			}
			//find y line intersections
			//()1/m*(y-k)+h=x
			for(int i=0;Math.abs(i)<Math.abs(yrange);i+=Math.signum(yrange)){
				p.add(new double[]{1/m*(i)+player.x,player.y+i});
			}
			//order them by dist from center
			MergeSort(p);
			g.translate(dungeon.getWidth()/2,dungeon.getHeight()/2);
			for(int i=0;i<p.size();i++){
//				System.out.println(Math.sqrt(Math.pow(p.get(i)[0], 2)+Math.pow(p.get(i)[1], 2)));
				g.setColor(Color.white);
				g.drawLine(0,0,(int)(p.get(i)[0]-player.x),(int)(p.get(i)[1]-player.y));
				g.setColor(Color.blue);
				g.drawOval((int)(p.get(i)[0]-player.x),(int)(p.get(i)[1]-player.y), 2, 2);
				System.out.println((int)(p.get(i)[0]-player.x)+" "+(int)(p.get(i)[1]-player.y));
			}
			
			//from those double[] points find the boxes the line is entering
			//using that information draw the line to go into the hitbox of but not through walls.
		}
		return dungeon;
	}
	private void MergeSort(ArrayList<double[]> p) {
		//i will do at home
	}
	private Image getImageFromTileID(int id) {
		if(id==0){
			return currentLevel.Void();
		}
		if(id==14){
			return currentLevel.Wall();
		}
		if(id==8||id==3/*this is the door id*/){
			return currentLevel.Floor();
		}
		if(id==10){
			return currentLevel.lowWall();
		}
		return null;
	}

	public void DrawGameOver(Graphics2D g2d, Point mousePosition) {

	}

	public void loadMapKit(String mapFile) {

	}
	public void loadTileset(String path) {
		URL tileSetUrl = this.getClass().getResource("/resources/images/"+path+".png");
		try {
			tileSet = ImageIO.read(tileSetUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void loadDecorations(String path) {
		URL decorationSetUrl = this.getClass().getResource("/resources/images/"+path+".png");
		try {
			decorationSet = ImageIO.read(decorationSetUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	boolean arraycontains(int[][] r,int w[]){
		for(int[] i : r){
			if (w.equals(i)){
				return true;
			}
		}
		return false;
	}
	Game(Player p){
		player=p;
		currentLevel =new Level(100,100,"testSpriteSheetforActors");
		allEntities.add(p);
		player.x=50*pixelTileWidth;
		player.y=50*pixelTileWidth;
	}
	int slowdown=0;
	int slowdownfactor=1;
	void UpdateGame(Point mousePosition,boolean[] keyPressed,boolean[] mousePressed){
		if(keyPressed[37/*left*/]){
			if(slowdown%slowdownfactor==0)
				tryLegalMovement(player,new int[]{-1,0});
		}
		if(keyPressed[38/*up*/]){
			if((slowdown%slowdownfactor)==0)
				tryLegalMovement(player,new int[]{0,-1});
		}
		if(keyPressed[39/*right*/]){
			if(slowdown%slowdownfactor==0)
				tryLegalMovement(player,new int[]{1,0});
		}
		if(keyPressed[40/*down*/]){
			if(slowdown%slowdownfactor==0)
				tryLegalMovement(player,new int[]{0,1});
		}
		slowdown++;
	}
	void tryLegalMovement(Entity e, int[] translation){
		if(legalMovement(e, translation)){
			e.x+=translation[0];
			e.y+=translation[1];
		}
	}

	boolean legalMovement(Entity e, int[] t){
		int xMin = (int) Math.floor((double)(e.getX()+t[0])/TILE_SIZE);
		int xMax = (int) Math.ceil ((double)(e.getX()+t[0]+e.getWidth ()-16)/TILE_SIZE);
		int yMin = (int) Math.floor((double)(e.getY()+t[1])/TILE_SIZE);
		int yMax = (int) Math.ceil ((double)(e.getY()+t[1]+e.getHeight()-16)/TILE_SIZE);
		for(int x=xMin;x<=xMax;x++){
			for(int y=yMin;y<=yMax;y++){
				try{
					if(Read.contains(e.invalidtiles, currentLevel.levellayout[x][y])){
						return false;
					}
				}
				catch(Exception ex){
					return false;
				}
			}
		}
		return true;
	}
}
