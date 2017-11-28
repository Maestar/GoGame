package goGame;

/* Coded by Jason Miller and Tera Benoit
 * This class creates/updates the UI elements of the game and its game board. */
import static goGame.Game.list;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import java.util.ArrayList;

public class GoGameUI extends JFrame {
    //huge list of variables for ALL the ui panels.
    //Variables for the GameUI

    static ArrayList list;
    int[][] goGrid = new int[9][9];
    public int[][] updatedBoard = new int[9][9];
    Game myGame = new Game();
    GameBoard myGameBoard = new GameBoard();
    JPanel gamePanel = new JPanel();
    private int errorCode;
    private static final int TURN_TIMER = 1000;

    //Used by GameBoard.
    JPanel boardPanel = new JPanel();

    //Variables for showLoginUI.
    JPanel loginPanel = new JPanel();
    String userInput1;
    String userInput2;

    String userPass1;
    String userPass2;
    JTextField usernameField1 = new JTextField(10);
    JTextField usernameField2 = new JTextField(10);

    JPasswordField passwordField1 = new JPasswordField(10);
    JPasswordField passwordField2 = new JPasswordField(10);

    //variables for the admin login screen
    String adminName;
    String adminPass;
    JTextField adminNameField = new JTextField(10);
    JPasswordField adminPassField = new JPasswordField(10);

    //This is the length of the turn timer in seconds.
    //It is currently set in the actionlistener in showTimerUI. It can be modified later to draw the timer setting from the database.
    //Change the setting of both of these turnTimeLimits to be a method call to the Database that sets them to equal the saved turn time limit.
    private int turnTimeLimit = 30;
    private int turnTimeLimitSaved = 30;
    private Timer turnTimer;

    //This is the current playerTurn. It starts as false (black).
    private boolean playerTurn = false;

    //Variables displayed by GUI that are fetched from the business code.
	JLabel scoreAmtWht = new JLabel();
   	JLabel scoreAmtBlk = new JLabel();

    String blkPlayerName = "Player 01";
    String whtPlayerName = "Player 02";

    //variables for the TitleUI
    JPanel titlePanel = new JPanel();

    //Panels for the other screens accessed from the title screen.
    JPanel timerPanel = new JPanel();
    JPanel newUserPanel = new JPanel();
    JPanel recordsPanel = new JPanel();
    JPanel adminPanel = new JPanel();

    //constructor that builds the UI
    GoGameUI() {
        //Initializes the goGrid to have no pieces (all 0s).
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                goGrid[x][y] = 0;
            }
        }

        //Initializes the UI.
        showGameUI(goGrid);
        showTimerUI();
        showNewUserUI();
        showRecordsUI();
        showLoginUI();
        showTitleUI();
        showAdminLoginUI();
        add(titlePanel);
    }

    /* ############### ACTUAL GAME GUI PANEL ####################### */
    //Updates the UI. Needs an int[][] array to update the board.
    //showGameUI sort of coded by both Jason and Tera with changes by both actively.
    private void showGameUI(int[][] board) {

        //Components displayed via the GUI.
        JButton exitbtn = new JButton("Exit >>");
        JButton passbtn = new JButton("Pass Turn");
        passbtn.setAlignmentX(Component.CENTER_ALIGNMENT);	//Aligns the button horizontally.

        JLabel timer = new JLabel("Time Left: .");
        JLabel countDown = new JLabel(Integer.toString(turnTimeLimit));

        JLabel BlkPlayerLabel = new JLabel("Black Player: ");
        JLabel WhtPlayerLabel = new JLabel("White Player: ");
        scoreAmtWht.setText(Integer.toString(myGame.getScore(true)));
	    scoreAmtBlk.setText(Integer.toString(myGame.getScore(false)));

        JLabel BlkPlayerNameLabel = new JLabel(Game.player1); //myGame.getPlayerName();
        JLabel WhtPlayerNameLabel = new JLabel(Game.player2); //myGame.GetPlayerName();

        JLabel scoreLabelblk = new JLabel("Score: ");
        JLabel scoreLabelwht = new JLabel("Score: ");
        System.out.println("blkScoreUI " + blkScore);
        System.out.println("whiteScoreUI " + whtScore);
        JLabel scoreAmtWht = new JLabel(whtScore);
        JLabel scoreAmtBlk = new JLabel(blkScore);

        //timer for countDown
        turnTimer = new Timer(TURN_TIMER, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                int i = turnTimeLimit;

                if (i <= 0) {
                    stopTimer();
                    //myGame.pass();
                    //...Update the GUI...
                } else {
                    i = decrement();
                    updateTimer(countDown, i);
                    //System.out.println(i);
                }

            }
        });

        turnTimer.start();

        //Action listener for the pass button (calls pass() in game).
        passbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                myGame.pass();
                //This changes the player turn.

            }
        });

        //Action listener for the exit button (calls quit() in game).
        exitbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove(gamePanel);
                resetGame();
                add(titlePanel);
                repaint();
                revalidate();
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
        JPanel topRightFrame = new JPanel();

        topFrame.setLayout(new BorderLayout());
        topRightFrame.setLayout(new BorderLayout());

        topFrame.add(exitbtn, BorderLayout.EAST);
        topFrame.add(topRightFrame, BorderLayout.WEST);

        topRightFrame.add(timer, BorderLayout.WEST);
        topRightFrame.add(countDown, BorderLayout.EAST);

        //score grids for displaying user score.
        JPanel blkScoreGrid = new JPanel();
        blkScoreGrid.setLayout(new GridLayout(2, 2));
        blkScoreGrid.add(BlkPlayerLabel);
        blkScoreGrid.add(BlkPlayerNameLabel);
        blkScoreGrid.add(scoreLabelblk);
        blkScoreGrid.add(scoreAmtBlk);

        JPanel whtScoreGrid = new JPanel();
        whtScoreGrid.setLayout(new GridLayout(2, 2));
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
        //JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout());
        gamePanel.add(topFrame, BorderLayout.NORTH);
        gamePanel.add(middleFrame, BorderLayout.CENTER);
        gamePanel.add(bottomFrame, BorderLayout.SOUTH);

        //add(gamePanel);      	
    }

    //coded by: Jason Miller
    //Updates the game board and returns the updated JPanel.
    private JPanel drawBoard(int[][] board) {
        //Creates and styles the JPanel.
        boardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        boardPanel.setLayout(new GridLayout(9, 9, 0, 0));
        boardPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 4));

        JButton[][] buttonGrid = new JButton[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {

                //Creates and styles the JButton.
                //The icon is based on whether the piece is absent, black, or white.
                Icon icon;
                if (board[x][y] == 1) {
                    icon = new ImageIcon("/home/akash/Desktop/JavaApplication4/src/goGame/black.png");
                } else if (board[x][y] == 2) {
                    icon = new ImageIcon("/home/akash/Desktop/JavaApplication4/src/goGame/white.png");
                } else {
                    icon = new ImageIcon("/home/akash/Desktop/JavaApplication4/src/goGame/icon.png");
                }
                JButton gridButton = new JButton(icon);
                gridButton.setIconTextGap(0);
                gridButton.setContentAreaFilled(false);
                gridButton.setBorderPainted(false);
                gridButton.setBorder(null);
                //gridButton.setPreferredSize(new Dimension(40, 40));

                //Loads the buttonGrid with the gridButtons so that we can use it to find the button's location later.
                buttonGrid[x][y] = gridButton;

                //Action listener for each button in the grid.
                gridButton.addActionListener(new ActionListener() {	//coded by Jason, internal if statements by Tera
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        JButton button = (JButton) event.getSource();
                        for (int x = 0; x < 9; x++) {
                            for (int y = 0; y < 9; y++) {
                                //If the button matches the one in the button grid, call move with x,y coordinate
                                if (buttonGrid[x][y] == button) {
                                    System.out.println(x + " " + y);
                                    errorCode = myGameBoard.move(x, y);
                                    updatedBoard = myGameBoard.getGameGrid();

                                    //handle the move in the GUI
                                    if (errorCode >= 1) {
                                        printError(errorCode);
                                    } else {
                                        updateUI(updatedBoard, boardPanel);
                                    }

                                }
                            }
                        }
                    }
                });
                boardPanel.add(gridButton);
            }
        }

        return boardPanel;
    }

    /* #### Stop Timer Method. #### */
    //Coded by: Tera Benoit
    //simple stop method.
    private void stopTimer() {
        turnTimer.stop();
    }

    //because action handlers are dumb.
    private int decrement() {
        turnTimeLimit--;
        return turnTimeLimit;
    }

    //Tera
    //update the gui to show the number counting down.
    private void updateTimer(JLabel label, int time) {
        label.removeAll();
        label.setText(Integer.toString(time));
        label.revalidate();
        label.repaint();
    }

    /* #### Methods for Handling Move and Updating the GUI Board #### */
    //Tera
    //print error messages based on error codes passed back from move.
    private void printError(int s) {
        int errorCode = s;

        switch (errorCode) {
            case 1:
                JOptionPane.showMessageDialog(gamePanel,
                        "There is Already a Stone there.",
                        "Move Error",
                        JOptionPane.ERROR_MESSAGE);

                break;
            case 2:
                JOptionPane.showMessageDialog(gamePanel,
                        "That move is suicidal!",
                        "Move Error",
                        JOptionPane.ERROR_MESSAGE);
                break;
            case 3:
                JOptionPane.showMessageDialog(gamePanel,
                        "You've Broken the Ko Rule.",
                        "Move Error",
                        JOptionPane.ERROR_MESSAGE);
                break;
        }

    }

    //Receives an updated game board from GameBoard and uses it to update the UI.
    // Coded by: Tera Benoit over two days of constant suffering and bug chasing.
    private void updateUI(int[][] board, JPanel frame) {
			JPanel boardGUI = frame;
			//hide board
			gamePanel.setVisible(false);
			//reset timer
			stopTimer();
			turnTimeLimit = turnTimeLimitSaved;
			turnTimer.start();
			//clear GUI and Board
			gamePanel.removeAll();
			boardGUI.removeAll();
			//pass new content to the gui
			showGameUI(board);
			scoreAmtWht.setText(Integer.toString(myGame.getScore(true)));
	       	scoreAmtBlk.setText(Integer.toString(myGame.getScore(false)));
			if(myGame.getPlayerTurn() == false){
				playerTurn.setText("Black Player's Turn");
			}
			else{
				playerTurn.setText("White Player's Turn");
			}
			//redraw GUI
			gamePanel.revalidate();
			gamePanel.repaint();
			boardGUI.revalidate();
			boardGUI.repaint();
			//show board
			gamePanel.setVisible(true);
		}

    //Coded by Tera.
    //Resets the game board when the game is exited back to title screen.
    public void resetGame() {
        //hide board
        gamePanel.setVisible(false);
        //reset timer
        turnTimeLimit = turnTimeLimitSaved;
        //clear GUI and Board
        gamePanel.removeAll();
        boardPanel.removeAll();
        //pass new content to the gui
        //myGameBoard.resetBoard();
        showGameUI(goGrid);
        //redraw GUI
        gamePanel.revalidate();
        gamePanel.repaint();
        boardPanel.revalidate();
        boardPanel.repaint();
        //show board
        gamePanel.setVisible(true);
    }
    // Coded By Tera Benoit

    /* ##################### TITLE UI FRAME ########################## */
    private void showTitleUI() {
        //creating and customizing the label
        JLabel title = new JLabel("Go Game");
        title.setFont(new Font(title.getFont().getFontName(), title.getFont().getStyle(), 90));

        //creating and customizing the buttons
        JButton play = new JButton("Play");
        JButton timerbtn = new JButton("Set Timer");
        JButton newUser = new JButton("New User");
        JButton records = new JButton("Records");
        //set font size on the buttons
        play.setFont(new Font(play.getFont().getFontName(), play.getFont().getStyle(), 30));
        timerbtn.setFont(new Font(timerbtn.getFont().getFontName(), timerbtn.getFont().getStyle(), 30));
        newUser.setFont(new Font(newUser.getFont().getFontName(), newUser.getFont().getStyle(), 30));
        records.setFont(new Font(records.getFont().getFontName(), records.getFont().getStyle(), 30));

        //action listeners for the buttons
        //For when the user presses "Play".
        play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove(titlePanel);
                add(loginPanel);
                repaint();
                revalidate();
            }
        });

        //For when the user presses "Set Timer".
        timerbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove(titlePanel);
                add(adminPanel);
                repaint();
                revalidate();
            }
        });

        //For when the user presses "New User".
        newUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove(titlePanel);
                add(newUserPanel);
                repaint();
                revalidate();
            }
        });

        //For when the user presses "Records".
        records.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove(titlePanel);
                add(recordsPanel);
                repaint();
                revalidate();
            }
        });

        //frame for the title
        JPanel topFrame = new JPanel();
        topFrame.add(title);

        //frame that contains all the buttons to navigate to other menus
        JPanel middleFrame = new JPanel();

        middleFrame.setLayout(new GridLayout(0, 1, 0, 10));
        middleFrame.add(play);
        middleFrame.add(timerbtn);
        middleFrame.add(newUser);
        middleFrame.add(records);

        //the following frames are just for formatting and contain only rigid invisible space.
        JPanel rightFrame = new JPanel();

        rightFrame.setLayout(new BoxLayout(rightFrame, BoxLayout.LINE_AXIS));
        rightFrame.add(Box.createRigidArea(new Dimension(150, 0)));

        JPanel leftFrame = new JPanel();

        leftFrame.setLayout(new BoxLayout(leftFrame, BoxLayout.LINE_AXIS));
        leftFrame.add(Box.createRigidArea(new Dimension(150, 0)));

        JPanel bottomFrame = new JPanel();

        bottomFrame.setLayout(new BoxLayout(bottomFrame, BoxLayout.LINE_AXIS));
        bottomFrame.add(Box.createRigidArea(new Dimension(0, 150)));
        //end of superficial padding frames.

        //main container in which all layout frames are added. 
        titlePanel.setLayout(new BorderLayout());
        titlePanel.add(topFrame, BorderLayout.NORTH);
        titlePanel.add(middleFrame, BorderLayout.CENTER);
        titlePanel.add(leftFrame, BorderLayout.WEST);
        titlePanel.add(rightFrame, BorderLayout.EAST);
        titlePanel.add(bottomFrame, BorderLayout.SOUTH);

        //add(titlePanel);
    }

    //The following three coded by Jason Miller.
    /* ##################### TIMER UI FRAME ########################## */
    //This creates the Set Timer page.
    private void showTimerUI() {
        //Sets and formats the title for the page.
        JLabel title = new JLabel("Set Turn Timer");
        title.setFont(new Font(title.getFont().getFontName(), title.getFont().getStyle(), 50));

        //Exit button to return to Title screen.
        JButton exit = new JButton("Exit >>");

        //Action listener for the Exit button. Returns to the title screen.
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove(timerPanel);
                add(titlePanel);
                repaint();
                revalidate();
            }
        });

        //Instruction label for setting the timer.
        JLabel instructions = new JLabel("Select the desired turn timer. You may exit after selecting a turn timer duration.");

        //The dropdown menu for the timer options.
        String[] options = {"30 seconds", "45 seconds", "1 minute", "1.5 minutes", "3 minutes", "5 minutes"};
        JComboBox timerList = new JComboBox(options);

        //Action listener for the dropdown menu.
        //ADD A METHOD CALL AFTER turnTimeLimit IS SET AT EVERY CASE TO SET THE DATABASE'S TIMER.
        //Each case is a time selected. Default is 30 seconds.
        timerList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                String option = (String) cb.getSelectedItem();
                switch (option) {
                    case "30 seconds":
                        turnTimeLimitSaved = 30;
                        break;
                    case "45 seconds":
                        turnTimeLimitSaved = 45;
                        break;
                    case "1 minute":
                        turnTimeLimitSaved = 60;
                        break;
                    case "1.5 minutes":
                        turnTimeLimitSaved = 90;
                        break;
                    case "3 minutes":
                        turnTimeLimitSaved = 180;
                        break;
                    case "5 minutes":
                        turnTimeLimitSaved = 300;
                        break;
                    default:
                        turnTimeLimitSaved = 30;
                }
            }
        });

        //The frames that contain the buttons, labels, etc.
        JPanel topFrame = new JPanel(); //Contains the title and exit button.
        topFrame.add(title);
        topFrame.add(exit);
        JPanel middleFrame = new JPanel(); //Contains the instructions and timer select.
        middleFrame.setLayout(new BoxLayout(middleFrame, BoxLayout.Y_AXIS));
        middleFrame.add(instructions);
        middleFrame.add(timerList);

        //The frames used for padding.
        JPanel leftFrame = new JPanel();
        JPanel rightFrame = new JPanel();
        JPanel bottomFrame = new JPanel();
        leftFrame.add(Box.createRigidArea(new Dimension(150, 0)));
        rightFrame.add(Box.createRigidArea(new Dimension(150, 0)));
        bottomFrame.add(Box.createRigidArea(new Dimension(0, 430)));

        //Adds all of the frames to the panel.
        timerPanel.setLayout(new BorderLayout());
        timerPanel.add(topFrame, BorderLayout.NORTH);
        timerPanel.add(leftFrame, BorderLayout.WEST);
        timerPanel.add(middleFrame, BorderLayout.CENTER);
        timerPanel.add(rightFrame, BorderLayout.EAST);
        timerPanel.add(bottomFrame, BorderLayout.SOUTH);
    }

    /* ##################### NEW USER UI FRAME ########################## */
    //This creates the New User page.
    private void showNewUserUI() {
        //Sets and formats the title for the page.
        JLabel title = new JLabel("Create New User");
        title.setFont(new Font(title.getFont().getFontName(), title.getFont().getStyle(), 65));
        //Exit button to return to Title screen.
        JButton exit = new JButton("Exit >>");

        //Action listener for the Exit button. Returns to the title screen.
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove(newUserPanel);
                add(titlePanel);
                repaint();
                revalidate();
            }
        });

        //Text fields for the creation of a new user.
        JLabel nameLabel = new JLabel("Username:");
        JTextField nameField = new JTextField(20);
        JLabel passLabel = new JLabel("New user:");
        JPasswordField passField = new JPasswordField(20);
        JButton submit = new JButton("Submit");

        //Action listener for the submit button.
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = nameField.getText(); //Assigns the input username to the String "username".
                String password = new String(passField.getPassword()); //Assigns the password to the "password" string.
                if (myGame.insertUser(username, password)==false){
                
                            JOptionPane.showMessageDialog(loginPanel,
                                "A Username already exist, please try again.",
                                "Login Error",
                                JOptionPane.ERROR_MESSAGE);
                
                  
                }
                
//Passes "username" and "password" to the database to create a new user/check if it can.
            
            }
        });

        //Frame that contains the title and exit button.
        JPanel topFrame = new JPanel();
        topFrame.add(title);
        topFrame.add(exit);

        //Frame that contains the input fields.
        JPanel middleFrame = new JPanel();
        middleFrame.setLayout(new BoxLayout(middleFrame, BoxLayout.Y_AXIS));
        middleFrame.add(nameLabel);
        middleFrame.add(nameField);
        middleFrame.add(passLabel);
        middleFrame.add(passField);
        middleFrame.add(submit);

        //Frames used for padding.
        JPanel leftFrame = new JPanel();
        JPanel rightFrame = new JPanel();
        JPanel bottomFrame = new JPanel();
        leftFrame.add(Box.createRigidArea(new Dimension(150, 0)));
        rightFrame.add(Box.createRigidArea(new Dimension(150, 0)));
        bottomFrame.add(Box.createRigidArea(new Dimension(0, 360)));

        //Adds all of the frames to the Panel.
        newUserPanel.setLayout(new BorderLayout());
        newUserPanel.add(topFrame, BorderLayout.NORTH);
        newUserPanel.add(leftFrame, BorderLayout.WEST);
        newUserPanel.add(middleFrame, BorderLayout.CENTER);
        newUserPanel.add(rightFrame, BorderLayout.EAST);
        newUserPanel.add(bottomFrame, BorderLayout.SOUTH);
    }

    /* ##################### RECORDS UI FRAME ########################## */
    //This creates the Records page.
    private void showRecordsUI() {
        //Sets and formats the title for the page.
        JLabel title = new JLabel("Records");
        title.setFont(new Font(title.getFont().getFontName(), title.getFont().getStyle(), 90));
        //Exit button to return to Title screen.
        JButton exit = new JButton("Exit >>");

        //Action listener for the Exit button. Returns to the title screen.
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove(recordsPanel);
                add(titlePanel);
                repaint();
                revalidate();
            }
        });

        //The labels, text field, and text area used to fetch and display a record.
        JLabel instructions = new JLabel("Input the username you would like to see the play records of and then press submit:");
        JTextField input = new JTextField(20);
        input.setPreferredSize(new Dimension(300, 20));
        input.setMaximumSize(input.getPreferredSize());
        JButton submit = new JButton("Submit");
        JTextArea recordsDisplay = new JTextArea();

        //Action listener for the submit button.
        //It fetches the text in the username field.
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = input.getText();
                if (username==null){
                    username="*";
                }    
//Pass the "username" string to the Database to fetch the appropriate record.
                //Append the record you received to the text field like this:
                list = new ArrayList();
                list = Game.list;
                System.out.println("record button is clicked");
                System.out.println(list);
                DBConnect db=new DBConnect();
                
                for (int i = 0; i < 10; i++) {
                    recordsDisplay.setText(db.getData(username).toString()+'\n'); //Use this to set text (may have to use a loop to set the lines properly.
                }
            }
        });

        //Frame that contains the title and exit button.
        JPanel topFrame = new JPanel();
        topFrame.add(title);
        topFrame.add(exit);

        //Frame that contains the instructions, username field, and records display.
        JPanel middleFrame = new JPanel();
        middleFrame.setLayout(new BoxLayout(middleFrame, BoxLayout.Y_AXIS));
        middleFrame.add(instructions);
        middleFrame.add(input);
        //middleFrame.add(Box.createVerticalGlue());
        middleFrame.add(submit);
        middleFrame.add(recordsDisplay);

        //Frame used for padding and the text area.
        JPanel bottomFrame = new JPanel();
        bottomFrame.add(Box.createRigidArea(new Dimension(0, 100)));

        //Adds all of the frames to the Panel.
        recordsPanel.setLayout(new BorderLayout());
        recordsPanel.add(topFrame, BorderLayout.NORTH);
        recordsPanel.add(middleFrame, BorderLayout.CENTER);
        recordsPanel.add(bottomFrame, BorderLayout.SOUTH);
    }

    /* ##################### LOGIN UI FRAME ########################## */
    //Coded by Tera.
    //This creates the Login page.
    private void showLoginUI() {

        //creating and customizing the ui elements.
        JLabel loginTitle = new JLabel("Player Login");
        loginTitle.setFont(new Font(loginTitle.getFont().getFontName(), loginTitle.getFont().getStyle(), 90));

        JButton loginbtn = new JButton("Login");

        //action listeners for the login button
        loginbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //myGame.username1=userInput1;
                  //  myGame.username2=userInput2;
                    //myGame.password1= userPass1;
                    //myGame.password2=userPass2;
                    //myGame.checkUser(); 
                    
                getCreds(usernameField1, usernameField2, passwordField1, passwordField2);
                   
                if (userInput1.equalsIgnoreCase(userInput2)) { // if the two usernames are the same it throws an error to the user and prevents them from logging in.
                    JOptionPane.showMessageDialog(loginPanel,
                            "You can not login as the same user for both Players.",
                            "Login Error",
                            JOptionPane.ERROR_MESSAGE);
                } 
                
                /* *Aakash*: Here is the section for the user logging in.
                     * I do not know what method you will be using to check if credentials are correct but this assumes it returns a boolean
                     * if they are correct and allows the UI to open the game menu.
                     *
                     * If they are incorrect and it returns false then if throws a login credentials incorrect and displays an error.
                     *
                     */
                else if(myGame.checkUser(userInput1,userInput2,userPass1,userPass2)){
                        remove(loginPanel);
                        add(gamePanel);
                        repaint();
                        revalidate();  
                    
                }
                        
                else{
                            JOptionPane.showMessageDialog(loginPanel,
                                "A Username or Password is incorrect, please try again.",
                                "Login Error",
                                JOptionPane.ERROR_MESSAGE);
                        }
                  
                }
            
        });

        JLabel name1 = new JLabel("Username: ");
        JLabel name2 = new JLabel("Username: ");

        JLabel pass1 = new JLabel("Password: ");
        JLabel pass2 = new JLabel("Password: ");

        JLabel user1 = new JLabel("Black Player");
        JLabel user2 = new JLabel("White Player");

        JPanel leftLogin = new JPanel();
        leftLogin.setLayout(new BoxLayout(leftLogin, BoxLayout.Y_AXIS));
        leftLogin.add(user1);
        leftLogin.add(name1);
        leftLogin.add(usernameField1);
        leftLogin.add(pass1);
        leftLogin.add(passwordField1);
        leftLogin.add(Box.createRigidArea(new Dimension(0, 290)));

        leftLogin.setBorder(new EmptyBorder(90, 180, 10, 10));

        JPanel rightLogin = new JPanel();
        rightLogin.setLayout(new BoxLayout(rightLogin, BoxLayout.Y_AXIS));
        rightLogin.add(user2);
        rightLogin.add(name2);
        rightLogin.add(usernameField2);
        rightLogin.add(pass2);
        rightLogin.add(passwordField2);
        rightLogin.add(Box.createRigidArea(new Dimension(0, 290)));

        rightLogin.setBorder(new EmptyBorder(90, 10, 10, 180));

        JPanel topFrame = new JPanel();
        topFrame.add(loginTitle);

        JPanel bottomFrame = new JPanel();
        bottomFrame.add(loginbtn);

        bottomFrame.setBorder(new EmptyBorder(10, 10, 200, 10));

        loginPanel.setLayout(new BorderLayout());

        loginPanel.add(topFrame, BorderLayout.NORTH);
        loginPanel.add(leftLogin, BorderLayout.WEST);
        loginPanel.add(rightLogin, BorderLayout.EAST);
        loginPanel.add(bottomFrame, BorderLayout.SOUTH);
    }

    private void showAdminLoginUI() {

        //creating and customizing the ui elements.
        JLabel adminTitle = new JLabel("Admin Login");
        adminTitle.setFont(new Font(adminTitle.getFont().getFontName(), adminTitle.getFont().getStyle(), 90));

        JButton adminLoginBtn = new JButton("Login");

        //action listeners for the login button
        adminLoginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                getCreds(adminNameField, adminPassField);
                 System.out.println(adminName);   
               if(myGame.checkAdmin(adminName,adminPass)){
                        remove(adminPanel);
                        add(timerPanel);
                        repaint();
                        revalidate();  
                    	
               }
               else{
                            JOptionPane.showMessageDialog(loginPanel,
                                "A Username or Password is incorrect, please try again.",
                                "Login Error",
                                JOptionPane.ERROR_MESSAGE);
                        }
                  
                   
               /* *Aakash*: Here is the section for the admin logging in.
                     * I do not know what method you will be using to check if credentials are correct but this assumes it returns a boolean
                     * if their credentials are correct and they ARE AN ADMIN
                     *
                     * If they are incorrect and it returns false then it throws a login credentials incorrect and displays an error.
                     *
                     * if(database.adminLogin(adminName, adminPass) == true){
                        remove(adminPanel);
                        add(setTimerPanel);
                        repaint();
                        revalidate();  
                    	}
                        else{
                            JOptionPane.showMessageDialog(loginPanel,
                                "A Username or Password is incorrect, please try again.",
                                "Login Error",
                                JOptionPane.ERROR_MESSAGE);
                        }
                 */
                //this is just placeholder code to make it function without the database for testing purposes.
                
            }
        });

        JLabel usrname = new JLabel("Username: ");
        JLabel passwd = new JLabel("Password: ");

        JPanel adminLogin = new JPanel();
        adminLogin.setLayout(new BoxLayout(adminLogin, BoxLayout.Y_AXIS));
        adminLogin.add(usrname);
        adminLogin.add(adminNameField);
        adminLogin.add(passwd);
        adminLogin.add(adminPassField);
        adminLogin.add(Box.createRigidArea(new Dimension(0, 290)));

        adminLogin.setBorder(new EmptyBorder(40, 180, 10, 180));

        JPanel topFrame = new JPanel();
        topFrame.add(adminTitle);

        JPanel bottomFrame = new JPanel();
        bottomFrame.add(adminLoginBtn);

        bottomFrame.setBorder(new EmptyBorder(10, 10, 250, 10));

        adminPanel.setLayout(new BorderLayout());

        adminPanel.add(topFrame, BorderLayout.NORTH);
        adminPanel.add(adminLogin, BorderLayout.CENTER);
        adminPanel.add(bottomFrame, BorderLayout.SOUTH);

    }

    //method for getting login data upon the push of the login button
    public void getCreds(JTextField usrname, JTextField usrname2, JPasswordField pass, JPasswordField pass2) {
        userInput1 = usrname.getText().toString();
        userInput2 = usrname2.getText().toString();

        userPass1 = new String(pass.getPassword());
        userPass2 = new String(pass2.getPassword());

        //System.out.println(userInput1 + " " + userInput2 + " " + userPass1 + " " + userPass2);
    }
    //overloaded check creds for admin

    public void getCreds(JTextField adName, JPasswordField adPass) {
        adminName = adName.getText().toString();
        adminPass = new String(adPass.getPassword());
    }
}
