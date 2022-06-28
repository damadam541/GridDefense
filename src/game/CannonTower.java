package game;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class CannonTower extends Tower implements ActionListener {

	Rectangle explosionRadius;
	int explosionSize;

	CannonTower(int x, int y, int width, int height, int boxW, int boxH) {
		super(x, y, width, height, boxW, boxH);

		dmg = 10;
		range = 5*boxW;
		firerate = 500;
		explosionSize = 3*boxW;
		radius = new Rectangle(this.x-(range/2-width/2),this.y-(range/2-height/2),range,range);
		fireTimer = new Timer(firerate,this);
		fireTimer.start();
	}

	void shoot(ArrayList<Enemies> enemies) {
		if (enemies.size() == 0) return;
		Enemies e1 = enemies.get(0);
		Rectangle enemy1Rect = new Rectangle(e1.x,e1.y,e1.width,e1.height);
		if (enemy1Rect.intersects(radius) || radius.intersects(enemy1Rect)) {
			int dx = e1.x - this.x;
			int dy = e1.y - this.y;
			angle = Math.atan2(dy, dx);
			e1.health -= dmg;
			System.out.println("Explosive Fire");
			explosionRadius = new Rectangle(e1.x*explosionSize,e1.y*explosionSize);
			for (int i = 0 ; i < enemies.size() ; i++) {
				Enemies e = enemies.get(i);
				Rectangle enemyRect = new Rectangle(e.x,e.y,e.width,e.height);
				if (e != e1) {
					if (enemyRect.intersects(explosionRadius) || explosionRadius.intersects(enemyRect)) {
						e.health -= dmg/2;
					}
				}
			}
			fire = true;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		shoot(enemies);
	}

}
