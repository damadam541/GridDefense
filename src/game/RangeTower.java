package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class RangeTower extends Tower {

	RangeTower(int x, int y, int width, int height, int boxW, int boxH) {
		super(x, y, width, height, boxW, boxH);
		
		dmg = 10;
		range = 5*boxW;
		firerate = 300;
		radius = new Rectangle(this.x-(range/2-width/2),this.y-(range/2-height/2),range,range);
	}
	
	void shoot(ArrayList<Enemies> enemies) {
		if (enemies.size() == 0) return;
		Enemies e1 = enemies.get(0);
		Rectangle enemyRect = new Rectangle(e1.x,e1.y,e1.width,e1.height);
		if (enemyRect.intersects(radius) || radius.intersects(enemyRect)) {
		int dx = e1.x - this.x;
		int dy = e1.y - this.y;
		angle = Math.atan2(dy, dx);
		System.out.println("Ranged shoot");
		e1.health -= dmg;
		}
	}

}
