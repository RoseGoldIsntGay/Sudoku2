package rosegold.example.sudoku;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class SudokuGenerator {
    private static SudokuGenerator instance;

    private ArrayList<ArrayList<Integer>> Free = new ArrayList<ArrayList<Integer>>();
    public static int[][] Board = new int[9][9];
    private Random rand = new Random();
    private int numToDelete;
    static String diff;

    private SudokuGenerator() {

    }

    public static SudokuGenerator getInstance() {
        if(instance == null) {
            instance = new SudokuGenerator();
        }
        return instance;
    }

    public int[][] generateGrid() {
        int[][] Sudoku = new int[9][9];
        int currentPos = 0;
        //clearGrid(Sudoku);

        while(currentPos < 81) {
            if(currentPos == 0) {
                clearGrid(Sudoku);
            }
            if(Free.get(currentPos).size() != 0) {
                int i = rand.nextInt(Free.get(currentPos).size());
                int number = Free.get(currentPos).get(i);

                if(!checkTaken(Sudoku, currentPos, number)) {
                    int xPos = currentPos % 9;
                    int yPos = currentPos / 9;

                    Sudoku[xPos][yPos] = number;
                    Board[xPos][yPos] = number;

                    Free.get(currentPos).remove(i);

                    currentPos++;
                } else {
                    Free.get(currentPos).remove(i);
                }


            } else {
                for(int i = 1; i <= 9; i++) {
                    Free.get(currentPos).add(i);
                }
                currentPos--;
            }

        }
        for(int x = 0; x < 9; x++) {
            for(int j = 0; j < 9; j++) {
                System.out.print(Sudoku[x][j] + "|");
            }
            System.out.println();
        }
        return Sudoku;
    }

    public int[][] removeElements(int[][] Sudoku) {
        diff = MainActivity.diff;
        Log.i("SudokuGenerator",this.diff);
        if(this.diff.equals("Easy")) {
            numToDelete = 36;
        } else if(this.diff.equals("Medium")) {
            numToDelete = 46;
        } else if(this.diff.equals("Hard")) {
            numToDelete = 56;
        }
        int i = 0;

        while(i < numToDelete) {
            int x = rand.nextInt(9);
            int y = rand.nextInt(9);

            if(Sudoku[x][y] != 0) {
                Sudoku[x][y] = 0;
                i++;
            }
        }
        return Sudoku;
    }

    private void clearGrid(int[][] Sudoku) {
        Free.clear();
        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 9; x++) {
                Sudoku[x][y] = -1;
            }
        }

        for(int i = 0; i < 81; i++) {
            Free.add(new ArrayList<Integer>());
            for(int j = 1; j <= 9; j++) {
                Free.get(i).add(j);
            }
        }
    }

    private boolean checkTaken(int[][] Sudoku, int currentPos, final int number) {
        int xPos = currentPos % 9;
        int yPos = currentPos / 9;

        if(checkHorizontal(Sudoku, xPos, yPos, number) || checkVertical(Sudoku, xPos, yPos, number) || checkSquare(Sudoku, xPos, yPos, number)) {
            return true;
        }

        return false;
    }

    private boolean checkHorizontal(final int[][] Sudoku, final int xPos, final int yPos, final int number) {
        for(int x = xPos -1 ; x >= 0; x--) {
            if(number == Sudoku[x][yPos]) { //return true means it's taken
                return true;
            }
        }

        return false;
    }

    private boolean checkVertical(final int[][] Sudoku, final int xPos, final int yPos, final int number) {
        for(int y = yPos -1 ; y >= 0; y--) {
            if(number == Sudoku[xPos][y]) { //return true means it's taken
                return true;
            }
        }

        return false;
    }

    private boolean checkSquare(final int[][] Sudoku, final int xPos, final int yPos, final int number) {
        int xSquare = xPos/3;
        int ySquare = yPos/3;

        for(int x = xSquare*3; x < xSquare*3 + 3; x++) {
            for(int y = ySquare*3; y < ySquare*3 + 3; y++) {
                if((x != xPos || y != yPos) && number == Sudoku[x][y]) {
                    return true;
                }
            }
        }
        return false;
    }
}
