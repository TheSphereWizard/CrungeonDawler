import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.luaj.vm2.*;
import org.luaj.vm2.lib.jse.*;

public class Entity {
	Actor actor;
	int lifetime;
	int level;
	int counter = 0;
	int vx;
	int vy;
	int maxHealth;
	int health;
	Integer speed;
	Integer width;
	Integer height;
	Integer misc;
	Dimension dimension;
	Point position;
	Effect onCollide;
	Effect onBirth;
	Effect onDeath;
	Weapon weapon;
	String moverPath;
	ArrayList<Buff> buffs;
	
	public Entity(int lifetime,int maxHealth, Actor actor, Integer width, Integer height, Effect onCollide, Effect onBirth, Effect onDeath,
			Weapon weapon, String moverPath,Integer speed,Integer misc,Integer minLevel,Integer maxLevel) {
		this.actor = actor;
		this.lifetime = lifetime;
		this.maxHealth = maxHealth;
		this.dimension = new Dimension(width,height);
		this.width = width;
		this.height = height;
		this.onCollide = onCollide;
		this.onBirth = onBirth;
		this.onDeath = onDeath;
		this.weapon = weapon;
		this.moverPath = moverPath;
		this.speed = speed;
		this.misc = misc;
		this.level = new Random().nextInt(maxLevel-minLevel+1)+minLevel;
	}
	
	public void update(){
		counter++;
		if(counter == lifetime){
			destroy();
		}
	}
	public void destroy(){
	
	}
	public void dealDamage(int d) {
		health-=d;
		if(health>maxHealth) {
			health = maxHealth;
		}
		if(health<0 && (maxHealth != -1)) {
			destroy();
		}
	}
	public void applyBuff(Buff b) {
		this.buffs.add(b);
	};
	public void setPos(Point p) {
		this.position = p;
	}
	public void move(int x,int y) {
		this.position.translate(x, y);
		vx = x;
		vy = y;
	}
	public int[] getMove(Entity target){
		if(position.distance(target.position)>this.getAggroRange(target.level)) {
			return new int[] {-1,-1};
		}
		Globals globals = JsePlatform.standardGlobals();
		globals.get("dofile").call(LuaValue.valueOf("resources/content/movers/"+moverPath+".lua"));
		
		LuaValue mover = globals.get("GetMove");
		LuaValue luaTarget = CoerceJavaToLua.coerce(target.position);
		LuaValue luaPosition = CoerceJavaToLua.coerce(position);
		LuaValue luaSpeed = LuaValue.valueOf(speed);
		LuaValue luaTime = LuaValue.valueOf(counter);
		LuaValue luaVX = LuaValue.valueOf(vx);
		LuaValue luaVY = LuaValue.valueOf(vy);
		LuaValue luaMisc = LuaValue.valueOf(misc);
		Varargs m = mover.invoke(new LuaValue[] {luaTarget,luaPosition,luaVX,luaVY,luaSpeed,luaTime,luaMisc});
		int[] a = new int[2];
		a[0] = m.arg(1).toint();
		a[1] = m.arg(2).toint();
		System.out.println(Arrays.toString(a));
		return a;
	}
	public Dimension getDimension(){
		return dimension;
	}
	public Point getPosition(){
		return position;
	}
	public Point getCenterPoint() {
		int x = (int) dimension.getWidth()/2;
		int y = (int) dimension.getHeight()/2;
		Point center = new Point(position);
		center.translate(x, y);
		return center;
	}
	public Rectangle getBoundingBox() {
		return new Rectangle(position,dimension);
	}
	public Actor getActor(){
		return actor;
	}
	public Integer getAggroRange(Integer level) {
		return 9999999;
	}
	
	public static void main(String[] args) {
		Entity e1 = new Entity(0,0,null,0,0,null,null,null,null,"test",3,10,0,0);
		Entity e2 = new Entity(0,0,null,0,0,null,null,null,null,"test",0,0,0,0);
		e1.setPos(new Point(0,0));
		e2.setPos(new Point(20,20));
		e1.getMove(e2);
	}
	
	
}
