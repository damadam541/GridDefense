package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Player extends JPanel {

    Player player;
    int health = 100;
    int money = 100;
    int coolDown = 0;
    JButton btn, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;

    Player() {
        btn = new JButton("");
        btn.setActionCommand("0");
        btn.setSize(20, 20);
        btn1 = new JButton("");
        btn1.setActionCommand("1");
        btn1.setSize(20, 20);
        btn2 = new JButton("2");
        btn2.setActionCommand("2");
        btn2.setSize(20, 20);
        btn3 = new JButton("3");
        btn3.setActionCommand("3");
        btn3.setSize(20, 20);
        btn4 = new JButton("4");
        btn4.setActionCommand("4");
        btn4.setSize(20, 20);

        this.setLayout(new BorderLayout());
        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(400, 720));
        this.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));

        setupGUI();

        this.setVisible(true);

    }

    void setupGUI() {

        GridLayout buttonLayout = new GridLayout(2, 0);
        JLabel moneyLabel = new JLabel("Money: $" + money, JLabel.CENTER);

        this.setLayout(buttonLayout);

        this.add(btn);
        btn.setText("<html><body>Melee Tower<br>Cost: $100</body></html>");
        this.add(btn1);
        btn1.setText("<html><body>Ranged Tower<br>Cost: $150</body></html>");
        this.add(btn2);
        btn2.setText("<html><body>Blank Tower<br>Cost: $x</body></html>");
        this.add(btn3);
        btn3.setText("<html><body>Blank Tower<br>Cost: $x</body></html>");
        this.add(btn4);
        btn4.setText("<html><body>Blank Tower<br>Cost: $x</body></html>");
        this.add(moneyLabel);
        moneyLabel.setForeground(Color.WHITE);

    }

}