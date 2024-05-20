package gui;

import benchmark.BenchmarkInfo;
import cpuBenchmarks.ArithmeticOperationBenchmark;
import cpuBenchmarks.FibonacciBenchmark;
import cpuBenchmarks.MatrixMultiplicationBenchmark;
import cpuBenchmarks.PiDigitComputationBenchmark;
import firebase.Firebase;
import org.lwjglb.game.Main;
import org.lwjglb.game.Warmup;

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
        Firebase.initializeFirebase();
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
        cpuFrame = new JFrame("CPU Benchmarks");
        cpuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cpuFrame.setSize(width, height);
        cpuFrame.setLocationRelativeTo(null);

        // Create a JPanel to hold the background image and buttons
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load and draw the background image
                ImageIcon image = new ImageIcon("resources" + File.separator + "gui" + File.separator + "cpuSelect.jpg");
                g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(null); // Use null layout to set absolute positions for buttons

        // Add buttons for CPU details with specific positions and sizes
        JButton button1 = createButton("FIBONACCI SEQUENCE");
        button1.setBounds(60, 240, 280, 50); // Set position and size
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Hide the CPU frame
                cpuFrame.setVisible(false);
                // Show the Button 1 frame
                showButton1Frame();
            }
        });
        panel.add(button1);

        JButton button2 = createButton("ARITHMETIC OPS");
        button2.setBounds(60, 310, 280, 50); // Set position and size
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Hide the CPU frame
                cpuFrame.setVisible(false);
                // Show the Button 2 frame
                showButton2Frame();
            }
        });
        panel.add(button2);

        JButton button3 = createButton("PI DIGIT COMPUTATION");
        button3.setBounds(60, 380, 280, 50); // Set position and size
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Hide the CPU frame
                cpuFrame.setVisible(false);
                // Show the Button 3 frame
                showButton3Frame();
            }
        });
        panel.add(button3);

        JButton button4 = createButton("MATRIX MULTIPLICATION");
        button4.setBounds(60, 450, 280, 50); // Set position and size
        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Hide the CPU frame
                cpuFrame.setVisible(false);
                // Show the Button 4 frame
                showButton4Frame();
            }
        });
        panel.add(button4);

        // Adding a button to close the CPU frame and show the main frame again
        JButton closeButton = createButton("Close");
        closeButton.setBounds(60, 550, 200, 50); // Set position and size
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cpuFrame.dispose();
                frame.setVisible(true);
            }
        });
        panel.add(closeButton);

        cpuFrame.setContentPane(panel); // Set the panel as the content pane
        cpuFrame.setVisible(true);
    }

    private void showButton1Frame() {
        JFrame button1Frame = new JFrame("Button 1 Frame");
        button1Frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        button1Frame.setSize(1041, 704); // Set size as needed
        button1Frame.setLocationRelativeTo(cpuFrame); // Position relative to CPU frame

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon image = new ImageIcon("resources" + File.separator + "gui" + File.separator + "button1background.jpg");
                g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(null); // Use null layout to set absolute positions for buttons

        JButton specificButton = createButton("START");
        specificButton.setBounds(50, 440, 250, 55);
        specificButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FibonacciBenchmark fibonacciBenchmark = new FibonacciBenchmark();
                BenchmarkInfo data = fibonacciBenchmark.startBenchmark();
                try {
                    Firebase.writeData(data);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        panel.add(specificButton);

        JButton closeButton = createButton("Close");
        closeButton.setBounds(50, 550, 200, 55);
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button1Frame.dispose();
                cpuFrame.setVisible(true);
            }
        });
        panel.add(closeButton);

        button1Frame.setContentPane(panel);
        button1Frame.setVisible(true);
    }

    private void showButton2Frame() {
        JFrame button2Frame = new JFrame("Button 2 Frame");
        button2Frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        button2Frame.setSize(1041, 704); // Set size as needed
        button2Frame.setLocationRelativeTo(cpuFrame); // Position relative to CPU frame

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon image = new ImageIcon("resources" + File.separator + "gui" + File.separator + "button2background.jpg");
                g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(null); // Use null layout to set absolute positions for buttons

        JButton specificButton = createButton("START");
        specificButton.setBounds(50, 440, 250, 55);
        specificButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArithmeticOperationBenchmark arithmeticOperationBenchmark = new ArithmeticOperationBenchmark();
                BenchmarkInfo data = arithmeticOperationBenchmark.startBenchmark();
                try {
                    Firebase.writeData(data);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        panel.add(specificButton);

        JButton closeButton = createButton("Close");
        closeButton.setBounds(50, 550, 200, 55);
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button2Frame.dispose();
                cpuFrame.setVisible(true);
            }
        });
        panel.add(closeButton);

        button2Frame.setContentPane(panel);
        button2Frame.setVisible(true);
    }

    private void showButton3Frame() {
        JFrame button3Frame = new JFrame("Button 3 Frame");
        button3Frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        button3Frame.setSize(1041, 704); // Set size as needed
        button3Frame.setLocationRelativeTo(cpuFrame); // Position relative to CPU frame

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon image = new ImageIcon("resources" + File.separator + "gui" + File.separator + "button3background.jpg");
                g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(null); // Use null layout to set absolute positions for buttons

        JButton specificButton = createButton("START");
        specificButton.setBounds(50, 440, 250, 55);
        specificButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PiDigitComputationBenchmark piDigitComputationBenchmark = new PiDigitComputationBenchmark();
                BenchmarkInfo data = piDigitComputationBenchmark.startBenchmark();
                try {
                    Firebase.writeData(data);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        panel.add(specificButton);

        JButton closeButton = createButton("Close");
        closeButton.setBounds(50, 550, 200, 55);
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button3Frame.dispose();
                cpuFrame.setVisible(true);
            }
        });
        panel.add(closeButton);

        button3Frame.setContentPane(panel);
        button3Frame.setVisible(true);
    }

    private void showButton4Frame() {
        JFrame button4Frame = new JFrame("Button 4 Frame");
        button4Frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        button4Frame.setSize(1041, 704); // Set size as needed
        button4Frame.setLocationRelativeTo(cpuFrame); // Position relative to CPU frame

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon image = new ImageIcon("resources" + File.separator + "gui" + File.separator + "button4background.jpg");
                g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(null); // Use null layout to set absolute positions for buttons

        JButton specificButton = createButton("START");
        specificButton.setBounds(50, 440, 250, 55);
        specificButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MatrixMultiplicationBenchmark matrixMultiplicationBenchmark = new MatrixMultiplicationBenchmark();
                BenchmarkInfo data = matrixMultiplicationBenchmark.startBenchmark();
                try {
                    Firebase.writeData(data);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        panel.add(specificButton);

        JButton closeButton = createButton("Close");
        closeButton.setBounds(50, 550, 200, 55);
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button4Frame.dispose();
                cpuFrame.setVisible(true);
            }
        });
        panel.add(closeButton);

        button4Frame.setContentPane(panel);
        button4Frame.setVisible(true);
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
        JLabel backgroundLabel = createLabel("resources" + File.separator + "gui" + File.separator + "image5.jpg", widthRes, heightRes);
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
        gpuFrame = new JFrame("GPU Benchmarks");
        gpuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gpuFrame.setSize(width, height);
        gpuFrame.setLocationRelativeTo(null);

        // Create a JPanel to hold the background image and buttons
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load and draw the background image
                ImageIcon image = new ImageIcon("resources" + File.separator + "gui" + File.separator + "gpuSelect.jpg");
                g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(null); // Use null layout to set absolute positions for buttons

        // Add buttons for GPU details with specific positions and sizes
        JButton button1 = createButton("<html>GENERATING 3D MODELS<br>AND FPS COUNTING</html>");
        button1.setBounds(50, 230, 300, 70); // Set position and size
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Hide the GPU frame
                gpuFrame.setVisible(false);
                // Show the Button 1 frame
                showButton1GPUFrame();
            }
        });
        panel.add(button1);

        // Adding a button to close the GPU frame and show the main frame again
        JButton closeButton = createButton("Go to the previous menu");
        closeButton.setBounds(60, 550, 300, 50); // Set position and size
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gpuFrame.dispose();
                frame.setVisible(true);
            }
        });
        panel.add(closeButton);

        gpuFrame.setContentPane(panel); // Set the panel as the content pane
        gpuFrame.setVisible(true);
    }


    private void showButton1GPUFrame() {
        JFrame button1GPUFrame = new JFrame("You entered Trigon's hell");
        button1GPUFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        button1GPUFrame.setSize(1041, 704); // Set size as needed
        button1GPUFrame.setLocationRelativeTo(gpuFrame); // Position relative to GPU frame

        // Create a JPanel to hold the background image and buttons
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load and draw the background image
                ImageIcon image = new ImageIcon("resources" + File.separator + "gui" + File.separator + "gpustart1.jpg");
                g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(null); // Use null layout to set absolute positions for buttons

        // Adding a button for some functionality specific to this frame
        JButton warmupButton = createButton("Start Warm-Up");
        warmupButton.setBounds(50, 330, 250, 55); // Set position and size
        warmupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Warmup main = new Warmup();
                main.runWarmup();
            }
        });
        panel.add(warmupButton);

        // Adding a button for some functionality specific to this frame
        JButton specificButton = createButton("Start Benchmark");
        specificButton.setBounds(50, 440, 250, 55); // Set position and size
        specificButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Main main = new Main();
                int numberOfCubesYouWantToBeGerated=2;//if we could make a way for this to be inputed in this frame or something it would be perfect
                BenchmarkInfo data = main.runMain(numberOfCubesYouWantToBeGerated);
                try {
                    Firebase.writeData(data);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        panel.add(specificButton);

        // Add a button to close this frame and show the GPU frame again
        JButton closeButton = createButton("Go to the previous menu");
        closeButton.setBounds(50, 550, 300, 55); // Set position and size
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button1GPUFrame.dispose();
                gpuFrame.setVisible(true); // Show the GPU frame again
            }
        });
        panel.add(closeButton);

        button1GPUFrame.setContentPane(panel); // Set the panel as the content pane
        button1GPUFrame.setVisible(true);
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
                g2.setComposite(AlphaComposite.SrcOver.derive(0.8f)); // Set the transparency here
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();

                super.paintComponent(g);
            }
        };

        transparentButton.setContentAreaFilled(false); // Ensure that the button doesn't paint its background
        transparentButton.setBorderPainted(false); // Remove the border
        transparentButton.setFocusable(false);

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



