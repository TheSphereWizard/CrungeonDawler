package newVersion;
import java.util.Map;

public class WeaponType {
	String name;
	String projectile;
	Integer cooldownTime;
	Integer minLevel;
	Integer maxLevel;
	Integer minRarity;
	Integer maxRarity;
	Map<String,Integer> baseStats;
	ContentManager contentManager;
	public void setContentManager(ContentManager c) {
		contentManager = c;
	}
	public Weapon getWeapon() {
		return new Weapon();
	}
}
