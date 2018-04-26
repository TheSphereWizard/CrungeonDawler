package crungeonDawler;

import AI.AI;
import AI.ArrowAI;

public class Monster extends Creature{
	public Monster(String name, Actor actor,AI ai) {
		super(name, actor, ai);
		//load JSON file based on monster name
	}

	public Monster(String name, Actor actor,AI ai, boolean b) {
		super(name,actor,ai,b);
	}
}
