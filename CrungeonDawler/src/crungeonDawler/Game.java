package crungeonDawler;

import java.awt.Point;
import java.util.ArrayList;

public class Game {
	Level currentlevel;
	GameRendering rendering;
	Player player;
	public ArrayList<Entity> allEntities =new ArrayList<Entity>();
	final int TILE_SIZE = 16;
	Game(Player p){
		player=p;
		currentlevel =new Level(100,100,"testSpriteSheetforActors");
		rendering = new GameRendering(p,currentlevel,this);
		allEntities.add(p);
	}
	int slowdown=0;
	int slowdownfactor=1;
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
	boolean legalMovement(Entity e, int[] t){
		int xMin = (int) Math.floor((double)(e.getX()+t[0])/TILE_SIZE);
		int xMax = (int) Math.ceil ((double)(e.getX()+t[0]+e.getWidth ())/TILE_SIZE);
		int yMin = (int) Math.floor((double)(e.getY()+t[1])/TILE_SIZE);
		int yMax = (int) Math.ceil ((double)(e.getY()+t[1]+e.getHeight())/TILE_SIZE);
		if(!Read.contains(e.invalidtiles,currentlevel.levellayout[xMin][yMin])){
			
		}
		return true;
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
