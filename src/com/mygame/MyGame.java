package com.mygame;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyGame extends JFrame {
	private JLabel heading;
	private JPanel board;
	private JLabel clock;
	private JButton[] block;
	
	MyGame() {
		setTitle("Tic Tac Toe");
		ImageIcon icon = new ImageIcon("bin/Images/Icon.png");
		setIconImage(icon.getImage());
		setSize(700, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		createGUI();
		setVisible(true);
	}
	private void createGUI() {
		this.setLayout(new BorderLayout());
		this.getContentPane().setBackground(Color.decode("#2196f3"));
		
		heading = new JLabel("Tic Tac Toe");
		heading.setHorizontalAlignment(JLabel.CENTER);
		heading.setFont(new Font("", Font.BOLD, 20));
		heading.setForeground(Color.white);
		
		board = new JPanel();
		board.setLayout(new GridLayout(3, 3));
		block = new JButton[9];
		for(int i=0; i<9; i++) {
			JButton button = new JButton();
			button.setIcon(new ImageIcon("bin/Images/Cross.png"));
			button.setBackground(Color.decode("#90caf9"));
			block[i] = button;
			board.add(button);
		}
		
		clock = new JLabel("Clock");
		clock.setFont(new Font("", Font.PLAIN, 15));
		clock.setForeground(Color.white);
		clock.setHorizontalAlignment(JLabel.CENTER);
		Thread timer = new Thread() {
			@Override
			public void run() {
				try {
					while(true) {
						SimpleDateFormat sdf = new SimpleDateFormat("hh : mm : ss a");
						String time = sdf.format(new Date());
						clock.setText(time);
						Thread.sleep(1000);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		timer.start();
		
		this.add(heading, BorderLayout.NORTH);
		this.add(board, BorderLayout.CENTER);
		this.add(clock, BorderLayout.SOUTH);
	}
}
