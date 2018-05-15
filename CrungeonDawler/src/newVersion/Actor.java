package newVersion;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Actor {
	BufferedImage spriteSheet;
	Integer width=32;
	Integer height=32;
	Integer currentFrame=0;
	Integer currentAnim=0;
	Boolean rotateable;
	boolean manualrotate=false;
	double theta=0;
	public Actor(String path,int w,int h,boolean rotateable) {
		File spriteSheetFile = new File("resources/actorSpriteSheets/"+path+".png");
		try {
			spriteSheet = ImageIO.read(spriteSheetFile);
		} catch (IOException e) {e.printStackTrace();}
		width  = w;
		height = h;
		this.rotateable=rotateable;
	}
	public int getFacing(){
		return (int) (theta/Math.PI*4+4)%8;
	}
	void updateAngle(int vx,int vy){
		if(vx!=0||vy!=0)
			theta=Math.atan2(vy, vx);
	}
	int slow=0;
	int slowdownfactor=10;
	public Image getSprite(Integer vx,Integer vy) {
		if(!currentAnim.equals(-1)) {
			if(!manualrotate){
				updateAngle(vx,vy);
			}
			if(rotateable){
				AffineTransform  t = AffineTransform.getRotateInstance(theta+Math.PI/2,width/2,height/2);
				AffineTransformOp op = new AffineTransformOp(t,AffineTransformOp.TYPE_BILINEAR);
				return op.filter(spriteSheet.getSubimage(0, 0, width, height), null);
			}else{
				if(vx==0&&vy==0){
					currentFrame=0;
					return spriteSheet.getSubimage(((int) currentAnim)*width+getFacing()*width, 0, width, height);
				}
				slow++;
				if(slow%slowdownfactor==0) {
					currentFrame ++;
					slow=0;
				}
				currentFrame %= 3;
				if(vx==0&&vy==0){
					currentFrame=0;
					return spriteSheet.getSubimage(((int) currentAnim)*width+getFacing()*width, 0, width, height);
				}
				return spriteSheet.getSubimage(((int) currentAnim)*width+getFacing()*width, ((int) currentFrame)*height, width, height);
			}
		}
		return spriteSheet.getSubimage(0, 0, width, height);
	}
}
