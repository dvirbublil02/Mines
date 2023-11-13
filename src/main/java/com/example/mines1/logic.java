package com.example.mines1;

import java.util.Random;

public class logic {
    //we using to boards one will be the show'en board and the other one will be the under board -- will indicate the bombs
    private String[][] board,BehindBoard;
    private int height,width;
    private boolean flag=false;
    private Random rand = new Random();

    //this is our constructor to set the game board settings number of mines , height , width and the board
    //we rand the bombs on the board
    //first we initialize the board with dots.(each place on the board will get "."
    //then we inserting the mines("X" will present a mine) into the Behindboard in random place's by using the random class of java.
    public logic(int height, int width, int numMines) {
        board = new String[height][width];
        BehindBoard = new String[height][width];
        this.height=height;
        this.width=width;
        for(int i=0;i<height;i++) {
            for(int j=0;j<width;j++) {
                board[i][j] = ".";
            }
        }
        for(int i=0; i < numMines; i++) {
            int j=0,k=0;
            j = rand.nextInt(height);
            k = rand.nextInt(width);
            BehindBoard[j][k]="X";
        }
    }



    //in this method we adding mine to the board in specific location (to the under board)
    //we initialize our mines as the string "X"
    //we using method that check if the given location is on the board (under board)
    public boolean addMine(int i,int j) {
        if(onBoard(i,j) && !(BehindBoard[i][j]=="X")) {
            BehindBoard[i][j]="X";
            return true;
        }
        return false;
    }
    //simple method to check if given coordinates are located on the board
    public boolean onBoard(int i,int j) {
        if(i<height && i>=0 && j<width && j>=0) return true;
        return false;
    }
    //in this method we checking if the location that we got is on the board if not we returning false
    //if the point on the board we checking if the point is not pressed(opened) not mine or not flag , if its one of them we returning false
    //then we checking if there is no mines around the point by calling other method(surroundMines).
    //if not we starting to open the whole neighbors by calling open
    //then return true
    public boolean open(int i,int j) {
        if(!onBoard(i, j)) return false;
        if(board[i][j] != "." || BehindBoard[i][j] == "X" || board[i][j] == "F") return false;
        if(surroundMines(i,j) == 0) { //opening all the neighbors
            open(i-1,j-1);
            open(i-1,j);
            open(i-1,j+1);
            open(i,j-1);
            open(i,j+1);
            open(i+1,j);
            open(i+1,j);
            open(i+1,j+1);
        }
        return true;
    }
    //in this method we checking if this point is mine or not
    private boolean checkIfMine(int i,int j) {
        if(onBoard(i,j))
            return BehindBoard[i][j] == "X" ;
        return false;
    }
    //in this method we counting all the mines that around specific point
    //if count > 0 then we writing on the board(if its not flag) the value else " " (empty place)
    private int surroundMines(int i,int j) {
        int count = 0;
        if(checkIfMine(i-1,j-1))
            count++;
        if(checkIfMine(i-1,j))
            count++;
        if(checkIfMine(i-1,j+1))
            count++;
        if(checkIfMine(i,j-1))
            count++;
        if(checkIfMine(i,j+1))
            count++;
        if(checkIfMine(i+1,j-1))
            count++;
        if(checkIfMine(i+1,j))
            count++;
        if(checkIfMine(i+1,j+1))
            count++;
        if(!flag) {
            if(count > 0)
                board[i][j] = String.valueOf(count);
            else
                board[i][j] = " ";
        }
        return count;

    }
    //in this method we putting flag on the given place or we remove the flag if its exist.
    public void toggleFlag(int x, int y) {
        if(board[x][y] != "F")
            board[x][y] = "F";
        else
            board[x][y]=".";
    }
    //in this method we running on all the board and if all the points that not open are not mines that we return true else false
    public boolean isDone() {
        for(int i=0;i<height;i++) {
            for(int j=0;j<width;j++) {
                if((board[i][j] == "." && BehindBoard[i][j] != "X") ||(board[i][j] == "F" && BehindBoard[i][j] != "X"))
                    return false;
            }
        }
        return true;
    }

    //return the represent of point in board depends on what he contain
    //if the location is close then we will return F
    //if the location is open we return 'X'
    // we first check if we want to show all or not by using flag if not we doing above
    //if yes we returning X if its bomb else we 'open' the place's nearby and retuning the point
    public String get(int i, int j) {
        if(!flag) {
            if(board[i][j] == "F")
                return "F";
            if(board[i][j] == ".")
                return ".";
            return board[i][j];
        }
        else {
            if(BehindBoard[i][j] == "X")
                return "X";
            int count = surroundMines(i,j);
            if(count > 0)
                BehindBoard[i][j] = String.valueOf(count);
            else
                BehindBoard[i][j] = " ";

            return BehindBoard[i][j];
        }

    }

    //will set the flag if to show all or not (open or not)
    public void setShowAll(boolean showAll) {
        flag = showAll;
    }
    //our implements of tostring
    //we building string and inserting all the string values by using get method of specific location we doing it to the whole board after finishing line we creating new line
    public String toString() {

        StringBuilder ReturningStr = new StringBuilder("");
        for(int i=0;i<height;i++) {
            for(int j=0;j<width;j++) {
                ReturningStr.append(get(i,j));
            }
            ReturningStr.append("\n");
        }
        return ReturningStr.toString();
    }
}
