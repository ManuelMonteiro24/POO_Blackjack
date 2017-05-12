package videopoker.gui;

import videopoker.game.GraphicGame;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.EventQueue;

public class VideopokerGui extends JFrame implements ActionListener {
	
	private JLabel titleLabel, creditLabel;
	private JTextField creditText;
	private JButton startButton;

	private int credit;

	public static final int W_M = 550;
	public static final int H_M = W_M / 2;

	public static final int X_TIT = 150;
	public static final int Y_TIT = 25;
	public static final int W_TIT = 281;
	public static final int H_TIT = 75;
	public static final int X_TC = 31;
	public static final int Y_TC = 110;
	public static final int W_TC = 225;
	public static final int H_TC = 50;
	public static final int X_T = 271;
	public static final int Y_T = 111;
	public static final int W_T = 198;
	public static final int H_T = 50;
	public static final int X_B = 205;
	public static final int Y_B = 185;
	public static final int W_B = 117;
	public static final int H_B = 40;

	

	public VideopokerGui() {

		setTitle("Video Poker");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Dimension menuSize = new Dimension(W_M, H_M);
		setPreferredSize(menuSize);
		setMinimumSize(menuSize);
		setLocationRelativeTo(null);

		getContentPane().setLayout(null);

		titleLabel = new JLabel("Welcome to Video Poker");
		titleLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
		titleLabel.setBounds(X_TIT, Y_TIT, W_TIT, H_TIT);
		getContentPane().add(titleLabel);

		creditLabel = new JLabel("Please insert your credit:");
		creditLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
		creditLabel.setBounds(X_TC, Y_TC, W_TC, H_TC);
		getContentPane().add(creditLabel);

		creditText = new JTextField();
		creditText.setBounds(X_T, Y_T, W_T, H_T);
		creditText.setColumns(10);
		getContentPane().add(creditText);

		startButton = new JButton("Start");
		startButton.setBounds(X_B, Y_B, W_B, H_B);
		startButton.setFocusPainted(false);
		getContentPane().add(startButton);
		startButton.addActionListener(this);

	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() instanceof JButton) {

			try {
				credit = Integer.parseInt(creditText.getText());
				
				if (credit > 0) {
					EventQueue.invokeLater(new GraphicGame(credit));
					dispose();
					
				} else {
					JOptionPane.showMessageDialog(null, "Invalid credit");
				}
			} catch (Exception notInt) {
				System.out.println("exception de nao conseguir");
				JOptionPane.showMessageDialog(null, "Invalid credit");
			}
		}
	}

}