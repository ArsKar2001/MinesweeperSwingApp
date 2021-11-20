package karmanov.geekbrains.hw;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        new SwingGame(
                "Minesweeper",
                new MinesweeperController(10, 10, 15))
                .play();
    }
}
