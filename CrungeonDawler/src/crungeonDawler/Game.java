package crungeonDawler;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.net.URL;
//import com.google.gson.*;
import javax.imageio.ImageIO;

public class Game {
	private Image tileSet;
	private Image decorationSet;
	public Game() {
		
	}
	public void DrawMenu(Graphics2D g2d, Point mousePosition) {
		Draw(g2d,mousePosition);
		g2d.setColor(new Color(0,0,0,128));
		g2d.fillRect(0,0,Screen.frameWidth,Screen.frameHeight);
		g2d.setColor(new Color(0,0,0,255));
		g2d.fillRect(Screen.frameHeight/3,Screen.frameWidth/3,Screen.frameHeight*2/3,Screen.frameHeight*2/3);
	}

	public void Draw(Graphics2D g2d, Point mousePosition) {
		g2d.setColor(new Color(0,0,0,255));
		g2d.fillOval(0,0,Screen.frameWidth,Screen.frameHeight);
		g2d.setColor(Color.red);
		g2d.fillRect(mousePosition.x, mousePosition.y, 10, 10);
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
