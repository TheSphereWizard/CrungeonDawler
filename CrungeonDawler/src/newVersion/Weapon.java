package newVersion;


public class Weapon {
	EntityType projectile;
	Integer coolDownTime;
	Integer currentTime;
	public void update(){
		this.currentTime++;
	}
	public void setTime(int t){
		this.currentTime = t;
	}
	public boolean getReady(){
		return currentTime>coolDownTime;
	}
}
