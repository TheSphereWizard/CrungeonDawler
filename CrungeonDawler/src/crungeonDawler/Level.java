package crungeonDawler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Level {
	int width, height;
	int[][] levellayout;
	int[][] roomids;
	Level(int width, int height, String RoomSpritepath){
		this.width=width;
		this.height=height;
		LevelLayout layout = new LevelLayout(width,height);
		levellayout=layout.level;
		roomids=layout.roomids;
		File spriteSheetFile = new File("resources/LevelSpriteSheets/"+RoomSpritepath+".png");
		BufferedImage spritesheet;
		try {
			spritesheet = ImageIO.read(spriteSheetFile);
		} catch (IOException e) {System.out.println("Error"+(1/0));
		}
	}
}
