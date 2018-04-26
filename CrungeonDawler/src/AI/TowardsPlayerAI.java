package AI;

import crungeonDawler.Entity;
import crungeonDawler.Player;

public class TowardsPlayerAI extends AI{
	int vx=0, vy=0, speed;
	public TowardsPlayerAI(int speed){
		this.speed=speed;
	}
	public void updateentitiy(Entity self, Player p, Entity lastcollided, int[] directionofwall) {
		double tempx=p.getX()/16-self.getX()/16;
		double tempy=p.getY()/16-self.getY()/16;
		double diff=Math.sqrt(Math.pow(tempx,2)+Math.pow(tempy, 2));
		vx=(int) (tempx*speed/diff);
		vy=(int) (tempy*speed/diff);
		self.setVX(vx);
		self.setVY(vy);
	}
}
