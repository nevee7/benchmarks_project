package gui;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

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
    public Gui() {
        InputStream is = getClass().getClassLoader().getResourceAsStream("themeSong.mp3");
        JFrame frame;

        JLabel backgroundLabel = createLabel("resources"+ File.separator + "gui" + File.separator +"background.jpg",885,598);


        int offset = 200;
        int yButtons = 500;
        buttonDimension dimensiuneButon = new buttonDimension(95,30);

        JButton cpuButton = new JButton("CPU");
        cpuButton.setBounds(50, yButtons, dimensiuneButon.getX(), dimensiuneButon.getY());

        JButton resultsButton = new JButton("Results");
        resultsButton.setBounds(50+offset, yButtons, dimensiuneButon.getX(), dimensiuneButon.getY());

        JButton gpuButton = new JButton("GPU");
        gpuButton.setBounds(50+offset*2, yButtons, dimensiuneButon.getX(), dimensiuneButon.getY());

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
    JLabel createLabel(String path,int dimX,int dimY){
        JLabel label;
        ImageIcon image;
        image = new ImageIcon(String.valueOf(new ImageIcon(path)));
        label = new JLabel(image);
        label.setSize(dimX, dimY);

        return label;
    }
    public static void main(String[] args) {
        new Gui();
    }
}