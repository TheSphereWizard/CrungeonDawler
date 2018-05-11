package AI;

import crungeonDawler.Entity;
import crungeonDawler.Player;

public abstract class AI {
	public boolean collides=true;
	public boolean slide = true;//if true This checks vx and vy changes separately, else only checks together
	public abstract void updateentitiy(Entity self,Player p);

	public void oncollide(Entity self,Entity other) {}

	public void onInteract(Player p) {}
}
