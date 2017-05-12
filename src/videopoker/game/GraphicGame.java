package videopoker.game;

import javax.swing.JFrame;
import javax.swing.JLabel;

//import java.awt.event.ActionListener;
//import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;

public class GraphicGame extends JFrame implements Runnable { //implements ActionListener {
	
	private int credit;

	private JLabel testLabel;

	public static final int W_G = 1200;
	public static final int H_G = W_G * 3 / 4;

	public GraphicGame(int credit) {
		this.credit = credit;

		setTitle("Video Poker");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Dimension gameSize = new Dimension(W_G, H_G);
		setPreferredSize(gameSize);
		setMinimumSize(gameSize);
		setLocationRelativeTo(null);
		
		drawBackground();
	}

	private void drawBackground() {

		getContentPane().setBackground(new Color(7, 99, 36));
		getContentPane().setLayout(null);

	}
	
	/*public void actionPerformed(ActionEvent e) {

	}*/

	public void run() {

		setVisible(true);
	}
}