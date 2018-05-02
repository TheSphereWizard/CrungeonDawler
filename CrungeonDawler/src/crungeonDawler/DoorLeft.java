package crungeonDawler;

import AI.AI;

public class DoorLeft extends Entity{
	public DoorLeft(String name, Actor actor, AI ai) {
		super(name, actor, ai);
		this.actor.manualrotate=true;
	}
	public void onInteract(){
		actor.theta-=Math.PI/4;
	}

}
