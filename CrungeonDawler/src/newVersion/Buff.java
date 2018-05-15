package newVersion;
import java.util.Map;

public class Buff {
	String type;
	
	//ticking buffs
	Integer tickTime;
	String effect;
	
	//stat buffs
	Map<String,Integer> stats;
	
	ContentManager contentManager;
	public void setContentManager(ContentManager c) {
		contentManager = c;
	}
}
