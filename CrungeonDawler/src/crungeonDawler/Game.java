package crungeonDawler;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import AI.ArrowAI;
import AI.NullAI;
import AI.ShooterAI;
import AI.StraightLineAI;
import ui.Screen;

public class Game {
	Level currentLevel;
	Player player;
	public ArrayList<Entity> allEntities =new ArrayList<Entity>();
	public static final int TILE_SIZE =32;
	public static final int renderdist = 15;
	private Image tileSet;
	private Image decorationSet;
	public Game(Player p){
		player=p;
		currentLevel =new Level(200,200,"Tiles");
		Read.outputroom(currentLevel.levellayout, "map output");
		addEntity(p,105,100);
		p.vx=0;
		p.vy=0;
		currentLevel.spawnMobs();
		/*for(int[] r :currentLevel.spawnmobs()){
//			addEntity(new Monster("test",new Actor("testSpriteSheetforActors2",pixelTileWidth,pixelTileWidth), new StraightLineAI((int) (Math.random()*4-1),(int) (Math.random()*4-1),true)),r[0],r[1]);
//			addEntity(new Monster("test",new Actor("testSpriteSheetforActors2",pixelTileWidth,pixelTileWidth), new WanderingAI((int) (Math.random()*4-1),(int) (Math.random()*4-1),3)),r[0],r[1]);
//			addEntity(new Monster("test",new Actor("testSpriteSheetforActors2",pixelTileWidth,pixelTileWidth), new TowardsPlayerAI((int) (Math.random()*4-1),(int) (Math.random()*4-1),3)),r[0],r[1]);
			Entity arrow=new Monster("Arrow", new Actor("arrow",32,32,true),new ArrowAI(0,0,StraightLineAI.Behavior.REFLECT,null),false);
			if(Math.random()*2<1)
				addEntity(new Monster("test",new Actor("bulbasor3",pixelTileWidth,pixelTileWidth,false), new ShooterAI(3,5,30,arrow)),r[0],r[1]);
			else
				addEntity(new Monster("test",new Actor("bulbasor3",pixelTileWidth,pixelTileWidth,false), new ShooterAI(3,5,30,arrow)),r[0],r[1]);
		}*/	
		for(int[] r :currentLevel.spawnDoors()){
			if(r[2]==0){
				addEntity(new Door(false,new DoorActor("Door",false)),r[0],r[1]);
			}else{
				Entity e =new Door(true,new DoorActor("Door",true));
				addEntity(e,r[0],r[1]);
				e.x-=16;
			}
		}
		addEntity(new Chest(new ChestActor("testChest")),107,100);
	}
	public void DrawMenu(Graphics2D g2d, Point mousePosition) {
		Draw(g2d,mousePosition);
		g2d.translate(-Screen.frameWidth/2,-Screen.frameHeight/2);//in future don't translate
		g2d.setColor(new Color(0,0,0,128));
		g2d.fillRect(0,0,Screen.frameWidth,Screen.frameHeight);
		g2d.setColor(new Color(75,75,75,255));
		g2d.fillRect(Screen.frameWidth/3,Screen.frameHeight/3,Screen.frameWidth/3,Screen.frameHeight/3);
	}
	
	public void Draw(Graphics2D g2d, Point mousePosition) {
		BufferedImage dungeon = new BufferedImage(TILE_SIZE*renderdist*2,TILE_SIZE*renderdist*2,BufferedImage.TYPE_4BYTE_ABGR); 
		for(int x=Math.max(player.getX()/TILE_SIZE-renderdist,0);x<currentLevel.width&&x<player.getX()/TILE_SIZE+renderdist+1;x++){
			for(int y=Math.max(player.getY()/TILE_SIZE-renderdist,0);y<currentLevel.height&&y<player.getY()/TILE_SIZE+renderdist+1;y++){
				dungeon.getGraphics().drawImage(getImageFromTileID(currentLevel.levellayout[x][y]),x*TILE_SIZE-player.getX()-TILE_SIZE/2+dungeon.getWidth()/2,y*TILE_SIZE-player.getY()-TILE_SIZE/2+dungeon.getHeight()/2, TILE_SIZE, TILE_SIZE,null);
			}
		}
		
		for(int i=0;i<allEntities.size();i++){
			Entity e = allEntities.get(i);
			if(Math.abs(e.getX()-player.getX())<renderdist*TILE_SIZE&& Math.abs(e.getY()-player.getY())<renderdist*TILE_SIZE){
				dungeon.getGraphics().drawImage(e.getSprite(), (e.getX()-player.getX()-TILE_SIZE/2)+dungeon.getWidth()/2, (e.getY()-player.getY()-TILE_SIZE/2)+dungeon.getHeight()/2, null);
			}
		}
//		int playerTileX = ((player.getX())/pixelTileWidth)*pixelTileWidth+dungeon.getWidth()/2-16;
//		int playerTileY = ((player.getY())/pixelTileWidth)*pixelTileWidth+dungeon.getHeight()/2-16;
//		Graphics g = dungeon.getGraphics();
//		g.setColor(Color.PINK);
//		g.fillRect(playerTileX-player.getX(), playerTileY-player.getY(), 32, 32);
//		g.setColor(Color.BLACK);
//		g.drawString("E", playerTileX-player.getX(), playerTileY-player.getY());

		g2d.drawImage(dungeon, (Screen.frameWidth-dungeon.getWidth())/2,(Screen.frameHeight-dungeon.getHeight())/2,null);
		g2d.setColor(Color.RED);
		g2d.drawString(String.valueOf(getCurrentRoom()), 20, 20);
	}
	public int getCurrentRoom() {
		return currentLevel.roomids[player.getX()/TILE_SIZE][player.getY()/TILE_SIZE];
	}
	

	private Image getImageFromTileID(int id) {
		if(id==LevelLayout.voidID){
			return currentLevel.Void();
		}
		if(id==LevelLayout.wallID){
			return currentLevel.Wall();
		}
		if(id==LevelLayout.floorID){
			return currentLevel.Floor();
		}
		if(id==LevelLayout.lowwallID){
			return currentLevel.lowWall();
		}
		return null;
	}

	public void DrawGameOver(Graphics2D g2d, Point mousePosition) {

	}

	public void loadMapKit(String mapFile) {

	}
	public void loadTileset(String path) {
		URL tileSetUrl = this.getClass().getResource("/resources/images/"+path+".png");
		try {
			tileSet = ImageIO.read(tileSetUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void loadDecorations(String path) {
		URL decorationSetUrl = this.getClass().getResource("/resources/images/"+path+".png");
		try {
			decorationSet = ImageIO.read(decorationSetUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	boolean arraycontains(int[][] r,int w[]){
		for(int[] i : r){
			if (w.equals(i)){
				return true;
			}
		}
		return false;
	}

	public void addEntity(Entity e, int x, int y){
		e.x=x*TILE_SIZE;
		e.y=y*TILE_SIZE;
		allEntities.add(e);
	}
//	private ArrayList<Entity> lateradd = new ArrayList<Entity>();
//	public void addEntityLater(Entity e, int x, int y){
//		e.x=x*pixelTileWidth;
//		e.y=y*pixelTileWidth;
//		lateradd.add(e);
//	}
//	private void addLaterEntities(){
//		for(Entity e :lateradd){
//			allEntities.add(e);
//		}
//		lateradd.clear();
//	}
	private ArrayList<Entity> laterremove = new ArrayList<Entity>();
	public void removeEntityLater(Entity e){
		laterremove.add(e);
	}
	private void removeLaterEntities(){
		for(Entity e :laterremove){
			allEntities.remove(e);
		}
		laterremove.clear();
	}
	
	int keyslowdown=0;
	int keyslowdownfactor=1;
	int mouseslowdown=0;
	int mouseslowdownfactor=10;
	int interactslowdown=0;
	int interactslowdownfactor=40;
	public void UpdateGame(Point mousePosition,boolean[] keyPressed,boolean[] mousePressed){
		for(int i=0;i<allEntities.size();i++){
			Entity e = allEntities.get(i);
			e.update(player, null, abouttocollide(e));
			tryLegalMovement(e,new int[]{0,e.vy});
			tryLegalMovement(e,new int[]{e.vx,0});
		}
//		addLaterEntities();
		removeLaterEntities();
		player.vx=0;
		player.vy=0;
		if(keyPressed[65/*left*/]){
			if(keyslowdown%keyslowdownfactor==0)
				player.vx-=4;
		}
		if(keyPressed[87/*up*/]){
			if((keyslowdown%keyslowdownfactor)==0)
				player.vy-=4;
		}
		if(keyPressed[68/*right*/]){
			if(keyslowdown%keyslowdownfactor==0)
				player.vx+=4;
		}
		if(keyPressed[83/*down*/]){
			if(keyslowdown%keyslowdownfactor==0)
				player.vy+=4;
		}
		if(keyPressed[81/*Q*/]){
			if(interactslowdown%interactslowdownfactor==1){
				for(int i=0;i<allEntities.size();i++){
					int[] cen = new int[]{player.x+Game.TILE_SIZE/2,player.y+Game.TILE_SIZE/2};
					Entity e = allEntities.get(i);
					if(e.x-cen[0]<10&&cen[0]-(e.x+e.getWidth())<10&&e.y-cen[1]<10&&cen[1]-(e.y+e.getHeight())<10){
						e.onInteract(player);
					}
				}
			}
		}else{
			interactslowdown=0;
		}
		keyslowdown++;
		mouseslowdown++;
		interactslowdown++;
		if(mousePressed[0]){
			if(mouseslowdown%mouseslowdownfactor==1)
				addEntity(new Monster("PlayerArrow", new Actor("arrow",32,32,true),new ArrowAI((mousePosition.x-Screen.frameWidth/2)/20,(mousePosition.y-Screen.frameHeight/2)/20,StraightLineAI.Behavior.REFLECT,player),false),player.x/32,player.y/32);
		}else{
			mouseslowdown=0;
		}
		
	}
	private int[] abouttocollide(Entity e) {
		//This whole method is terrible and should be rewritten
		int[] ret = new int[]{0,0};
		int xMin = (int) Math.floor((double)(e.getX()+e.vx)/TILE_SIZE);
		int xMax = (int) Math.ceil ((double)(e.getX()+e.vx+e.getWidth ()-TILE_SIZE)/TILE_SIZE);
		int yMin = (int) Math.floor((double)(e.getY()+e.vy)/TILE_SIZE);
		int yMax = (int) Math.ceil ((double)(e.getY()+e.vy+e.getHeight()-TILE_SIZE)/TILE_SIZE);
		for(int x=xMin;x<=xMax;x++){
			for(int y=yMin;y<=yMax;y++){
				try{
					if(Read.contains(e.invalidtiles, currentLevel.levellayout[x][y])){
						ret[0]+=x;
						ret[1]+=y;
					}
				}
				catch(Exception ex){
					ret[0]+=x;
					ret[1]+=y;
				}
			}
		}
//		for(Entity other : allEntities)
//			if(other!=e)
//				if(Math.abs(e.x+e.vx-other.x)<pixelTileWidth&Math.abs(e.y+e.vy-other.y)<pixelTileWidth){
//					if(e.x-other.x<e.y-other.y)
//						ret[0]+=1;
//					else
//						ret[1]+=1;
//				}
		return ret;
	}
	void tryLegalMovement(Entity e, int[] translation){
		if(legalMovement(e, translation)){
			e.x+=translation[0];
			e.y+=translation[1];
		}
	}
	boolean legalMovement(Entity e, int[] t){
		int xMin = (int) Math.floor((double)(e.getX()+t[0])/TILE_SIZE);
		int xMax = (int) Math.ceil ((double)(e.getX()+t[0]+e.getWidth ()-TILE_SIZE)/TILE_SIZE);
		int yMin = (int) Math.floor((double)(e.getY()+t[1])/TILE_SIZE);
		int yMax = (int) Math.ceil ((double)(e.getY()+t[1]+e.getHeight()-TILE_SIZE)/TILE_SIZE);
		for(int x=xMin;x<=xMax;x++){
			for(int y=yMin;y<=yMax;y++){
				try{
					if(Read.contains(e.invalidtiles, currentLevel.levellayout[x][y])){
						return false;
					}
				}
				catch(Exception ex){
					return false;
				}
			}
		}
		for(Entity other : allEntities)
			if(other!=e){
				boolean isnotdoor=false;//You are a door until proven otherwise
				try{
					Door door=(Door)other;
					if(door.vertical){
						if (!(e.x+t[0]+e.getWidth()/4<other.x+16 || other.x+16+other.getWidth()/4<e.x+t[0] || e.y+t[1]+e.getHeight()/2<other.y || other.y+other.getHeight()/2<e.y+t[1])){
							if(e.collides&other.collides)
								return false;
						}
					}else{
						if (!(e.x+t[0]+e.getWidth()/2<other.x+16 || other.x+16+other.getWidth()/2<e.x+t[0] || e.y+t[1]+e.getHeight()/4<other.y+16 || other.y+16+other.getHeight()/4<e.y+t[1])){
							if(e.collides&other.collides)
								return false;
						}
					}
					
				}catch(Exception E){isnotdoor=true;}
				if(isnotdoor){
					if (!(e.x+t[0]+e.getWidth()<other.x || other.x+other.getWidth()<e.x+t[0] || e.y+t[1]+e.getHeight()<other.y || other.y+other.getHeight()<e.y+t[1])){
						if(e.collides&other.collides)
							return false;
						e.oncollide(other);
					}
				}
			}
		return true;
	}
}
