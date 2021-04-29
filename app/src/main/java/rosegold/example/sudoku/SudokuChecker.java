package rosegold.example.sudoku;

public class SudokuChecker {
    private static SudokuChecker instace;

    private SudokuChecker() {

    }

    public static SudokuChecker getInstance() {
        if(instace == null) {
            instace = new SudokuChecker();
        }
        return  instace;
    }

    public boolean checkSudoku(int[][] Sudoku) {
        return(checkHorizontal(Sudoku, -1, -1) || checkVertical(Sudoku, -1 ,-1) || checkSquares(Sudoku, -1, -1));
    }

    public boolean checkNumber(int[][] Sudoku, int x, int y) {
        if(!checkHorizontal(Sudoku, x, y) || !checkVertical(Sudoku, x, y) || !checkSquares(Sudoku, x ,y)) {
            return false;
        }
        return true;
    }

    private boolean checkHorizontal(int[][] Sudoku, int x2, int y2) {
        if(x2 != -1 && y2 != -1) {
            for(int x = 0; x < 9; x++) {
                if(Sudoku[x2][y2] == Sudoku[x][y2]) {
                    if(x2 != x) {
                        System.out.println("Horizontal Fail [" + x2 + "], [" + y2 + "] Equals [" + x + "], [" + y2 + "]");
                        return false;
                    }
                }
            }
            return true;
        }
        for(int y = 0; y < 9; y++) {
            for(int xPos = 0; xPos < 9; xPos++) {
                if(Sudoku[xPos][y] == 0) {
                    return false;
                }
                for(int x = xPos+1; x < 9; x++) {
                    if(Sudoku[xPos][y] == Sudoku[x][y] || Sudoku[x][y] == 0) {
                        return false;
                    }
                }

            }

        }
        return true;
    }
    private boolean checkVertical(int[][] Sudoku, int x2, int y2) {
        if(x2 != -1 && y2 != -1) {
            for(int y = 0; y < 9; y++) {
                if(Sudoku[x2][y2] == Sudoku[x2][y]) {
                    if(y2 != y) {
                        System.out.println("Vertical Fail [" + x2 + "], [" + y2 + "] Equals [" + x2 + "], [" + y + "]");
                        return false;
                    }
                }
            }
            return true;
        }
        for(int x = 0; x < 9; x++) {
            for(int yPos = 0; yPos < 9; yPos++) {
                if(Sudoku[x][yPos] == 0) {
                    return false;
                }
                for(int y = yPos+1; y < 9; y++) {
                    if(Sudoku[x][yPos] == Sudoku[x][y] || Sudoku[x][y] == 0) {
                        return false;
                    }
                }

            }

        }
        return true;

    }
    private boolean checkSquares(int[][] Sudoku, int x2, int y2) {
        if(x2 != -1 && y2 != -1) {
            if(!checkSquare(Sudoku, x2/3, y2/3)) {
                System.out.println("Square Fail. Square:"+x2/3+", "+y2/3);
                return false;
            }
            return true;
        }
        for(int xSquare = 0; xSquare < 3; xSquare++) {
            for(int ySquare = 0; ySquare < 3; ySquare++) {
                if(!checkSquare(Sudoku, xSquare, ySquare)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkSquare(int[][] Sudoku, int xSquare, int ySquare) {
        for(int xPos = xSquare*3; xPos < xSquare*3 + 3; xPos++) {
            for(int yPos = ySquare*3; yPos < ySquare*3 + 3; yPos++) {
                for(int x = xPos;x < xSquare*3 + 3; x++) {
                    for(int y = yPos; y < ySquare*3 +3; y++) {
                        if(x != xPos || y != yPos && Sudoku[xPos][yPos] == Sudoku[x][y] || Sudoku[x][y] == 0) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }



}
