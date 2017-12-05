/*
File Name: Game.java
Description: Use to handle all the database connection and queries
Authors: Sanjay And Akash

*/

package goGame;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Game //Main class
{
    /*
        Author: Sanjay
        Description: class definition along with its member variables and methods
    */
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

    static boolean playerTurn=false;//false for black
    
    public Game() //constructor 
    {
        /*
        Author: Sanjay
        Description: used to connect the system with the database
        */
        
        connect= new DBConnect();
        player1Score=0;
        player2Score=0;
        
        newAttr=0;
        passCount=0;
        blackPieces=0;
        whitePieces=0;
        player1Color=1;
        player2Color=2;
        timer=connect.gettimer();
        gmBoard=new GameBoard();
        list = new ArrayList();
        
        list=connect.list; 
        
        System.out.println("Here in GAME:");
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }
    }
    
    public static void updateScore(boolean white,int score )
    {
        /*
        Author: Smit
        Description: used to update the score
        */
        
        if(white==false){
            player1Score+=score;
            System.out.println(player1Score+"score blackPlayer "+player1);
        }
        else  
            player2Score+=score;
        System.out.println(player2Score+"score whitePlayer "+player2);
        
        }
    
    public static int getScore(boolean color)
    {
        /*
        Author: Sanjay and akash
        Description: used to return the player color
        */
        if (color==false){
            return player1Score;
        }
        else return player2Score;
    }
    
    public void pass()
    {
        /*
        Author: Akash
        Description: This method helps to update the class variable pass
        */
        
    	//new code by markus
        System.out.println(passCount);
        if(playerTurn) {
            ++player2Score;
            System.out.println("test");
        }
        else
            player1Score++;
        
        //end of new code
    	if (this.passCount==0){       
        passCount++;
        playerTurnNext();
        }else{
         this.gameEnd=true;
         endGame();
         
        }
    } 
    
    //author Tera
    public void recordGame(String player1, String player2, int blkscore, int whtscore, int victoryCode) {
    	
    	//make new string in this format Blk: player1 vs wht: player2 with score of blkscore vs wht score
    	//save string to database
    	
    }
    public void reset(){//markus made
        player1Score=0;
        player2Score=0;
        passCount=0;
        player1Color=1;
        player2Color=2;
        timer=connect.gettimer();
        gmBoard=new GameBoard();
        list = new ArrayList();
        
        list=connect.list; 
        
        System.out.println("Here in GAME:");
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }
    }
    public void endGame()
    { 
        /*
        Author: Sanjay
        Description: set score into database after the game ends
        */
        
        connect.setScore(player1, player1Score, player1Color);
        connect.setScore(player2, player2Score, player2Color);
        //JOptionPane.showMessageDialog(null,"Game is end","GAME END",JOptionPane.INFORMATION_MESSAGE);
        /*
        passCount=0;
        player2Score=0;
        player1Score=0;
        playerTurn=false;
        */
        
    }
    
    public boolean getGameEnd() {
    	return gameEnd;
    }
    public void setGameEnd(){//markus add
        gameEnd=false;
    }
    
    public int getTimer()
    {
        /*
        Author: Sanjay
        Description: get timer from the database
        */
        return(connect.gettimer());
    }
    
    public void updateTimer(int timer)
    {
        /*
        Author: Sanjay
        Description: set timer into database after the successful admin login
        */
        connect.setTimer(timer);
        
    }
    
    public static void  resetPass(){
        /*
        Author: Akash
        Description: reset the pass value after 2nd pass is not done consecutively 
        */
        passCount=0;
    }
    
    public static boolean getPlayerTurn(){
        /*
        Author: Akash
        Description: get the player turn
        */
        
        return playerTurn;
    }
    
    public static boolean playerTurnNext(){
        /*
        Author: Akash
        Description: get next player turn
        */
        playerTurn=!playerTurn;
        return playerTurn;
    }
    
    public static int victory(){
    if (player1Score>player2Score){
        return 1; //player 1 wins
    }else if(player2Score>player1Score)
        return 2; //player 2 wins
        else
        return 3; //draw
    }
    
    public int getPassCount(){
    	return passCount;
    }
    
    public boolean checkUser(String user1,String user2,String pass1,String pass2){
        /*
        Author: Sanjay
        Description: check user for login and set username into respective players
        */
        if(connect.checkUser(user1,pass1) && connect.checkUser(user2, pass2))
        {
            player1=user1;
            player2=user2;
        }
        
        return (connect.checkUser(user1,pass1) && connect.checkUser(user2, pass2));
    }
    
    public boolean checkAdmin(String user,String pass){
        /*
        Author: Akash
        Description: admin validation
        */
        return (connect.checkAdmin(user,pass));
    }
    
    public boolean insertUser(String name, String pass){
        /*
        Author: Akash
        Description: admin validation
        */
        return connect.insertUser(name, pass,0);
        
    }
}
