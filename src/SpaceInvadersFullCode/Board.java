package SpaceInvadersFullCode;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Board extends JPanel implements KeyListener {

	private Timer timer;
	private String win_message = "You won!";
	private String game_over_message = "Game Over!";

	// game status
	private boolean play = true; // if game is not over
	private int numOfDestroyed = 0;
	private int direction = -1;

	// characters
	private PlayerShip playerShip;
	private List<AlienShip> alienShips;
	private Shot shot;

	private int score;
	
	public Board() {

		addKeyListener(this);
		setFocusable(true); // to handle events here
		setBounds(10, 10, GameConstants.BOARD_WIDTH, GameConstants.BOARD_HEIGHT);
		setBackground(Color.BLACK);
		setVisible(true);

		timer = new Timer(GameConstants.DELAY, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				updateGame();
			}
		});

		loadCharacters();
		timer.start();

	}

	public void loadCharacters() {

		alienShips = new ArrayList<>();

		// add all alien objects to list
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 6; j++) {

				AlienShip alien = new AlienShip(GameConstants.ALIEN_START_X + 18 * j,
						GameConstants.ALIEN_START_Y + 18 * i);

				alienShips.add(alien);
			}
		}

		playerShip = new PlayerShip();
		shot = new Shot();

	}

	// update
	public void updateGame() {

		// start player
		playerShip.moveShip();

		// shot
		if (shot.isAlive()) {

			int sx = shot.getXPos();
			int sy = shot.getYPos();

			for (AlienShip a : alienShips) {

				int ax = a.getXPos();
				int ay = a.getYPos();

				// if shot hits alien
				if (a.isAlive() && shot.isAlive()) {
            
					if ((sx >= ax) && (sx <= ax + GameConstants.ALIEN_SHIP_WIDTH) && (sy >= ay)
							&& (sy <= ay + GameConstants.ALIEN_SHIP_HEIGTH)) {
                         
						a.setImage(GameImages.img_exploded);// change image
						update(this.getGraphics()) ;
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						numOfDestroyed++;
						shot.Destroy(); // hide
						a.setAlive(false); // hide						
					}
					
					
				}
				
			}

			// move shot towards aliens by 4 pixel
			int y = shot.getYPos();
			y -= 4;

			if (y < 0) { // if shot passes top of border
				shot.Destroy(); // hide shot
			} else {
				shot.setYPos(y);// else update y position of shot
			}

		}

		// bombs
		
		Random randnum = new Random();
		
        for(AlienShip a : alienShips) {
             
        	
        	int selected = randnum.nextInt(10) ;
        	
        	Bomb b = a.getBomb();
        	int y = b.getYPos() ;
        	
        	if( !b.isAlive() && a.isAlive()) { 
        	  if(selected == 2)
        		b.setAlive(true);	
        	 
           	// b.setYPos(y + 1);
        	}
        	
        	 if (b.isAlive()) { //if bomb is alive

        		 b.setYPos(y + 1);
        		 
                 //if bomb hits Ground level hide bomb
                 if (b.getYPos() >= GameConstants.EARTH_LEVEL - GameConstants.BOMB_HEIGHT) {

                     b.setAlive(false);
                     //drop new bombs
                     a.setBomb(new Bomb(a.getXPos(),a.getYPos()));
                 }
             }
        	 
        	 //bomb hits player
        	 int bX = b.getXPos();
             int bY = b.getYPos();
             int pX = playerShip.getXPos();
             int pY = playerShip.getYPos();
        	 
        	 if (playerShip.isAlive() && b.isAlive()) {
                 
             	//if bomb hits player's rectangle
                 if (     bX >= (pX)
                         && bX <= (pX + GameConstants.PLAYER_SHIP_WIDTH)
                         && bY >= (pY)
                         && bY <= (pY + GameConstants.PLAYER_SHIP_HEIGHT)) {

                     b.setAlive(false) ;   //hide bomb
                     playerShip.setAlive(false);     //hide player
                 }
             }
        	 
        	
        }
		

		// aliens
		for (AlienShip a : alienShips) {

			int curX = a.getXPos();

			// Alien grubu Board un sagina ya da soluna gelirse grupdaki tüm alien ları
			// 15 px aşagi hareket ettir.

			// alien lardan herhangi birinin x'i 328 i geçerse ve sağa dogru hareket
			// ediyorsa
			if (curX >= GameConstants.BOARD_WIDTH - GameConstants.ALIEN_SHIP_WIDTH && direction != -1) {
				direction = -1; // saga hareket ettir

				Iterator<AlienShip> i1 = alienShips.iterator();

				while (i1.hasNext()) {

					AlienShip a2 = i1.next();
					a2.setYPos(a2.getYPos() + 15); // move down by 15 px
				}

			}

			// alien lardan herhangi birinin x'i 5px den azsa ve sola dogru hareket ediyorsa
			if (curX <= GameConstants.LIMIT_LEFT && direction != 1) {
				direction = 1;// sola hareket ettir

				Iterator<AlienShip> i1 = alienShips.iterator();

				while (i1.hasNext()) {

					AlienShip a2 = i1.next();
					a2.setYPos(a2.getYPos() + 15); // move down by 15 px
				}

			}

		}

		Iterator<AlienShip> it = alienShips.iterator();

		// eger alienlardan herhangi biri ground seviyesine ulaşırsa
		while (it.hasNext()) {

			AlienShip alien = it.next();

			if (alien.isAlive()) { // if alien is still alive

				int y = alien.getYPos();

				if (y > GameConstants.EARTH_LEVEL - GameConstants.ALIEN_SHIP_HEIGTH) {

					play = false; // stop timer
					gameOver(this.getGraphics());// invasion occured
				}

				alien.moveShip(direction); // move to right/left according to which button is pressed
			}
		}

		repaint();

	}

	// render
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);

		if (play) {
			drawEarth(g);
			drawPlayerShip(g);
			drawAlienShips(g);
			drawShots(g);
			drawBombs(g);
			drawScore(g) ;

			// if all aliens died you win
			if (numOfDestroyed == GameConstants.TOTAL_NUM_OF_ENEMIES) {

				// play = false;
				timer.stop();
				youWon(g);// print message
			}

		} else {
			timer.stop();
			gameOver(g);
		}

	}

	public void gameOver(Graphics g) {

		Font f = new Font("Helvetica", Font.BOLD, 14);

		g.setColor(Color.RED);
		g.setFont(f);
		g.drawString(game_over_message +"      Your Score : " + String.valueOf(score),
				40, GameConstants.BOARD_HEIGHT / 2);

	}

	public void youWon(Graphics g) {

		Font f = new Font("Helvetica", Font.BOLD, 14);

		g.setColor(Color.RED);
		g.setFont(f);
		g.drawString(win_message, 40, GameConstants.BOARD_HEIGHT / 2);

	}
    
	public void drawScore(Graphics g) {
		score = numOfDestroyed * 10 ;
		g.setFont(new Font("Verdana",Font.BOLD,18));
		g.drawString("Score : " +String.valueOf(score), GameConstants.BOARD_WIDTH-140, 15);
	}
	
	public void drawAlienShips(Graphics g) {

		for (AlienShip a : alienShips) {

			if (a.isAlive()) {

				g.drawImage(a.getImage(), a.getXPos(), a.getYPos(), GameConstants.ALIEN_SHIP_WIDTH,
						GameConstants.ALIEN_SHIP_HEIGTH, null);

			} else {
				a.Destroy();
			}

		}

	}
	
	public void drawBombs(Graphics g) {
    	
    	 for(AlienShip a : alienShips) 
    	 {
    		 
    		 if(a.getBomb().isAlive()) {

    			 g.drawImage(a.getBomb().getImage(), a.getBomb().getXPos(),a.getBomb().getYPos(),
    					 10,GameConstants.BOMB_HEIGHT,
    					  null) ;
    		 }
    		 
    	 }
		
    	
    }

	public void drawShots(Graphics g) {

		if (shot.isAlive()) {

			g.drawImage(shot.getImage(), shot.getXPos(), shot.getYPos(), GameConstants.SHOT_WIDTH,
					GameConstants.SHOT_HEIGHT, null);
		}

	}



	public void drawEarth(Graphics g) {

		g.drawImage(GameImages.img_earth_surface, 0, 300, GameConstants.BOARD_WIDTH,
				GameImages.img_earth_surface.getHeight(this), null);

		g.setColor(Color.BLUE);
		g.drawLine(0, GameConstants.EARTH_LEVEL, GameConstants.BOARD_WIDTH, GameConstants.EARTH_LEVEL);
	}

	public void drawPlayerShip(Graphics g) {

		if (playerShip.isAlive()) {
			g.drawImage(GameImages.img_player_ship, playerShip.getXPos(), playerShip.getYPos(),
					GameConstants.PLAYER_SHIP_WIDTH, GameConstants.PLAYER_SHIP_HEIGHT, null);

		} else {
			playerShip.Destroy();
			play = false;
		}
	}

	// Key events
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_A)
			System.out.println("A is pressed ...");

		if (key == KeyEvent.VK_RIGHT) {
			playerShip.dx = 2;
		}
		if (key == KeyEvent.VK_LEFT) {
			playerShip.dx = -2;
		}

		if (key == KeyEvent.VK_SPACE) {

			int x = playerShip.getXPos();
			int y = playerShip.getYPos();
			System.out.println("y :" + y);
			if (play) {

				if (!shot.isAlive()) {

					shot = new Shot(x + GameConstants.PLAYER_SHIP_WIDTH / 2, y);
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_RIGHT)
			playerShip.dx = 0;
		if (key == KeyEvent.VK_LEFT)
			playerShip.dx = 0;

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
