package crungeonDawler;

import AI.NullAI;

public class Player extends Creature{
	public Player(String name, Actor actor) {
		super(name, actor,new NullAI());
	}	
}
