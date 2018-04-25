package AI;

import crungeonDawler.Entity;
import crungeonDawler.Player;

public class StraightLineAI extends AI{
	int vx, vy;
	boolean reflect;
	public StraightLineAI(int vx, int vy, boolean reflect){
		this.vx=vx;
		this.vy=vy;
		this.reflect=reflect;
	}
	public void updateentitiy(Entity self, Player p, Entity lastcollided, int[] directionofwall) {
		if(reflect&&!directionofwall.equals(new int[]{0,0})){
			if(directionofwall[0]!=0){
				vx=-vx;
			}
			if(directionofwall[1]!=0){
				vy=-vy;
			}
		}
		self.setVX(vx);
		self.setVY(vy);
	}
	
}
