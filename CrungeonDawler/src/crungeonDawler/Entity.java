package crungeonDawler;
import java.awt.Image;

public abstract class Entity {
	Actor actor;
	String name;
	int x,y;
	int vx,vy;
	int[] invalidtiles = new int[]{0,14};
	public Entity(String name, Actor actor){
		this.name = name;
		this.actor = actor;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public void updateXY(double tStep){
		this.x += vx*tStep;
		this.y += vy*tStep;
	}
	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
	}
	public void setVX(int vx){
		this.vx = vx;
	}
	public void setVY(int vy){
		this.vy = vy;
	}
	public Image getSprite(){
		return actor.getSprite(vx, vy);
	}
	public int getWidth(){
		return actor.getWidth();
	}
	public int getHeight(){
		return actor.getHeight();
	}
}
