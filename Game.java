package goGame;

import com.mysql.jdbc.Connection;
import java.sql.*;
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
    public static int player1Color,player2Color;
    public static int timer;
    GameBoard gmBoard;    
    static int play1;
    static int play2;
    static int playerTurn=0;
    public Game() //constructor 
    {
        play1=0;
        play2=0;
        newAttr=0;
        passCount=0;
        blackPieces=0;
        whitePieces=0;
        
        player1="akash";
        player2="sanjay";
        player1Color=1;
        player2Color=2;
        timer=25;
        gmBoard=new GameBoard();
//GameBoard gmBoard=new GameBoard();
    }
    
    public static void updateScore(int t,int score ){
        if(t%2!=0){
            play1+=score;
            System.out.println(play2+"score1 "+player1);
        }
        else  
            play2+=score;
        System.out.println(play2+"score2 "+player2);
        
        }
    
    public void score(){
    }
    public void victory(){
    }
    public void endTurn(){
    
    }
    public void pass()//This method helps to update the class variable pass
    {
        if (this.passCount==0){
               
        passCount++;
        System.out.println(passCount+"1st pass");
        this.endTurn();
        }else{
        
                
        System.out.println(passCount+"2nd pass");
         endGame();       
        }
    }    
public void play(){
    }
    public void quit(){
    
    }
    public void endGame()//the GameUI is updated when the endgame happens
    { 
        System.out.print(GameBoard.tempScore);
        //connection();//database connection to save data.
       JOptionPane.showMessageDialog(null,"","END",JOptionPane.INFORMATION_MESSAGE);
        this.gameEnd=true;
    }
    
    public void resetTimer(){
        
    }
    
    public static void  resetPass(){
        passCount=0;
    }

    public static boolean getPlayerTurn(){
        
        playerTurn++;
        return (playerTurn%2==0);
    }
/*private static void connection(){
    String url = "jdbc:mysql://localhost:3306/";
        String dbName = "godb";
        String userName = "gouser"; 
        String password = "gopass";
        
        Connection db = null;
try
{
            System.out.println("Connected to the DB");
            db = (Connection) DriverManager.getConnection(url+dbName,userName,password);
    Statement query = (Statement) db.createStatement(); 
    //query.executeUpdate("insert into goodb(playerName,playerPassword,isAdmin,playerColor,timer) values('akash', 'aa',false,1,12) where playerName="+);

    db.close();
            System.out.println("closed connection");
        } catch (SQLException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }

}
*/
}