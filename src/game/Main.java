package game;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;

public class Main extends JFrame implements ActionListener {

	final static int WIN = 720;
	final static int SIZE = 16;

	PlayingField gamePanel;
	static int[][] field = new int[SIZE][SIZE];

	private int boxW, boxH;

	Timer timer;
	final int timerSpeed = 1;

	final static int path = 1;
	final static int pathStart = 2;
	final static int pathEnd = -1;

	int wave = 1;

	ArrayList<Enemies> enemies = new ArrayList<Enemies>();
	
	ArrayList<Maps> nodes = new ArrayList<Maps>();

	public static void main(String[] args) {
		new Main();
	}

	Main(){
		this.setTitle("The Tower Defense");
		this.setSize(WIN, WIN);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		gamePanel = new PlayingField();
		this.add(gamePanel);

		gamePanel.setPreferredSize(new Dimension(WIN,WIN));
		this.pack();
		this.setLocationRelativeTo(null);

		this.setVisible(true);
		timer = new Timer(timerSpeed,this);
		timer.start();
	}

	void initBox() {
		boxW = (int) ((WIN/SIZE) + 0.5);
		boxH = (int) ((WIN/SIZE) + 0.5);
	}

	void lvl1() {
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
		
		
		field[0][5] = pathStart;
		for (int i = 1 ; i < 3 ; i++) {
			field[i][5] = path;
		}
		for (int i = 5 ; i > 1 ; i--) {
			field[3][i] = path;
		}
		for (int i = 3 ; i < 7 ; i++) {
			field[i][2] = path;
		}
		for (int i = 2 ; i < 8 ; i++) {
			field[6][i] = path;
		}
		for (int i = 6 ; i < 9 ; i++) {
			field[i][7] = path;
		}
		for (int i = 7 ; i < 10 ; i++) {
			field[9][i] = path;
		}
		for (int i = 9 ; i > 1 ; i--) {
			field[i][9] = path;
		}
		for (int i = 9 ; i < 14 ; i++) {
			field[2][i] = path;
		}
		for (int i = 2 ; i < 15 ; i++) {
			field[i][13] = path;
		}
		field[15][13] = pathEnd;
	}

	void moveEnemies(int i) {
		Enemies temp = enemies.get(i);
		int x = temp.x;
		int y = temp.y;
		int v = temp.v;
		int width = temp.width;
		int height = temp.height;

		if (x < 3*boxW+(boxW/2)-(enemies.get(i).width/2)) x = x + v;

		enemies.get(i).x = x;
		enemies.get(i).y = y;
	}

	private class PlayingField extends JPanel {

		PlayingField(){
			this.setBackground(Color.BLACK); //light gray
			initBox();
			lvl1();
			new Spawn().start();
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			//drawGrid
			g2.setColor(Color.WHITE);
			for (int i = 0; i < SIZE ; i++) {
				g2.drawLine(i*boxW, 0 , i*boxW, WIN);
				g2.drawLine(0, i*boxH, WIN , i*boxH);
			}

			for (int x = 0 ; x < SIZE ; x++) {
				for (int y = 0 ; y < SIZE ; y++) {
					if (field[x][y] == path) {
						g2.setColor(Color.GRAY);
						g2.fillRect(x*boxW, y*boxH, boxW, boxH);
					}
					if (field[x][y] == pathStart) {
						g2.setColor(Color.GREEN);
						g2.fillRect(x*boxW, y*boxH, boxW, boxH);
					}
					if (field[x][y] == pathEnd) {
						g2.setColor(Color.RED);
						g2.fillRect(x*boxW, y*boxH, boxW, boxH);
					}
				}
			}

			//GRID DEBUG
			g2.setColor(Color.WHITE);
			for (int x = 0 ; x < SIZE ; x++) {
				for (int y = 0 ; y < SIZE ; y ++) {
					g2.drawString(x+" "+y, x*boxW+10, y*boxH+20);
				}
			}

			for (int i = 0 ; i < enemies.size() ; i++) {
				g2.fillRect(enemies.get(i).x,enemies.get(i).y,enemies.get(i).width,enemies.get(i).height);
			}

		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0 ; i < enemies.size() ; i++) {
			moveEnemies(i);
		}


		gamePanel.repaint();
	}

	class Spawn extends Thread {
		public void run() {
			while (enemies.size() < Math.pow(2, wave)) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {}
				int spawnX = 0;
				int spawnY = 0;
				for (int x = 0 ; x < SIZE ; x++) {
					for (int y = 0 ; y < SIZE ; y++) {
						if (field[x][y] == pathStart) {
							spawnX = x*boxW-boxW;
							spawnY = y*boxW+(boxW/2);
						}
					}
				}
				if (wave == 1) {
					enemies.add(new Enemies(spawnX,spawnY,"small"));
				}
			}
		}
	}
}
