import javax.swing.*;
import java.awt.*;

public class Psycg2Main {



    final static int WIDTH = 10, HEIGHT = 10, NUM_MINES = 10;

    public Psycg2Main(){
        Board board = new Board(WIDTH, HEIGHT);
        Dimension windowSize = new Dimension(1000,1000);
        Dimension buttonSize = new Dimension(windowSize.width/WIDTH, windowSize.height/HEIGHT);
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                board.boardSquareButtons[x][y] = new BoardSquareButton(buttonSize, null, x, y, board);
            }
        }
        board.createMines(NUM_MINES);
        JFrame guiFrame = new JFrame();
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("Minesweeper");
        guiFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        guiFrame.setResizable(false);
        board.applyToAll(guiFrame::add);
        guiFrame.pack();
        guiFrame.setSize(windowSize.width + guiFrame.getInsets().left + guiFrame.getInsets().right, windowSize.height + guiFrame.getInsets().top + guiFrame.getInsets().bottom);
        guiFrame.setVisible(true);
        board.loseOp = ()->{
            JOptionPane.showMessageDialog(null, "You lose.");
            board.applyToAll((BoardSquareButton b)->b.mine=false);
            board.createMines(NUM_MINES);
            board.reset();
        };
        board.winOp = ()->{
            JOptionPane.showMessageDialog(null, "You win!");
            board.applyToAll((BoardSquareButton b)->b.mine=false);
            board.createMines(NUM_MINES);
            board.reset();
        };
    }

    public static void main(String[] args) {
        new Psycg2Main();
    }
}
