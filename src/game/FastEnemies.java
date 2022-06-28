package game;

public class FastEnemies extends Enemies{

	FastEnemies(int x, int y) {
		super(x, y);
		v = 8;
		maxHealth = 10;
		health = maxHealth;
	}

}
