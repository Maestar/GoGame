package goGame;

//coded by: Tera Benoit main method to run gui.
import java.awt.*;
import javax.swing.*;

public class GoGameMain {
	public static GameUI goFrame = new GameUI();
	public static void main(String[] args){
		
		//int[][]gameGrid;
		
	   
	   goFrame.setTitle("Go Game");
	   goFrame.setSize(800, 600);
	   goFrame.setLocationRelativeTo(null);
	   goFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   goFrame.setVisible(true);
	   
	   //System.out.println(goFrame.getUpdateStatus());
/* testing purposes for making the gui function
 * 
 *      int[][] board = new int[9][9];
 *     for(int x = 0; x < 9; x++){
 *      	for(int y = 0; y < 9; y++){
 *      		board[x][y] = 1;
 *      	}
 *      }
 *      
 *    goFrame.updateUI(board);
 *    while(true){
		   
		   goFrame.removeAll();
		   goFrame.revalidate();
		   goFrame.repaint();
		   }
		   
 */    

	   //alternative here would be:
	   //boolean updateStatus = goFrame.getUpdateStatus();
	   //and then use updateStatus inside the if statement == true
	   
	   
	   
	   
	 
	}
}