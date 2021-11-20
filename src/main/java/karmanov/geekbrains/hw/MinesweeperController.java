package karmanov.geekbrains.hw;

import java.util.concurrent.ThreadLocalRandom;

public class MinesweeperController {
    private static final int MINE = 9;
    private static final int EMPTY = 0;

    private static final int CELL_OPEN = 1;
    private static final int CELL_CLOSE = 0;
    private static final int CELL_FLAG = -1;


    private final int rows;
    private final int columns;
    private final int mineCount;
    private boolean visibleMine;

    private final int[][] moves;
    private int[][] board;


    public MinesweeperController(int rows, int columns, int mineCount) {
        this.rows = rows;
        this.columns = columns;
        this.mineCount = mineCount;
        this.visibleMine = false;

        this.moves = new int[rows][columns];
        generateBoard();
    }

    public void updateButtons(GameButton[][] buttons) {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++) {
                if (visibleMine) {
                    if (board[i][j] == MINE) {
                        buttons[i][j].setEnabled(false);
                        buttons[i][j].setText("*");
                    }
                } else {
                    if (moves[i][j] == CELL_CLOSE) {
                        buttons[i][j].setText("");
                        continue;
                    }
                    if (moves[i][j] == CELL_FLAG) {
                        buttons[i][j].setText("!");
                        continue;
                    }
                    if (board[i][j] == EMPTY) {
                        buttons[i][j].setEnabled(false);
                    } else {
                        buttons[i][j].setEnabled(false);
                        buttons[i][j].setText(String.valueOf(board[i][j]));
                    }
                }
            }
    }

    private void generateBoard() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        board = new int[rows][columns];
        int mines = mineCount;

        while (mines > 0) {
            int x = random.nextInt(columns), y = random.nextInt(rows);
            if (board[x][y] == MINE) continue;
            board[x][y] = MINE;
            mines--;
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (board[i][j] == MINE) continue;
                int count = 0;
                for (int k = i - 1; k <= i + 1; k++) {
                    for (int l = j - 1; l <= j + 1; l++) {
                        if (k >= rows || k < 0 || l < 0 || l >= columns) continue;
                        if (board[k][l] == MINE) count++;
                    }
                }
                board[i][j] = count;
            }
        }
    }

    public void openEmptyCells(int row, int col) {
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i < 0 || i >= columns || j < 0 || j >= rows) continue;
                if (i == row && j == col) {
                    moves[i][j] = CELL_OPEN;
                    continue;
                }
                if (board[i][j] == EMPTY && moves[i][j] != CELL_OPEN) {
                    moves[i][j] = CELL_OPEN;
                    openEmptyCells(i, j);
                }
                if (board[i][j] != EMPTY && board[i][j] != MINE && moves[i][j] != CELL_OPEN) {
                    moves[i][j] = CELL_OPEN;
                }
            }
        }
    }

    public void setFlag(int row, int col) {
        if (moves[row][col] != CELL_FLAG) {
            moves[row][col] = CELL_FLAG;
        } else {
            moves[row][col] = CELL_CLOSE;
        }
    }

    public boolean move(int row, int col) {
        if (row >= 0 && row <= rows && col >= 0 && col <= columns) {
            if (moves[row][col] == CELL_FLAG) return true;
            if (board[row][col] == MINE) {
                return false;
            }
            if (board[row][col] == EMPTY) {
                openEmptyCells(row, col);
                return true;
            }
            moves[row][col] = CELL_OPEN;
            return true;
        }
        return false;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public boolean isVisibleMine() {
        return visibleMine;
    }

    public void setVisibleMine(boolean visibleMine) {
        this.visibleMine = visibleMine;
    }
}
