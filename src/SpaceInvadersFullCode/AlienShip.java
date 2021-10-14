package SpaceInvadersFullCode;

public class AlienShip extends Sprite {

	private Bomb bomb;
	
	public AlienShip(int x,int y) {

   		this.setXPos(x);
	    this.setYPos(y);
	    setImage(GameImages.img_alien_ship);
	    bomb = new Bomb(this.getXPos(), this.getYPos() ) ;
	   
	   
	}
	
   //movement of ship
	public void moveShip(int dir) {
		this.setXPos(this.getXPos() + dir);
	}
	//bomb of ship
	public Bomb getBomb() {
		return bomb;
	}
	
	public void setBomb(Bomb bomb) {
		this.bomb = bomb ;
	}
	
}
