package game;

import java.awt.Rectangle;
import java.util.ArrayList;

public class RangeTower extends Tower {

	RangeTower(int x, int y, int width, int height, int boxW, int boxH) {
		super(x, y, width, height, boxW, boxH);
		dmg = 10;
		range = 5*boxW;
		firerate = 500;
	}
	
	void shoot(ArrayList<Enemies> enemies) {
		
		for (Enemies e : enemies) {
			
		}
		
	}

}
