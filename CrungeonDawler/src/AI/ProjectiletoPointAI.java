package AI;

import java.awt.Point;
import crungeonDawler.Entity;
import crungeonDawler.Player;
import ui.Screen;

public class ProjectiletoPointAI extends AI{
	int v;
	Point t;
	int age = 0;
	int prevx=0,prevy=0;
	public ProjectiletoPointAI(int v,Point Target){
		t=Target;
		this.v=v;
		this.collides=false;
		this.slide=false;
	}
	public void oncollide(Entity self,Entity other){
		try {
			self.oncollide.doThing(new Point(self.x,self.y));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateentitiy(Entity self, Player p) {
		double dist= Math.sqrt(Math.pow(t.y-self.y, 2)+Math.pow(t.x-self.x, 2));
		self.vx=(int) (v*(t.x-self.x)/dist);
		self.vy=(int) (v*(t.y-self.y)/dist);
		//Temp code because direction of wall doesn't function
		if(dist<v||age++>=1000||(prevx==self.x&prevy==self.y)){
			try {
				self.ondeath.doThing(new Point(self.x,self.y));
			} catch (Exception e) {
				e.printStackTrace();
			}
			Screen.game.removeEntityLater(self);
		}else{
			prevx=self.x;
			prevy=self.y;
		}
	}
}
