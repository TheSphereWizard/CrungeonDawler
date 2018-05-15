package newVersion;

public class ActorType {
	String spriteSheetPath;
	Integer width;
	Integer height;
	Boolean rotateable;
	ContentManager contentManager;
	public void setContentManager(ContentManager c) {
		contentManager = c;
	}
	public Actor getActor() {
		return new Actor(spriteSheetPath,width,height,rotateable);
	}
}
