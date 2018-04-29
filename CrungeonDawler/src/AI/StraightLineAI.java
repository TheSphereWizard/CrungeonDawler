package AI;

import crungeonDawler.Entity;
import crungeonDawler.Player;

public class StraightLineAI extends AI{
	int vx, vy;
	Behavior behaviortype;
	public static enum Behavior{REFLECT, SLIDE, STICK}
	public StraightLineAI(int vx, int vy, Behavior behaviortype){
		this.vx=vx;
		this.vy=vy;
		this.behaviortype=behaviortype;
	}
	public void updateentitiy(Entity self, Player p, Entity lastcollided, int[] directionofwall) {
		if(!directionofwall.equals(new int[]{0,0})){
			switch (behaviortype) {
			case REFLECT:
				//reflect (currently broken somewhat), lasers and bouncy balls
				if(directionofwall[0]!=0){
					vx=-vx;
				}
				if(directionofwall[1]!=0){
					vy=-vy;
				}
			case SLIDE:
				//slides along wall but doesn't reflect, like fire from a flamethrower
				
				if(directionofwall[0]!=0){
					vx=0;
				}
				if(directionofwall[1]!=0){
					vy=0;
				}
			case STICK:
				//sticks in wall if it hits, like arrow or spear
//				if(directionofwall[0]!=0||directionofwall[1]!=0){
//					vx=0;
//					vy=0;
//				}
			}
		}
		self.setVX(vx);
		self.setVY(vy);
	}
	
}
