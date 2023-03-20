import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePannel extends JPanel implements ActionListener {
    static  final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_HEIGHT * SCREEN_WIDTH) / UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 5;
    int appleseaten;
    int applex;
    int appley;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    int highScore;
    boolean playAgain;
    JButton retryButton;
    GamePannel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdaptor());
        startGame();

    }
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY , this);
        timer.start();

    }

    public void speedUp() {
        timer.stop();
        timer = new Timer(DELAY - (this.appleseaten * 2), this);
        timer.start();

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }
    public void draw(Graphics g){
        if(running) {
            g.setColor(Color.red);
            g.fillOval(applex, appley, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.red);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillOval(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD ,20 ));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + appleseaten, (SCREEN_WIDTH - metrics.stringWidth("Score:"  + appleseaten))/2 , g.getFont().getSize());
        }else{
            endGame(g);
        }

    }
    public void newApple(){
        applex = random.nextInt((int)SCREEN_WIDTH/UNIT_SIZE) * UNIT_SIZE;
        appley = random.nextInt((int)SCREEN_HEIGHT/UNIT_SIZE) * UNIT_SIZE;

    }
    public void move(){
        for(int i = bodyParts; i>0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch (direction) {
            case 'U' -> y[0] = y[0] - UNIT_SIZE;
            case 'D' -> y[0] = y[0] + UNIT_SIZE;
            case 'L' -> x[0] = x[0] - UNIT_SIZE;
            case 'R' -> x[0] = x[0] + UNIT_SIZE;
        }

    }
    public void checkApple(){
        if((x[0] == applex) &&(y[0] == appley)){
            bodyParts ++;
            appleseaten++;
            speedUp();
            newApple();
        }

    }
    public void checkCollisions(){
        for(int i = bodyParts; i > 0; i--){
            if((x[0] == x[i])&& (y[0] == y[i])){
                running = false;
            }
        }
        if(x[0] < 0){
            running = false;
        }
        if(x[0] > SCREEN_WIDTH){
            running = false;
        }
        if (y[0] < 0){
            running = false;
        }
        if(y[0] > SCREEN_HEIGHT){
            running = false;
        }
        if (!running){
            timer.stop();
        }

    }
    public void endGame(Graphics g){

            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 100));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("GAME OVER", (SCREEN_WIDTH - metrics.stringWidth("GAME OVER")) / 2, SCREEN_HEIGHT / 2);
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 20));
            FontMetrics metrics1 = getFontMetrics(g.getFont());
            g.drawString("Your Score: " + appleseaten, (SCREEN_WIDTH - metrics1.stringWidth("Your Score:" + appleseaten)) / 2, g.getFont().getSize());
            highScore(appleseaten);
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 20));
            FontMetrics metrics2 = getFontMetrics(g.getFont());
            g.drawString("HighScore: " + highScore, (SCREEN_WIDTH - metrics2.stringWidth("HighScore:" + highScore)) / 2, SCREEN_WIDTH - (g.getFont().getSize()));
        timer = new Timer(100000000 , this);
        timer.start();

        retryButton = new JButton("Try Again! ");
        retryButton.setFont(new Font("Lexenda Deca", Font.PLAIN, 20));
        retryButton.setBackground(Color.blue);
        retryButton.setBounds((SCREEN_WIDTH / 2 ) - 100, (SCREEN_HEIGHT / 2) + 200, 200, 50);
        this.add(retryButton);
        retryButton.addActionListener(e -> restart(g));

    }
    public void restart(Graphics g) {
        g.clearRect(0, 0, this.getWidth(), this.getHeight());
        startGame();

    }

    public void startAgain(){
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdaptor());
        startGame();
    }
    public void highScore(int score){
        if(score > this.highScore)
            this.highScore = score;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();

    }
    public class MyKeyAdaptor extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT -> {
                    if(direction != 'R'){direction = 'L';
                    }
                }
                case KeyEvent.VK_RIGHT -> {
                    if(direction != 'L'){direction = 'R';
                    }
                }
                case KeyEvent.VK_UP -> {
                    if(direction != 'D'){direction = 'U';
                    }
                }
                case KeyEvent.VK_DOWN -> {
                    if(direction != 'U'){direction = 'D';
                    }
                }
                case KeyEvent.VK_SPACE-> { playAgain = true;}
            }

        }
    }

}
