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
		g2d.setColor(Color.red);
		g2d.fillRect(mousePosition.x, mousePosition.y, 10, 10);
		g2d.translate(Screen.frameWidth/2,Screen.frameHeight/2);//in future don't translate
		for(int x=player.getX()-renderdist<0?0:player.getX()-renderdist;x<level.width&&x<player.getX()+renderdist;x++){
			for(int y=player.getY()-renderdist<0?0:player.getY()-renderdist;y<level.height&&y<player.getY()+renderdist;y++){
				g2d.drawImage(getImageFromTileID(level.levellayout[x][y]),(x-player.getX())*pixeltilewidth, (y-player.getY())*pixeltilewidth, pixeltilewidth, pixeltilewidth,null);
			}
		}
		for(Entity e : allEntities){
			if(Math.abs(e.getX()-player.getX())<renderdist&& Math.abs(e.getY()-player.getY())<renderdist){
//				g2d.drawImage(e.getSprite(), -300, -300,pixeltilewidth,pixeltilewidth, null);
				g2d.drawImage(e.getSprite(), (e.getX()-player.getX())*pixeltilewidth, (e.getY()-player.getY())*pixeltilewidth,pixeltilewidth,pixeltilewidth, null);
			}
		}
		
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

	public void UpdateGame(Point mousePosition) {
		
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
}
