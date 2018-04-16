package crungeonDawler;

import java.awt.Point;

public class Game {
	Level currentlevel;
	GameRendering rendering;
	Player player;
	Game(Player p){
		player=p;
		currentlevel =new Level(100,100,"testSpriteSheetforActors");
		rendering = new GameRendering(p,currentlevel);
		rendering.allEntities.add(p);
	}
	void UpdateGame(Point mousePosition,boolean[] keyPressed,boolean[] mousePressed){
		rendering.UpdateGame(mousePosition);
		if(keyPressed[37/*left*/]){
			tryLegalMovement(player,new int[]{-1,0});
		}
		if(keyPressed[38/*up*/]){
			tryLegalMovement(player,new int[]{0,-1});
		}
		if(keyPressed[39/*right*/]){
			tryLegalMovement(player,new int[]{1,0});	
		}
		if(keyPressed[40/*down*/]){
			tryLegalMovement(player,new int[]{0,1});
		}
	}
	void tryLegalMovement(Entity e, int[] translation){
		if(legalMovement(e, translation)){
			e.x+=translation[0];
			e.y+=translation[1];
		}
	}
	boolean legalMovement(Entity e, int[] translation){
		try{
			if(!contains(e.invalidtiles,currentlevel.levellayout[e.x+translation[0]][e.getY()+translation[1]])){
				return true;
			}
		}catch(IndexOutOfBoundsException error){
			
		}
		return false;
	}
	boolean contains(int[] r,int w){
		for(int i : r){
			if (w==i){
				return true;
			}
		}
		return false;
	}
}
