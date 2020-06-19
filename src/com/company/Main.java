package com.company;

import javax.swing.*;
import java.awt.*;


public class Main {

    public static void main(String[] args) {
	  JFrame snake = new JFrame();
	  Gameplay gameplay = new Gameplay();


	  snake.setBounds(10,10,850,700);
	  snake.setLayout(new BorderLayout());
	  snake.setBackground(Color.DARK_GRAY);
	  snake.setResizable(false);
	  snake.setVisible(true);
	  snake.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  snake.add(gameplay);
    }
}
