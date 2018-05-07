package crungeonDawler;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ChestActor extends Actor{
	public BufferedImage spriteSheet;
	public ChestActor(String path){
		super(path,Game.TILE_SIZE,Game.TILE_SIZE, false);
		File spriteSheetFile = new File("resources/ActorSpriteSheets/"+path+".png");
		try {
			spriteSheet = ImageIO.read(spriteSheetFile);
		} catch (IOException e) {System.out.println("Error"+(1/0));
		}
	}
	int chestState=0;//0 to 4 open one way to open other way
	int change=0;
	public Image getSprite(int vx,int vy){
		chestState+=change;
		if(chestState==0||chestState==4){
			change=0;
		}
		chestState=Math.max(0, chestState);
		chestState=Math.min(4, chestState);
		
		BufferedImage b = spriteSheet.getSubimage(0, ((int) chestState)*height, width, height);
		return b;
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
}
