package goGame;

import java.sql.DriverManager;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
In this file, Sanjay declare the function and me(akash) defines the function, we did kind of pair programming//
*/


public class Game //Main class
{

    
    boolean gameEnd=false;
    private int newAttr,blackPieces,whitePieces,loginCount;
    private static String username,password;
    private static String currentPlayername,str1,str2;
    private static int playerTurn,passCount;
    ArrayList list; 
GameBoard gmBoard;    

    
    public Game() //constructor 
    {
        player1Color=1;
        player2Color=2;
        
       	play1=0;
        play2=0;
        newAttr=0;
        passCount=0;
        loginCount=0;
        blackPieces=0;
        whitePieces=0;
        username="sanjay";
        password="adhikari";
        gmBoard=new GameBoard();
        list = new ArrayList();
//GameBoard gmBoard=new GameBoard();
        
        DBConnect connect = new DBConnect(); 
        connect.getData();
        list=connect.list; 
        
        System.out.println("Here in GAME:");
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }
    }
    
    public static void updateScore(int t,int score ){
        if(t%2!=0){
            play1+=score;
            System.out.println(play2+"score1 "+player1);
        }
        else  
        System.out.println(play2+"score2 "+player2);
            play2+=score;
        
        }
    
    public String getPlayerName(){
        str1 = "sanjay";
        str2 = "akash";
        loginCount++;
        if(loginCount%2==1)
            return str1;
        else
            return str2;
    }
    
    public void score(){
    }
    
    public void login(){
        DBConnect connect = new DBConnect(); 
        connect.getData();
    }
    }
    public void victory(){
    
    public void endTurn(){
    
    }
    
    public void pass()//This method helps to update the class variable pass
        {
        if (this.passCount==0){
               
        passCount++;
        JOptionPane.showMessageDialog(null, 
                              "You pressed the Pass Button", 
                              "Pass", 
                              JOptionPane.WARNING_MESSAGE); 
        this.endTurn();
        }else{
        JOptionPane.showMessageDialog(null, 
                              "This is the second consucutive pass. So Game is finished", 
                              "End Game", 
                              JOptionPane.WARNING_MESSAGE); 
        endGame();        
                }
    }    
    public static void resetPass() 
        {
        passCount=0;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void play()
        {
    }

    public static boolean getPlayerTurn() //true for white and false for black
        {
        
        playerTurn++;
    
        return(playerTurn%2==0);
    
}
    public static void quit()
    {
        JOptionPane.showMessageDialog(null, 
                              str1 + " currentPlayername", 
                              "End Game", 
                              JOptionPane.WARNING_MESSAGE);
        if(playerTurn==0){
            JOptionPane.showMessageDialog(null, 
                              currentPlayername+ " quits the game. So YOU LOST", 
                              "End Game", 
                              JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public void endGame()//the GameUI is updated when the endgame happens
        { 
        this.gameEnd=true;
    }
    
    public void resetTimer()
        {
    
    }
    


}
