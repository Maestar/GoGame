package goGame;

//coded by: Tera Benoit main method to run gui.
import java.awt.*;
import javax.swing.*;

public class GoGameMain {
	//public static GameUI goFrame = new GameUI();
	//public static TitleUI titleFrame = new TitleUI();
	public static GoGameUI gui = new GoGameUI();
	public static void main(String[] args){
		
	
	   
		gui.setTitle("Go Game");
		gui.setSize(800, 600);
		gui.setLocationRelativeTo(null);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setVisible(true);
	 
	}
}