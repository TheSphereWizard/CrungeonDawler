package crungeonDawler;

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
		
	}

	public void Draw(Graphics2D g2d, Point mousePosition) {
		
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
