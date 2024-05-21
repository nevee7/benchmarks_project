package gui;

import benchmark.BenchmarkInfo;
import benchmark.ComputerIdentifier;
import cpuBenchmarks.ArithmeticOperationBenchmark;
import cpuBenchmarks.FibonacciBenchmark;
import cpuBenchmarks.MatrixMultiplicationBenchmark;
import cpuBenchmarks.PiDigitComputationBenchmark;
import firebase.Firebase;
import org.lwjglb.game.GPUBenchmarky;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.sound.sampled.*;
import javax.swing.*;

import static firebase.Firebase.getMyData;

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
    private JFrame waitingFrame;

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
                try {
                    showResultFrame(frameWidth, frameHeight);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
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
        JFrame button1Frame = new JFrame("Fibonacci Sequence");
        button1Frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        button1Frame.setSize(1041, 704);
        button1Frame.setLocationRelativeTo(cpuFrame);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon image = new ImageIcon("resources" + File.separator + "gui" + File.separator + "fiboCPU.jpg");
                g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(null);

        JButton specificButton = createButton("START");
        specificButton.setBounds(50, 440, 250, 55);
        specificButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button1Frame.setVisible(false);
                showWaitingFrame();
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        FibonacciBenchmark fibonacciBenchmark = new FibonacciBenchmark();
                        BenchmarkInfo data = fibonacciBenchmark.startBenchmark();
                        try {
                            Firebase.writeData(data);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        waitingFrame.setVisible(false);
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                if (data.getScore() >= 90.0) {
                                    showVictoryCPUFrame(data.getScore(), "victory1.jpg");
                                } else if (data.getScore() >= 50.0) {
                                    showMediumCPUFrame(data.getScore(), "medium1.jpg");
                                } else {
                                    showDefeatCPUFrame(data.getScore(), "defeat1.jpg");
                                }
                            }
                        });
                    }
                });
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
        JFrame button2Frame = new JFrame("Arithmetic Operations");
        button2Frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        button2Frame.setSize(1041, 704);
        button2Frame.setLocationRelativeTo(cpuFrame);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon image = new ImageIcon("resources" + File.separator + "gui" + File.separator + "ariCPU.jpg");
                g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(null);

        JButton specificButton = createButton("START");
        specificButton.setBounds(50, 440, 250, 55);
        specificButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button2Frame.setVisible(false);
                showWaitingFrame();
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        ArithmeticOperationBenchmark arithmeticOperationBenchmark = new ArithmeticOperationBenchmark();
                        BenchmarkInfo data = arithmeticOperationBenchmark.startBenchmark();
                        try {
                            Firebase.writeData(data);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        waitingFrame.setVisible(false);
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                if (data.getScore() >= 90.0) {
                                    showVictoryCPUFrame(data.getScore(), "victory2.jpg");
                                } else if (data.getScore() >= 50.0) {
                                    showMediumCPUFrame(data.getScore(), "medium2.jpg");
                                } else {
                                    showDefeatCPUFrame(data.getScore(), "defeat2.jpg");
                                }
                            }
                        });
                    }
                });
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
        JFrame button3Frame = new JFrame("PI Digit Computation");
        button3Frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        button3Frame.setSize(1041, 704);
        button3Frame.setLocationRelativeTo(cpuFrame);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon image = new ImageIcon("resources" + File.separator + "gui" + File.separator + "piCPU.jpg");
                g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(null);

        JButton specificButton = createButton("START");
        specificButton.setBounds(50, 440, 250, 55);
        specificButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button3Frame.setVisible(false);
                showWaitingFrame();
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        PiDigitComputationBenchmark piDigitComputationBenchmark = new PiDigitComputationBenchmark();
                        BenchmarkInfo data = piDigitComputationBenchmark.startBenchmark();
                        try {
                            Firebase.writeData(data);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        waitingFrame.setVisible(false);
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                if (data.getScore() >= 90.0) {
                                    showVictoryCPUFrame(data.getScore(), "victory3.jpg");
                                } else if (data.getScore() >= 50.0) {
                                    showMediumCPUFrame(data.getScore(), "medium3.jpg");
                                } else {
                                    showDefeatCPUFrame(data.getScore(),"defeat3.jpg");
                                }
                            }
                        });
                    }
                });
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
        JFrame button4Frame = new JFrame("Matrix Multiplication");
        button4Frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        button4Frame.setSize(1041, 704);
        button4Frame.setLocationRelativeTo(cpuFrame);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon image = new ImageIcon("resources" + File.separator + "gui" + File.separator + "num.jpg");
                g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(null);

        JButton specificButton = createButton("START");
        specificButton.setBounds(50, 440, 250, 55);
        specificButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button4Frame.setVisible(false);
                showWaitingFrame();
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        MatrixMultiplicationBenchmark matrixMultiplicationBenchmark = new MatrixMultiplicationBenchmark();
                        BenchmarkInfo data = matrixMultiplicationBenchmark.startBenchmark();
                        try {
                            Firebase.writeData(data);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        waitingFrame.setVisible(false);
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                if (data.getScore() >= 90.0) {
                                    showVictoryCPUFrame(data.getScore(), "victory4.jpg");
                                } else if (data.getScore() >= 50.0) {
                                    showMediumCPUFrame(data.getScore(), "medium4.jpg");
                                } else {
                                    showDefeatCPUFrame(data.getScore(), "defeat4.jpg");
                                }
                            }
                        });
                    }
                });
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

    private void showScoreCPU(double score) {
        JFrame defeatFrame = new JFrame("Score");
        defeatFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        defeatFrame.setSize(720, 540); // Set size as needed
        defeatFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load and draw the background image
                ImageIcon image = new ImageIcon("resources" + File.separator + "gui" + File.separator + "scoreCPU.png");
                g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(null); // Use null layout to set absolute positions for buttons


        JLabel finalScoreLabel = new JLabel(String.format("Final Score: %.2f", score));
        finalScoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        finalScoreLabel.setForeground(Color.WHITE); // Set text color to white
        finalScoreLabel.setBounds(230, 300, 300, 50);
        panel.add(finalScoreLabel);


        // Add a button to close the frame
        JButton closeButton = createButton("Close");
        closeButton.setBounds(50, 400, 200, 50); // Set position and size
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                defeatFrame.dispose();
                cpuFrame.setVisible(true);
            }
        });
        panel.add(closeButton);

        defeatFrame.setContentPane(panel); // Set the panel as the content pane
        defeatFrame.setVisible(true);
    }



    private void showResultFrame(int width, int height) throws Exception {
        resultFrame = new JFrame("Results");
        int widthRes = 1041;
        int heightRes = 704;
        resultFrame.setSize(width, height);
        resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        resultFrame.setLocationRelativeTo(null);
        resultFrame.setVisible(true);

        int offsetX = 20;
        int offsetY = 70;
        int labelWidth = 900;
        ArrayList<String> benchmarks = new ArrayList<>();
        benchmarks.add("ArithmeticOperationBenchmark");
        benchmarks.add("FibonacciBenchmark");
        benchmarks.add("MatrixMultiplicationBenchmark");
        benchmarks.add("PiDigitComputationBenchmark");
        benchmarks.add("GPU benchmark");
        int i = 0;
        for (String name: benchmarks) {
            Double benchmarkScore;
            String rank;
            if (getMyData(name,new ComputerIdentifier()) == null){
                benchmarkScore = 0.0;
                rank = "Not ran";
            }else {
                benchmarkScore = getMyData(name,new ComputerIdentifier());
                rank = String.valueOf((Firebase.getAllData(name).indexOf(getMyData(name,new ComputerIdentifier())) + 1));
            }
            String finalScoreText = String.format("%s\t\t Score:%.2f \t\t Rank:%s", name, benchmarkScore, rank);
            JLabel finalScoreLabel = new JLabel(finalScoreText);
            finalScoreLabel.setFont(new Font("Arial", Font.BOLD, 27));
            finalScoreLabel.setForeground(Color.WHITE);
            finalScoreLabel.setBackground(Color.BLUE);
            finalScoreLabel.setOpaque(true);
            finalScoreLabel.setHorizontalAlignment(SwingConstants.LEFT); // Center text horizontally
            finalScoreLabel.setBounds(offsetX, 30+ offsetY*i, labelWidth, 50); // Adjust bounds for better centering
            resultFrame.add(finalScoreLabel);
            i++;
        }

        // Add result details components to this frame
        JLabel resultLabel = new JLabel("Results Details");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultFrame.add(resultLabel, BorderLayout.CENTER);

        // Add background image
        JLabel backgroundLabel = createLabel("resources" + File.separator + "gui" + File.separator + "resultsFin.jpg", widthRes, heightRes);
        resultFrame.add(backgroundLabel);

        // Example: Adding a button to close the result frame and show the main frame again
        JButton closeButton = createButton("Go to the previous menu");
        closeButton.setBounds(50, 550, 300, 50); // Set position and size
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

        // Add a JLabel and JTextField for the number input
        JLabel numberLabel = new JLabel("<html>Test your GPU and see if you can defeat Trigon in the battle of generating entities<br><br>RANKING:<br>Victory -> Final Score larger than 550<br>Draw -> Final Score between 550 and 500<br>Defeat -> Final Score lower than 500 </html>\"");
        numberLabel.setBounds(55, 330, 500, 160);
        numberLabel.setForeground(Color.WHITE); // Set text color to white

        // Create a new Font object with the desired size
        Font font = new Font("Arial", Font.BOLD, 19); // Adjust the font size as needed
        numberLabel.setFont(font); // Set the font of the JLabel

        panel.add(numberLabel);

        // Adding a button to close the GPU frame and show the main frame again
        JButton closeButton = createButton("Go to the previous menu");
        closeButton.setBounds(50, 550, 300, 50); // Set position and size
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

        // Add a JLabel and JTextField for the number input
        JLabel numberLabel = new JLabel("<html>Enter the number<br>of cubes/second<br>to generate:</html>\"");
        numberLabel.setBounds(605, 200, 200, 70);
        numberLabel.setForeground(Color.WHITE); // Set text color to red
        numberLabel.setBackground(Color.RED); // Set background color to white
        numberLabel.setOpaque(true); // Make the
        panel.add(numberLabel);

        Font font = new Font("Arial", Font.BOLD, 18); // Adjust the font size as needed
        numberLabel.setFont(font); // Set the font of the JLabel

        JTextField numberField = new JTextField();
        numberField.setBounds(850, 215, 100, 40);
        panel.add(numberField);

        // Add a JLabel and JTextField for the number input
        JLabel numberLabel2 = new JLabel("<html>Enter the number<br>of titans/second<br>to generate:</html>\"");
        numberLabel2.setBounds(605, 300, 200, 70);
        numberLabel2.setForeground(Color.WHITE); // Set text color to red
        numberLabel2.setBackground(Color.RED); // Set background color to white
        numberLabel2.setOpaque(true); // Make the
        panel.add(numberLabel2);

        Font font2 = new Font("Arial", Font.BOLD, 18); // Adjust the font size as needed
        numberLabel2.setFont(font2); // Set the font of the JLabel

        JTextField numberField2 = new JTextField();
        numberField2.setBounds(850, 315, 100, 40);
        panel.add(numberField2);

        // Add a JLabel and JTextField for the number input
        JLabel numberLabel3 = new JLabel("<html>Enter the number<br>of runs for<br>the benchmark:</html>\"");
        numberLabel3.setBounds(605, 400, 200, 70);
        numberLabel3.setForeground(Color.WHITE); // Set text color to red
        numberLabel3.setBackground(Color.RED); // Set background color to white
        numberLabel3.setOpaque(true); // Make the
        panel.add(numberLabel3);

        Font font3 = new Font("Arial", Font.BOLD, 18); // Adjust the font size as needed
        numberLabel3.setFont(font2); // Set the font of the JLabel

        JTextField numberField3 = new JTextField();
        numberField3.setBounds(850, 415, 100, 40);
        panel.add(numberField3);

        // Adding a button for some functionality specific to this frame
        JButton specificButton = createButton("<html>Start Benchmark<br>(with input numbers)</html>\"");
        specificButton.setBounds(605, 540, 350, 70); // Set position and size
        specificButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = numberField.getText();
                String input2 = numberField2.getText();
                String input3 = numberField3.getText();
                try{
                    button1GPUFrame.setVisible(false);
                    int number = Integer.parseInt(input);
                    int number2 = Integer.parseInt(input2);
                    int number3 = Integer.parseInt(input3);
                    if (number > 1000000){
                        JOptionPane.showMessageDialog(button1GPUFrame, "<html>The provided number is too large!<br>Maximum 1.000.000 cubes/second can be generated!</html>", "error", JOptionPane.ERROR_MESSAGE);
                        button1GPUFrame.setVisible(true);
                        throw new RuntimeException();
                    }
                    if(number2 % 5 != 0){
                        JOptionPane.showMessageDialog(button1GPUFrame, "The number of titans should be a multiple of 5!", "error", JOptionPane.ERROR_MESSAGE);
                        button1GPUFrame.setVisible(true);
                        throw new RuntimeException();
                    }
                    if(number2 > 1000){
                        JOptionPane.showMessageDialog(button1GPUFrame, "<html>The number of titans is too large!<br>Maximum 1000 titans/second can be generated!</html>", "error", JOptionPane.ERROR_MESSAGE);
                        button1GPUFrame.setVisible(true);
                        throw new RuntimeException();
                    }
                    if(number3 > 10){
                        JOptionPane.showMessageDialog(button1GPUFrame, "Maximum 10 runs are accepted!", "error", JOptionPane.ERROR_MESSAGE);
                        button1GPUFrame.setVisible(true);
                        throw new RuntimeException();
                    }
                    GPUBenchmarky GPUBenchmarky = new GPUBenchmarky();
                    GPUBenchmarky.setCubesToGenerate(number);
                    GPUBenchmarky.setRobinsToGenerate(number2/5);
                    GPUBenchmarky.setBboysToGenerate(number2/5);
                    GPUBenchmarky.setCyborgsToGenerate(number2/5);
                    GPUBenchmarky.setSarsToGenerate(number2/5);
                    GPUBenchmarky.setRavensToGenerate(number2/5);
                    GPUBenchmarky.SetTotalRuns(number3);
                    BenchmarkInfo data = GPUBenchmarky.runMain();

                    // Check the final score and show the corresponding frame
                    double finalScore = GPUBenchmarky.GetFinalScore();
                    double fps_average= GPUBenchmarky.GetFPSAverage();
                    int total_runs= GPUBenchmarky.GetRunsNumber();
                    int total_gen_enitities=GPUBenchmarky.TotalGeneratedEntities();
                    if (finalScore > 550) {
                        showVictoryFrame(finalScore, fps_average,total_runs, total_gen_enitities);
                    } else if (finalScore > 500) {
                        showMediumFrame(finalScore, fps_average,total_runs, total_gen_enitities);
                    } else {
                        showDefeatFrame(finalScore, fps_average,total_runs, total_gen_enitities);
                    }

                    try {
                        Firebase.writeData(data);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(button1GPUFrame, "All fields must be completed with valid numbers!", "error", JOptionPane.ERROR_MESSAGE);
                    button1GPUFrame.setVisible(true);
                }
            }
        });
        panel.add(specificButton);

        // Adding a button for some functionality specific to this frame
        JButton specificButton2 = createButton("<html>Start Default Benchmark<br>(5 entities/second <br>generated and 5 runs)</html>\"");
        specificButton2.setBounds(50, 410, 350, 90); // Set position and size
        specificButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    button1GPUFrame.setVisible(false);
                    GPUBenchmarky GPUBenchmarky = new GPUBenchmarky();
                    GPUBenchmarky.setCubesToGenerate(5);
                    GPUBenchmarky.setRobinsToGenerate(5);
                    GPUBenchmarky.setBboysToGenerate(5);
                    GPUBenchmarky.setCyborgsToGenerate(5);
                    GPUBenchmarky.setSarsToGenerate(5);
                    GPUBenchmarky.setRavensToGenerate(5);
                    GPUBenchmarky.SetTotalRuns(5);
                    BenchmarkInfo data = GPUBenchmarky.runMain();

                    // Check the final score and show the corresponding frame
                    double finalScore = GPUBenchmarky.GetFinalScore();
                    double fps_average= GPUBenchmarky.GetFPSAverage();
                    int total_gen_entities=GPUBenchmarky.TotalGeneratedEntities();
                    int total_runs=GPUBenchmarky.GetRunsNumber();
                    if (finalScore > 550) {
                        showVictoryFrame(finalScore,fps_average, total_runs, total_gen_entities);
                    } else if (finalScore > 500) {
                        showMediumFrame(finalScore,fps_average,total_runs, total_gen_entities);
                    } else {
                        showDefeatFrame(finalScore,fps_average,total_runs, total_gen_entities);
                    }

                    try {
                        Firebase.writeData(data);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
            }
        });
        panel.add(specificButton2);

        // Add a button to close this frame and show the GPU frame again
        JButton closeButton = createButton("Go to the previous menu");
        closeButton.setBounds(50, 540, 300, 70); // Set position and size
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

    private void showVictoryFrame(double finalScore, double fps_average, int total_runs, int gen_cubes) {
        JFrame victoryFrame = new JFrame("Victory");
        victoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        victoryFrame.setSize(800, 600); // Set size as needed
        victoryFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load and draw the background image
                ImageIcon image = new ImageIcon("resources" + File.separator + "gui" + File.separator + "victoryGPU.jpg");
                g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(null); // Use null layout to set absolute positions for buttons

        JLabel finalScoreLabel = new JLabel(String.format("~ %.2f f.s. ~", finalScore));
        finalScoreLabel.setFont(new Font("Arial", Font.BOLD, 27));
        finalScoreLabel.setForeground(Color.WHITE); // Set text color to red
        finalScoreLabel.setBackground(Color.RED); // Set background color to white
        finalScoreLabel.setOpaque(true); // Make the
        finalScoreLabel.setBounds(500, 200, 260, 50);
        panel.add(finalScoreLabel);

        JLabel finalFPSLabel = new JLabel(String.format("<html>%.0f FPS Average<br>after %d runs</html>", fps_average, total_runs));
        finalFPSLabel.setFont(new Font("Arial", Font.BOLD, 27));
        finalFPSLabel.setForeground(Color.WHITE); // Set text color to red
        finalFPSLabel.setBackground(Color.RED); // Set background color to white
        finalFPSLabel.setOpaque(true); // Make the
        finalFPSLabel.setBounds(500, 260, 280, 80);
        panel.add(finalFPSLabel);

        JLabel finalCubesLabel = new JLabel(String.format("<html>%d<br>entities gen.</html>", gen_cubes));
        finalCubesLabel.setFont(new Font("Arial", Font.BOLD, 27));
        finalCubesLabel.setForeground(Color.WHITE); // Set text color to red
        finalCubesLabel.setBackground(Color.RED); // Set background color to white
        finalCubesLabel.setOpaque(true); // Make the
        finalCubesLabel.setBounds(500, 350, 190, 80);
        panel.add(finalCubesLabel);

        // Add a button to close the frame
        JButton closeButton = createButton("Close results");
        closeButton.setBounds(50, 500, 300, 50); // Set position and size
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                victoryFrame.dispose();
                gpuFrame.setVisible(true);
            }
        });
        panel.add(closeButton);

        victoryFrame.setContentPane(panel); // Set the panel as the content pane
        victoryFrame.setVisible(true);
    }

    private void showMediumFrame(double finalScore, double fps_average, int total_runs, int gen_cubes) {
        JFrame mediumFrame = new JFrame("Draw");
        mediumFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mediumFrame.setSize(800, 600); // Set size as needed
        mediumFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load and draw the background image
                ImageIcon image = new ImageIcon("resources" + File.separator + "gui" + File.separator + "mediumGPU.jpg");
                g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(null); // Use null layout to set absolute positions for buttons

        JLabel finalScoreLabel = new JLabel(String.format("~ %.2f f.s. ~", finalScore));
        finalScoreLabel.setFont(new Font("Arial", Font.BOLD, 27));
        finalScoreLabel.setForeground(Color.WHITE); // Set text color to red
        finalScoreLabel.setBackground(Color.RED); // Set background color to white
        finalScoreLabel.setOpaque(true); // Make the JLabel opaque to display the background color
        finalScoreLabel.setBounds(300, 170, 260, 50);
        panel.add(finalScoreLabel);

        JLabel finalFPSLabel = new JLabel(String.format("<html>%.0f FPS Average<br>after %d runs</html>", fps_average, total_runs));
        finalFPSLabel.setFont(new Font("Arial", Font.BOLD, 27));
        finalFPSLabel.setForeground(Color.WHITE); // Set text color to red
        finalFPSLabel.setBackground(Color.RED); // Set background color to white
        finalFPSLabel.setOpaque(true); // Make the JLabel opaque to display the background color
        finalFPSLabel.setBounds(300, 230, 280, 80);
        panel.add(finalFPSLabel);

        JLabel finalCubesLabel = new JLabel(String.format("<html>%d<br>entities gen.</html>", gen_cubes));
        finalCubesLabel.setFont(new Font("Arial", Font.BOLD, 27));
        finalCubesLabel.setForeground(Color.WHITE); // Set text color to red
        finalCubesLabel.setBackground(Color.RED); // Set background color to white
        finalCubesLabel.setOpaque(true); // Make the
        finalCubesLabel.setBounds(300, 320, 190, 80);
        panel.add(finalCubesLabel);


        // Add a button to close the frame
        JButton closeButton = createButton("Close results");
        closeButton.setBounds(50, 500, 300, 50); // Set position and size
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mediumFrame.dispose();
                gpuFrame.setVisible(true);
            }
        });
        panel.add(closeButton);

        mediumFrame.setContentPane(panel); // Set the panel as the content pane
        mediumFrame.setVisible(true);
    }

    private void showDefeatFrame(double finalScore, double fps_average, int total_runs, int gen_cubes) {
        JFrame defeatFrame = new JFrame("Defeat");
        defeatFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        defeatFrame.setSize(800, 600); // Set size as needed
        defeatFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load and draw the background image
                ImageIcon image = new ImageIcon("resources" + File.separator + "gui" + File.separator + "defeatGPU.jpg");
                g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(null); // Use null layout to set absolute positions for buttons


        JLabel finalScoreLabel = new JLabel(String.format("~ %.2f f.s. ~", finalScore));
        finalScoreLabel.setFont(new Font("Arial", Font.BOLD, 27));
        finalScoreLabel.setForeground(Color.WHITE); // Set text color to red
        finalScoreLabel.setBackground(Color.RED); // Set background color to white
        finalScoreLabel.setOpaque(true); // Make the
        finalScoreLabel.setBounds(450, 260, 260, 50);
        panel.add(finalScoreLabel);

        JLabel finalFPSLabel = new JLabel(String.format("<html>%.0f FPS Average<br>after %d runs</html>", fps_average, total_runs));
        finalFPSLabel.setFont(new Font("Arial", Font.BOLD, 27));
        finalFPSLabel.setForeground(Color.WHITE); // Set text color to red
        finalFPSLabel.setBackground(Color.RED); // Set background color to white
        finalFPSLabel.setOpaque(true); // Make the
        finalFPSLabel.setBounds(450, 320, 280, 80);
        panel.add(finalFPSLabel);

        JLabel finalCubesLabel = new JLabel(String.format("<html>%d<br>entities gen.</html>", gen_cubes));
        finalCubesLabel.setFont(new Font("Arial", Font.BOLD, 27));
        finalCubesLabel.setForeground(Color.WHITE); // Set text color to red
        finalCubesLabel.setBackground(Color.RED); // Set background color to white
        finalCubesLabel.setOpaque(true); // Make the
        finalCubesLabel.setBounds(450, 410, 190, 80);
        panel.add(finalCubesLabel);


        // Add a button to close the frame
        JButton closeButton = createButton("Close results");
        closeButton.setBounds(50, 500, 300, 50); // Set position and size
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                defeatFrame.dispose();
                gpuFrame.setVisible(true);
            }
        });
        panel.add(closeButton);

        defeatFrame.setContentPane(panel); // Set the panel as the content pane
        defeatFrame.setVisible(true);
    }

    private void showVictoryCPUFrame(double finalScore, String path) {
        JFrame victoryFrame = new JFrame("Victory");
        victoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        victoryFrame.setSize(800, 600); // Set size as needed
        victoryFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load and draw the background image
                ImageIcon image = new ImageIcon("resources" + File.separator + "gui" + File.separator + path);
                g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(null); // Use null layout to set absolute positions for buttons

        JLabel finalScoreLabel = new JLabel(String.format("%.2f", finalScore));
        finalScoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        finalScoreLabel.setForeground(Color.WHITE); // Set text color to white
        finalScoreLabel.setBounds(550, 300, 300, 50);
        panel.add(finalScoreLabel);

        // Add a button to close the frame
        JButton closeButton = createButton("Close results");
        closeButton.setBounds(50, 500, 300, 50); // Set position and size
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                victoryFrame.dispose();
                cpuFrame.setVisible(true);
            }
        });
        panel.add(closeButton);

        victoryFrame.setContentPane(panel); // Set the panel as the content pane
        victoryFrame.setVisible(true);
    }

    private void showMediumCPUFrame(double finalScore, String path) {
        JFrame mediumFrame = new JFrame("Medium Score");
        mediumFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mediumFrame.setSize(800, 600); // Set size as needed
        mediumFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load and draw the background image
                ImageIcon image = new ImageIcon("resources" + File.separator + "gui" + File.separator + path);
                g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(null); // Use null layout to set absolute positions for buttons

        JLabel finalScoreLabel = new JLabel(String.format("%.2f", finalScore));
        finalScoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        finalScoreLabel.setForeground(Color.WHITE); // Set text color to white
        finalScoreLabel.setBounds(300, 150, 300, 50);
        panel.add(finalScoreLabel);


        // Add a button to close the frame
        JButton closeButton = createButton("Close results");
        closeButton.setBounds(50, 500, 300, 50); // Set position and size
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mediumFrame.dispose();
                cpuFrame.setVisible(true);
            }
        });
        panel.add(closeButton);

        mediumFrame.setContentPane(panel); // Set the panel as the content pane
        mediumFrame.setVisible(true);
    }

    private void showDefeatCPUFrame(double finalScore, String path) {
        JFrame defeatFrame = new JFrame("Defeat");
        defeatFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        defeatFrame.setSize(800, 600); // Set size as needed
        defeatFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load and draw the background image
                ImageIcon image = new ImageIcon("resources" + File.separator + "gui" + File.separator + path);
                g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(null); // Use null layout to set absolute positions for buttons


        JLabel finalScoreLabel = new JLabel(String.format(" %.2f", finalScore));
        finalScoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        finalScoreLabel.setForeground(Color.WHITE); // Set text color to white
        finalScoreLabel.setBounds(400, 450, 300, 50);
        panel.add(finalScoreLabel);


        // Add a button to close the frame
        JButton closeButton = createButton("Close results");
        closeButton.setBounds(50, 500, 300, 50); // Set position and size
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                defeatFrame.dispose();
                cpuFrame.setVisible(true);
            }
        });
        panel.add(closeButton);

        defeatFrame.setContentPane(panel); // Set the panel as the content pane
        defeatFrame.setVisible(true);
    }

    private void showWaitingFrame() {
        waitingFrame = new JFrame("Running Benchmark");
        waitingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        waitingFrame.setSize(800, 600); // Set size as needed
        waitingFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load and draw the background image
                ImageIcon image = new ImageIcon("resources" + File.separator + "gui" + File.separator + "runningBenchmarkCPU.png");
                g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(null); // Use null layout to set absolute positions for buttons

        waitingFrame.setContentPane(panel); // Set the panel as the content pane
        waitingFrame.setVisible(true);
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



