package crungeonDawler;


public class Modifier {
	String stat;
	String text;
	boolean pre;
	int value;
	public Modifier(String stat,int value){
		this.stat = stat;
		this.value = value;
	}
	public String getStat(){
		return stat;
	}
	public String getText() {
		return text;
	}
	public boolean prefix() {
		return pre;
	}
}
