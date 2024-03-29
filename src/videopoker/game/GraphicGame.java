package videopoker.game;

import java.util.Hashtable;
import java.util.Arrays;

import videopoker.Player;
import videopoker.Dealer;
import videopoker.cards.*;
import videopoker.utils.HandRank;
import videopoker.evaluators.Evaluator;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JOptionPane;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dialog.ModalityType;
//import java.awt.Point;

public class GraphicGame extends JFrame implements Runnable, ActionListener {

	public static final int W_G = 1200;
	public static final int H_G = W_G * 3 / 4;
	public static final int W_STATS = W_G / 2;
	public static final int H_STATS = W_STATS * 3 / 4;

	public static final int QUITBTN = 0;
	public static final int STATSBTN = 1;
	public static final int ADVICEBTN = 2;
	public static final int HOLDBTN = 3;
	public static final int DEALBTN = 4;
	public static final int BETBTN = 5;
	protected boolean quitBtnFlag, adviceBtnFlag, holdBtnFlag, dealBtnFlag, betBtnFlag;

	protected Color backgroundColor = new Color(7, 99, 36);
	protected Color textColor = Color.WHITE;

	protected String imagesPath = "videopoker/icons/";
	protected String imagesExtension = ".png";
	protected String[] buttonsText = {"Quit", "Statistics", "Advice", "Hold", "Deal", "Bet"};
	protected String[] statsTitlesText = {"Hand", "Jacks or Better", "Two Pair", "Three of a Kind", "Straight", "Flush", "Full House", "Four of a Kind", "Straight Flush", "Royal Flush", "Others", "Total", "Credit", "Number of Bets"};
	protected int betOnTheTable = 0;
	protected Player player;
	protected Dealer dealer;

	private JLabel creditLabel, betLabel;
	private JLabel[] cards = new JLabel[5];
	private JPanel topPanel, centerPanel, rightPanel, downPanel;
	private JPanel[] cardsPanel = new JPanel[5];
	private JTextArea textArea;
	private JCheckBox[] cardsCB = new JCheckBox[5];
	private JButton[] buttons = new JButton[buttonsText.length];
	private JSlider betSlider;
	private JDialog statsDialog;
	protected JPanel statsTitlesPanel, statsNumPanel;
	protected JLabel[] statsTitlesLabels = new JLabel[statsTitlesText.length - 1];
	protected JLabel[] statsNumLabels = new JLabel[statsTitlesText.length - 1];


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

		buttons[STATSBTN].addActionListener(this);
		buttons[BETBTN].addActionListener(this);
		buttons[QUITBTN].addActionListener(this);
		buttons[DEALBTN].addActionListener(this);
		buttons[HOLDBTN].addActionListener(this);
		buttons[ADVICEBTN].addActionListener(this);

		quitBtnFlag = false;
		adviceBtnFlag = false;
		holdBtnFlag = false;
		dealBtnFlag = false;
		betBtnFlag = false;
	}

	public void run() {

		setVisible(true);
		betStage();
	}

	private void betStage() {
		textArea.setText("Make a bet to start the round or deal if bet is setted");

		betSlider.setEnabled(true);

		betBtnFlag = true;
		quitBtnFlag = true;

	}

	private void dealStage() {
		textArea.setText("Press deal to receive hand");

		dealBtnFlag = true;
	}

	private void holdStage() {
		textArea.setText("Choose cards to hold and press Hold button (Use check boxes)");

		for (int i = 0; i < 5; i++)
		cardsCB[i].setEnabled(true);

		holdBtnFlag = true;
		adviceBtnFlag = true;

	}

	private void evaluationStage() {

		this.dealer.updateEvaluator(this.player.getHand());
		HandRank playerRank = this.dealer.getHandRank();
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

			if (quitBtnFlag) {
				JOptionPane.showMessageDialog(null, "Player will leave the game with credit: " + this.player.getBalance());
				dispose();
				System.exit(0);
			} else
			JOptionPane.showMessageDialog(null, "Invalid! Player can only quit in the beggining of each round!");

		} else if (e.getSource() == buttons[BETBTN]) {

			if (betBtnFlag) {

				quitBtnFlag = false;
				betBtnFlag = false;

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

				dealStage();

			} else
			JOptionPane.showMessageDialog(null, "Invalid! Player can only bet in the beggining of a round!");

		} else if (e.getSource() == buttons[DEALBTN]) {

			if (dealBtnFlag) {

				dealBtnFlag = false;

				this.dealer.shuffleDeck();
				this.player.setHand(this.dealer.dealFullHand());

				Card[] hand = this.player.getHand().toCardArray();

				for (int i = 0; i < 5; i++) {
					cards[i].setIcon(new ImageIcon(imagesPath + hand[i].toString() + imagesExtension));
					cards[i].setVisible(true);
					cardsCB[i].setVisible(true);
				}

				holdStage();

			} else
			JOptionPane.showMessageDialog(null, "Invalid! Player can only deal after a bet");


		} else if (e.getSource() == buttons[HOLDBTN]) {

			if (holdBtnFlag) {

				adviceBtnFlag = false;
				holdBtnFlag = false;

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



				evaluationStage();

			} else
			JOptionPane.showMessageDialog(null, "Invalid! Player can only hold after a deal");

		} else if (e.getSource() == buttons[STATSBTN]) {

			statsDialog = new JDialog(this, "Statistics", ModalityType.DOCUMENT_MODAL);
			statsDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			Dimension statsSize = new Dimension(W_STATS, H_STATS);
			statsDialog.setPreferredSize(statsSize);
			statsDialog.setMinimumSize(statsSize);
			statsDialog.setLocationRelativeTo(this);

			statsTitlesPanel = new JPanel();
			statsTitlesPanel.setLayout(new GridLayout((statsTitlesText.length - 1), 1));
			for (int i = 0; i < (statsTitlesText.length - 1); i++) {
				statsTitlesLabels[i] = new JLabel(statsTitlesText[i]);
				statsTitlesLabels[i].setOpaque(false);
				statsTitlesPanel.add(statsTitlesLabels[i]);
			}
			statsTitlesPanel.setOpaque(false);

			statsNumPanel = new JPanel();
			statsNumPanel.setLayout(new GridLayout((statsTitlesText.length - 1), 1));
			statsNumLabels[0] = new JLabel(statsTitlesText[(statsTitlesText.length - 1)]);
			statsNumLabels[0].setOpaque(false);
			statsNumPanel.add(statsNumLabels[0]);
			for (int i = 1; i < (statsTitlesText.length - 3); i++) {
				statsNumLabels[i] = new JLabel("" + this.player.scoreboard.getPlaysNb()[i-1]);
				statsNumLabels[i].setOpaque(false);
				statsNumPanel.add(statsNumLabels[i]);
			}
			statsNumLabels[statsTitlesText.length - 3] = new JLabel("" + this.player.scoreboard.getDealsNb());
			statsNumLabels[statsTitlesText.length - 3].setOpaque(false);
			statsNumPanel.add(statsNumLabels[statsTitlesText.length - 3]);
			double gainPercentage = (this.player.scoreboard.getCurrentBalance() - this.player.scoreboard.getInitialBalance())*100/this.player.scoreboard.getInitialBalance();
			statsNumLabels[statsTitlesText.length - 2] = new JLabel(this.player.scoreboard.getCurrentBalance() + " (" + gainPercentage + "%)");
			statsNumLabels[statsTitlesText.length - 2].setOpaque(false);
			statsNumPanel.add(statsNumLabels[statsTitlesText.length - 2]);

			statsDialog.getContentPane().setLayout(new GridLayout(1, 2));
			statsDialog.getContentPane().add(statsTitlesPanel);
			statsDialog.getContentPane().add(statsNumPanel);

			statsDialog.setVisible(true);

		} else if (e.getSource() == buttons[ADVICEBTN]) {

			if (adviceBtnFlag) {

				int[] decision;
				int[] holdIndexes = {};

				this.dealer.updateEvaluator(this.player.getHand());
				this.dealer.getHandRank();
				if ((decision = this.dealer.getAdvice()) != null) {
					holdIndexes = this.dealer.indexOrderedToUnordered(decision, this.player.getHand());

					for (int i = 0, k = 0; (i < 5) && (k < holdIndexes.length); i++) {
						if (holdIndexes[k] == i) {
							cardsCB[i].setSelected(true);
							k++;
						} else
						cardsCB[i].setSelected(false);
					}

					JOptionPane.showMessageDialog(null, "Player should hold checked cards");
				} else {
					for (int i = 0; i < 5; i++)
					cardsCB[i].setSelected(false);
					JOptionPane.showMessageDialog(null, "Player should hold no cards");
				}

			} else
			JOptionPane.showMessageDialog(null, "Invalid! Player can only get advice after a deal");

		}
	}
}
