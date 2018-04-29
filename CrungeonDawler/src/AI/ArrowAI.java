package AI;

import crungeonDawler.Entity;
import crungeonDawler.Player;
import crungeonDawler.Screen;

public class ArrowAI extends StraightLineAI{
	int age =0;
	Entity shooter;
	
	public ArrowAI(int vx, int vy,Behavior behaviortype, Entity shooter) {
		super(vx, vy, behaviortype);
		this.shooter=shooter;
	}
	public void oncollide(Entity self,Entity other){
		if(shooter!=other)
		try{
			Player p = (Player) other;
			if(Math.sqrt(Math.pow(p.x-self.x, 2)+Math.pow(p.y-self.y, 2))<p.getWidth()/2)
				Screen.game.removeEntityLater(self);
		}catch(Exception E){	
		}
	}
	public void updateentitiy(Entity self, Player p, Entity lastcollided, int[] directionofwall) {
		super.updateentitiy(self, p, lastcollided, directionofwall);
		if(age++>=1000){
			Screen.game.removeEntityLater(self);
		}
	}
}