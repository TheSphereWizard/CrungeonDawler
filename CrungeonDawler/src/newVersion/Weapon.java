package newVersion;

import java.util.Map;

public class Weapon {
	EntityType projectile;
	Integer cooldownTime;
	Integer currentTime=0;
	Weapon(String name, String projectile, Integer cooldownTime, Integer minLevel, Integer maxLevel, Integer minRarity, Integer maxRarity, Map<String, Integer> baseStats, ContentManager contentManager){
		this.projectile=new ContentManager().entities.get("test");
		this.cooldownTime=cooldownTime;
	}
	public void update(){
		this.currentTime++;
	}
	public void setTime(int t){
		this.currentTime = t;
	}
	public boolean getReady(){
		return currentTime>cooldownTime;
	}
}
