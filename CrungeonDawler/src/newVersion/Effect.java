package newVersion;
import java.util.ArrayList;

public class Effect {
	String type;
	
	//summon effects
	String summon;
	
	//damage effects
	Integer damage;
	
	//buff effects
	String buff;
	
	//AOE effects
	Integer radius;
	String effect;
	
	//compound effects
	ArrayList<String> compounds;
	
	ContentManager contentManager;
	public void setContentManager(ContentManager c) {
		contentManager = c;
	}
}
