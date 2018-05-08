public class Board {

    BoardSquareButton[][] boardSquareButtons;
    Runnable loseOp, winOp;
    int width, height;


    public Board(int width, int height) {
        boardSquareButtons = new BoardSquareButton[width][height];
        this.width = width;
        this.height = height;
    }

    public BoardSquareButton getButton(int x, int y){
        return boardSquareButtons[x][y];
    }

    public void reset(){
        applyToAll(BoardSquareButton::reset);
    }

    public void createMines(int numberOfMines){
        for (int i = 0; i < numberOfMines; i++) {
            int cX = (int)(Math.random()*width);
            int cY = (int)(Math.random()*height);
            while (getButton(cX, cY).mine){
                cX = (int)(Math.random()*width);
                cY = (int)(Math.random()*height);
            }
            getButton(cX, cY).setMine(true);
        }
    }

    public void reveal(){
        applyToAll(BoardSquareButton::reveal);
        if(loseOp!=null){
            loseOp.run();
        }
    }

    public void hasWon(){
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                BoardSquareButton b = getButton(x, y);
                if (!b.investigated && !b.mine) return;
            }
        }
        winOp.run();
    }

    public void applyToAll(ButtonOp op){
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                op.op(getButton(x, y));
            }
        }
    }
}

interface ButtonOp{
    void op(BoardSquareButton button);
}