package newVersion;


import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Game {
	public static final int TILE_SIZE = 32;
	public int width  = 800;
	public int height = 600;
	public Point screenCenter;
	ArrayList<Entity> entities = new ArrayList<Entity>();
	Entity player;
	Level level;
	ArrayList<int[]> moves;
	ContentManager manager;
	
	public Game(int w,int h) {
		width = w;
		height = h;
		screenCenter = new Point(width/2,height/2);
		manager = new ContentManager();
		manager.loadActorType("test");
		manager.loadEffect("damageTest");
		manager.loadWeaponType("test");
		manager.loadEntityType("test");
		player=manager.entities.get("test").getEntity();
		player.setPos(new Point(100,100));
		level = new Level("test","test");
	}
	public void updateGame(Point mousePosition,boolean[] mousePressed,boolean[] keyPressed,boolean[] buttonPressed){
		int[] pMove = {0,0};
		if(keyPressed[KeyEvent.VK_A]) {pMove[0]-=3;}
		if(keyPressed[KeyEvent.VK_W]) {pMove[1]-=3;}
		if(keyPressed[KeyEvent.VK_D]) {pMove[0]+=3;}
		if(keyPressed[KeyEvent.VK_S]) {pMove[1]+=3;}
		moves = new ArrayList<int[]>();
		for(Entity e:entities) {
			moves.add(e.getMove(player));
		}
		for(int i=0;i<entities.size();i++) {
			int[] move = moves.get(i);
			Entity e1 = entities.get(i);
			attemptMovement(e1,move[0],move[1]);
		}
		attemptMovement(player,pMove[0],pMove[1]);
	}
	public void addEntity(Entity e,Point p) {
		e.setPos(p);
		executeEffect(e.onBirth,e,e,p);
		this.entities.add(e);
	}
	private void attemptMovement(Entity e1,int x,int y) {
		e1.move(x, y);
		boolean canMove = true;
		for(Entity e2:entities) {
			if(e1.getBoundingBox().intersects(e2.getBoundingBox())) {
				canMove = false;
				executeEffect(e1.onCollide,e1,e2,e2.getCenterPoint());
			}
		}
		int minX = (e1.getPosition().x+level.getWidth()*TILE_SIZE/2)/TILE_SIZE;
		int minY = (e1.getPosition().y+level.getHeight()*TILE_SIZE/2)/TILE_SIZE;
		int maxX = (e1.getPosition().x+e1.getDimension().width-1+level.getWidth()*TILE_SIZE/2)/TILE_SIZE;
		int maxY = (e1.getPosition().y+e1.getDimension().height-1+level.getHeight()*TILE_SIZE/2)/TILE_SIZE;
//		System.out.println(minX+" "+minY+" "+maxX+" "+maxY);
		for(int tX=minX;tX<=maxX;tX++) {
			for(int tY=minY;tY<=maxY;tY++) {
				int tileID = level.getTileID(tX, tY);
				canMove &= (tileID==1);
			}
		}
		if(!canMove){
			e1.move(-x, -y);
		}
	}
	public void draw(Graphics2D g2d) {
		Point center = player.getCenterPoint();
		Point playerPos = new Point(player.getPosition());
		playerPos.translate(-center.x,-center.y);
		playerPos.translate(screenCenter.x,screenCenter.y);
		
		//draw map
		for(int x=0;x<level.getWidth();x++) {
			for(int y=0;y<level.getHeight();y++) {
				Point tPos = new Point((x-level.getWidth()/2)*TILE_SIZE,(y-level.getHeight()/2)*TILE_SIZE);
				tPos.translate(-center.x,-center.y);
				tPos.translate(screenCenter.x,screenCenter.y);
				g2d.drawImage(level.getTile(x, y),tPos.x,tPos.y,null);
			}
		}
		
		//drawing player
		g2d.drawImage(player.getActor().getSprite(player.vx,player.vy), playerPos.x,playerPos.y, null);
		
		//draw all entites
		for(Entity e:entities) {
			Point ePos = new Point(e.getPosition());
			ePos.translate(-center.x,-center.y);
			ePos.translate(screenCenter.x,screenCenter.y);
			ePos.translate((e.width-e.getActor().width)/2,(e.height-e.getActor().height)/2);
			g2d.drawImage(e.getActor().getSprite(e.vx,e.vy), ePos.x,ePos.y, null);
		}
	}
	
	
	public void executeEffect(Effect e,Entity caster,Entity targetEntity,Point targetPoint){
		if(e.type.equals("damage")) {
			targetEntity.dealDamage(e.damage);
		}
		else if(e.type.equals("summon")) {
			Entity n = manager.getEntityType(e.summon).getEntity();
			addEntity(n,targetPoint);
		}
		else if(e.type.equals("buff")){
			targetEntity.applyBuff(manager.getBuff(e.buff));
		}
		else if(e.type.equals("area")) {
			for(Entity n:entities) {
				if(targetPoint.distance(n.getCenterPoint())<e.radius) {
					executeEffect(manager.getEffect(e.effect),caster,n,n.getCenterPoint());
				}
			}
		}
		else if(e.type.equals("compound")) {
			for(String s:e.compounds) {
				executeEffect(manager.getEffect(s),caster,targetEntity,targetPoint);
			}
		}
	}
}
