package crungeonDawler;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Game {
	Level currentlevel;
	Player player;
	public ArrayList<Entity> allEntities =new ArrayList<Entity>();
	final int TILE_SIZE = 16;
	private Image tileSet;
	private Image decorationSet;
	private Level level;
	private Game game;

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
		g2d.translate(Screen.frameWidth/2,Screen.frameHeight/2);//in future don't translate
		int[][] visible = getVisible();
		for(int x=Math.max(player.getX()/pixelTileWidth-renderdist,0);x<level.width&&x<player.getX()/pixelTileWidth+renderdist;x++){
			for(int y=Math.max(player.getY()/pixelTileWidth-renderdist,0);y<level.height&&y<player.getY()/pixelTileWidth+renderdist;y++){
				dungeon.getGraphics().drawImage(getImageFromTileID(level.levellayout[x][y]),x*pixelTileWidth-player.getX(),y*pixelTileWidth-player.getY(), pixelTileWidth, pixelTileWidth,null);
			}
		}
		for(Entity e : game.allEntities){
			if(Math.abs(e.getX()-player.getX())<renderdist&& Math.abs(e.getY()-player.getY())<renderdist){
				dungeon.getGraphics().drawImage(e.getSprite(), (e.getX()-player.getX()), (e.getY()-player.getY()),e.getWidth(),e.getHeight(), null);
			}
		}
		g2d.drawImage(dungeon, (Screen.frameWidth-dungeon.getWidth())/2,(Screen.frameHeight-dungeon.getHeight())/2,null);
	}
	public int[][] getVisible() {
		/*ArrayList<int[]> visibletiles = new ArrayList<int[]>();
		BufferedImage dungeon = new BufferedImage(GameRendering.pixelTileWidth*GameRendering.renderdist*2,GameRendering.pixelTileWidth*GameRendering.renderdist*2,BufferedImage.TYPE_4BYTE_ABGR); 
		for(double theta=0;theta<Math.PI*2;theta+=Math.PI*2/50){
			ArrayList<Point> line = RasterLine(player.getX(),player.getY(),theta,player.lengthOfLineOfSight);
			for(int i=0;i<line.size();i++){
				if(Game.currentlevel[line.get(i).x][line.get(i).y]){

				}
			}
		}*/
		return null;
	}
	private ArrayList<Point> RasterLine(double x,double y,double theta,int lengthOfLineOfSight) {
		if(theta == Math.PI/2||theta==Math.PI*3/2){
			return null;
		}else{
			for()
		}
		return null;
	}
	private Image getImageFromTileID(int id) {
		if(id==0){
			return level.Void();
		}
		if(id==14){
			return level.Wall();
		}
		if(id==8||id==3/*this is the door id*/){
			return level.Floor();
		}
		if(id==10){
			return level.lowWall();
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
		currentlevel =new Level(100,100,"testSpriteSheetforActors");
		allEntities.add(p);
		player=p;
		player.x=50;
		player.y=50;
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
		int xMax = (int) Math.ceil ((double)(e.getX()+t[0]+e.getWidth ())/TILE_SIZE);
		int yMin = (int) Math.floor((double)(e.getY()+t[1])/TILE_SIZE);
		int yMax = (int) Math.ceil ((double)(e.getY()+t[1]+e.getHeight())/TILE_SIZE);

		return false;
	}
}
