package game;

public class BigEnemies extends Enemies {

	BigEnemies(int x, int y) {
		super(x, y);
		v = 1;
		maxHealth = 200;
		health = maxHealth;
	}

}
