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

        before_gpu_frames = frame; // Do not delete or comment this; we are using this on GPU to get back to the main menu

        JLabel backgroundLabel = createLabel("resources" + File.separator + "gui" + File.separator + "image3.png", frameWidth, frameHeight);

        int offset = 230;
        int yButtons = 565;
        int xButtons = 65;
        ButtonDimension buttonDimension = new ButtonDimension(110, 50);

        JButton cpuButton = gpu_button("CPU");
        cpuButton.setBounds(xButtons, yButtons, buttonDimension.getX(), buttonDimension.getY());
        cpuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                showCPUFrame(frameWidth, frameHeight); // Pass the frame object as the first argument
            }
        });

        JButton resultsButton = gpu_button("Results");
        resultsButton.setBounds(xButtons + offset, yButtons, buttonDimension.getX(), buttonDimension.getY());
        resultsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                try {
                    showResultFrame(frameWidth, frameHeight); // Pass the frame object as the first argument
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JButton gpuButton = gpu_button("GPU");
        gpuButton.setBounds(xButtons + offset * 2, yButtons, buttonDimension.getX(), buttonDimension.getY());
        gpuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                SelectGPUFrame(frame, frameWidth, frameHeight); // Pass the frame object as the first argument
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

        // Add component listener to handle resizing events
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int newFrameWidth = frame.getWidth();
                int newFrameHeight = frame.getHeight();

                // Adjust button positions based on frame size
                cpuButton.setBounds(newFrameWidth * xButtons / frameWidth, newFrameHeight * yButtons / frameHeight, newFrameWidth * buttonDimension.getX() / frameWidth, newFrameHeight * buttonDimension.getY() / frameHeight);
                resultsButton.setBounds(newFrameWidth * (xButtons + offset) / frameWidth, newFrameHeight * yButtons / frameHeight, newFrameWidth * buttonDimension.getX() / frameWidth, newFrameHeight * buttonDimension.getY() / frameHeight);
                gpuButton.setBounds(newFrameWidth * (xButtons + offset * 2) / frameWidth, newFrameHeight * yButtons / frameHeight, newFrameWidth * buttonDimension.getX() / frameWidth, newFrameHeight * buttonDimension.getY() / frameHeight);
                backgroundLabel.setSize(newFrameWidth, newFrameHeight);
            }
        });
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

    /*
    *
    * GPU PROFESSIONAL TEAM GUI IMPLEMENTATION
    *
    * */

    JFrame before_gpu_frames; //used for returning on the select frame after victory, draw or defeat

    private void SelectGPUFrame(JFrame previousFrame, int width, int height) {
        JFrame selectFrame = new JFrame("GPU Benchmarks");
        selectFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        selectFrame.setSize(width, height);
        selectFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon("resources" + File.separator + "gui" + File.separator + "gpuSelect.jpg");
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(null);

        //generating 3D button
        JButton gen3d_button = gpu_button("<html>GENERATING 3D MODELS<br>AND FPS COUNTING</html>");
        gen3d_button.setBounds(50, 230, 300, 70);
        panel.add(gen3d_button);

        //description for the gpu benchmark and ranking
        JLabel instructionsLabel = new JLabel("<html>Test your GPU and see if you can defeat Trigon in the battle of generating entities<br><br>RANKING:<br>Victory -> Final Score larger than 550<br>Draw -> Final Score between 550 and 500<br>Defeat -> Final Score lower than 500 </html>");
        instructionsLabel.setBounds(55, 330, 500, 160);
        instructionsLabel.setForeground(Color.WHITE);
        instructionsLabel.setFont(new Font("Arial", Font.BOLD, 19));
        panel.add(instructionsLabel);

        //close button
        JButton closeButton = gpu_button("Go to the previous menu");
        closeButton.setBounds(50, 550, 300, 50);
        panel.add(closeButton);

        selectFrame.setContentPane(panel);
        selectFrame.setVisible(true);

        //action listener for the close button
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectFrame.dispose(); //close the current frame
                previousFrame.setVisible(true); //open previous frame "Stressing Titans"
            }
        });

        //action listener for the gen3d button
        gen3d_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectFrame.dispose(); //close the current frame
                StartGPUBenchmarkFrame(selectFrame); //open "You entered Trigon's hell" frame
            }
        });

        //add component listener to handle resizing events
        selectFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                //adjust button positions based on frame size
                int frameWidth = selectFrame.getWidth();
                int frameHeight = selectFrame.getHeight();

                //set the buttons' positions relative to the frame size
                gen3d_button.setBounds(frameWidth * 50 / width, frameHeight * 230 / height, frameWidth * 300 / width, frameHeight * 70 / height);
                instructionsLabel.setBounds(frameWidth * 55 / width, frameHeight * 330 / height, frameWidth * 500 / width, frameHeight * 160 / height);
                closeButton.setBounds(frameWidth * 50 / width, frameHeight * 550 / height, frameWidth * 300 / width, frameHeight * 50 / height);
            }
        });
    }


    private void StartGPUBenchmarkFrame(JFrame previousFrame) {
        JFrame startFrame = new JFrame("You entered Trigon's hell");
        startFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        startFrame.setSize(1041, 704);
        startFrame.setLocationRelativeTo(previousFrame);

        //panel for frame elements
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon("resources" + File.separator + "gui" + File.separator + "gpustart1.jpg");
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(null);

        //description for the cubes text field
        JLabel cubes_description = new JLabel("<html>Enter the number<br>of cubes/second<br>to generate:</html>");
        cubes_description.setBounds(605, 200, 200, 70);
        cubes_description.setForeground(Color.WHITE);
        cubes_description.setBackground(Color.RED);
        cubes_description.setOpaque(true);
        panel.add(cubes_description);

        //font used in the frame
        Font font = new Font("Arial", Font.BOLD, 18);
        cubes_description.setFont(font);

        //text_field for the cubes input number
        JTextField cubes_text_field = new JTextField();
        cubes_text_field.setBounds(850, 215, 100, 40);
        panel.add(cubes_text_field);

        //description for the titans text field
        JLabel titans_description = new JLabel("<html>Enter the number<br>of titans/second<br>to generate:</html>");
        titans_description.setBounds(605, 300, 200, 70);
        titans_description.setForeground(Color.WHITE);
        titans_description.setBackground(Color.RED);
        titans_description.setOpaque(true);
        panel.add(titans_description);

        //setting the font
        titans_description.setFont(font);

        //text field for the titans input number
        JTextField titans_text_field = new JTextField();
        titans_text_field.setBounds(850, 315, 100, 40);
        panel.add(titans_text_field);

        //description for the runs text field
        JLabel runs_description = new JLabel("<html>Enter the number<br>of runs for<br>the benchmark:</html>");
        runs_description.setBounds(605, 400, 200, 70);
        runs_description.setForeground(Color.WHITE); // Set text color to white
        runs_description.setBackground(Color.RED); // Set background color to red
        runs_description.setOpaque(true); // Make the background color visible
        panel.add(runs_description);

        //setting the font
        runs_description.setFont(font); // Set the font of the JLabel

        JTextField runs_text_field = new JTextField();
        runs_text_field.setBounds(850, 415, 100, 40);
        panel.add(runs_text_field);

        JButton start_input = gpu_button("<html>Start Benchmark<br>(with input numbers)</html>");
        start_input.setBounds(605, 540, 350, 70); // Set position and size
        start_input.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cubes_input = cubes_text_field.getText();
                String titans_input = titans_text_field.getText();
                String runs_input = runs_text_field.getText();
                try {
                    startFrame.setVisible(false);
                    int cubes_number = Integer.parseInt(cubes_input);
                    int titans_number = Integer.parseInt(titans_input);
                    int runs_number = Integer.parseInt(runs_input);
                    if (cubes_number > 1000000) {
                        JOptionPane.showMessageDialog(startFrame, "<html>The provided number is too large!<br>Maximum 1.000.000 cubes/second can be generated!</html>", "error", JOptionPane.ERROR_MESSAGE);
                        startFrame.setVisible(true);
                        throw new RuntimeException();
                    }
                    if (cubes_number < 0) {
                        JOptionPane.showMessageDialog(startFrame, "<html>The provided number is too small!<br>Numbers smaller than 0 are not accepted!</html>", "error", JOptionPane.ERROR_MESSAGE);
                        startFrame.setVisible(true);
                        throw new RuntimeException();
                    }
                    if (titans_number > 100000) {
                        JOptionPane.showMessageDialog(startFrame, "<html>The number of titans is too large!<br>Maximum 100.000 titans/second can be generated!</html>", "error", JOptionPane.ERROR_MESSAGE);
                        startFrame.setVisible(true);
                        throw new RuntimeException();
                    }
                    if (titans_number < 0) {
                        JOptionPane.showMessageDialog(startFrame, "<html>The number of titans is too small!<br>Numbers smaller than 0 are not accepted!</html>", "error", JOptionPane.ERROR_MESSAGE);
                        startFrame.setVisible(true);
                        throw new RuntimeException();
                    }
                    if (runs_number > 10) {
                        JOptionPane.showMessageDialog(startFrame, "Maximum 10 runs are accepted!", "error", JOptionPane.ERROR_MESSAGE);
                        startFrame.setVisible(true);
                        throw new RuntimeException();
                    }
                    GPUBenchmarky GPUBenchmarky = new GPUBenchmarky();
                    GPUBenchmarky.setCubesToGenerate(cubes_number);
                    GPUBenchmarky.setTitansToGenerate(titans_number);
                    GPUBenchmarky.SetTotalRuns(runs_number);
                    BenchmarkInfo data = GPUBenchmarky.runMain();

                    //check the final score and show frames based on that
                    double finalScore = GPUBenchmarky.GetFinalScore();
                    double fps_average = GPUBenchmarky.GetFPSAverage();
                    int total_runs = GPUBenchmarky.GetRunsNumber();
                    int total_gen_enitities = GPUBenchmarky.TotalGeneratedEntities();
                    if (finalScore > 550) {
                        gpuVictoryFrame(finalScore, fps_average, total_runs, total_gen_enitities, previousFrame);
                    } else if (finalScore > 500) {
                        gpuDrawFrame(finalScore, fps_average, total_runs, total_gen_enitities, previousFrame);
                    } else {
                        gpuDefeatFrame(finalScore, fps_average, total_runs, total_gen_enitities, previousFrame);
                    }

                    try {
                        Firebase.writeData(data);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(startFrame, "All fields must be completed with valid numbers!", "error", JOptionPane.ERROR_MESSAGE);
                    startFrame.setVisible(true);
                }
            }
        });
        panel.add(start_input);

        JButton start_default = gpu_button("<html>Start Default Benchmark<br>(5 entities/second <br>generated and 5 runs)</html>");
        start_default.setBounds(50, 410, 350, 90); // Set position and size
        start_default.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startFrame.setVisible(false);
                GPUBenchmarky GPUBenchmarky = new GPUBenchmarky();
                GPUBenchmarky.setCubesToGenerate(5);
                GPUBenchmarky.setTitansToGenerate(5);
                GPUBenchmarky.SetTotalRuns(5);
                BenchmarkInfo data = GPUBenchmarky.runMain();

                //check the final score and show frames based on that
                double finalScore = GPUBenchmarky.GetFinalScore();
                double fps_average = GPUBenchmarky.GetFPSAverage();
                int total_gen_entities = GPUBenchmarky.TotalGeneratedEntities();
                int total_runs = GPUBenchmarky.GetRunsNumber();
                if (finalScore > 550) {
                    gpuVictoryFrame(finalScore, fps_average, total_runs, total_gen_entities, previousFrame);
                } else if (finalScore > 500) {
                    gpuDrawFrame(finalScore, fps_average, total_runs, total_gen_entities, previousFrame);
                } else {
                    gpuDefeatFrame(finalScore, fps_average, total_runs, total_gen_entities, previousFrame);
                }

                try {
                    Firebase.writeData(data);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        panel.add(start_default);

        //close button
        JButton closeButton = gpu_button("Go to the previous menu");
        closeButton.setBounds(50, 540, 300, 70);
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startFrame.dispose(); //close the current frame
                previousFrame.setVisible(true); //opens the previous frame "GPU Benchmarks"
            }
        });
        panel.add(closeButton);

        startFrame.setContentPane(panel);
        startFrame.setVisible(true);

        //add a component listener to handle resizing events
        startFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                //reposition components based on new size
                int frameWidth = startFrame.getWidth();
                int frameHeight = startFrame.getHeight();

                //update component positions and sizes based on frame size
                cubes_description.setBounds(frameWidth * 605 / 1041, frameHeight * 200 / 704, frameWidth * 200 / 1041, frameHeight * 70 / 704);
                cubes_text_field.setBounds(frameWidth * 850 / 1041, frameHeight * 215 / 704, frameWidth * 100 / 1041, frameHeight * 40 / 704);

                titans_description.setBounds(frameWidth * 605 / 1041, frameHeight * 300 / 704, frameWidth * 200 / 1041, frameHeight * 70 / 704);
                titans_text_field.setBounds(frameWidth * 850 / 1041, frameHeight * 315 / 704, frameWidth * 100 / 1041, frameHeight * 40 / 704);

                runs_description.setBounds(frameWidth * 605 / 1041, frameHeight * 400 / 704, frameWidth * 200 / 1041, frameHeight * 70 / 704);
                runs_text_field.setBounds(frameWidth * 850 / 1041, frameHeight * 415 / 704, frameWidth * 100 / 1041, frameHeight * 40 / 704);

                start_input.setBounds(frameWidth * 605 / 1041, frameHeight * 540 / 704, frameWidth * 350 / 1041, frameHeight * 70 / 704);
                start_default.setBounds(frameWidth * 50 / 1041, frameHeight * 410 / 704, frameWidth * 350 / 1041, frameHeight * 90 / 704);
                closeButton.setBounds(frameWidth * 50 / 1041, frameHeight * 540 / 704, frameWidth * 300 / 1041, frameHeight * 70 / 704);

                panel.repaint();
            }
        });
    }

    //helper method for creating buttons used on the gpu gui
    private JButton gpu_button(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        return button;
    }


    private void gpuVictoryFrame(double finalScore, double fps_average, int total_runs, int gen_cubes, JFrame previousFrame) {
        JFrame victoryFrame = new JFrame("Victory");
        victoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        victoryFrame.setSize(1041, 704);
        victoryFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon("resources" + File.separator + "gui" + File.separator + "victoryGPU.jpg");
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(null);

        JLabel finalScoreLabel = new JLabel(String.format("~ %.2f f.s. ~", finalScore));
        finalScoreLabel.setFont(new Font("Arial", Font.BOLD, 27));
        finalScoreLabel.setForeground(Color.WHITE);
        finalScoreLabel.setBackground(Color.RED);
        finalScoreLabel.setOpaque(true);
        finalScoreLabel.setBounds(700, 250, 260, 50);
        panel.add(finalScoreLabel);

        JLabel finalFPSLabel = new JLabel(String.format("<html>%.0f FPS Average<br>after %d runs</html>", fps_average, total_runs));
        finalFPSLabel.setFont(new Font("Arial", Font.BOLD, 27));
        finalFPSLabel.setForeground(Color.WHITE);
        finalFPSLabel.setBackground(Color.RED);
        finalFPSLabel.setOpaque(true);
        finalFPSLabel.setBounds(700, 320, 280, 80);
        panel.add(finalFPSLabel);

        JLabel finalCubesLabel = new JLabel(String.format("<html>%d<br>entities gen.</html>", gen_cubes));
        finalCubesLabel.setFont(new Font("Arial", Font.BOLD, 27));
        finalCubesLabel.setForeground(Color.WHITE);
        finalCubesLabel.setBackground(Color.RED);
        finalCubesLabel.setOpaque(true);
        finalCubesLabel.setBounds(700, 405, 190, 80);
        panel.add(finalCubesLabel);

        //close button
        JButton closeButton = gpu_button("Close results");
        closeButton.setBounds(50, 500, 200, 50);
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                victoryFrame.dispose(); //close the current frame
                SelectGPUFrame(before_gpu_frames, 1041, 704); //opens the frame "GPU Benchmarks"
            }
        });
        panel.add(closeButton);

        victoryFrame.setContentPane(panel);
        victoryFrame.setVisible(true);

        //store initial positions and sizes
        Rectangle initialBoundsFinalScoreLabel = finalScoreLabel.getBounds();
        Rectangle initialBoundsFinalFPSLabel = finalFPSLabel.getBounds();
        Rectangle initialBoundsFinalCubesLabel = finalCubesLabel.getBounds();
        Rectangle initialBoundsCloseButton = closeButton.getBounds();

        //add component listener to handle resizing events
        victoryFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int frameWidth = victoryFrame.getWidth();
                int frameHeight = victoryFrame.getHeight();

                //update component positions and sizes based on frame size
                finalScoreLabel.setBounds(
                        frameWidth * initialBoundsFinalScoreLabel.x / 1041,
                        frameHeight * initialBoundsFinalScoreLabel.y / 704,
                        frameWidth * initialBoundsFinalScoreLabel.width / 1041,
                        frameHeight * initialBoundsFinalScoreLabel.height / 704
                );
                finalFPSLabel.setBounds(
                        frameWidth * initialBoundsFinalFPSLabel.x / 1041,
                        frameHeight * initialBoundsFinalFPSLabel.y / 704,
                        frameWidth * initialBoundsFinalFPSLabel.width / 1041,
                        frameHeight * initialBoundsFinalFPSLabel.height / 704
                );
                finalCubesLabel.setBounds(
                        frameWidth * initialBoundsFinalCubesLabel.x / 1041,
                        frameHeight * initialBoundsFinalCubesLabel.y / 704,
                        frameWidth * initialBoundsFinalCubesLabel.width / 1041,
                        frameHeight * initialBoundsFinalCubesLabel.height / 704
                );
                closeButton.setBounds(
                        frameWidth * initialBoundsCloseButton.x / 1041,
                        frameHeight * initialBoundsCloseButton.y / 704,
                        frameWidth * initialBoundsCloseButton.width / 1041,
                        frameHeight * initialBoundsCloseButton.height / 704
                );
            }
        });
    }


    private void gpuDrawFrame(double finalScore, double fps_average, int total_runs, int gen_cubes, JFrame previousFrame) {
        JFrame mediumFrame = new JFrame("Draw");
        mediumFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mediumFrame.setSize(1041, 704); // Set size as needed
        mediumFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon("resources" + File.separator + "gui" + File.separator + "mediumGPU.jpg");
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(null);

        JLabel finalScoreLabel = new JLabel(String.format("~ %.2f f.s. ~", finalScore));
        finalScoreLabel.setFont(new Font("Arial", Font.BOLD, 27));
        finalScoreLabel.setForeground(Color.WHITE); // Set text color to white
        finalScoreLabel.setBackground(Color.RED); // Set background color to red
        finalScoreLabel.setOpaque(true);
        finalScoreLabel.setBounds(450, 210, 260, 50);
        panel.add(finalScoreLabel);

        JLabel finalFPSLabel = new JLabel(String.format("<html>%.0f FPS Average<br>after %d runs</html>", fps_average, total_runs));
        finalFPSLabel.setFont(new Font("Arial", Font.BOLD, 27));
        finalFPSLabel.setForeground(Color.WHITE); // Set text color to white
        finalFPSLabel.setBackground(Color.RED); // Set background color to red
        finalFPSLabel.setOpaque(true);
        finalFPSLabel.setBounds(450, 270, 280, 80);
        panel.add(finalFPSLabel);

        JLabel finalCubesLabel = new JLabel(String.format("<html>%d<br>entities gen.</html>", gen_cubes));
        finalCubesLabel.setFont(new Font("Arial", Font.BOLD, 27));
        finalCubesLabel.setForeground(Color.WHITE);
        finalCubesLabel.setBackground(Color.RED);
        finalCubesLabel.setOpaque(true);
        finalCubesLabel.setBounds(450, 360, 190, 80);
        panel.add(finalCubesLabel);

        //close button
        JButton closeButton = gpu_button("Close results");
        closeButton.setBounds(50, 500, 200, 50);
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mediumFrame.dispose(); //close the current frame
                SelectGPUFrame(before_gpu_frames, 1041, 704); //opens the frame "GPU Benchmarks"
            }
        });
        panel.add(closeButton);

        mediumFrame.setContentPane(panel);
        mediumFrame.setVisible(true);

        //add component listener to handle resizing events
        mediumFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                //adjust component positions based on frame size
                int frameWidth = mediumFrame.getWidth();
                int frameHeight = mediumFrame.getHeight();

                finalScoreLabel.setBounds(frameWidth * 450 / 1041, frameHeight * 210 / 704, frameWidth * 260 / 1041, frameHeight * 50 / 704);
                finalFPSLabel.setBounds(frameWidth * 450 / 1041, frameHeight * 270 / 704, frameWidth * 280 / 1041, frameHeight * 80 / 704);
                finalCubesLabel.setBounds(frameWidth * 450 / 1041, frameHeight * 360 / 704, frameWidth * 190 / 1041, frameHeight * 80 / 704);
                closeButton.setBounds(frameWidth * 50 / 1041, frameHeight * 500 / 704, frameWidth * 200 / 1041, frameHeight * 50 / 704);
            }
        });
    }

    private void gpuDefeatFrame(double finalScore, double fps_average, int total_runs, int gen_cubes, JFrame previousFrame) {
        JFrame defeatFrame = new JFrame("Defeat");
        defeatFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        defeatFrame.setSize(1041, 704);
        defeatFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon("resources" + File.separator + "gui" + File.separator + "defeatGPU.jpg");
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(null);

        JLabel finalScoreLabel = new JLabel(String.format("~ %.2f f.s. ~", finalScore));
        finalScoreLabel.setFont(new Font("Arial", Font.BOLD, 27));
        finalScoreLabel.setForeground(Color.WHITE);
        finalScoreLabel.setBackground(Color.RED);
        finalScoreLabel.setOpaque(true);
        finalScoreLabel.setBounds(560, 360, 260, 50);
        panel.add(finalScoreLabel);

        JLabel finalFPSLabel = new JLabel(String.format("<html>%.0f FPS Average<br>after %d runs</html>", fps_average, total_runs));
        finalFPSLabel.setFont(new Font("Arial", Font.BOLD, 27));
        finalFPSLabel.setForeground(Color.WHITE);
        finalFPSLabel.setBackground(Color.RED);
        finalFPSLabel.setOpaque(true);
        finalFPSLabel.setBounds(560, 420, 280, 80);
        panel.add(finalFPSLabel);

        JLabel finalCubesLabel = new JLabel(String.format("<html>%d<br>entities gen.</html>", gen_cubes));
        finalCubesLabel.setFont(new Font("Arial", Font.BOLD, 27));
        finalCubesLabel.setForeground(Color.WHITE);
        finalCubesLabel.setBackground(Color.RED);
        finalCubesLabel.setOpaque(true);
        finalCubesLabel.setBounds(560, 510, 190, 80);
        panel.add(finalCubesLabel);

        //close button
        JButton closeButton = gpu_button("Close results");
        closeButton.setBounds(50, 555, 200, 50);
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                defeatFrame.dispose(); //close the current frame
                SelectGPUFrame(before_gpu_frames, 1041, 704); //opens the frame "GPU Benchmarks"
            }
        });
        panel.add(closeButton);

        defeatFrame.setContentPane(panel);
        defeatFrame.setVisible(true);

        //add component listener to handle resizing events
        defeatFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int frameWidth = defeatFrame.getWidth();
                int frameHeight = defeatFrame.getHeight();

                //adjust component positions and sizes based on frame size
                finalScoreLabel.setBounds(frameWidth * 560 / 1041, frameHeight * 360 / 704, frameWidth * 260 / 1041, frameHeight * 50 / 704);
                finalFPSLabel.setBounds(frameWidth * 560 / 1041, frameHeight * 420 / 704, frameWidth * 280 / 1041, frameHeight * 80 / 704);
                finalCubesLabel.setBounds(frameWidth * 560 / 1041, frameHeight * 510 / 704, frameWidth * 190 / 1041, frameHeight * 80 / 704);
                closeButton.setBounds(frameWidth * 50 / 1041, frameHeight * 555 / 704, frameWidth * 200 / 1041, frameHeight * 50 / 704);
            }
        });
    }

    /*
    *
    * END OF GPU PROFESSIONAL TEAM GUI IMPLEMENTATION
    *
    * */

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



