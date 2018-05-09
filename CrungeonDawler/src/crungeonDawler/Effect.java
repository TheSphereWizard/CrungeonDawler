package crungeonDawler;
//import com.google.gson.*;

import java.awt.Point;

import ui.Screen;

public class Effect {
	static enum Type{DAMAGE,AOE,MODIFIER,SUMMON,COMPOUND};
	private static Type type;
	private int damage;
	private int radius;
	private Effect effect;
	private Modifier modifier;
	private int numSummon;
	private Entity summon;
	private Effect[] compound;
	public Effect(int damage){
		this.damage = damage;
		type = Type.DAMAGE;
	}
	public Effect(int radius, Effect effect){//test
		this.radius = radius;
		this.effect = effect;
		type = Type.AOE;
	}
	public Effect(Modifier modifier){
		this.modifier = modifier;
		type = Type.MODIFIER;
	}
	public Effect(int numSummon, Entity summon){
		this.numSummon = numSummon;
		this.summon = summon;
		type = Type.SUMMON;
	}
	public Effect(Effect[] compound){
		this.compound = compound;
		type = Type.COMPOUND;
	}
	public void doThing(Entity caster, Point mouse, Entity target) {
		//Really weird:
		//Method should run on interact, when proj hits target
		//Should be called when it hits target point (need new AI for this)
		//so will not need mouse as will simply call on death of projectile
		switch (type){
		case SUMMON:
			System.out.println(caster.x+" "+caster.y);
			Screen.game.addEntity(summon, caster.x/32,caster.y/32);
		}
	}
	
}
