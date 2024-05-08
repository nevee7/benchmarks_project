package gui;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.sound.sampled.*;
import javax.swing.*;

class buttonDimension{
    int x,y;
    public buttonDimension(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

public class Gui {
    public Gui() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File musicPath = new File("resources" + File.separator + "gui" + File.separator + "themeSong.wav");
        AudioInputStream song = AudioSystem.getAudioInputStream(musicPath);
        Clip clip = AudioSystem.getClip();
        clip.open(song);
        clip.start();
        clip.close();
        musicPath = new File("resources" + File.separator + "gui" + File.separator + "melodie1.wav");
        song = AudioSystem.getAudioInputStream(musicPath);
        clip.open(song);
        clip.loop(1000);
        clip.start();


        JFrame frame;

        JLabel backgroundLabel = createLabel("resources"+ File.separator + "gui" + File.separator + "background.jpg",885,598);

        int offset = 200;
        int yButtons = 500;
        buttonDimension dimensiuneButon = new buttonDimension(95,30);



        JButton cpuButton = getjButton("CPU");;
        cpuButton.setBounds(50, yButtons, dimensiuneButon.getX(), dimensiuneButon.getY());


        JButton resultsButton = getjButton("Results");
        resultsButton.setBounds(50+offset, yButtons, dimensiuneButon.getX(), dimensiuneButon.getY());

        JButton gpuButton = getjButton("GPU");
        gpuButton.setBounds(50+offset*2, yButtons, dimensiuneButon.getX(), dimensiuneButon.getY());

        frame = new JFrame("Swing Tester");

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
        frame.setResizable(false);
    }

    private static JButton getjButton(String text) {
        JButton transparentButton = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.setComposite(AlphaComposite.SrcOver.derive(0.7f)); // Set the transparency here
                g2.fillRoundRect(0, 0, getWidth(), getHeight(),10,10);
                g2.dispose();

                super.paintComponent(g);
            }
        };

        transparentButton.setContentAreaFilled(false); // Ensure that the button doesn't paint its background
        transparentButton.setBorderPainted(false); // Remove the border
        return transparentButton;
    }

    JLabel createLabel(String path,int dimX,int dimY){
        JLabel label;
        ImageIcon image;
        image = new ImageIcon(String.valueOf(new ImageIcon(path)));
        label = new JLabel(image);
        label.setSize(dimX, dimY);

        return label;
    }
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        new Gui();
    }
}