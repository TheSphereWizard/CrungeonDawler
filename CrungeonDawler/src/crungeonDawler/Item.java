package crungeonDawler;

import java.awt.Point;
import java.util.ArrayList;

public class Item {
	//player should have an inventory... holds items 
	//needs to have the ability to select items, need more UI for that
	
	ArrayList<Modifier> modifiers;
	ArrayList<Gem> gems;
	Effect effect;
	ItemIdentity identity;
	Item(Actor a, Effect e, ItemIdentity r){
		effect=e;
		identity=r;
		//the Effect is the Item, aka a bow will have an effect that summons an arrow the arrow is an entity defined in the effect
	}
	public void causeEffect(Entity caster, Point Mouse){
		try {
			effect.doThing(Mouse);//need something that allows modifiers to affect the effect.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
