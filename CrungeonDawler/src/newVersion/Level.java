package newVersion;

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
	public Level(String sheetPath,int w,int h) {
		LevelLayout l =new LevelLayout(w,h);
		levelLayout = l.level;
		regionLayout = l.roomids;
		try {
			map = Read.outputroom(levelLayout);;
			sheet = ImageIO.read(new File("resources/tileSets/"+sheetPath+".png"));
		} catch (IOException e) {e.printStackTrace();}
		width = w;
		height = h;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public Image getTile(int x,int y) {
		if(levelLayout[x][y]<4)
			return sheet.getSubimage(0, Game.TILE_SIZE*levelLayout[x][y], Game.TILE_SIZE, Game.TILE_SIZE);
		return sheet.getSubimage(0, 0, Game.TILE_SIZE, Game.TILE_SIZE);
	}
	public int getTileID(int x,int y) {
		return levelLayout[x][y];
	}
	public int getRegion(int x,int y) {
		return regionLayout[x][y];
	}
	public ArrayList<int[]> spawnMobs() {
		ArrayList<int[]> r = new ArrayList<int[]>();
		for(int x=0;x<width;x++){
			for(int y=0;y<height;y++){
				if(levelLayout[x][y]==LevelLayout.monsterID){
					levelLayout[x][y]=LevelLayout.floorID;
					r.add(new int[]{x,y});
				}
			}
		}
		return r;
	}
	public ArrayList<int[]> spawnDoors() {
		ArrayList<int[]> r = new ArrayList<int[]>();
		for(int x=0;x<width;x++){
			for(int y=0;y<height;y++){
				if(levelLayout[x][y]==LevelLayout.placeddoorID){
					levelLayout[x][y]=LevelLayout.floorID;
					if(levelLayout[x+1][y]==LevelLayout.placeddoorID){
						levelLayout[x+1][y]=LevelLayout.floorID;
						r.add(new int[]{x,y,0});
					}else{
						levelLayout[x][y+1]=LevelLayout.floorID;
						r.add(new int[]{x,y,1});
					}
					
				}
			}
		}
		return r;
	}
	
}