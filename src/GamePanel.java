import javax.swing.JPanel;
import java.awt.*;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;
import java.util.Random;
import javax.swing.Timer;
public class GamePanel extends JPanel implements ActionListener, KeyListener
{
    // Defining/Creating Snack position
    private int[] snakeXlength = new int[750];

    private int[] snakeYlength = new int[750];
    private int lengthOfSnack = 3;
    private int score = 0;
    private boolean gameOver = false;

    // making/initializing array (x and y co-ordinates) for "eggs" to generate.
    private int[] xPow = {25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625, 650, 675, 700, 725, 750, 775, 800, 825, 850};
    private int[] yPow = {75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625};
    private Random random = new Random();
    private int enemyX, enemyY;

    // Defining/Creating all direction
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;

    // to check initial state of snake
    private int moves = 0;

    // images of snake game
    private final ImageIcon snakeTitle = new ImageIcon(Objects.requireNonNull(getClass().getResource("snaketitle.jpg")));
    private final ImageIcon leftMouth = new ImageIcon(Objects.requireNonNull(getClass().getResource("leftmouth.png")));
    private final ImageIcon rightMouth = new ImageIcon(Objects.requireNonNull(getClass().getResource("rightmouth.png")));
    private final ImageIcon upMouth = new ImageIcon(Objects.requireNonNull(getClass().getResource("upmouth.png")));
    private final ImageIcon downMouth = new ImageIcon(Objects.requireNonNull(getClass().getResource("downmouth.png")));
    private final ImageIcon snakeImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("snakeimage.png")));
    private final ImageIcon enemy = new ImageIcon(Objects.requireNonNull(getClass().getResource("enemy.png")));

    //
    private Timer timer;
    private int delay = 100;

    // creating constructor
    GamePanel ()
    {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);

        timer = new Timer(delay, this);
        timer.start();

        newEnemy();

    }


    // creating method
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);

        g.setColor(Color.WHITE);
        g.drawRect(24, 10, 851, 55);
        g.drawRect(24, 74, 851, 576);

        snakeTitle.paintIcon(this, g, 25, 11);

        g.setColor(Color.BLACK);
        g.fillRect(25, 75, 850, 575);

        // drawing snake
        if (moves == 0)
        {
            // X length of snake
            snakeXlength[0] = 100;
            snakeXlength[1] = 75;
            snakeXlength[2] = 50;
            // Y length of snake
            snakeYlength[0] = 100;
            snakeYlength[1] = 100;
            snakeYlength[2] = 100;
        }
        if (left)
        {
            leftMouth.paintIcon(this, g, snakeXlength[0], snakeYlength[0]);
        }
        if (right)
        {
            rightMouth.paintIcon(this, g, snakeXlength[0], snakeYlength[0]);
        }
        if (up)
        {
            upMouth.paintIcon(this, g, snakeXlength[0], snakeYlength[0]);
        }
        if (down)
        {
            downMouth.paintIcon(this, g, snakeXlength[0], snakeYlength[0]);
        }

        for(int i = 1; i < lengthOfSnack; i++)
        {
            snakeImage.paintIcon(this, g, snakeXlength[i], snakeYlength[i]);
        }

        enemy.paintIcon(this, g, enemyX, enemyY);

        if(gameOver)
        {

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("Game Over", 300, 300);

            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press Space [---] to restart", 320, 350);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 14));
            g.drawString("Score: " + score, 750, 30);
            g.drawString("Length: " + lengthOfSnack, 750, 50);
        }

        g.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        for(int i = lengthOfSnack -1; i > 0; i--)
        {
            snakeXlength[i] = snakeXlength[i - 1];
            snakeYlength[i] = snakeYlength[i - 1];
        }

        if(left)
        {
            snakeXlength[0] = snakeXlength[0] - 25;
        }
        if(right)
        {
            snakeXlength[0] = snakeXlength[0] + 25;
        }
        if(up)
        {
            snakeYlength[0] = snakeYlength[0] - 25;
        }
        if(down)
        {
            snakeYlength[0] = snakeYlength[0] + 25;
        }

        /*
         * if goes to right then come from left side vise-versa
         * if goes to up then come from down side vise-versa
         */
        if(snakeXlength[0] > 850)
        {
            snakeXlength[0] = 25;
        }
        if(snakeXlength[0] < 25)
        {
            snakeXlength[0] = 850;
        }
        if(snakeYlength[0] > 625)
        {
            snakeYlength[0] = 75;
        }
        if(snakeYlength[0] < 75)
        {
            snakeYlength[0] = 625;
        }

        colliedWithEnemy();
        colliedWithBody();

        repaint();
    }

    private void colliedWithBody()
    {
        for (int i = lengthOfSnack - 1; i > 0; i--)
        {
            if(snakeXlength[i] == snakeXlength[0] && snakeYlength[i] == snakeYlength[0])
            {
                timer.stop();
                gameOver = true;
            }
        }
    }

    private void colliedWithEnemy()
    {
        if(snakeXlength[0] == enemyX && snakeYlength[0] == enemyY)
        {
            newEnemy();
            lengthOfSnack++;
            score++;
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            restart();
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT && (!right))
        {
            left = true;
            up = false;
            down = false;
            moves++;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT && (!left))
        {
            right = true;
            up = false;
            down = false;
            moves++;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP && (!down))
        {
            left = false;
            right = false;
            up = true;
            moves++;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN && (!up))
        {
            left = false;
            right = false;
            down = true;
            moves++;
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }

    private void newEnemy()
    {
        enemyX = xPow[random.nextInt(34)];
        enemyY = yPow[random.nextInt(23)];

        for(int i = lengthOfSnack -1; i >= 0; i--)
        {
            if(snakeXlength[i] == enemyX && snakeYlength[i] == enemyY)
            {
                newEnemy();
            }
        }
    }

    private void restart()
    {
        gameOver = false;
        moves = 0;
        score = 0;
        lengthOfSnack = 3;
        left = false;
        right = true;
        up = false;
        down = false;
        timer.start();
        repaint();
    }
}
