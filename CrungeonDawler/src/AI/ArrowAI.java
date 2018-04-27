package AI;

import crungeonDawler.Actor;
import crungeonDawler.Entity;
import crungeonDawler.Player;
import crungeonDawler.Screen;

public class ArrowAI extends StraightLineAI{
	int age =0;
	Entity shooter;
	public ArrowAI(int vx, int vy,Entity shooter) {
		super(vx, vy, false);
		this.shooter=shooter;
	}
	public void oncollide(Entity self,Entity other){
		try{
			Player p = (Player) other;
//			other.vx*=.5;
//			other.vy*=.5;
//			other.setVX(other.vx+self.vx/5);
//			other.setVY(other.vy+self.vy/5);
			Screen.game.removeEntityLater(self);
		}catch(Exception E){
//			if(other!=shooter){
//				Screen.game.removeEntityLater(self);
//				Screen.game.removeEntityLater(other);
//			}		
		}
	}
	public void updateentitiy(Entity self, Player p, Entity lastcollided, int[] directionofwall) {
		super.updateentitiy(self, p, lastcollided, directionofwall);
		if(age++>=200){
			Screen.game.removeEntityLater(self);
		}
	}
}