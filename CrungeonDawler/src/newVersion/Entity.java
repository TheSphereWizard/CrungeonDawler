package newVersion;

import java.awt.Point;

public class Entity {
	Actor actor;
	int lifetime;
	int counter = 0;
	int dx;
	int dy;
	Point position;
	public void update(){
		counter++;
		if(counter == lifetime){
			destroy();
		}
	}
	public void destroy(){
	
	}
	public int[] getMove(Point target){
		int[] a = {1,1};
		return a;
	}
	public Actor getActor(){
		return actor;
	}
}
