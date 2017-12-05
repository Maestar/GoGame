package goGame;

//coded by: Tera Benoit main method to run gui.
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class GoGameMain {
	public static GoGameUI goFrame = new GoGameUI();
	public static void main(String[] args){
  
	   goFrame.setTitle("Go Game");
	   goFrame.setSize(800, 600);
	   goFrame.setLocationRelativeTo(null);
	   goFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   goFrame.setVisible(true);
	   

	   
	   
	 
	}
}

