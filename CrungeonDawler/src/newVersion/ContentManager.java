package newVersion;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ContentManager {
	GsonBuilder builder;
	Gson gson;
	
	Map<String,EntityType> entities;
	Map<String,Effect> effects;
	Map<String,WeaponType> weapons;
	Map<String,ItemType> items;
	Map<String,ActorType> actors;
	Map<String,Buff> buffs;
	
	public ContentManager(){
		builder = new GsonBuilder();
		gson = builder.create();
		this.entities = new HashMap<String,EntityType>();
		this.effects = new HashMap<String,Effect>();
		this.weapons = new HashMap<String,WeaponType>();
		this.items = new HashMap<String,ItemType>();
		this.actors = new HashMap<String,ActorType>();
		this.buffs = new HashMap<String,Buff>();
	}
	
	public EntityType readEntityType(String path) {
		BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new FileReader("resources/content/entities/"+path+".json"));
			EntityType n = gson.fromJson(bufferedReader, EntityType.class);
			entities.put(path, n);
			return n;
		} catch (FileNotFoundException e) {e.printStackTrace();}
		return null;
	}
	public EntityType getEntityType(String name) {
		return entities.get(name);
	}
	public void loadEntityType(String path) {
		readEntityType(path).setContentManager(this);
	}
	
	public ActorType readActorType(String path) {
		BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new FileReader("resources/content/actors/"+path+".json"));
			ActorType a = gson.fromJson(bufferedReader, ActorType.class);
			actors.put(path, a);
			return a;
		} catch (FileNotFoundException e) {e.printStackTrace();}
		return null;
	}
	public ActorType getActorType(String name) {
		return actors.get(name);
	}
	public void loadActorType(String path) {
		readActorType(path).setContentManager(this);
	}
	
	public Effect readEffect(String path) {
		BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new FileReader("resources/content/effects/"+path+".json"));
			Effect f = gson.fromJson(bufferedReader, Effect.class);
			effects.put(path, f);
			return f;
		} catch (FileNotFoundException e) {e.printStackTrace();}
		return null;
	}
	public Effect getEffect(String name) {
		return effects.get(name);
	}
	public void loadEffect(String path) {
		readEffect(path).setContentManager(this);
	}
	
	public WeaponType readWeaponType(String path) {
		BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new FileReader("resources/content/weapons/"+path+".json"));
			WeaponType w = gson.fromJson(bufferedReader, WeaponType.class);
			weapons.put(path, w);
			return w;
		} catch (FileNotFoundException e) {e.printStackTrace();}
		return null;
	}
	public WeaponType getWeaponType(String name) {
		return weapons.get(name);
	}
	public void loadWeaponType(String path) {
		readWeaponType(path).setContentManager(this);
	}
	
	public ItemType readItemType(String path) {
		BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new FileReader("resources/content/items/"+path+".json"));
			ItemType i = gson.fromJson(bufferedReader, ItemType.class);
			items.put(path, i);
			return i;
		} catch (FileNotFoundException e) {e.printStackTrace();}
		return null;
	}
	public ItemType getItemType(String name) {
		return items.get(name);
	}
	public void loadItemType(String path) {
		readItemType(path).setContentManager(this);
	}

	public Buff readBuff(String path) {
		BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new FileReader("resources/content/buffs/"+path+".json"));
			Buff b = gson.fromJson(bufferedReader, Buff.class);
			buffs.put(path, b);
			return b;
		} catch (FileNotFoundException e) {e.printStackTrace();}
		return null;
	}
	public Buff getBuff(String name) {
		return buffs.get(name);
	}
	public void loadBuff(String path) {
		readBuff(path).setContentManager(this);
	}

	public static void main(String[] args) { 
		ContentManager c = new ContentManager();
		File effects = new File("resources/content/effects");
		for(File f:effects.listFiles()) {
			c.loadEffect(f.getName());
		}
		for(String n:c.effects.keySet()) {
			System.out.println(c.getEffect(n).type);
		}
	} 
	
}
