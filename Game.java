/**
 * Class to initiate logic and gameplay of tiktaktoe
 *
 * @author Meruzhan Sargsyan
 */
import java.util.Scanner;

public class Game{
    private String[][] board;
    private boolean player = true;
    private boolean inPlay = true;
    private boolean isX = true;
    private Scanner input = new Scanner(System.in);

    /** 
     * baord size the create the game with
     * creates a boardSize x boardSize 2D array initially with all 0 values
     *
     * @param (boardSize) size of the 2D array board
     */
    public Game(int boardSize) {
        if(boardSize <= 0) {
            throw new RuntimeException("board size must be greater than 0");
        }
        board = new String[boardSize][boardSize];
        for(int i = 0; i < boardSize; i++) {
            for(int j = 0; j < boardSize; j++) {
                board[i][j] = "0";
            }
        }
    }

    /**
     * helper method to initiate game
     * calls requried methods to begin
     */
    public void initate() {
        do {
            printBoard();
            makeMove();
            if(checkState()) {
                printBoard();
                inPlay = false;
            }
        } while(inPlay);
        System.out.println("Game is over _______ wins"); 
    }
    /**
     * getter method for board instance field 
     */
    public String[][] getBoard() {
        return board;
    }
    /** 
     * used to edit the 2x2 board array to place x and o s
     * also checks if position already exists
     * 
     * @param (v) Vector position of board to edit
     * @param (x) if true places an x character, o otherwise
     * @return true if successfully edited, false otherwise
     */
    public boolean editBoard(Vector v, boolean player) {
        try {
            int x = v.getX(), y = v.getY();
            if(!(board[x][y].equals("0"))) {
                System.out.println("Position already taken");
                return false;
            }
            if(player) {
                board[x][y] = "x";
                return true;
            }
            else {
                board[x][y] = "o";
                return true;
            }
        } catch(Exception ex) {
            throw new ArrayIndexOutOfBoundsException(ex.getMessage());
        }
    }

    /**
     * prints the board in this format:
     * _________                 _________                 _________
     * |       |                 |       |                 |       | 
     * |       | if there is x:  |   x   | if there is o:  |   o   |
     * |_______|                 |_______|                 |_______|
     */
    public void printBoard() {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                System.out.print("_________ ");
            }
            System.out.println();
            for(int j = 0; j < board[0].length; j++) {
                System.out.print("|       | ");
            }
            System.out.println();
            for(int j = 0; j < board[0].length; j++) {
                String value = board[i][j];
                if(value.equals("0")) {
                    System.out.print("|       | ");
                } else if(value.equals("x")) {
                    System.out.print("|   X   | ");
                } else if(value.equals("o")) {
                    System.out.print("|   O   | ");
                }
            }
            System.out.println();
            for(int j = 0; j < board[0].length; j++) {
                System.out.print("|_______| ");
            }
            System.out.println();
        }
    }

    /** 
     * checks the state of the game using helper methods
     *
     * @return true if any helped methods return true
     */
    public boolean checkState() {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                if(checkRow(new Vector(i, j))) return true;
                if(checkColumn(new Vector(i, j))) return true;
                if(checkDiagonals()) return true;
            }
        }
        return false;
    }
    
    /** 
     * helper method for checkState method that checks the left and right 
     * of all spots on the board
     *
     * @return true if there exists 3 of the same character in a row 
     *         either right or left diagonal 
     */
    public boolean checkDiagonals() {
        String[] chosen = new String[3];
        //loops through entire String[][] and fills chosen
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                chosen[0] = board[i][j];
                //checks left side
                if (i+1 < board.length && j-1 >= 0) {
                    chosen[1] = board[i+1][j-1];
                }
                if (i+2 < board.length && j-2 >= 0) {
                    chosen[2] = board[i+2][j-2];
                }
                
                if(chosen[0].equals(chosen[1]) && chosen[1].equals(chosen[2]) &&
                    !chosen[0].equals("0")) 
                {
                    if(chosen[0].equals("X")) {
                        isX = true;
                    } else {
                        isX = false;
                    }
                    return true;
                }

                //checks right
                if (i+1 < board.length && j+1 <= board.length-1) {
                    chosen[1] = board[i+1][j+1];
                }
                if (i+2 < board.length && j+2 <= board.length-1) {
                    chosen[2] = board[i+2][j+2];
                }

                if(chosen[0].equals(chosen[1]) && chosen[1].equals(chosen[2]) &&
                    !chosen[0].equals("0")) 
                {
                    if(chosen[0].equals("X")) {
                        isX = true;
                    } else {
                        isX = false;
                    }
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * helper method for checkState method that checks the row of a given 
     * position
     *
     * @param (pos) position to check the entire row of  
     * @return true if 3 of the same symbol are adjacent vertically, false
     *         otherwise
     */
    public boolean checkColumn(Vector pos) {
        String[] chosen = new String[3];
        int x = pos.getX(), y = pos.getY();
        //loops through column and fills in chosen
        for(int i = 0; i < board[y].length; i++) {
            chosen[0] = board[i][y]; 
            if(!(i+1 >= board.length)) {
                chosen[1] = board[i+1][y];
            } else {
                continue;
            }
            if(!(i+2 >= board.length)) {
                chosen[2] = board[i+2][y];
            } else {
                continue;
            }
            //checks if the values in chosen are the same and are not 0
            if(chosen[0].equals(chosen[1]) && chosen[0].equals(chosen[2]) &&
                !chosen[0].equals("0")) {
                if(chosen[0].equals("X")) {
                    isX = true;
                } else {
                    isX = false;
                }
                return true;
            }
            chosen = new String[3];
        }
        return false;
    }

    /** 
     * uses getuserInput helper method to get position of baord to edit board
     */
    public void makeMove() {
        int[] validInputs = new int[board.length];
        for(int i = 0; i < board.length; i++) {
            validInputs[i] = i;
        }
        int inputX = getUserInput("Enter number for row of board to edit");
        int inputY = getUserInput("Enter number for column of board to edit");
        while(!validateInput(inputX, validInputs) || 
            !validateInput(inputY, validInputs)) 
        {  
            System.out.println("The number should be between 0 and " + 
                (board.length-1));
            inputX=getUserInput("Enter number for row");
            inputY=getUserInput("Enter number for column");
        }
        if(editBoard(new Vector(inputX, inputY), player)) player = !player;
    }

    /**
     * helper method for movement
     * checks if an input exists in an array of validInputs
     *
     * @param (validInputs) array of valid inputs
     * @param (input) input to compare with 
     * @return boolean true if input is found, false if not
     */
    public boolean validateInput(int input, int[] validInputs) {
        for(int i : validInputs) {
            if(i == input) {
                return true;
            }
        }
        return false;
    }

    /** 
     * prints a given text to the console and recieves user input
     * if input is not in validInputs, the function asks again
     */
    public int getUserInput(String text) {
        try {
            System.out.println(text);
            String userInput = input.nextLine();
            int inputValue = Integer.parseInt(userInput);
            return inputValue;
        } catch(NumberFormatException ex) {
            System.out.println("Please enter a number");
            return getUserInput(text);
        }
    }
    /**
     * helper method for checkState method that checks the row of a given 
     * position
     *
     * @param (pos) position to check the entire row of  
     * @return true if 3 of the same symbol are adjacent vertically, false
     *         otherwise
     */
    public boolean checkRow(Vector pos) {
        String[] chosen = new String[3];
        int x = pos.getX(), y = pos.getY();
        //loops through column and fills in chosen
        for(int i = 0; i < board.length; i++) {
            chosen[0] = board[x][i]; 
            if(!(i+1 >= board.length)) {
                chosen[1] = board[x][i+1];
            } else {
                continue;
            }
            if(!(i+2 >= board.length)) {
                chosen[2] = board[x][i+2];
            } else {
                continue;
            }
            //checks if the values in chosen are the same and are not 0
            if(chosen[0].equals(chosen[1]) && chosen[0].equals(chosen[2]) &&
                !chosen[0].equals("0")) {
                if(chosen[0].equals("X")) {
                    isX = true;
                } else {
                    isX = false;
                }
                return true;
            }
            chosen = new String[3];
        }
        return false;
    }

    public static void main(String args[]) {
        Game game = new Game(3);
        game.initate();
    }
}