package game;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Tower extends Rectangle {
	
	int dmg = 0;
	int range = 0;
	int firerate = 0;
	
	Tower(int x, int y, int width, int height, int boxW, int boxH){
		this.x = x*boxW+(boxW/2-width/2);
		this.y = y*boxH+(boxH/2-height/2);
		this.width = width;
		this.height = height;
	}
	
	void shoot(ArrayList<Enemies> enemies) {
		System.out.println("Basic Shoot");
		
		
	}

}
