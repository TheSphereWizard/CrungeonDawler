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
	Item(Effect e,ItemIdentity r){//maybe pass in player as owner, but not if enemies will hold items
		effect=e;
		identity=r;
	}
	public void causeEffect(Entity caster, Point Mouse){
//		Effect.dothing();???
	}
}
