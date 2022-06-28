package game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class Tower extends Rectangle {
	
	int dmg;
	int range;
	int firerate;
	Rectangle radius;
	double angle;
	boolean fire;
	Timer fireTimer;
	ArrayList<Enemies> enemies = Main.enemies;
	
	Tower(int x, int y, int width, int height, int boxW, int boxH){
		
		this.x = x*boxW+(boxW/2-width/2);
		this.y = y*boxH+(boxH/2-height/2);
		this.width = width;
		this.height = height;
	}

}
