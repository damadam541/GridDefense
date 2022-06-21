package game;

<<<<<<< HEAD
public class Tower {
=======
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Tower extends Rectangle {
	
	int dmg = 0;
	int range = 0;
	int firerate = 0;
	Rectangle radius;
	double angle;
	
	Tower(int x, int y, int width, int height, int boxW, int boxH){
		
		this.x = x*boxW+(boxW/2-width/2);
		this.y = y*boxH+(boxH/2-height/2);
		this.width = width;
		this.height = height;
	}
	
	void shoot(ArrayList<Enemies> enemies) {
		System.out.println("Basic Shoot");
		
		
	}
>>>>>>> branch 'master' of https://github.com/damadam541/GridDefense.git

}
