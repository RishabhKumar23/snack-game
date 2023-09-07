import javax.swing.JFrame;
import java.awt.Color;

//package snackGame;
public class Main {
    public static void main(String[] args)
    {
        // creating game frame
        JFrame frame = new JFrame("Snack Game");
        frame.setBounds(10, 10, 905, 700);
        frame.setResizable(false); // to fixed frame size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // add game panel to frame
        GamePanel panel = new GamePanel(); // creating object
        panel.setBackground(Color.DARK_GRAY); // setting background
        frame.add(panel); // adding panel to frame

        frame.setVisible(true);

    }
}