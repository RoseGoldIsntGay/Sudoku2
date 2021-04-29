package rosegold.example.sudoku;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;

public class GameEngine {
    private static GameEngine instance;

    private GameGrid grid = null;
    private int selectedX, selectedY = -1;
    private int prevSelectedX, prevSelectedY = -1;

    private GameEngine(){

    }

    public static GameEngine getInstance() {
        if(instance == null) {
            instance = new GameEngine();
        }
        return instance;
    }

    public GameEngine createGrid(Context context) {
        int[][] Sudoku = SudokuGenerator.getInstance().generateGrid();
        Sudoku = SudokuGenerator.getInstance().removeElements(Sudoku);
        grid = new GameGrid(context);
        grid.setGrid(Sudoku);
        return instance;
    }

    public GameGrid getGrid() {
        return grid;
    }

    public void setSelected(int x, int y) {
        prevSelectedX = selectedX;
        prevSelectedY = selectedY;
        this.selectedX = x;
        this.selectedY = y;
        if(prevSelectedY != -1 && prevSelectedX != -1) {
            grid.getItem(prevSelectedX, prevSelectedY).setSelected(false);
        }
        grid.getItem(x, y).setSelected(true);
        for(int xPos = 0; xPos < 9; xPos++) {
            for(int yPos = 0; yPos < 9; yPos++) {
                if(prevSelectedY != -1 && prevSelectedX != -1) {
                    if(grid.getItem(prevSelectedX, prevSelectedY).getPrevValue() == grid.getItem(xPos, yPos).getValue() || grid.getItem(prevSelectedX, prevSelectedY).getValue() == grid.getItem(xPos, yPos).getValue()) {
                        grid.getItem(xPos, yPos).friendSelected = false;
                    }
                }
            }
        }
        for(int xPos = 0; xPos < 9; xPos++) {
            for(int yPos = 0; yPos < 9; yPos++) {
                if(grid.getItem(x, y).getValue() == grid.getItem(xPos, yPos).getValue() && grid.getItem(xPos, yPos).getValue() != 0) {
                    if(x == xPos && y == yPos) {
                        grid.getItem(xPos, yPos).friendSelected = false;
                    } else {
                        grid.getItem(xPos, yPos).friendSelected = true;
                    }
                }
            }
        }

    }

    public void setNumber(int number) {
        if(selectedX != -1 && selectedY != -1) {
            grid.setItem(selectedX, selectedY, number);
        }
        if(grid.checkGame()) {

        }
        if(number != 0) {
            if(grid.getItem(selectedX, selectedY).getValue() == SudokuGenerator.Board[selectedX][selectedY]) {
                grid.getItem(selectedX, selectedY).isFalse = false;
            } else {
                grid.getItem(selectedX, selectedY).isFalse = true;
            }
        }
        setSelected(selectedX, selectedY);
    }

    public boolean checkWon() {
        return(grid.checkGame());
    }
}
