import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BoardSquareButton extends JButton {

    int bX, bY;
    boolean mine = false, investigated = false, flagged = false;
    Board board;
    Color color;

    public BoardSquareButton(Dimension size, Color color, int bX, int bY, Board board) {
        this.board = board;
        this.bX = bX;
        this.bY = bY;
        setMinimumSize(new Dimension(10, 10));
        setPreferredSize(size);
        setFont(new Font("Arial", Font.PLAIN, 30));
        this.color = color == null?Color.gray:color;
        reset();
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switch (e.getButton()){
                    case 1:
                        investigate();
                        break;
                    case 3:
                        flag();
                    default:
                }
            }

            @Override
            public void mousePressed(MouseEvent e) { }

            @Override
            public void mouseReleased(MouseEvent e) { }

            @Override
            public void mouseEntered(MouseEvent e) { }

            @Override
            public void mouseExited(MouseEvent e) { }
        });
    }

    public void reset(){
        setText("?");
        setBackground(color);
    }

    public int countSurrounding(){
        if(mine) return -1;
        int count = 0;
        for (int x = Math.max(bX -1, 0); x < Math.min(bX +2, board.width); x++) {
            for (int y = Math.max(bY -1, 0); y < Math.min(bY +2, board.height); y++) {
                if(board.getButton(x, y).mine) count++;
            }
        }
        return count;
    }

    public void investigate(){
        investigated=true;
        if(mine){
            board.reveal();

        }else{
            reveal();
            if(countSurrounding()==0){
                for (int x = Math.max(bX -1, 0); x < Math.min(bX +2, board.width); x++) {
                    for (int y = Math.max(bY -1, 0); y < Math.min(bY +2, board.height); y++) {
                        if((x != bX || y!= bY) && !board.getButton(x, y).investigated) board.getButton(x, y).investigate();
                    }
                }
            }
        }
        board.hasWon();
    }

    public void reveal(){
        if(mine){
            setBackground(Color.red);
            setText("X");
        }else{
            setBackground(Color.green);
            setText(countSurrounding()+"");
        }
    }

    public void flag(){
        flagged = !flagged;
        if(flagged){
            setText("?");
            setBackground(Color.orange);
        }else{
            reset();
        }

    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }
}
