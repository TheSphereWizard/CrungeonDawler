package crungeonDawler;
import java.awt.Image;

import AI.AI;
import AI.NullAI;

public abstract class Entity {
	public Actor actor;
	public String name;
	public int x, y;
	public int vx,vy;
	public AI ai;
	int[] invalidtiles = new int[]{0,LevelLayout.wallID,LevelLayout.lowwallID};
	public Effect onbirth,oncollide, ondeath;
	public Entity(String name, Actor actor,AI ai){
		this.name = name;
		this.actor = actor;
		this.ai= ai;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getFacing(){
		return actor.getFacing();
	}
	public void setVX(int vx){
		this.vx = vx;
	}
	public void setVY(int vy){
		this.vy = vy;
	}
	public void update(Player p){
		ai.updateentitiy(this,p);
	}
	public void oncollide(Entity other){
		ai.oncollide(this,other);
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
	public void onInteract(Player p) {
		ai.onInteract(p);
	}
}
