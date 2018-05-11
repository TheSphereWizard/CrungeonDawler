package AI;

import java.awt.Point;

import crungeonDawler.Entity;
import crungeonDawler.Player;
import ui.Screen;

public class StraightLineAI extends AI{
	int vx, vy;
	int age=0;
	int prevx=0,prevy=0;
	public StraightLineAI(int vx, int vy){
		this.vx=vx;
		this.vy=vy;
	}
	public void updateentitiy(Entity self, Player p) {
		self.setVX(vx);
		self.setVY(vy);
		if(age++>=1000||(prevx==self.x&prevy==self.y)){
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
