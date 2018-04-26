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
	private double currentFrame=0;
	private int width=Game.pixelTileWidth;
	private int height=Game.pixelTileWidth;
	public Actor(String path,int w,int h){
		File spriteSheetFile = new File("resources/ActorSpriteSheets/"+path+".png");
		try {
			spriteSheet = ImageIO.read(spriteSheetFile);
		} catch (IOException e) {System.out.println("Error"+(1/0));
		}
		width = w;
		height = h;
	}
	public Image getSprite(int vx,int vy){
		currentFrame += .1;
		currentFrame %= 4;
		return spriteSheet.getSubimage(((int) currentAnim)*width, ((int) currentFrame)*height, width, height);
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
}
