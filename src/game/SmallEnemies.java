package game;

public class SmallEnemies extends Enemies{

	SmallEnemies(int x, int y) {
		super(x, y);
		v = 2;
		maxHealth = 50;
		health = maxHealth;
	}

}
