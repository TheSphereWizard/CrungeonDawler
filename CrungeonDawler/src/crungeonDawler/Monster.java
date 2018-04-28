package crungeonDawler;

import AI.AI;

public class Monster extends Creature{
	public Monster(String name, Actor actor,AI ai) {
		super(name, actor, ai);
	}

	public Monster(String name, Actor actor,AI ai, boolean b) {
		super(name,actor,ai,b);
	}
}
