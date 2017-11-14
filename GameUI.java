package goGame;
/* Coded by Jason Miller and Tera Benoit
 * This class creates/updates the UI elements of the game and its game board. */
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

public class GameUI extends JFrame {
	int[][] goGrid = new int[9][9];
	public int[][] updatedBoard = new int[9][9];
	Game myGame = new Game();
    GameBoard myGameBoard = new GameBoard();
	JPanel mainFrame = new JPanel();
	public boolean updateNeeded = false;
	
	//Variables displayed by GUI that are fetched from the business code.
	//String ColorScore = Integer.toString(game.getScore(color));
	String blkScore = Integer.toString(0);
	String whtScore = Integer.toString(0);
	//String ColorName = game.getPlayer(color);
	String blkPlayerName = "Player 01";
	String whtPlayerName = "Player 02";
	
	//Constructor. Initializes the goGrid and sends that to showGameUI(). 
	GameUI() {
		//Initializes the goGrid to have no pieces (all 0s).
		for (int x = 0; x < 9; x++) {
       		for (int y = 0; y < 9; y++) {
       			goGrid[x][y] = 0;
       		}	
		}
		
		//Initializes the UI.
		showGameUI(goGrid);
	}
	
	//Updates the UI. Needs an int[][] array to update the board.
	private void showGameUI(int[][] board) {
	
		
		if(mainFrame != null){
			mainFrame.removeAll();
		}
		
		//Components displayed via the GUI.
        JButton exitbtn = new JButton("Exit >>");
        JButton passbtn = new JButton("Pass Turn");
        passbtn.setAlignmentX(Component.CENTER_ALIGNMENT);	//Aligns the button horizontally.

        JLabel timer = new JLabel("Timer goes Here.");

        JLabel BlkPlayerLabel = new JLabel("Black Player: ");
        JLabel WhtPlayerLabel = new JLabel("White Player: ");
        JLabel BlkPlayerNameLabel = new JLabel(blkPlayerName);
        JLabel WhtPlayerNameLabel = new JLabel(whtPlayerName);

       	JLabel scoreLabelblk = new JLabel("Score: ");
       	JLabel scoreLabelwht = new JLabel("Score: ");
       	JLabel scoreAmtWht = new JLabel(whtScore);
       	JLabel scoreAmtBlk = new JLabel(blkScore);
       	
      //Action listener for the pass button (calls pass() in game).
       	passbtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                myGame.pass();
            }
        });
       	
       	//Action listener for the exit button (calls quit() in game).
       	exitbtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                myGame.quit();
            }
        });
       	
       	//middle frame, contains the game grid and the pass turn button
       	JPanel middleFrame = new JPanel();
       		middleFrame.setLayout(new BoxLayout(middleFrame, BoxLayout.X_AXIS));
       		middleFrame.add(Box.createHorizontalGlue());
       		middleFrame.add(drawBoard(board));				//Uses drawBoard() to create the game board.
       		middleFrame.add(Box.createHorizontalGlue());

       	//top frame, contains an exit button and the turn timer.
       	JPanel topFrame = new JPanel();
       		topFrame.setLayout(new BorderLayout());
       		topFrame.add(exitbtn, BorderLayout.EAST);
       		topFrame.add(timer, BorderLayout.WEST);	    
          	
       	//score grids for displaying user score.
       	JPanel blkScoreGrid = new JPanel();
       		blkScoreGrid.setLayout(new GridLayout(2,2));
       		blkScoreGrid.add(BlkPlayerLabel);
       		blkScoreGrid.add(BlkPlayerNameLabel);
       		blkScoreGrid.add(scoreLabelblk);
       		blkScoreGrid.add(scoreAmtBlk);
    	
       	JPanel whtScoreGrid = new JPanel();
       		whtScoreGrid.setLayout(new GridLayout(2,2));
       		whtScoreGrid.add(WhtPlayerLabel);
       		whtScoreGrid.add(WhtPlayerNameLabel);
       		whtScoreGrid.add(scoreLabelwht);
       		whtScoreGrid.add(scoreAmtWht);

       	//bottom frame, contains the two player's scores and the pass button.
       	JPanel bottomFrame = new JPanel();
       		bottomFrame.setLayout(new BoxLayout(bottomFrame, BoxLayout.X_AXIS));
       		bottomFrame.add(Box.createHorizontalGlue());
       		bottomFrame.add(blkScoreGrid);
       		bottomFrame.add(Box.createHorizontalGlue());
       		bottomFrame.add(passbtn);
       		bottomFrame.add(Box.createHorizontalGlue());
       		bottomFrame.add(whtScoreGrid);
       		bottomFrame.add(Box.createHorizontalGlue());

       	//creates the main container that will hold all the other elements, and then adds those elements to itself in their proper position.
       	//JPanel mainFrame = new JPanel();
       		mainFrame.setLayout(new BorderLayout());
       		mainFrame.add(topFrame, BorderLayout.NORTH);
       		mainFrame.add(middleFrame, BorderLayout.CENTER);
       		mainFrame.add(bottomFrame, BorderLayout.SOUTH);

		add(mainFrame);      	
	}
	
	//Updates the game board and returns the updated JPanel.
	private JPanel drawBoard(int[][] board) {
		//Creates and styles the JPanel.
		JPanel boardPanel = new JPanel();
		boardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		boardPanel.setLayout(new GridLayout(9,9,0,0));
		boardPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 4));
		
		JButton[][] buttonGrid = new JButton[9][9];
       	for (int x = 0; x < 9; x++) {
       		for (int y = 0; y < 9; y++) {
       			
       			//Creates and styles the JButton.
       			//The icon is based on whether the piece is absent, black, or white.
       			Icon icon;
       			if (board[x][y] == 1)
       				icon = new ImageIcon("iconblk.png");
       			else if (board[x][y] == 2)
       				icon = new ImageIcon("iconwht.png");
       			else
       				icon = new ImageIcon("icon.png");
       			JButton gridButton = new JButton(icon);
       			gridButton.setIconTextGap(0); gridButton.setContentAreaFilled(false); gridButton.setBorderPainted(false); gridButton.setBorder(null);
       			//gridButton.setPreferredSize(new Dimension(40, 40));
       			
       			//Loads the buttonGrid with the gridButtons so that we can use it to find the button's location later.
       			buttonGrid[x][y] = gridButton;
       				       			
       			//Action listener for each button in the grid.
       			gridButton.addActionListener(new ActionListener()
                {
       				@Override
                    public void actionPerformed(ActionEvent event) {
                        JButton button = (JButton)event.getSource();
                        for (int x = 0; x < 9; x++) {
            	       		for (int y = 0; y < 9; y++) {
            	       			//If the button matches the one in the button grid, call move with x,y coordinate
            	       			if (buttonGrid[x][y] == button) {
            	       				System.out.println(x+ " " + y);
            	       				myGameBoard.move(x, y);
            	       				updatedBoard = myGameBoard.getGameGrid();
            	       				System.out.println(Arrays.deepToString(updatedBoard));
            	       				GameUI.this.updateNeeded = true;
            	       				System.out.println(updateNeeded);
            	}}}}});
                boardPanel.add(gridButton);
       		}
       	}
		
		return boardPanel;
	}
	
	//Receives an updated game board from GameBoard and uses it to update the UI.
	public void updateUI(int[][] board) {
		goGrid = board;
		showGameUI(goGrid);
		updateNeeded = false;
		
	}
	
	

	public boolean getUpdateStatus(){
		return updateNeeded;
	}
	
	public int[][] getBoard(){
		return this.updatedBoard;
	}
}