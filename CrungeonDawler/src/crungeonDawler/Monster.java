package crungeonDawler;

import AI.AI;

public class Monster extends Creature{
	public Monster(String name, Actor actor,AI ai) {
		super(name, actor, ai);
		//load JSON file based on monster name
	}
}
