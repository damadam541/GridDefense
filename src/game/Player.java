package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Player extends JPanel {

	Player player;
	int health = 100;
	static int money = 100;
	JButton[] btn = new JButton[9];
	ButtonListener click = new ButtonListener();
	static int towerSelect = -1;
	
	static JLabel moneyLabel;

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
		moneyLabel = new JLabel("Money: $" + money, JLabel.CENTER);

		this.setLayout(buttonLayout);

		this.add(btn[0]);
		btn[0].setText("<html><body>Melee Tower<br>Cost: $100</body></html>");
		this.add(btn[1]);
		btn[1].setText("<html><body>Ranged Tower<br>Cost: $150</body></html>");
		this.add(btn[2]);
		btn[2].setText("<html><body>Cannon Tower<br>Cost: $200</body></html>");
		this.add(btn[3]);
		btn[3].setText("<html><body>Blank Tower<br>Cost: $x</body></html>");
		this.add(btn[4]);
		btn[4].setText("<html><body>Blank Tower<br>Cost: $x</body></html>");
		this.add(moneyLabel);
		moneyLabel.setForeground(Color.WHITE);
	}
	
	static public void placeTower(ArrayList<Tower> towers, int gridPosX, int gridPosY, int boxW, int boxH) {
		switch (towerSelect) {
		case -1: break;
		case 0: 
			if (money >= 100) {
			towers.add(new MeleeTower(gridPosX,gridPosY,10,10,boxW,boxH)); 
			money -= 100;
			System.out.println();
			}
			break;
		case 1: 
			if (money >= 150) {
			towers.add(new RangeTower(gridPosX,gridPosY,10,10,boxW,boxH));
			money -= 150;
			}
			break;
		case 2: 
			if (money >= 200) {
			towers.add(new CannonTower(gridPosX,gridPosY,10,10,boxW,boxH)); 
			money -= 200;
			}
			break;
		}
		moneyLabel.setText("Money: $" + money);
	}

	class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (e.getActionCommand().equals(Integer.toString(towerSelect))) {
				revertButton();
				return;
			}
			
			for (int i = 0 ; i < btn.length ; i++) {
				if (e.getActionCommand().equals(Integer.toString(i))) {
					btn[i].setBackground(Color.decode("#6fa85d"));
					towerSelect = i;
					Main.getTowerSelect(towerSelect);
				} else {
					btn[i].setBackground(Color.decode("#737373"));
				}
			}
			
		}
		
		public void revertButton() {
			
			for (int i = 0; i < btn.length ; i++) {
				btn[i].setBackground(new JButton().getBackground());
				towerSelect = -1;
				Main.getTowerSelect(towerSelect);
			}
			
		}
		
	}

}