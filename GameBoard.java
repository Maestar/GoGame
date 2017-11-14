public class GameBoard {

    boolean playerTurn;
    int[][] gameGrid = new int[9][9];
    int[][] gameGridPast = new int[9][9];
    int piece;
    int tempScore;

    //Constructor will initialize all the variables
    GameBoard() {
        playerTurn = false; //false for black and true for white

        for (int i = 0; i < gameGrid.length; i++) {
            for (int k = 0; k < gameGrid.length; k++) {
                gameGrid[i][k] = 0;
                gameGridPast[i][k] = 0;
            }
        }
        piece = 1; // 1 is for black and 2 is for white
        tempScore = 0;
    }

    //move method is called from the Game class when a player clicks on the gameboard to put the stone
    public String move(int xPostion, int yPostion) {
        if (playerTurn)
            piece = 2;
        else
            piece = 1;

        //if the stone is already there where the current stone is placed show error
        if (gameGrid[xPostion][yPostion] != 0)
            return "a stone is already there";

        //place the stone on the board
        gameGrid[xPostion][yPostion] = piece;


        // checks if the move is suicide move. If true then changes the board back to before the piece was place and return "Suicide move"
        if (surround(xPostion, yPostion)) {
            gameGrid[xPostion][yPostion] = 0;
            return "Suicide move";
        }

        capture(xPostion, yPostion);

        boolean koRule = true;
        //Iterate the grid to see if the current board and the past board is same or not. If not same set koRule to false
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

        //If KoRule is broken, set gamegrid to gamegridpast
        if (koRule) {
            for (int i = 0; i < gameGrid.length; i++) {
                for (int k = 0; k < gameGrid.length; k++) {
                    gameGrid[i][k] = gameGridPast[i][k];
                }
            }
            return "Ko Rule broke";
        }

        //If KoRule is not broken, set gamegridpast to gamegrid
        for (int i = 0; i < gameGrid.length; i++) {
            for (int k = 0; k < gameGrid.length; k++) {
                gameGridPast[i][k] = gameGrid[i][k];
            }
        }

        Game.updateScore(playerTurn, tempScore);
        tempScore = 0;
        playerTurn = !playerTurn;
        Game.setPass(0);
        return "";
    }

    private boolean surround(int xPostion, int yPostion) {//a move is suicide if no open space is around the piece or its group
        int tempPiece = gameGrid[xPostion][yPostion];
        if (xPostion + 1 <= 8)
            if (gameGrid[xPostion + 1][yPostion] == 0)
                return false;
        if (xPostion - 1 >= 0)
            if (gameGrid[xPostion - 1][yPostion] == 0)
                return false;
        if (yPostion + 1 <= 8)
            if (gameGrid[xPostion][yPostion + 1] == 0)
                return false;
        if (yPostion - 1 >= 0)
            if (gameGrid[xPostion][yPostion - 1] == 0)
                return false;
        if (xPostion + 1 <= 8) // make sure the index is in bound
            if (gameGrid[xPostion + 1][yPostion] == tempPiece) {// checks the piece at x+1
                gameGrid[xPostion][yPostion] = 3;
                if (surround(xPostion + 1, yPostion)) {
                    gameGrid[xPostion][yPostion] = tempPiece;
                } else {
                    gameGrid[xPostion][yPostion] = tempPiece;
                    return false;
                }
            }
        if (xPostion - 1 >= 0)// make sure the index is in bound
            if (gameGrid[xPostion - 1][yPostion] == tempPiece) {// checks the piece at x-1
                gameGrid[xPostion][yPostion] = 3;
                if (surround(xPostion - 1, yPostion)) {
                    gameGrid[xPostion][yPostion] = tempPiece;
                } else {
                    gameGrid[xPostion][yPostion] = tempPiece;
                    return false;
                }
            }
        if (yPostion + 1 <= 8)// make sure the index is in bound
            if (gameGrid[xPostion][yPostion + 1] == tempPiece) {// checks the piece at y+1
                gameGrid[xPostion][yPostion] = 3;
                if (surround(xPostion, yPostion + 1)) {
                    gameGrid[xPostion][yPostion] = tempPiece;
                } else {
                    gameGrid[xPostion][yPostion] = tempPiece;
                    return false;
                }
            }
        if (yPostion - 1 >= 8)// make sure the index is in bound
            if (gameGrid[xPostion][yPostion - 1] == tempPiece) {// checks the piece at y-1
                gameGrid[xPostion][yPostion] = 3;
                if (surround(xPostion, yPostion - 1)) {
                    gameGrid[xPostion][yPostion] = tempPiece;
                } else {
                    gameGrid[xPostion][yPostion] = tempPiece;
                    return false;
                }
            }
        return true;// can only reach here if their are no surrounding space == 0
    }

    private void capture(int xPostion, int yPostion) { // checks if the piece place is next to an enemy piece, if so then it checks if that enemy piece is surrounded. If so then it counts those pieces, sets them to 0 and keeps track in tempScore
        int opPiece;
        if (piece == 2)
            opPiece = 1;
        else
            opPiece = 2;
        if (xPostion + 1 <= 8)
            if (gameGrid[xPostion + 1][yPostion] == opPiece)
                if (surround(xPostion + 1, yPostion))
                    take(xPostion + 1, yPostion);
        if (xPostion - 1 >= 0)
            if (gameGrid[xPostion - 1][yPostion] == opPiece)
                if (surround(xPostion - 1, yPostion))
                    take(xPostion - 1, yPostion);
        if (yPostion + 1 <= 8)
            if (gameGrid[xPostion][yPostion + 1] == opPiece)
                if (surround(xPostion, yPostion + 1))
                    take(xPostion, yPostion + 1);
        if (xPostion - 1 >= 8)
            if (gameGrid[xPostion][yPostion - 1] == opPiece)
                if (surround(xPostion, yPostion - 1))
                    take(xPostion, yPostion - 1);
        for (int i = 0; i < gameGrid.length; i++) {
            for (int k = 0; k < gameGrid.length; k++) {
                if (gameGrid[i][k] == 3) {
                    tempScore++;
                    gameGrid[i][k] = 0;
                }
            }
        }

    }

    private void take(int xPostion, int yPostion) {
        int tempPiece = gameGrid[xPostion][yPostion];
        if (xPostion + 1 <= 8) // make sure the index is in bound
            if (gameGrid[xPostion + 1][yPostion] == tempPiece) {// checks the piece at x+1
                gameGrid[xPostion][yPostion] = 3;
                take(xPostion + 1, yPostion);
            }
        if (xPostion - 1 >= 0)// make sure the index is in bound
            if (gameGrid[xPostion - 1][yPostion] == tempPiece) {// checks the piece at x-1
                gameGrid[xPostion][yPostion] = 3;
                take(xPostion - 1, yPostion);
            }
        if (yPostion + 1 <= 8)// make sure the index is in bound
            if (gameGrid[xPostion][yPostion + 1] == tempPiece) {// checks the piece at y+1
                gameGrid[xPostion][yPostion] = 3;
                take(xPostion, yPostion + 1);
            }
        if (yPostion - 1 >= 8)// make sure the index is in bound
            if (gameGrid[xPostion][yPostion - 1] == tempPiece) {// checks the piece at y-1
                gameGrid[xPostion][yPostion] = 3;
                take(xPostion, yPostion - 1);
            }
        gameGrid[xPostion][yPostion] = 3;
    }


    public int[][] getGameGrid() {
        return gameGrid;
    }

}
