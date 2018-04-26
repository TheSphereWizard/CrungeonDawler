package AI;

import crungeonDawler.Actor;
import crungeonDawler.Entity;
import crungeonDawler.Game;
import crungeonDawler.Monster;
import crungeonDawler.Player;
import crungeonDawler.Screen;

public class ShooterAI extends AI{
	int vx=0, vy=0, speed, range, refresh, slow=0;
	public ShooterAI(int maxspeed, int range, int refresh) {
		this.speed=maxspeed;
		this.refresh=refresh;
		this.range=range*Game.pixelTileWidth;
	}
	public void updateentitiy(Entity self, Player p, Entity lastcollided, int[] directionofwall) {
		double dist = Math.sqrt(Math.pow(p.getX()-self.getX(), 2)+Math.pow(p.getY()-self.getY(),2));
		double tempx=p.getX()/16-self.getX()/16;
		double tempy=p.getY()/16-self.getY()/16;
		double diff=Math.sqrt(Math.pow(tempx,2)+Math.pow(tempy, 2));
		if(dist<range){
			vx=-(int) (tempx*speed/diff);
			vy=-(int) (tempy*speed/diff);
			self.setVX(vx);
			self.setVY(vy);
			
		}else{
			if(dist>range){
				vx=(int) (tempx*speed/diff);
				vy=(int) (tempy*speed/diff);
				self.setVX(vx);
				self.setVY(vy);
			}else{
				self.setVX(0);
				self.setVY(0);
			}
		}
		if(Math.abs(dist-range)<Game.pixelTileWidth*2){//p.getX()-self.getX(),p.getY()-self.getY()
			if(slow==0){
				Screen.game.addEntityLater(new Monster("Arrow", new Actor("testSpriteSheetforActors2",32,32),new NullAI(),false), self.getX()/32, self.getY()/32);
			}
			slow++;
			slow%=(refresh*8);
		}
	}

}
