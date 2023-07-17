import javax.swing.*;

public class SnakeGame extends JFrame {
    Board  board;
    SnakeGame(){
        board=new Board();
        add(board);//caling Board class
        setTitle("Snake Game");//
        pack();
        setResizable(false);//it will not resizeble
        setVisible(true);
    }

    public static void main(String[] args) {
        //Initialization value
         SnakeGame snakeGame = new SnakeGame();

    }
}