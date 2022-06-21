package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Player extends JPanel {

    int health = 100;
    int money = 0;
    int coolDown = 0;

    Player() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.GRAY);
        this.setPreferredSize(new Dimension(200, 720));
        this.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        
        
        
        this.setVisible(true);
    }
}

