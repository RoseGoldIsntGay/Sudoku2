package rosegold.example.sudoku;

import android.content.Context;
import android.widget.Toast;

public class GameGrid {
    private SudokuCell[][] Sudoku = new SudokuCell[9][9];

    private Context context;

    public GameGrid(Context context) {
        this.context = context;
        for(int x = 0; x < 9; x++) {
            for(int y = 0; y < 9; y++) {
                Sudoku[x][y] = new SudokuCell(context, x+y*9);
                //System.out.print(Sudoku[x][y] + "|");
            }
            System.out.println();
        }
    }

    public void setGrid(int[][] grid) {
        for(int x = 0; x < 9; x++) {
            for(int y = 0; y < 9; y++) {
                Sudoku[x][y].setInit(grid[x][y]);
                if(grid[x][y] != 0) {
                    Sudoku[x][y].setNotChangable();
                }

            }
        }
    }

    public SudokuCell[][] getGrid() {
        return Sudoku;

    }
    public void setItem(int x, int y, int number) {
        Sudoku[x][y].setValue(number);
    }

    public SudokuCell getItem(int x, int y) {
        return Sudoku[x][y];
    }

    public SudokuCell getItem(int position) {
        int x = position / 9;
        int y = position % 9;
        //System.out.println("item = "+Sudoku[x][y].getValue());

        return Sudoku[x][y];
    }

    public boolean checkGame() {
        int[][] sudokuGrid = new int[9][9];
        for(int x = 0; x < 9; x++) {
            for(int y = 0; y < 9; y++) {
                sudokuGrid[x][y] = getItem(x, y).getValue();
            }
        }
        if(SudokuChecker.getInstance().checkSudoku(sudokuGrid)) {
            return true;
        }
        return false;
    }

    public void checkNumber(int xPos, int yPos) {
        int[][] sudokuGrid = new int[9][9];
        for(int x = 0; x < 9; x++) {
            for(int y = 0; y < 9; y++) {
                sudokuGrid[x][y] = getItem(x, y).getValue();
            }
        }
    }
}
