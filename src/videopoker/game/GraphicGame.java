package videopoker.game;

import java.util.Hashtable;

import videopoker.Player;
import videopoker.Dealer;
import videopoker.cards.*;
import videopoker.utils.HandRank;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class GraphicGame extends JFrame implements Runnable, ActionListener {
	
	private JLabel creditLabel, betLabel;
	private JLabel[] cards = new JLabel[5];//firstCard, secondCard, thirdCard, fourthCard, fifthCard;
	private JPanel topPanel, centerPanel, rightPanel, downPanel;
	private JPanel[] cardsPanel = new JPanel[5];//firstCardPanel, secondCardPanel, thirdCardPanel, fourthCardPanel, fifthCardPanel;
	private JTextArea textArea;
	private JCheckBox[] cardsCB = new JCheckBox[5];//firstCB, secondCB, thirdCB, fourthCB, fifthCB;
	private JButton[] buttons = new JButton[6];//quitBtn, statsBtn, adviceBtn, holdBtn, dealBtn, betBtn;
	private JSlider betSlider;

	public static final int W_G = 1200;
	public static final int H_G = W_G * 3 / 4;
	public static final int QUITBTN = 0;
	public static final int STATSBTN = 1;
	public static final int ADVICEBTN = 2;
	public static final int HOLDBTN = 3;
	public static final int DEALBTN = 4;
	public static final int BETBTN = 5;

	protected Color backgroundColor = new Color(7, 99, 36);
	protected Color textColor = Color.WHITE;

	protected String imagesPath = "videopoker/icons/";
	protected String imagesExtension = ".png";
	protected String[] buttonsText = {"Quit", "Statistics", "Advice", "Hold", "Deal", "Bet"};
	protected int betOnTheTable = 0;
	protected Player player;
	protected Dealer dealer;


	public GraphicGame(int iniBalance) {
		
		player = new Player(iniBalance);
		dealer = new Dealer();

		setTitle("Video Poker");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Dimension gameSize = new Dimension(W_G, H_G);
		setPreferredSize(gameSize);
		setMinimumSize(gameSize);
		setLocationRelativeTo(null);

		drawBackground();
	}

	private void drawBackground() {
		Container c = getContentPane();

		topPanel = new JPanel();
		textArea = new JTextArea(5, 20);
		textArea.setEditable(false);
		textArea.setBackground(backgroundColor);
		textArea.setForeground(textColor);
		topPanel.add(textArea);
		topPanel.setOpaque(false);

		rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(2, 1));
		creditLabel = new JLabel("Credit: " + this.player.getBalance());
		creditLabel.setForeground(textColor);
		betLabel = new JLabel("Bet: ");
		betLabel.setForeground(textColor);
		rightPanel.add(creditLabel);
		rightPanel.add(betLabel);
		rightPanel.setOpaque(false);
		

		centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(1, 5));
		for (int i = 0; i < 5; i++) {
			cardsPanel[i] = new JPanel();
			cardsPanel[i].setLayout(new GridLayout(2, 1));
			cards[i] = new JLabel();
			cards[i].setVisible(false);
			cardsCB[i] = new JCheckBox();
			cardsCB[i].setBackground(backgroundColor);
			cardsCB[i].setEnabled(false);
			cardsCB[i].setVisible(false);
			cardsCB[i].setSelected(false);
			cardsPanel[i].add(cards[i]);
			cardsPanel[i].add(cardsCB[i]);
			cardsPanel[i].setOpaque(false);
			centerPanel.add(cardsPanel[i]);
		}
		centerPanel.setOpaque(false);


		downPanel = new JPanel();
		downPanel.setLayout(new GridLayout(1, 7));
		for (int i = 0; i < 6; i++) {
			buttons[i] = new JButton(buttonsText[i]);
			buttons[i].setFocusPainted(false);
			downPanel.add(buttons[i]);
		}
		betSlider = new JSlider(JSlider.VERTICAL, 0, 5, 5);
		betSlider.setMajorTickSpacing(1);
		betSlider.setMinorTickSpacing(1);
		betSlider.setPaintTicks(true);
		JLabel lbl0 = new JLabel("Use Previous Bet");
		lbl0.setForeground(textColor);
		JLabel lbl1 = new JLabel("1");
		lbl1.setForeground(textColor);
		JLabel lbl2 = new JLabel("2");
		lbl2.setForeground(textColor);
		JLabel lbl3 = new JLabel("3");
		lbl3.setForeground(textColor);
		JLabel lbl4 = new JLabel("4");
		lbl4.setForeground(textColor);
		JLabel lbl5 = new JLabel("5");
		lbl5.setForeground(textColor);
		Hashtable<Integer, JLabel> betSliderLabels = new Hashtable<Integer, JLabel>();
		betSliderLabels.put(new Integer(0), lbl0);
		betSliderLabels.put(new Integer(1), lbl1);
		betSliderLabels.put(new Integer(2), lbl2);
		betSliderLabels.put(new Integer(3), lbl3);
		betSliderLabels.put(new Integer(4), lbl4);
		betSliderLabels.put(new Integer(5), lbl5);
		betSlider.setLabelTable(betSliderLabels);
		betSlider.setPaintLabels(true);
		betSlider.setBackground(backgroundColor);
		betSlider.setEnabled(false);
		downPanel.add(betSlider);
		downPanel.setOpaque(false);
		


		c.setLayout(new BorderLayout());
		c.add(topPanel, BorderLayout.NORTH);
		c.add(rightPanel, BorderLayout.EAST);
		c.add(centerPanel, BorderLayout.CENTER);
		c.add(downPanel, BorderLayout.SOUTH);

		c.setBackground(backgroundColor);

		//validate();
		//pack(); //NAO FAZ NADA??



	}

	public void run() {

		setVisible(true);
		betStage();
	}

	private void betStage() {
		textArea.setText("Make a bet to start the round");

		betSlider.setEnabled(true);

		buttons[BETBTN].addActionListener(this);
		buttons[QUITBTN].addActionListener(this);

	}

	private void dealStage() {
		textArea.setText("Press deal to receive hand");

		buttons[DEALBTN].addActionListener(this);
	}

	private void holdStage() {
		textArea.setText("Choose cards to hold and press Hold button (Use check boxes)");

		for (int i = 0; i < 5; i++)
			cardsCB[i].setEnabled(true);

		buttons[HOLDBTN].addActionListener(this);
			
	}

	private void evaluationStage() {
		
		this.dealer.updateEvaluator(this.player.getHand());
        HandRank playerRank = this.dealer.evaluate();
        this.player.updateBalance(this.dealer.payout(this.betOnTheTable));
        this.player.updateScoreboard(playerRank);
        this.dealer.receiveCards(this.player.releaseHand()); //return players cards to deck

        betLabel.setText("Bet: ");
		creditLabel.setText("Credit: " + this.player.getBalance());

        if(playerRank != HandRank.NON && playerRank != HandRank.PAIR) {
            textArea.setText("WON");
            JOptionPane.showMessageDialog(null, "Player wins with a " + playerRank + "\nHis credit is " + this.player.getBalance());
        } else {
        	textArea.setText("LOST");
        	JOptionPane.showMessageDialog(null, "Player loses\nHis credit is " + this.player.getBalance());
        }

		for (int i = 0; i < 5; i++) {
			cards[i].setVisible(false);
			cardsCB[i].setSelected(false);
			cardsCB[i].setEnabled(false);
			cardsCB[i].setVisible(false);
		}

		betStage();

    }


	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == buttons[QUITBTN]) {
			JOptionPane.showMessageDialog(null, "Player will leave the game with credit: " + this.player.getBalance());
			dispose();
			System.exit(0);

		} else if (e.getSource() == buttons[BETBTN]) {

			buttons[QUITBTN].removeActionListener(this);

			betSlider.setEnabled(false);

			int betChose = betSlider.getValue();
			if (betChose == 0) {
				if (this.betOnTheTable == 0) {
					JOptionPane.showMessageDialog(null, "No previous bet\nUser will bet 5 credits");
					betChose = 5;
				}
				else
					betChose = this.betOnTheTable;
			}

			this.betOnTheTable = this.player.bet(betChose);
			if (this.betOnTheTable == 0) {
				JOptionPane.showMessageDialog(null, "Player has no more funds.\nGame will terminate");
				dispose();
				System.exit(0);
			}
			if (betChose > this.betOnTheTable)
				JOptionPane.showMessageDialog(null, "Player only has " + this.betOnTheTable + " credits\nBetted all!");

			betLabel.setText("Bet: " + this.betOnTheTable);
			creditLabel.setText("Credit: " + this.player.getBalance());

			buttons[BETBTN].removeActionListener(this);

			dealStage();
		
		} else if (e.getSource() == buttons[DEALBTN]) {

			this.dealer.shuffleDeck();
			this.player.setHand(this.dealer.dealFullHand());

			Card[] hand = this.player.getHand().toCardArray();

			for (int i = 0; i < 5; i++) {
				cards[i].setIcon(new ImageIcon(imagesPath + hand[i].toString() + imagesExtension));
				cards[i].setVisible(true);
				cardsCB[i].setVisible(true);
			}

			buttons[DEALBTN].removeActionListener(this);


			holdStage();

		} else if (e.getSource() == buttons[HOLDBTN]) {

			boolean[] indexes = new boolean[5];
			int nToHold = 0;

			for (int i = 0; i < 5; i++) {
				cardsCB[i].setEnabled(false);
				if (cardsCB[i].isSelected()) {
					nToHold++;
					indexes[i] = true;
				} else
					indexes[i] = false;
			}

			int[] holdIndexes = new int[nToHold];

			int k = 0;
			for (int i = 0; i < 5; i++) {
				if (indexes[i]) {
					holdIndexes[k] = (i + 1);
					k++;
				}
			}

			Card[] ret = this.player.hold(holdIndexes, this.dealer.dealSecondCards(5 - holdIndexes.length));
			dealer.receiveCards(ret);

			Card[] hand = this.player.getHand().toCardArray();

			for (int i = 0; i < 5; i++)
				cards[i].setIcon(new ImageIcon(imagesPath + hand[i].toString() + imagesExtension));

			buttons[HOLDBTN].removeActionListener(this);

			evaluationStage();
		}
	}
}