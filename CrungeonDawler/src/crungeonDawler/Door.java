package crungeonDawler;

import AI.NullAI;

public class Door extends Entity{
	boolean vertical;
	DoorActor actor;//to avoid casting trycatches
	public Door(boolean vertical,DoorActor d) {
		super("Door", d, new NullAI());
		actor=d;
		this.vertical=vertical;
	}
	int change=1;
	public void onInteract(Player p){
		if(vertical){
			if(p.x<this.x+16){
				actor.change=-1;
			}else{
				actor.change=1;
			}
		}else{
			if(p.y<this.y+16){
				actor.change=1;
			}else{
				actor.change=-1;
			}
		}
	}
	public void update(Player p){
		super.update(p);
		if(actor.doorState==0||actor.doorState==8){
			this.ai.collides=false;
		}else{
			this.ai.collides=true;
		}
	}
}
