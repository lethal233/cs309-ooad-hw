import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Observer Pattern 2020");
        setSize(720, 630);
        setBackground(Color.gray);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        MainPanel mainPanel = new MainPanel();
        mainPanel.setLocation(5, 5);
        this.add(mainPanel);

        ButtonPanel buttonPanel = new ButtonPanel();
        buttonPanel.setLocation(600, 5);
        this.add(buttonPanel);


        JButton red = buttonPanel.getAddRed();
        JButton blue = buttonPanel.getAddBlue();
        JButton start = buttonPanel.getStart();

        GreenBall greenBall = new GreenBall(Color.GREEN, 7, 7, 40, mainPanel);
        mainPanel.setGreenBall(greenBall);
//        for (int i = 0; i < 99; i++) {
        RedBall redBall = new RedBall(Color.RED, 4, 5, 60, mainPanel);
        mainPanel.addBallToPanel(redBall);
        greenBall.registerObservers(redBall);
//        }


        red.addActionListener(l -> {
            if (Ball.getCount() < Ball.TOTAL_NUM) {
                RedBall redBall1 = new RedBall(Color.RED, 4, 5, 60, mainPanel);
                greenBall.registerObservers(redBall1);
                mainPanel.addBallToPanel(redBall1);
            }
        });

        blue.addActionListener(l -> {
            if (Ball.getCount() < Ball.TOTAL_NUM) {
                BlueBall ball = new BlueBall(Color.BLUE, 6, 4, 60, mainPanel);
                greenBall.registerObservers(ball);
                mainPanel.addBallToPanel(ball);
            }
        });

        start.addActionListener(l -> {
            mainPanel.startGame();
            red.setEnabled(false);
            blue.setEnabled(false);
            start.setEnabled(false);
        });

    }
}
