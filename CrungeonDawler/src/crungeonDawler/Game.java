package crungeonDawler;

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
}
