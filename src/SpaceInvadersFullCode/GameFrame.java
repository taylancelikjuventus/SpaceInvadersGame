package SpaceInvadersFullCode;

import java.awt.EventQueue;

import javax.swing.JFrame;


public class GameFrame extends JFrame {

	public GameFrame() {
        
		setTitle("Space Invaders") ;
		setLayout(null);
		setBounds(100, 100, GameConstants.BOARD_WIDTH+40, GameConstants.BOARD_HEIGHT+60);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
		//Board
		Board b = new Board() ;
		add(b);
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		 GameFrame f = new GameFrame();
		 f.setVisible(true);
		
	
	}   

}
