package AI;

import java.awt.Point;

import crungeonDawler.Effect;
import crungeonDawler.Entity;
import crungeonDawler.Player;
import ui.Screen;

public class ProjectiletoPointAI extends AI{
	int v;
	Point t;
	int age = 0;
	Effect effect;
	public ProjectiletoPointAI(int v,Point Target,Effect e){
		t=Target;
		this.v=v;
		this.collides=false;
		this.slide=false;
		this.effect=e;
	}
	public ProjectiletoPointAI(int v,int[] Target){
		t=new Point(Target[0],Target[1]);
		this.v=v;
		this.collides=false;
		this.slide=false;
	}
	public void oncollide(Entity self,Entity other){
		//self.Run Death Effect
	}
	int prevx=0,prevy=0;
	public void updateentitiy(Entity self, Player p, Entity lastcollided, int[] directionofwall) {
		double dist= Math.sqrt(Math.pow(t.y-self.y, 2)+Math.pow(t.x-self.x, 2));
		self.vx=(int) (v*(t.x-self.x)/dist);
		self.vy=(int) (v*(t.y-self.y)/dist);
		//Temp code because direction of wall doesn't function
		if(dist<v||age++>=1000||(prevx==self.x&prevy==self.y)){
			effect.doThing(self,null,null);
			Screen.game.removeEntityLater(self);
		}else{
			prevx=self.x;
			prevy=self.y;
		}
	}
}
