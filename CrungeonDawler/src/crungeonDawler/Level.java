package crungeonDawler;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.imageio.ImageIO;

public class Level {
	int width, height;
	int[][] levellayout;
	int[][] roomids;
	BufferedImage floor;
	BufferedImage wall;
	BufferedImage lowwall;//idk what to callit
	BufferedImage voidtile;
	final static int tilewidth=Game.pixelTileWidth;
	final static int tileheight=Game.pixelTileWidth;
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
		floor=spritesheet.getSubimage(0, 0, Game.pixelTileWidth, Game.pixelTileWidth);;
		wall=spritesheet.getSubimage(0, 1*tileheight, tilewidth, Game.pixelTileWidth);;
		lowwall=spritesheet.getSubimage(0, 2*tileheight, tilewidth, tileheight);;//idk what to callit
		voidtile=spritesheet.getSubimage(0, 3*tileheight, tilewidth, tileheight);;
			
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
	public ArrayList<int[]> spawnmobs() {
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
}
