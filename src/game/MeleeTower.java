package game;

public class MeleeTower extends Tower {

	MeleeTower(int x, int y, int width, int height, int boxW, int boxH) {
		super(x, y, width, height, boxW, boxH);
		
		dmg = 50;
	}

}
