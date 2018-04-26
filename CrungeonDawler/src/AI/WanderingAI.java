package AI;

import crungeonDawler.Entity;
import crungeonDawler.Player;

public class WanderingAI extends AI{
	int vx, vy, speed;
	public WanderingAI(int vx, int vy, int speed){
		this.vx=vx;
		this.vy=vy;
		this.speed=speed;
	}
	public void updateentitiy(Entity self, Player p, Entity lastcollided, int[] directionofwall) {
		vx+=(Math.random()*5-2.5d);
		vy+=(Math.random()*5-2.5d);
		double diff=Math.sqrt(Math.pow(vx,2)+Math.pow(vy, 2));
		vx=(int) (vx*speed/diff);
		vy=(int) (vy*speed/diff);
		self.setVX(vx);
		self.setVY(vy);
	}

}
