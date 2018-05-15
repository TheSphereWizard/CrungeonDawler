package newVersion;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Actor {
	BufferedImage spriteSheet;
	Integer width;
	Integer height;
	Integer currentFrame;
	Integer currentAnim;
	Boolean rotateable;
	public Actor(String path,int w,int h,boolean rotateable) {
		File spriteSheetFile = new File("resources/actorSpriteSheets/"+path+".png");
		try {
			spriteSheet = ImageIO.read(spriteSheetFile);
		} catch (IOException e) {e.printStackTrace();}
		width = w;
		height = h;
	}
	public Image getSprite(Integer vx,Integer vy) {
		if(currentAnim.equals(-1)) {
			if(rotateable) {
				
			}
		}
		return spriteSheet.getSubimage(0, 0, width, height);
	}
}
