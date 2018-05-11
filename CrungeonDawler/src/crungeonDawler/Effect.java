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
	public Effect(int radius, Effect effect){
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
	public void doThing(Point target) throws Exception {
		switch (type){
		case DAMAGE:
			throw new Exception("invalid effect type");
		case AOE:
			for(Entity e : Screen.game.allEntities){
				if(Math.sqrt(Math.pow(target.x-e.x-e.getWidth()/2, 2)+Math.pow(target.y-e.y-e.getHeight()/2, 2))<radius){
					doThing(e);
				}
			}
		case MODIFIER:
			throw new Exception("invalid effect type");
		case SUMMON:
			Screen.game.addEntity(summon, target.x/32,target.y/32);
		case COMPOUND:
			for(Effect e : compound){
				e.doThing(target);
			}
		}
	}
	public void doThing(Entity target) throws Exception {
		switch (type){
		case DAMAGE:
//			target.dealDamage(damage,type);
		case AOE:
			for(Entity e : Screen.game.allEntities){
				if(Math.sqrt(Math.pow(target.x-e.x-e.getWidth()/2, 2)+Math.pow(target.y-e.y-e.getHeight()/2, 2))<radius){
					doThing(e);
				}
			}
		case MODIFIER:
//			target.addModifier(modifier);
		case SUMMON:
			throw new Exception("invalid effect type");
		case COMPOUND:
			for(Effect e : compound){
				e.doThing(target);
			}
		}
		
		
	}
	
}
