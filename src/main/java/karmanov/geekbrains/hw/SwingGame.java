package karmanov.geekbrains.hw;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SwingGame extends JFrame {

    private static final int POS_Y = 300;
    private static final int POS_X = 300;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 450;

    private final MinesweeperController gameController;

    private GameButton[][] buttons;
    private GridLayout layout;

    public SwingGame(String title, MinesweeperController gameController) {
        super(title);
        this.gameController = gameController;
    }

    private LayoutManager createLayout() {
        layout = new GridLayout();
        layout.setRows(gameController.getRows());
        layout.setColumns(gameController.getColumns());
        loadComponents();
        return layout;
    }

    public void play() {
        setLayout(createLayout());
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void loadComponents() {
        int rows = layout.getRows();
        int columns = layout.getColumns();

        buttons = new GameButton[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                buttons[i][j] = new GameButton(i, j);
                buttons[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        GameButton button = (GameButton) e.getSource();
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            if (!gameController.isVisibleMine())
                                gameController.setVisibleMine(!gameController.move(button.getRow(), button.getCol()));
                            gameController.updateButtons(buttons);
                        }
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            gameController.setFlag(button.getRow(), button.getCol());
                            gameController.updateButtons(buttons);
                        }
                    }
                });
                add(buttons[i][j]);
            }
        }
    }
}
