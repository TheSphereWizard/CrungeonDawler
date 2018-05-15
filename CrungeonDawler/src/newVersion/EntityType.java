package newVersion;
import java.awt.Dimension;
import java.util.Map;

public class EntityType {
	String name;
	String actor;
	String mover;
	String onBirth;
	String onDeath;
	String onCollide;
	String weapon;
	Integer minLevel;
	Integer maxLevel;
	Integer lifetime;
	Integer baseHealth;
	Integer width;
	Integer height;
	Integer speed;
	Integer misc;
	Map<String,Integer> baseStats;
	ContentManager contentManager;
	public void setContentManager(ContentManager c) {
		contentManager = c;
	}
	public Entity getEntity() {
		return new Entity(lifetime, 
						  baseHealth, 
						  contentManager.getActorType(actor).getActor(), 
						  width,
						  height, 
						  contentManager.getEffect(onCollide), 
						  contentManager.getEffect(onBirth), 
						  contentManager.getEffect(onDeath), 
						  contentManager.getWeaponType(weapon).getWeapon(),
						  mover,
						  speed,
						  misc,
						  minLevel,
						  maxLevel);
	}
}
