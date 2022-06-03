package game;

public class Maps {
	
	int[][] field = Main.field;
	int pathStart = Main.pathStart;
	int path = Main.path;
	int pathEnd = Main.pathEnd;
	int xNode;
	int yNode;
	
	Maps(int xNode, int yNode){
		this.xNode = xNode;
		this.yNode = yNode;
	}

}
