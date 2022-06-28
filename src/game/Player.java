package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Player extends JPanel {

	Player player;
	int health = 100;
	int money = 100;
	int coolDown = 0;
	JButton[] btn = new JButton[9];
	ButtonListener click = new ButtonListener();

	Player() {

		for (int i = 0 ; i < btn.length ; i++) {
			btn[i] = new JButton();
		}

		this.setLayout(new BorderLayout());
		this.setBackground(Color.BLACK);
		this.setPreferredSize(new Dimension(400, 720));
		this.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));

		setupGUI();

		this.setVisible(true);

	}

	void setupGUI() {
		
		for (int i = 0 ; i < btn.length ; i++) {
			btn[i].setActionCommand(Integer.toString(i));
			btn[i].setSize(20,20);
			btn[i].addActionListener(click);
		}

		GridLayout buttonLayout = new GridLayout(2, 0);
		JLabel moneyLabel = new JLabel("Money: $" + money, JLabel.CENTER);

		this.setLayout(buttonLayout);

		this.add(btn[0]);
		btn[0].setText("<html><body>Melee Tower<br>Cost: $100</body></html>");
		this.add(btn[1]);
		btn[1].setText("<html><body>Ranged Tower<br>Cost: $150</body></html>");
		this.add(btn[2]);
		btn[2].setText("<html><body>Blank Tower<br>Cost: $x</body></html>");
		this.add(btn[3]);
		btn[3].setText("<html><body>Blank Tower<br>Cost: $x</body></html>");
		this.add(btn[4]);
		btn[4].setText("<html><body>Blank Tower<br>Cost: $x</body></html>");
		this.add(moneyLabel);
		moneyLabel.setForeground(Color.WHITE);

	}

	class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
		}

	}

}