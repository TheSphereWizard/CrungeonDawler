package crungeonDawler;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DoorActor extends Actor{
	public BufferedImage spriteSheet;
	boolean vertical;
	public DoorActor(String path,boolean vertical){
		super(path,Game.pixelTileWidth*2,Game.pixelTileWidth, false);
		File spriteSheetFile = new File("resources/ActorSpriteSheets/"+path+".png");
		try {
			spriteSheet = ImageIO.read(spriteSheetFile);
		} catch (IOException e) {System.out.println("Error"+(1/0));
		}
		this.vertical=vertical;
	}
	int doorState=4;//0 to 7 open one way to open other way
	public Image getSprite(int vx,int vy){
		BufferedImage b = spriteSheet.getSubimage(0, ((int) doorState)*height, width, height);
		if(vertical){
			AffineTransform  t = AffineTransform.getRotateInstance(Math.PI/2,1,1);
			AffineTransformOp op = new AffineTransformOp(t,AffineTransformOp.TYPE_BILINEAR);
			return op.filter(b, null);
			//also may need to translate image
		}
		return b;
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
}
