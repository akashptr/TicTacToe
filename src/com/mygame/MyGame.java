package com.mygame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyGame extends JFrame implements ActionListener {
	
	private JLabel heading;
	private JPanel board;
	private JLabel clock;
	
	private String crossImage = "bin/Images/Cross.png";
	private String circleImage = "bin/Images/Circle.png";
	private int[] gameChances = {2, 2, 2, 2, 2, 2, 2, 2, 2};
	private int activePlayer = 0;
	
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
		for(int i=0; i<9; i++) {
			JButton button = new JButton();
			button.setName(String.valueOf(i));
			button.setBackground(Color.decode("#90caf9"));
			button.addActionListener(this);
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
	@Override
	public void actionPerformed(ActionEvent e) {
		var currentButton = (JButton)e.getSource();
		int chanceNumber = Integer.parseInt(currentButton.getName().trim());
		if(gameChances[chanceNumber] == 2) {
			if(activePlayer == 0)
				currentButton.setIcon(new ImageIcon(crossImage));
			else
				currentButton.setIcon(new ImageIcon(circleImage));
			gameChances[chanceNumber] = activePlayer;
			activePlayer = (activePlayer + 1) % 2;
		}else {
			JOptionPane.showMessageDialog(this, "Position already occupied");
		}
	}
}
