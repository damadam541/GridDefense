package game;
import java.awt.Rectangle;

public class Enemies extends Rectangle {
	int x;
	int y;
	final int width = 20;
	final int height = 20;
	int v;
	int currentNode = 1;

	Enemies(int x, int y, String type){
		this.x = x;
		this.y = y;
		setEnemyType(type);
	}
	
	void setEnemyType(String type) {
		if (type.equals("small")) {
			v = 4;
		}
		if (type.equals("medium")) {
			v = 3;
		}
		if (type.equals("large")) {
			v = 2;
		}
		if (type.equals("fast")) {
			v = 8;
		}
	}
	
}
