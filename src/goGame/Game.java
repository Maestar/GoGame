package goGame;

import com.mysql.jdbc.Connection;
//import static goGame.DBConnect.list;
import java.sql.*;
import java.util.ArrayList;
//import static java.util.Collections.list;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
In this file, Sanjay declare the function and me(akash) defines the function, we did kind of pair programming//
*/


/**
 *
 * @author akash
 */
public class Game //Main class
{
    String name;
    boolean gameEnd=false;
    private int newAttr,blackPieces,whitePieces;
    public static int passCount;
    public static String player1,player2,currentPlayer;
    public static String username1,username2,password1,password2;
    public static int player1Color,player2Color;
    public static int timer;
    GameBoard gmBoard;    
    static int player1Score;
    static int player2Score;
    static ArrayList list;
    DBConnect connect;
//static int playerTurn=0;
    static boolean playerTurn=false;//false for black
    
    public Game() //constructor 
    {
         connect= new DBConnect();
        player1Score=0;
        player2Score=0;
        newAttr=0;
        passCount=0;
        blackPieces=0;
        whitePieces=0;
        player1Color=1;
        player2Color=2;
        timer=25;
        gmBoard=new GameBoard();
        list = new ArrayList();
        
        list=connect.list; 
        
        System.out.println("Here in GAME:");
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }
    }
    
    public static void updateScore(boolean t,int score ){
        if(t==false){
            player1Score+=score;
            System.out.println(player1Score+"score blackPlayer "+player1);
        }
        else  
            player2Score+=score;
        System.out.println(player2Score+"score whitePlayer "+player2);
        
        }
    
    public int getScore(boolean color){
        if (color==false){
            return player1Score;
        }
        else return player2Score;
    }
    
    public void pass()//This method helps to update the class variable pass
    {
        if (this.passCount==0){
               
        passCount++;
        }else{
         endGame();       
        }
    }    
    public void quit(){
    
    }
    public void endGame()//the GameUI is updated when the endgame happens
    { 
        System.out.print(GameBoard.tempScore);
        //connection();//database connection to save data.
       JOptionPane.showMessageDialog(null,"",victory(),JOptionPane.INFORMATION_MESSAGE);
        this.gameEnd=true;
    }
    
    public void resetTimer(){
        
    }
    
    
    
    public static void  resetPass(){
        passCount=0;
    }
    public static boolean getPlayerTurn(){
        return playerTurn;
    }
    public static boolean playerTurnNext(){
        playerTurn=!playerTurn;
    return playerTurn;
    }
    public static String victory(){
    if (player1Score>player2Score){
        return player1+" win with "+player1Score+" score";
    }else if(player2Score<player1Score)
        return player2+" win with "+player2Score+" score";
        else
        return "draw";
}
    //new user validation
    public boolean checkUser(String user1,String user2,String pass1,String pass2){
    return (connect.checkUser(user1,pass1,0) && connect.checkUser(user2, pass2,0));
    }
    // admin validation
    public boolean checkAdmin(String user,String pass){
        return (connect.checkUser(user,pass,1));
    }
    public boolean insertUser(String name, String pass){
        return connect.insertUser(name, pass,0);
        
    }
}
