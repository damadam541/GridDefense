package game;
import java.awt.Rectangle;

public class Enemies extends Rectangle {
	int x;
	int y;
	int width;
	int height;
	int v;
	int currentNode = 1;
	
	int[][] visited = new int[Main.SIZE][Main.SIZE];

	Enemies(int x, int y, String type){
		this.x = x;
		this.y = y;
		setEnemyType(type);
	}
	
	void setEnemyType(String type) {
		if (type.equals("small")) {
			width = 10;
			height = 10;
			v = 1;
		}
		if (type.equals("medium")) {
			width = 15;
			height = 15;
			v = 2;
		}
		if (type.equals("large")) {
			width = 20;
			height = 20;
			v = 4;
		}
	}
	
}
