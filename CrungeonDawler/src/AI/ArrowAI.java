package AI;

import crungeonDawler.Entity;
import crungeonDawler.Player;
import crungeonDawler.Screen;

public class ArrowAI extends StraightLineAI{
	int age =0;
	public ArrowAI(int vx, int vy) {
		super(vx, vy, false);
	}
	public void updateentitiy(Entity self, Player p, Entity lastcollided, int[] directionofwall) {
		super.updateentitiy(self, p, lastcollided, directionofwall);
		if(age++>=100){
			Screen.game.removeEntityLater(self);
		}
	}
}