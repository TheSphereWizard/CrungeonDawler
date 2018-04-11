package crungeonDawler;


import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Actor {
	private BufferedImage spriteSheet;
	private int currentAnim=0;
	private int currentFrame=0;
	private int width=16;
	private int height=16;
	public Actor(String path){
		File spriteSheetFile = new File("resources/ActorSpriteSheets/"+path+".png");
		try {
			spriteSheet = ImageIO.read(spriteSheetFile);
		} catch (IOException e) {System.out.println("Error"+(1/0));
		}
	}
	public Image getSprite(int vx,int vy){
		currentFrame += 1;
		currentFrame %= 4;
		return spriteSheet.getSubimage(currentAnim*width, currentFrame*height, width, height);
	}
}
