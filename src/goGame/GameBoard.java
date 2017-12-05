/*
File Name: GameBoard.java
Description: set up the game board to play, Logic when a player makes a move, when a capture happens 
Authors: Markus And Smit
*/
package goGame;

	/*
        Authors: Smit and Markus (Pair programming)
        Description: class definition along with its member variables and methods
    */
public class GameBoard {

    int[][] gameGrid = new int[9][9];
    int[][] gameGridPast = new int[9][9];
    int piece;
    public static int  tempScore;
    boolean koRule;
    int turn;
	
	//Initializes all the variables in Constructor 
    GameBoard() {

        for (int i = 0; i < gameGrid.length; i++) {
            for (int k = 0; k < gameGrid.length; k++) {
                gameGrid[i][k] = 0;
                gameGridPast[i][k] = 0;
            }
        }
        piece = 1;
        tempScore = 0;
        koRule=false;
    }
    
	/*
        Authors: Smit and Markus (Pair programming)
        Description: game logic when a player makes a move
    */
    public int move(int xPostion, int yPostion) {
        int [][] backUp = new int [9][9];
        for (int i = 0; i < gameGrid.length; i++) {
                for (int k = 0; k < gameGrid.length; k++) {
                    backUp[i][k]=gameGrid[i][k];
                }
        }
        
        if (Game.getPlayerTurn()) // calls Game.getPlayerTurn to get the turn of the player true for white and false for black
            piece = 2;
        else
            piece = 1;
        
        if (gameGrid[xPostion][yPostion] != 0)
            return 1;
        
        gameGrid[xPostion][yPostion] = piece;
        
     
        capture(xPostion,yPostion);
        
        if(surround(xPostion,yPostion)){// checks if the move is sucide move if true then changes the board back to befor the piece was place and return Sucide move
            gameGrid=backUp;
            return 2;
        }
		
		//logic to check KO rule
        if(koRule){
            for (int i = 0; i < gameGrid.length; i++) {
                for (int k = 0; k < gameGrid.length; k++) {
                    if (gameGrid[i][k] != gameGridPast[i][k]) {
                        koRule = false;
                        break;
                    }
                }
                if (!koRule)
                    break;
            }
        }
		
        if (koRule) {
            tempScore=0;
            for (int i = 0; i < gameGrid.length; i++) {
                for (int k = 0; k < gameGrid.length; k++) {
                    gameGrid[i][k]=backUp[i][k];
                }
            }
            return 3;
        }
        
        if(tempScore==1){
            for (int i = 0; i < gameGrid.length; i++) {
                for (int k = 0; k < gameGrid.length; k++) {
                    gameGridPast[i][k]=backUp[i][k];
                }
            }
            koRule = true;
        }
        

        Game.updateScore(Game.getPlayerTurn(),tempScore);
        tempScore=0;
        Game.playerTurnNext();
        //Game.setPass(0);
        Game.resetPass();
        return 0;

        
    }
    
	/*
        Author: Markus
        Description: game logic for a piece's sourrounding space
    */
    private boolean surround(int xPostion, int yPostion) {//a move is suicide if no open space is around the piece or its group 
        int tempPiece = gameGrid[xPostion][yPostion];
        if(xPostion+1<=8)
            if(gameGrid[xPostion+1][yPostion]==0)
                return false;
        if(xPostion-1>=0)
            if(gameGrid[xPostion-1][yPostion]==0)
                return false;
        if(yPostion+1<=8)
            if(gameGrid[xPostion][yPostion+1]==0)
                return false;
        if(yPostion-1>=0)
            if(gameGrid[xPostion][yPostion-1]==0)
                return false;
        if(xPostion+1<=8) // make sure the index is in bound
            if (gameGrid[xPostion+1][yPostion]==tempPiece){// checks the piece at x+1
                gameGrid[xPostion][yPostion]=3;
            if(surround(xPostion+1,yPostion)){
                gameGrid[xPostion][yPostion]=tempPiece;
            }else{
                gameGrid[xPostion][yPostion]=tempPiece;
                return false;
            }
        }if(xPostion-1>=0)// make sure the index is in bound
            if (gameGrid[xPostion-1][yPostion]==tempPiece){// checks the piece at x-1
                gameGrid[xPostion][yPostion]=3;
            if(surround(xPostion-1,yPostion)){
                gameGrid[xPostion][yPostion]=tempPiece;
            }else{
                gameGrid[xPostion][yPostion]=tempPiece;
                return false;
            }
        }if(yPostion+1<=8)// make sure the index is in bound
            if (gameGrid[xPostion][yPostion+1]==tempPiece){// checks the piece at y+1
                gameGrid[xPostion][yPostion]=3;
            if(surround(xPostion,yPostion+1)){
                gameGrid[xPostion][yPostion]=tempPiece;
            }else{
                gameGrid[xPostion][yPostion]=tempPiece;
                return false;
            }
        }if(yPostion-1>=0)// make sure the index is in bound
            if (gameGrid[xPostion][yPostion-1]==tempPiece){// checks the piece at y-1
                gameGrid[xPostion][yPostion]=3;
            if(surround(xPostion,yPostion-1)){
                gameGrid[xPostion][yPostion]=tempPiece;
            }else{
                gameGrid[xPostion][yPostion]=tempPiece;
                return false;
            }
        }
        return true;// can only reach here if their are no surounding space == 0 
    }
    
	/*
        Author: Markus
        Description: game logic for to capture a piece
    */	
    private void capture(int xPostion,int yPostion) { // checks if the piece place is next 2 a enemy piece if so then it cehcks if that emey piece is sround if so then it count those pices sets them to 0 and keeps track in tempScore
        int opPiece;
        if(piece==2)
            opPiece=1;
        else
            opPiece=2;
        if(xPostion+1<=8)
            if(gameGrid[xPostion+1][yPostion]==opPiece)
                if(surround(xPostion+1,yPostion))
                    take(xPostion+1,yPostion);
        if(xPostion-1>=0)
            if(gameGrid[xPostion-1][yPostion]==opPiece)
                if(surround(xPostion-1,yPostion))
                    take(xPostion-1,yPostion);
        if(yPostion+1<=8)
            if(gameGrid[xPostion][yPostion+1]==opPiece)
                if(surround(xPostion,yPostion+1))
                    take(xPostion,yPostion+1);
        if(yPostion-1>=0)
            if(gameGrid[xPostion][yPostion-1]==opPiece)
                if(surround(xPostion,yPostion-1))
                    take(xPostion,yPostion-1);
        for (int i = 0; i < gameGrid.length; i++) {
            for (int k = 0; k < gameGrid.length; k++) {
                if(gameGrid[i][k]==3){
                    tempScore++;
                    gameGrid[i][k]=0;
                }
            }
        }
       
    }  
    
	/*
        Author: Markus
    */
    private void take(int xPostion, int yPostion) {
        int tempPiece = gameGrid[xPostion][yPostion];
        if(xPostion+1<=8) // make sure the index is in bound
            if (gameGrid[xPostion+1][yPostion]==tempPiece){// checks the piece at x+1
                gameGrid[xPostion][yPostion]=3;
                take(xPostion+1,yPostion);
        }if(xPostion-1>=0)// make sure the index is in bound
            if (gameGrid[xPostion-1][yPostion]==tempPiece){// checks the piece at x-1
                gameGrid[xPostion][yPostion]=3;
                take(xPostion-1,yPostion);
        }if(yPostion+1<=8)// make sure the index is in bound
            if (gameGrid[xPostion][yPostion+1]==tempPiece){// checks the piece at y+1
                gameGrid[xPostion][yPostion]=3;
                take(xPostion,yPostion+1);
        }if(yPostion-1>=0)// make sure the index is in bound
            if (gameGrid[xPostion][yPostion-1]==tempPiece){// checks the piece at y-1
                gameGrid[xPostion][yPostion]=3;
                take(xPostion,yPostion-1);    
        }
        gameGrid[xPostion][yPostion]=3;
    }
    
	/*
        Author: Markus
    */   
   public int[][] getGameGrid(){
       return gameGrid;
   }
   
	/*
        Author: Smit
		Description : to reset a board's pieces' position to previous position when an illegal move is made
    */
    public void resetBoard(){
       for (int i = 0; i < gameGrid.length; i++) {
          for (int k = 0; k < gameGrid.length; k++) {
              gameGrid[i][k] = 0;
              gameGridPast[i][k] = 0;
          }
      }
  }
}
