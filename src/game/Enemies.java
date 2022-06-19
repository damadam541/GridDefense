package game;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Enemies extends Rectangle {
	int x;
	int y;
	final int width = 20;
	final int height = 20;
	int v;
	int health;
	int currentNode = 1;

	Enemies(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	int moveEnemies(ArrayList<Maps> nodes, int boxW, int boxH, int enemyIndex) {
		int xNode = nodes.get(currentNode).xNode*boxW+(boxW/2)-(height/2);
		int yNode = nodes.get(currentNode).yNode*boxH+(boxH/2)-(width/2);

		if (x < xNode) {
			x = x + v;
		}
		if (x > xNode) {
			x = x - v;
		}

		if (y < yNode) {
			y = y + v;
		}
		if (y > yNode) {
			y = y - v;
		}

		if (x > xNode-10 && x < xNode+10) {
			if (y > yNode-10 && y < yNode+10) 
				if (currentNode < nodes.size()-1) currentNode++;
				else {
					return enemyIndex;
				}
		}
		
		return -1;

	}
	
}
