package crungeonDawler;

import AI.AI;

public class DoorRight extends Entity{
	public DoorRight(String name, Actor actor, AI ai) {
		super(name, actor, ai);
		this.actor.manualrotate=true;
		this.collides=false;
	}
	public void onInteract(){
		actor.theta+=Math.PI/16;
	}

}
