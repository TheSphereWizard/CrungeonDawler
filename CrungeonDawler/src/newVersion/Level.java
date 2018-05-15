package newVersion;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Level {
	int[][] levelLayout;
	int[][] regionLayout;
	int width;
	int height;
	BufferedImage map;
	BufferedImage sheet;
	public Level(String mapPath,String sheetPath) {
		try {
			map = ImageIO.read(new File("resources/levels/"+mapPath+".png"));
			sheet = ImageIO.read(new File("resources/tileSets/"+sheetPath+".png"));
		} catch (IOException e) {e.printStackTrace();}
		width = map.getWidth();
		height = map.getHeight();
		levelLayout = new int[width][height];
		regionLayout = new int[width][height];
		for(int x=0;x<width;x++) {
			for(int y=0;y<height;y++) {
				Color c = new Color(map.getRGB(x, y));
				levelLayout[x][y] = c.getBlue()/127 + c.getGreen()*3/127;
				regionLayout[x][y] = c.getRed();
			}	
		}
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public Image getTile(int x,int y) {
		return sheet.getSubimage(0, Game.TILE_SIZE*levelLayout[x][y], Game.TILE_SIZE, Game.TILE_SIZE);
	}
	public int getTileID(int x,int y) {
		return levelLayout[x][y];
	}
	public int getRegion(int x,int y) {
		return regionLayout[x][y];
	}
}