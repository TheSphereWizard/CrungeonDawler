package crungeonDawler;
import java.awt.Image;

import AI.AI;

public abstract class Entity {
	Actor actor;
	String name;
	int x,y;
	int vx,vy;
	AI ai;
	int[] invalidtiles = new int[]{0,LevelLayout.wallID,LevelLayout.lowwallID};
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
	public void setVX(int vx){
		this.vx = vx;
	}
	public void setVY(int vy){
		this.vy = vy;
	}
	public void update(Player p, Entity lastcollided, int[] directionofwall){
		ai.updateentitiy(this,p,lastcollided, directionofwall);
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
