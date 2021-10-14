package SpaceInvadersFullCode;

import java.awt.Image;

import javax.security.auth.Destroyable;

public class Sprite {

	private boolean alive;
	private Image image;

	private int x, y;
	public int dx;

	public Sprite() {

		alive = true; // all sprites are alive when starting the game
	}

	public void Destroy() {
		alive = false;   //hides sprite
	}
	
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}


	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setXPos(int x) {
		this.x = x;
	}

	public void setYPos(int y) {
		this.y = y;
	}

	public int getXPos() {
		return x;
	}

	public int getYPos() {
		return y;
	}

}
