package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import javax.swing.*;

class ButtonDimension {
    int x, y;

    public ButtonDimension(int x, int y) {
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
    private JFrame frame;
    private JFrame resultFrame;
    private JFrame gpuFrame;
    private JFrame cpuFrame;

    public Gui() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        playAudio("resources" + File.separator + "gui" + File.separator + "melodie1.wav", true, 1000);

        frame = new JFrame("SRESSING TITANS");
        int frameWidth = 1041;
        int frameHeight = 704;
        frame.setSize(frameWidth, frameHeight);

        JLabel backgroundLabel = createLabel("resources" + File.separator + "gui" + File.separator + "image3.png", frameWidth, frameHeight);

        int offset = 230;
        int yButtons = 585;
        int xButtons = 60;
        ButtonDimension buttonDimension = new ButtonDimension(110, 50);

        JButton cpuButton = createButton("CPU");
        cpuButton.setBounds(xButtons, yButtons, buttonDimension.getX(), buttonDimension.getY());
        cpuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                showCPUFrame(frameWidth, frameHeight);
            }
        });

        JButton resultsButton = createButton("Results");
        resultsButton.setBounds(xButtons + offset, yButtons, buttonDimension.getX(), buttonDimension.getY());
        resultsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                showResultFrame(frameWidth, frameHeight);
            }
        });

        JButton gpuButton = createButton("GPU");
        gpuButton.setBounds(xButtons + offset * 2, yButtons, buttonDimension.getX(), buttonDimension.getY());
        gpuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                showGPUFrame(frameWidth, frameHeight);
            }
        });

        frame.add(cpuButton);
        frame.add(resultsButton);
        frame.add(gpuButton);
        frame.add(backgroundLabel);

        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void showCPUFrame(int width, int height) {
        cpuFrame = new JFrame("CPU Details");
        int widthCPU = 1041;
        int heightCPU = 704;
        cpuFrame.setSize(width, height);
        cpuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cpuFrame.setLocationRelativeTo(null);
        cpuFrame.setVisible(true);

        // Add CPU details components to this frame
        JLabel cpuLabel = new JLabel("CPU Details");
        cpuLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cpuFrame.add(cpuLabel, BorderLayout.CENTER);

        // Add background image
        JLabel backgroundLabel = createLabel("resources" + File.separator + "gui" + File.separator + "imageCPU1.png", widthCPU, heightCPU);
        cpuFrame.add(backgroundLabel);

        // Example: Adding a button to close the CPU frame and show the main frame again
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cpuFrame.dispose();
                frame.setVisible(true);
            }
        });
        cpuFrame.add(closeButton, BorderLayout.SOUTH);
    }

    private void showResultFrame(int width, int height) {
        resultFrame = new JFrame("Results");
        int widthRes = 1041;
        int heightRes = 704;
        resultFrame.setSize(width, height);
        resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        resultFrame.setLocationRelativeTo(null);
        resultFrame.setVisible(true);

        // Add result details components to this frame
        JLabel resultLabel = new JLabel("Results Details");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultFrame.add(resultLabel, BorderLayout.CENTER);

        // Add background image
        JLabel backgroundLabel = createLabel("resources" + File.separator + "gui" + File.separator + "image4.png", widthRes, heightRes);
        resultFrame.add(backgroundLabel);

        // Example: Adding a button to close the result frame and show the main frame again
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resultFrame.dispose();
                frame.setVisible(true);
            }
        });
        resultFrame.add(closeButton, BorderLayout.SOUTH);
    }

    private void showGPUFrame(int width, int height) {
        gpuFrame = new JFrame("GPU Details");
        int widthGPU = 1041;
        int heightGPU = 704;
        gpuFrame.setSize(width, height);
        gpuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gpuFrame.setLocationRelativeTo(null);
        gpuFrame.setVisible(true);

        // Add GPU details components to this frame
        JLabel gpuLabel = new JLabel("GPU Details");
        gpuLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gpuFrame.add(gpuLabel, BorderLayout.CENTER);

        // Add background image
        JLabel backgroundLabel = createLabel("resources" + File.separator + "gui" + File.separator + "imageGPU1.png", widthGPU, heightGPU);
        gpuFrame.add(backgroundLabel);

        // Example: Adding a button to close the GPU frame and show the main frame again
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gpuFrame.dispose();
                frame.setVisible(true);
            }
        });
        gpuFrame.add(closeButton, BorderLayout.SOUTH);
    }

    private void playAudio(String filePath, boolean loop, int loopCount) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File musicPath = new File(filePath);
        AudioInputStream song = AudioSystem.getAudioInputStream(musicPath);
        Clip clip = AudioSystem.getClip();
        clip.open(song);
        if (loop) {
            clip.loop(loopCount);
        } else {
            clip.start();
        }
    }

    private static JButton createButton(String text) {
        JButton transparentButton = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.setComposite(AlphaComposite.SrcOver.derive(0.7f)); // Set the transparency here
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();

                super.paintComponent(g);
            }
        };

        transparentButton.setContentAreaFilled(false); // Ensure that the button doesn't paint its background
        transparentButton.setBorderPainted(false); // Remove the border

        // Set a larger font for the button text
        Font buttonFont = new Font("Dreaming Outloud Pro", Font.BOLD, 18); // You can adjust the size (18) as needed
        transparentButton.setFont(buttonFont);

        return transparentButton;
    }

    JLabel createLabel(String path, int dimX, int dimY) {
        JLabel label;
        ImageIcon image;
        image = new ImageIcon(path);
        label = new JLabel(image);
        label.setSize(dimX, dimY);

        return label;
    }

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        new Gui();
    }
}
