package game;

import java.util.ArrayList;

public class Maps {

	int xNode;
	int yNode;
	

	Maps(int xNode, int yNode) {
		this.xNode = xNode;
		this.yNode = yNode;
	}
	
	// When creating new maps, DO NOT MAKE NODES THAT CONNECT DIAGONALLY

	static void lvl1(ArrayList<Maps> nodes) {
		nodes.add(new Maps(0, 5));
		nodes.add(new Maps(3, 5));
		nodes.add(new Maps(3, 2));
		nodes.add(new Maps(6, 2));
		nodes.add(new Maps(6, 7));
		nodes.add(new Maps(9, 7));
		nodes.add(new Maps(9, 9));
		nodes.add(new Maps(2, 9));
		nodes.add(new Maps(2, 13));
		nodes.add(new Maps(15, 13));
	}

	static void lvl2(ArrayList<Maps> nodes) {
		nodes.add(new Maps(0, 12));
		nodes.add(new Maps(4, 12));
		nodes.add(new Maps(4, 8));
		nodes.add(new Maps(2, 8));
		nodes.add(new Maps(2, 5));
		nodes.add(new Maps(2, 3));
		nodes.add(new Maps(10, 3));
		nodes.add(new Maps(10, 6));
		nodes.add(new Maps(8, 6));
		nodes.add(new Maps(8, 11));
		nodes.add(new Maps(13, 11));
		nodes.add(new Maps(13, 8));
		nodes.add(new Maps(14, 8));
		nodes.add(new Maps(14, 0));
	}

	static void lvl3(ArrayList<Maps> nodes) {
		nodes.add(new Maps(5, 0));
		nodes.add(new Maps(5, 10));
	}

}
