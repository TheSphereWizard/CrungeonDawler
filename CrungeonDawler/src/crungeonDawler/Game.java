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
	int slowdown=0;
	int slowdownfactor=6;
	void UpdateGame(Point mousePosition,boolean[] keyPressed,boolean[] mousePressed){
		rendering.UpdateGame(mousePosition,keyPressed,mousePressed);
		if(keyPressed[37/*left*/]){
			if(slowdown%slowdownfactor==0)
				tryLegalMovement(player,new int[]{-1,0});
		}
		if(keyPressed[38/*up*/]){
			if((slowdown%slowdownfactor)==0)
				tryLegalMovement(player,new int[]{0,-1});
		}
		if(keyPressed[39/*right*/]){
			if(slowdown%slowdownfactor==0)
				tryLegalMovement(player,new int[]{1,0});
		}
		if(keyPressed[40/*down*/]){
			if(slowdown%slowdownfactor==0)
				tryLegalMovement(player,new int[]{0,1});
		}
		slowdown++;
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
