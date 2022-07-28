import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;


class InputException extends Exception{
    public InputException(String msg){
        super(msg);
    }
}


class Player
{
    private final String player;
    private final char piece;


    public Player(String player, char piece){
        this.player=player;
        this.piece = piece;
    }
    String getPlayer(){
        return this.player;
    }
    char getPiece(){
        return this.piece;
    }


}



class Computer extends Player{

    public Computer(String player, char piece) {
        super(player, piece);
    }

    int generateMove(){
        return ThreadLocalRandom.current().nextInt(0,6+1);
    }
}


class Board {

    char[][] board = new char[13][29];
    public boolean hasWinner = false;

    //Coordinates
    int[] row = {1,3,5,7,9,11};
    int[] col = {2,6,10,14,18,22,26};

    public Board(){
        for(int i = 0; i<13;i++){
            for(int j = 0; j<29;j++) {
                board[i][j] = ' ';

                if(i%2==0 ){
                    if(j%4==0){
                        board[i][j] = '+';
                    }else{
                        board[i][j] = '-';
                    }


                }else{
                    if(j%4==0){
                        board[i][j] = '|';
                    }
                }

            }

        }
    }

    void printBoard(){
        for (int i =0, j=0; i<29;i++){
            if(i==col[j]){
                System.out.print(j+1);
                if(j==6){
                    break;
                }
                j++;
            }else{
                System.out.print(" ");
            }
        }

        System.out.println();

        for(int i = 0; i<13;i++){
            for(int j = 0; j<29;j++){
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    void addPiece(int col, char piece){
        int _col = this.col[col];
        for (int i = row.length - 1; i >= 0; i--) {

            int row = this.row[i];
            if(board[row][_col]==' '){
                board[row][_col]=piece;
                hasWinner = checkWinner(i,col, piece);
                break;
            }
        }
    }

    boolean invalidMove(int col){
        return board[this.row[0]][this.col[col]] != ' ';
    }

    boolean checkWinner(int row, int col, char piece){
        return checkHorizontal(row,col,piece) ||
                checkVertical(row,col,piece) ||
                checkRightDiagonal(row,col,piece)||
                checkLeftDiagonal(row,col,piece);
    }

    boolean checkLeftDiagonal(int row,int col, char piece){
        int count = 0;
        int i=row;
        int j=col;
        int _row =0;
        int _col = 0;

        //get starting point

        while(i<5 && j<6){

            j++;
            i++;

        }

        while(i>=0 && j>=0) {


            _row = this.row[i];
            _col = this.col[j];
            if (board[_row][_col] == piece) {
                count++;
                if (count == 4) {

                    break;
                }
            } else {
                count = 0;
            }
            i--;
            j--;


        }

        return count == 4;
    }
    boolean checkRightDiagonal(int row,int col, char piece){
        int count = 0;
        int i=row;
        int j=col;
        int _row =0;
        int _col = 0;

        //get starting point
        while(i<5 && j>0){

            j--;
            i++;

        }


        while (i>=0 && j<7){

            _row = this.row[i];
            _col = this.col[j];
            if(board[_row][_col]==piece){
                count++;
                if(count==4){

                    break;
                }
            }else {
                count=0;
            }
            i--;
            j++;
        }


        return count == 4;
    }

    boolean checkVertical(int row,int col, char piece){
        int count=0;
        for(int i = 0 ; i<6;i++){
            int _row = this.row[i];
            int _col = this.col[col];

            if(board[_row][_col]==piece){
                count++;

                if (count==4 ){
                    break;
                }
            }else{
                count=0;
            }
        }
        return count==4;
    }

    boolean checkHorizontal(int row,int col, char piece){
        int count = 0;
        for(int i = 0 ; i<7;i++){
            int _row = this.row[row];
            int _col = this.col[i];

            if(board[_row][_col]==piece){
                count++;

                if (count==4 ){
                    break;
                }
            }else{
                count=0;
            }
        }

        return count==4;
    }


}


class ConnectFour {
    Scanner sc = new Scanner(System.in);

    public static boolean isNotDigit(String strNum) {

        try {
            int n = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return true;
        }
        return false;
    }

    void start() {
        Player player1 = new Player("player 1", 'O');
        Computer comp = new Computer("Computer", 'X');
        Board board = new Board();

        boolean playerTurn = true;
        String name = " ";
        char piece = ' ';
        String move = " ";

        while (!board.hasWinner) {
            board.printBoard();


            try {
                if (playerTurn) {
                    piece = player1.getPiece();
                    name = player1.getPlayer();

                    System.out.print("Enter Move: ");
                    move = sc.next();


                } else {
                    piece = comp.getPiece();
                    name = comp.getPlayer();
                    move = String.valueOf(comp.generateMove() + 1);

                }

                if ( isNotDigit(move) ){
                    throw new InputException("Invalid Input");
                }else{
                    int _move = Integer.parseInt(move);
                    if ( _move < 1 || _move > 7 || board.invalidMove(_move - 1)) {
                        throw new InputException("Invalid Move");
                    } else {
                        board.addPiece(_move - 1, piece);
                        playerTurn = !playerTurn;

                    }
                }

            } catch (InputException e ) {
                System.out.println(e);
            }


        }
        board.printBoard();

        System.out.println(name + " Wins");


    }



}


public class Main {

    public static void main(String[] args) {
        ConnectFour cf = new ConnectFour();
        cf.start();
    }
}
