import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
    int B_HEIGHT = 400;

    int B_WiDTH = 400;

    int MAX_Dot = 1600;

    int x[] = new int[MAX_Dot];
    int y[] = new int[MAX_Dot];
    int DOT_Size = 10;
    int DOTS = 3;

    int apple_x ;
    int apple_y ;

    Image body, head,apple;

    Timer timer; 

    int DELAY = 300;

    boolean leftDirection = true;
    boolean rightDirection = false;
    boolean upDirection = false;
    boolean downDirection = false;

    boolean inGame = true;


    Board(){
        TAdapter tAdapter = new TAdapter();
        addKeyListener(tAdapter);
        setFocusable(true);
        setPreferredSize(new Dimension(B_WiDTH, B_HEIGHT));//here fram size
        setBackground(Color.black);//
        initGame();
        loadImages();
    }

    //Initialization sotre each index
    public void initGame(){

        DOTS = 3;
        x[0] = 250;
        y[0] = 250;
        for (int i = 1; i <DOTS ; i++) {
             x[i] = x[0]+DOT_Size*i;
             y[i] = y[0];

        }
        locateApple();
        timer = new Timer(DELAY, this);
        timer.start();
    }
     //Load image from resources folder to image object
    public void loadImages(){
       ImageIcon bodyIcon = new ImageIcon("src/Resources/dot.png");
       body = bodyIcon.getImage();
       ImageIcon headIcon= new ImageIcon("src/Resources/head.png");
       head = headIcon.getImage();
       ImageIcon appleIcon= new ImageIcon("src/Resources/apple.png");
       apple = appleIcon.getImage();
    }
    // draw images of Snake and apple position
    @Override
    public  void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }
    public void doDrawing(Graphics g){
          if(inGame){
              g.drawImage(apple,apple_x,apple_y, this);
              for (int i = 0; i < DOTS; i++) {
                  if(i==0){
                      g.drawImage(head, x[0], y[0], this);
                  }else
                      g.drawImage(body, x[i], y[i], this);

              }
          }
          else {
              gameOver(g);
              timer.stop();

          }

    }
    //Randomize apple position
    public void locateApple(){
        apple_x =((int)(Math.random()*39))*DOT_Size;
        apple_y =((int)(Math.random()*39))*DOT_Size;
    }
   //Chech colisions with Border and Body
   public  void  checkCollision(){
        //Colision with Body
        for(int i = 1;i<DOTS;i++){
            if(i>4&&x[0]==x[i] && y[0]==y[i]){
                inGame = false;
            }
        }
        //Collision with Border
       if(x[0]<0){
           inGame = false;
       }
       if(x[0]>=B_WiDTH){
           inGame = false;
       }
       if(y[0]<0){
           inGame = false;
       }
       if(y[0]>=B_HEIGHT){
           inGame = false;
       }
   }
     //Display gameOver message
    public void gameOver(Graphics g){
        String msg = "Game Over";
        int score =(DOTS-3)*100;
        String scoremsg = "Score:"+Integer.toString(score);
        Font small = new Font("Helvetica", Font.BOLD, 24);
        FontMetrics fontMetrics = getFontMetrics(small);

        g.setColor(Color.BLUE);
        g.setFont(small);
        g.drawString(msg,(B_WiDTH-fontMetrics.stringWidth(msg))/2, 180);
        g.drawString(scoremsg,(B_WiDTH-fontMetrics.stringWidth(scoremsg))/2, 220);
//        JLabel status = new JLabel("status", SwingConstants.SOUTH_EAST);
//        status.setVerticalAlignment(SwingConstants.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
            if(inGame){
                checkApple();
                move();
                checkCollision();
            }
        repaint();
    }
    public void move(){
        for (int i = DOTS-1; i >0 ; i--) {
               x[i] = x[i-1];
               y[i] = y[i-1];
        }
        if(leftDirection){
            x[0] -=DOT_Size;
        }
        if(rightDirection){
            x[0] +=DOT_Size;
        }
        if(upDirection){
            y[0] -=DOT_Size;
        }
        if(downDirection){
            y[0] +=DOT_Size;
        }
    }
    // make snake eat food
    public void checkApple(){
        if(apple_x==x[0] && apple_y==y[0]){
            DOTS++;
            locateApple();
        }
    }
    //Implement Controlas
    private  class TAdapter extends KeyAdapter{
        @Override
        public void  keyPressed(KeyEvent keyEvent){
            int key = keyEvent.getKeyCode();
            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }


        }
    }
}
