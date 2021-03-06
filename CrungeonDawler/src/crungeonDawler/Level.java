package crungeonDawler;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Level {
	int width, height;
	int[][] levellayout;
	int[][] roomids;
	BufferedImage floor;
	BufferedImage wall;
	BufferedImage lowwall;//idk what to callit
	BufferedImage voidtile;
	final static int tilewidth=Game.TILE_SIZE;
	final static int tileheight=Game.TILE_SIZE;
	Level(int width, int height, String RoomSpritepath){
		this.width=width;
		this.height=height;
		LevelLayout layout = new LevelLayout(width,height);
		levellayout=layout.level;
		roomids=layout.roomids;
		File spriteSheetFile = new File("resources/LevelSpriteSheets/"+RoomSpritepath+".png");
		BufferedImage spritesheet=null;
		try {
			spritesheet = ImageIO.read(spriteSheetFile);
		} catch (IOException e) {System.out.println("Error"+(1/0));
		}
		floor=spritesheet.getSubimage(0, 0, Game.TILE_SIZE, Game.TILE_SIZE);
		lowwall=spritesheet.getSubimage(0, 1*tileheight, tilewidth, tileheight);//idk what to callit
		wall=spritesheet.getSubimage(0, 2*tileheight, tilewidth, Game.TILE_SIZE);
		voidtile=spritesheet.getSubimage(0, 3*tileheight, tilewidth, tileheight);
			
	}
	BufferedImage Wall(){
		return wall;
	}
	BufferedImage Floor(){
		return floor;
	}
	BufferedImage lowWall(){
		return lowwall;
	}
	BufferedImage Void(){
		return voidtile;
	}
	public ArrayList<int[]> spawnMobs() {
		ArrayList<int[]> r = new ArrayList<int[]>();
		for(int x=0;x<width;x++){
			for(int y=0;y<height;y++){
				if(levellayout[x][y]==LevelLayout.monsterID){
					levellayout[x][y]=LevelLayout.floorID;
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
				if(levellayout[x][y]==LevelLayout.placeddoorID){
					levellayout[x][y]=LevelLayout.floorID;
					if(levellayout[x+1][y]==LevelLayout.placeddoorID){
						levellayout[x+1][y]=LevelLayout.floorID;
						r.add(new int[]{x,y,0});
					}else{
						levellayout[x][y+1]=LevelLayout.floorID;
						r.add(new int[]{x,y,1});
					}
					
				}
			}
		}
		return r;
	}
}
