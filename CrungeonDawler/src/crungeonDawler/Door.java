package crungeonDawler;

import AI.NullAI;

public class Door extends Entity{
	boolean vertical;
	public Door(boolean vertical) {
		super("Door", new DoorActor("Door",vertical), new NullAI());
		this.vertical=vertical;
		this.collides=false;
	}
}
