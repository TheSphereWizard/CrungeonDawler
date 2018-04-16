package crungeonDawler;
//import com.google.gson.*;

public class Effect {
	static enum Type{DAMAGE,AOE,MODIFIER,SUMMON,COMPOUND};
	private Type type;
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
	
}
