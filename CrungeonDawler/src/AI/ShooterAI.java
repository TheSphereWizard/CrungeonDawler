package AI;

import java.lang.reflect.Constructor;

import crungeonDawler.Actor;
import crungeonDawler.Effect;
import crungeonDawler.Entity;
import crungeonDawler.Game;
import crungeonDawler.Monster;
import crungeonDawler.Player;
import ui.Screen;

public class ShooterAI extends AI{
	int vx=0, vy=0, speed, range, refresh, slow=0;
	Entity projectile;
	Effect effect=new Effect(0);//needs to be passed somehow in constructor
	public ShooterAI(int maxspeed, int range, int refresh,Entity projectile) {
		this.speed=maxspeed;
		this.refresh=refresh;
		this.range=range*Game.TILE_SIZE;
		this.projectile = projectile;
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
			if(dist>range+5&dist<range*3){
				vx=(int) (tempx*speed/diff);
				vy=(int) (tempy*speed/diff);
				self.setVX(vx);
				self.setVY(vy);
			}else{
				self.setVX(0);
				self.setVY(0);
			}
		}
		if(Math.abs(dist-range)<Game.TILE_SIZE*2){//
			if(slow==0){
				Class<? extends Entity> clazz = projectile.getClass();
				try {
					Constructor<?> ctor = clazz.getConstructor(String.class,Actor.class,AI.class,boolean.class);
					Entity e = projectile;
					ArrowAI r = (ArrowAI)projectile.ai;
					
					Entity newprojectile = (Entity) ctor.newInstance(self.name+"'s projectile",new Actor(projectile.actor.spriteSheet,projectile.getWidth(),projectile.getHeight(),projectile.actor.rotatable),new ArrowAI((p.getX()+8-self.getX())/16,(p.getY()+8-self.getY())/16,self),false);
					Screen.game.addEntity(newprojectile, self.getX()/32, self.getY()/32);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			slow++;
			slow%=(refresh*2);
		}
	}
	public void updateentitiy(Entity self, Player p) {}
}
