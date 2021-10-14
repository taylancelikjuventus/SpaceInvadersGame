package SpaceInvadersFullCode;

public class Shot extends Sprite {

	public Shot() {
	}
	
	public Shot(int x, int y) {
		
		setImage(GameImages.img_shot);
		setXPos(x);
		setYPos(y);
		
	}
}
