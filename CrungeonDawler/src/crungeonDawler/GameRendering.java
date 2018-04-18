package crungeonDawler;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

//import com.google.gson.*;
import javax.imageio.ImageIO;

public class GameRendering {
	private Image tileSet;
	private Image decorationSet;
	private Level level;
	//I am working under the assumption that this class is solely for rendering, and will not do collision detections
	ArrayList<Entity> allEntities =new ArrayList<Entity>();
	public GameRendering(Player p,Level level) {
		this.level=level;
		player=p;
		player.x=50;
		player.y=50;
		allEntities.add(player);
	}
	Player player;
	public void DrawMenu(Graphics2D g2d, Point mousePosition) {
		Draw(g2d,mousePosition);
		g2d.translate(-Screen.frameWidth/2,-Screen.frameHeight/2);//in future don't translate
		g2d.setColor(new Color(0,0,0,128));
		g2d.fillRect(0,0,Screen.frameWidth,Screen.frameHeight);
		g2d.setColor(new Color(75,75,75,255));
		g2d.fillRect(Screen.frameWidth/3,Screen.frameHeight/3,Screen.frameWidth/3,Screen.frameHeight/3);
	}
	public static final int pixeltilewidth =16;
	public static final int renderdist = 15;
	public void Draw(Graphics2D g2d, Point mousePosition) {
		BufferedImage dungeon = new BufferedImage(pixeltilewidth*renderdist*2,pixeltilewidth*renderdist*2,BufferedImage.TYPE_4BYTE_ABGR); 
		for(int x=player.getX()-renderdist<0?0:player.getX()-renderdist;x<level.width&&x<player.getX()+renderdist;x++){
			for(int y=player.getY()-renderdist<0?0:player.getY()-renderdist;y<level.height&&y<player.getY()+renderdist;y++){
				dungeon.getGraphics().drawImage(getImageFromTileID(level.levellayout[x][y]),(x-player.getX()+renderdist)*pixeltilewidth, (y-player.getY()+renderdist)*pixeltilewidth, pixeltilewidth, pixeltilewidth,null);
			}
		}
//		for(Entity e : allEntities){
//			if(Math.abs(e.getX()-player.getX())<renderdist&& Math.abs(e.getY()-player.getY())<renderdist){
//				dungeon.getGraphics().drawImage(e.getSprite(), (e.getX()-player.getX())*pixeltilewidth, (e.getY()-player.getY())*pixeltilewidth,pixeltilewidth,pixeltilewidth, null);
//			}
//		}
		g2d.drawImage(dungeon, (Screen.frameWidth-dungeon.getWidth())/2,(Screen.frameHeight-dungeon.getHeight())/2,null);
	}
	public int[][] getVisible() {
		ArrayList<int[]> visibletiles = new ArrayList<int[]>();
		BufferedImage dungeon = new BufferedImage(GameRendering.pixeltilewidth*GameRendering.renderdist*2,GameRendering.pixeltilewidth*GameRendering.renderdist*2,BufferedImage.TYPE_4BYTE_ABGR); 
		for(double theta=0;theta<Math.PI*2;theta+=Math.PI*2/50){
			ArrayList<Point> line = RasterLine(player.getX(),player.getY(),theta,player.lengthOfLineOfSight);
			for(int i=0;i<line.size();i++){
				if(Game.currentlevel[line.get(i).x][line.get(i).y]){
					
				}
			}
		}
		return null;
	}
	private ArrayList<Point> RasterLine(double x,double y,double theta,int lengthOfLineOfSight) {
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

	public void UpdateGame(Point mousePosition,boolean[] keyPressed,boolean[] mousePressed) {
		
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
}
