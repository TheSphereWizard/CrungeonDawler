package AI;

import crungeonDawler.Entity;
import crungeonDawler.Player;

public abstract class AI {
	public abstract void updateentitiy(Entity self,Player p, Entity lastcollided, int[] directionofwall);

	public void oncollide(Entity self,Entity other) {}
}
