package game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Tower extends Rectangle {
	
	int dmg;
	int range;
	int firerate;
	Rectangle radius;
	double angle;
	boolean fire;
	
	Tower(int x, int y, int width, int height, int boxW, int boxH){
		
		this.x = x*boxW+(boxW/2-width/2);
		this.y = y*boxH+(boxH/2-height/2);
		this.width = width;
		this.height = height;
	}
	
	void shoot(ArrayList<Enemies> enemies) {
		if (enemies.size() == 0) return;
		Enemies e1 = enemies.get(0);
		Rectangle enemyRect = new Rectangle(e1.x,e1.y,e1.width,e1.height);
		if (enemyRect.intersects(radius) || radius.intersects(enemyRect)) {
			int dx = e1.x - this.x;
			int dy = e1.y - this.y;
			angle = Math.atan2(dy, dx);
			e1.health -= dmg;
			fire = true;
		}
	}

}
