package game;

import java.awt.Rectangle;
import java.util.ArrayList;

public class MeleeTower extends Tower {

	MeleeTower(int x, int y, int width, int height, int boxW, int boxH) {
		super(x, y, width, height, boxW, boxH);
		
		dmg = 50;
		range = 3*boxW;
		firerate = 500;
		radius = new Rectangle(this.x-(range/2-width/2),this.y-(range/2-height/2),range,range);
	}

}
