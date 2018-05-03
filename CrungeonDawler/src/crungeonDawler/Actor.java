package crungeonDawler;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Actor {
	public BufferedImage spriteSheet;
	private int currentAnim=0;
	private double currentFrame=0;
	private int width=Game.pixelTileWidth;
	private int height=Game.pixelTileWidth;
	public double theta=-Math.PI/2;
	public boolean rotatable;
	public Actor(BufferedImage i,int w,int h,boolean rotatable){
		spriteSheet =i;
		width = w;
		height = h;
		this.rotatable=rotatable;
	}
	public Actor(String path,int w,int h,boolean rotatable){
		File spriteSheetFile = new File("resources/ActorSpriteSheets/"+path+".png");
		try {
			spriteSheet = ImageIO.read(spriteSheetFile);
		} catch (IOException e) {System.out.println("Error"+(1/0));
		}
		width = w;
		height = h;
		this.rotatable=rotatable;
	}
	void updateAngle(int vx,int vy){
		if(vx!=0||vy!=0)
			theta=Math.atan2(vy, vx);
	}
	public Image getSprite(int vx,int vy){
		if(!manualrotate)
			updateAngle(vx,vy);
		if(vx==0&&vy==0){
			currentFrame=0;
			return spriteSheet.getSubimage(((int) currentAnim)*width+getFacing()*width, 0, width, height);
		}
		if(rotatable){
			AffineTransform  t = AffineTransform.getRotateInstance(theta+Math.PI/2,width/2,height/2);
			AffineTransformOp op = new AffineTransformOp(t,AffineTransformOp.TYPE_BILINEAR);
			return op.filter(spriteSheet.getSubimage(0, 0, width, height), null);
		}else{
			currentFrame += .1;
			currentFrame %= 3;
			return spriteSheet.getSubimage(((int) currentAnim)*width+getFacing()*width, ((int) currentFrame)*height, width, height);
		}
	}
	boolean manualrotate=false;
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	public int getFacing(){
		return (int) (theta/Math.PI*4+4)%8;
	}
}
