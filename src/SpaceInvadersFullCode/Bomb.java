package SpaceInvadersFullCode;

public class Bomb extends Sprite {

	public Bomb() {
		
	}
	public Bomb(int x,int y) {
      
		this.setXPos(x);
		this.setYPos(y);
	    
		setAlive(false);
		setImage(GameImages.img_alien_bomb);
	}
	
	
	
}
