package crungeonDawler;

import AI.NullAI;

public class Chest extends Entity{
	boolean vertical;
	ChestActor actor;//to avoid casting trycatches
	public Chest(ChestActor c/*Probably pass in data as to what is in the chest*/) {
		super("chest", c, new NullAI());
		actor=c;
	}
	int change=1;
	public void onInteract(Player p){
		actor.change=-actor.change;
		//later code to drop items/ open inventory of chest
	}
}
