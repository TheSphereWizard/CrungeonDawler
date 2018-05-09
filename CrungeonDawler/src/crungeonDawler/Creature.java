package crungeonDawler;
import java.util.HashMap;

import AI.AI;

public abstract class Creature extends Entity{
	private int totalLife;
	private int currentLife;
	private HashMap<String,Integer> stats;
	private int speed;
	public Creature(String name, Actor actor,AI ai) {
		super(name, actor,ai);
	}
	
}
