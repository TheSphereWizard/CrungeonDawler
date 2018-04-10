package crungeonDawler;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
//import com.google.gson.*;
import javax.imageio.ImageIO;

public class Game {
	private Image tileSet;
	private Image decorationSet;
	private Level level;
	public Game() {
		level=new Level(100,100);
		//Probablly pass in a player, if we are making classes, but for right now will just define some const, but should be player pos
		playerpos = new int[]{level.width/2,level.height/2};
	}
	int[] playerpos;
	public void DrawMenu(Graphics2D g2d, Point mousePosition) {
		Draw(g2d,mousePosition);
		g2d.setColor(new Color(0,0,0,128));
		g2d.fillRect(0,0,Screen.frameWidth,Screen.frameHeight);
		g2d.setColor(new Color(0,0,0,255));
		g2d.fillRect(Screen.frameHeight/3,Screen.frameWidth/3,Screen.frameHeight*2/3,Screen.frameHeight*2/3);
	}
	public static final int pixeltilewidth =16;
	public static final int renderdist = 5;
	BufferedImage[] Tilesforlevel;
	public void Draw(Graphics2D g2d, Point mousePosition) {
		g2d.setColor(new Color(0,0,0,255));
		g2d.fillOval(0,0,Screen.frameWidth,Screen.frameHeight);
		g2d.setColor(Color.red);
		g2d.fillRect(mousePosition.x, mousePosition.y, 10, 10);
		
		for(int x=playerpos[0]-renderdist<0?0:playerpos[0]-renderdist;x<level.width&&x<playerpos[0]+renderdist;x++){
			for(int y=playerpos[1]-renderdist<0?0:playerpos[1]-renderdist;y<level.height&&y<playerpos[1]+renderdist;y++){
				//Tilesforlevel should take the tileid stored in level and return the image that should be drawn for the player to see
				g2d.drawImage(Tilesforlevel[level.level[x][y]],x*pixeltilewidth, y*pixeltilewidth, pixeltilewidth, pixeltilewidth,null);
			}
		}
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
