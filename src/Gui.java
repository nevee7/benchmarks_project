import java.awt.*;
import java.io.IOException;
import java.net.URL;

import javax.swing.*;

public class gui {
    private JFrame frame;
    private ImageIcon background;
    private ImageIcon logo;
    private JLabel backgroundLabel;
    private JLabel logoLabel;
    public gui() {
        background = new ImageIcon(String.valueOf(new ImageIcon("background.jpg")));
        backgroundLabel = new JLabel(background);
        backgroundLabel.setSize(885, 598);

        logo = new ImageIcon(String.valueOf(new ImageIcon("logo.png")));
        logoLabel = new JLabel(logo);
        logoLabel.setSize(785, 535);
        logoLabel.setBounds(0,0,770,144);

        int offset = 200;
        int yButtons = 500;
        JButton cpuButton = new JButton("CPU");
        cpuButton.setBounds(50,yButtons,95,30);
        cpuButton.setOpaque(false);
        cpuButton.setContentAreaFilled(false);
        cpuButton.setBorderPainted(false);
        JButton resultsButton = new JButton("Results");
        resultsButton.setBounds(50+offset,yButtons,95,30);
        JButton gpuButton = new JButton("GPU");
        gpuButton.setBounds(50+offset*2,yButtons,95,30);

        frame = new JFrame("Swing Tester");
        //frame.add(logoLabel);
        frame.add(cpuButton);
        frame.add(resultsButton);
        frame.add(gpuButton);
        frame.add(backgroundLabel);

        frame.setSize(885, 598);
        frame.repaint();
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new gui();
    }
}
