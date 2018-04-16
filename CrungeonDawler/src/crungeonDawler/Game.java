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
			player.x--;
		}
		if(keyPressed[38/*up*/]){
			player.y--;
		}
		if(keyPressed[39/*right*/]){
			player.x++;		
		}
		if(keyPressed[40/*down*/]){
			player.y++;
		}
	}
}
