package SpaceInvadersFullCode;

public class PlayerShip extends Sprite {

	public PlayerShip() {

		this.setXPos(GameConstants.PLAYER_START_X);
	    this.setYPos(GameConstants.PLAYER_START_Y);
	    setImage(GameImages.img_player_ship);
	    
	}
	
	public void moveShip() {
		
		setXPos(getXPos() + dx);
		
		//limit movements
		if(this.getXPos() <= 2)
			this.setXPos(2);
		if(this.getXPos() >= GameConstants.BOARD_WIDTH-GameConstants.PLAYER_SHIP_WIDTH)
			this.setXPos(GameConstants.BOARD_WIDTH-GameConstants.PLAYER_SHIP_WIDTH);
		
		
	}
	
	
	
}
