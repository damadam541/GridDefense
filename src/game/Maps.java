package game;
import java.util.ArrayList;

public class Maps {
	static ArrayList<Maps> nodes = Main.nodes;
	int xNode;
	int yNode;
	
	Maps(int xNode, int yNode){
		this.xNode = xNode;
		this.yNode = yNode;
	}
	
	static void lvl1() {
		//When creating new maps, DO NOT MAKE DIAGONAL PATHS
		nodes.add(new Maps(0,5));
		nodes.add(new Maps(3,5));
		nodes.add(new Maps(3,2));
		nodes.add(new Maps(6,2));
		nodes.add(new Maps(6,7));
		nodes.add(new Maps(9,7));
		nodes.add(new Maps(9,9));
		nodes.add(new Maps(2,9));
		nodes.add(new Maps(2,13));
		nodes.add(new Maps(15,13));
	}
	
	static void lvl2() {
		nodes.add(new Maps(0,0));
		nodes.add(new Maps(0,10));
	}
	
	static void lvl3() {
		nodes.add(new Maps(5,0));
		nodes.add(new Maps(5,10));
	}

}
