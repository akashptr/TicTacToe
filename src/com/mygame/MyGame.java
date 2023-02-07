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
	private int[] gameChances = { 2, 2, 2, 2, 2, 2, 2, 2, 2 };
	private int activePlayer = 0;
	private int chanceLeft = 9;

	private static int[][] winPos = { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 },
			{ 0, 4, 8 }, { 2, 4, 6 }, };

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
		for (int i = 0; i < 9; i++) {
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
					while (true) {
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
		var currentButton = (JButton) e.getSource();
		int chanceNumber = Integer.parseInt(currentButton.getName().trim());
		if (gameChances[chanceNumber] == 2) {
			if (activePlayer == 0)
				currentButton.setIcon(new ImageIcon(crossImage));
			else
				currentButton.setIcon(new ImageIcon(circleImage));
			gameChances[chanceNumber] = activePlayer;

			// Winning logic
			boolean winnerFound = false;
			for (int[] winPat : winPos) {
				if (gameChances[winPat[0]] == gameChances[winPat[1]] && gameChances[winPat[1]] == gameChances[winPat[2]]
						&& gameChances[winPat[2]] != 2) {
					winnerFound = true;
					int choice = JOptionPane.showConfirmDialog(this,
							"Player " + (activePlayer + 1) + " has won\nDo you want to start again?", "Confirm",
							JOptionPane.YES_NO_OPTION);
					if (choice == JOptionPane.YES_OPTION) {
						new MyGame();
						this.dispose();
					} else {
						System.exit(1);
					}
					break;
				}
			}

			activePlayer = (activePlayer + 1) % 2;

			// Draw Logic
			chanceLeft--;
			if (chanceLeft == 0 && winnerFound == false) {
				int choice = JOptionPane.showConfirmDialog(this, "Match Draw\nDo you want to start again?", "Game Over",
						JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					new MyGame();
					this.dispose();
				} else {
					System.exit(1);
				}
			}
		} else {
			JOptionPane.showMessageDialog(this, "Position already occupied");
		}
	}
}
