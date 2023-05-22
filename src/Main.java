import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java. lang. Math;

public class Main {
    static int sum = 0;

    //setting up the board
    public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);
        char[][] grid = new char[6][7];
        for (int i = 0; i < grid.length; i++){
            for (int k = 0; k < grid[0].length; k++){
                grid[i][k] = ' ';
            }
        }

        //asking if the players know how to play connect four
        System.out.println("Do you know how to play Connect 4? (y/n)");
        String explanation = scanner.nextLine();
        if (Objects.equals(explanation, "n")) {
            System.out.println("Connect 4 is a two-player strategy game where the objective is to connect four of your pieces in a ");
            System.out.println("vertical, horizontal, or diagonal line before your opponent does. Players take turns dropping disks ");
            System.out.println("into a 7x6 grid, and once a disk is dropped into a column, it cannot be moved. Lastly, if you are");
            System.out.println("unsure of where to place your piece, you can enter 7 for a recommended column.");}

        else if (!Objects.equals(explanation, "y")){
            System.out.println("Invalid Response, please retry");
            System.exit(0);}

        System.out.println("How many players are there? (1/2)");
        int twoPlayers = scanner.nextInt();
        //asking how many players there are

        if (twoPlayers != 1 && twoPlayers != 2)
            System.out.println("Invalid response, please restart the program");
        //Prints line informing user of error (invalid response to player count)

        int turn = 1;
        char player = 'X';
        boolean winner = false;

        //play a turn
        while (winner == false && turn <= 42){
            //when there is a winner, winner will become true, and the loop will stop running. If the game results in a draw, 42 turns will be exceeded, and the loop will stop
            boolean validPlay = false;
            int play =0;

                do{
                    if (twoPlayers == 2) {
                        display(grid);
                        System.out.print("Player " + player + ", choose a column: ");
                        play = scanner.nextInt();

                        //recommend feature. If the user types 7, a random number will be generated, same way the AI generates numbers
                        if (play ==7){
                            for (int i = 0; i < 6; i++) {
                                double randomDouble = Math.random();
                                int randomInt = (randomDouble < 0.5) ? 0 : 1;
                                sum = sum + randomInt;}
                            System.out.println("recommendation: " + sum + ".");
                            play = sum;
                            sum = 0;}
                        // if there are two players, the game will run normally

                        validPlay = validate(play,grid);}
                    else if (twoPlayers == 1) {
                        if (player == 'X') {
                            display(grid);
                            System.out.print("Player " + player + ", choose a column: ");
                            play = scanner.nextInt();

                            //recommend feature. If the user types 7, a random number will be generated, same way the AI generates numbers
                            if (play ==7){
                                for (int i = 0; i < 6; i++) {
                                    double randomDouble = Math.random();
                                    int randomInt = (randomDouble < 0.5) ? 0 : 1;
                                    sum = sum + randomInt;}
                                System.out.println("recommendation: " + sum + ".");
                                play = sum;
                                sum = 0;}
                            //Player X is the human, during their turn, everything will act as usual

                            validPlay = validate(play, grid);}
                        else if (player == 'O') {

                            for (int i = 0; i < 6; i++) {
                                double randomDouble = Math.random();
                                int randomInt = (randomDouble < 0.5) ? 0 : 1;
                                sum = sum + randomInt;
                            }// during the AI's turn, a random double under 1 and above 0 will be generated. It will then be rounded to either 1 or 0. This is then added into a sum, where this will loop 6 times. This sum will finally be the AI's move position. This is also really effective because the chances of generating the number of the middle positions is higher, due to more combinations to create the middle numbers

                            play = sum;
                            System.out.println("Play: " + play);
                            validPlay = validate(play, grid);
                            sum = 0;
                        }
                    }
                }while (validPlay == false);

            //drop the piece
            for (int row = grid.length-1; row >= 0; row--){
                if(grid[row][play] == ' '){
                    grid[row][play] = player;
                    break;
                }
            }

            //check if there is a winner
            winner = isWinner(player,grid);
            //switch players
            if (player == 'X'){
                player = 'O';
            }else
                player = 'X';
            turn++;
        }
        display(grid);

        //printing winning/ draw message
        if (winner){
            if (player=='X'){
                System.out.println("Player O has won");
            }else{
                System.out.println("Player X won");
            }
        }else{
            System.out.println("The game has ended in a tie. Please play again");
        }
    }

    public static void display(char[][] grid){
        System.out.println(" 0 1 2 3 4 5 6");
        for (int row = 0; row < grid.length; row++){
            System.out.print("|");
            for (int col = 0; col < grid[0].length; col++){
                System.out.print(grid[row][col]);
                System.out.print("|");
            }
            System.out.println();

        }
        System.out.println(" 0 1 2 3 4 5 6");
        System.out.println();
    }

    public static boolean validate(int column, char[][] grid){
        //Check for valid column
        if (column < 0 || column > grid[0].length){
            System.out.println("Invalid entry, please re-enter");
            return false;
        }

        //Check for when a column is full
        if (grid[0][column] != ' '){
            System.out.println("Column is full. Please re-enter");
            return false;
        }

        return true;
    }

    public static boolean isWinner(char player, char[][] grid){
        //check for 4 across
        for(int row = 0; row<grid.length; row++){
            for (int col = 0;col < grid[0].length - 3;col++){
                if (grid[row][col] == player   &&
                        grid[row][col+1] == player &&
                        grid[row][col+2] == player &&
                        grid[row][col+3] == player){
                    return true;
                }
            }
        }
        //check for 4 up and down
        for(int row = 0; row < grid.length - 3; row++){
            for(int col = 0; col < grid[0].length; col++){
                if (grid[row][col] == player   &&
                        grid[row+1][col] == player &&
                        grid[row+2][col] == player &&
                        grid[row+3][col] == player){
                    return true;
                }
            }
        }
        //check upward diagonal
        for(int row = 3; row < grid.length; row++){
            for(int col = 0; col < grid[0].length - 3; col++){
                if (grid[row][col] == player   &&
                        grid[row-1][col+1] == player &&
                        grid[row-2][col+2] == player &&
                        grid[row-3][col+3] == player){
                    return true;
                }
            }
        }
        //check downward diagonal
        for(int row = 0; row < grid.length - 3; row++){
            for(int col = 0; col < grid[0].length - 3; col++){
                if (grid[row][col] == player   &&
                        grid[row+1][col+1] == player &&
                        grid[row+2][col+2] == player &&
                        grid[row+3][col+3] == player){
                    return true;
                }
            }
        }
        return false;
    }
}