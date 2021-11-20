package karmanov.geekbrains.hw;

import javax.swing.*;

public class GameButton extends JButton {
    private final int col;
    private final int row;


    public GameButton(int row, int col) {
        this.col = col;
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
}
