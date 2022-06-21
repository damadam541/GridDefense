package game;

import java.awt.BorderLayout;
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

	Object[] options = { "Level 1", "Level 2", "Level 3" };
	int lvl = 0;

	Player player;
<<<<<<< HEAD
	ArrayList<Enemies> enemies = new ArrayList<Enemies>();
=======

	static ArrayList<Enemies> enemies = new ArrayList<Enemies>();
>>>>>>> branch 'master' of https://github.com/damadam541/GridDefense.git

	static ArrayList<Maps> nodes = new ArrayList<Maps>();

<<<<<<< HEAD
=======
	ArrayList<Tower> towers = new ArrayList<Tower>();

	int cursorPosX = 0;
	int cursorPosY = 0;

>>>>>>> branch 'master' of https://github.com/damadam541/GridDefense.git
	public static void main(String[] args) {
		new Main();
	}

	Main() {
		this.setTitle("The Tower Defense");
		this.setSize(WIN, WIN);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		gamePanel = new PlayingField();
		this.add(gamePanel);

		player = new Player();
		this.add(player, BorderLayout.EAST);

		gamePanel.setPreferredSize(new Dimension(WIN, WIN));
		this.pack();
		this.setLocationRelativeTo(null);

		this.setVisible(true);
		timer = new Timer(timerSpeed, this);
		timer.start();
	}

	void initBox() {
		boxW = (int) ((WIN / SIZE) + 0.5);
		boxH = (int) ((WIN / SIZE) + 0.5);
	}

	void createMap() {

		if (lvl == 0)
			Maps.lvl1();
		if (lvl == 1)
			Maps.lvl2();
		if (lvl == 2)
			Maps.lvl3();

		field[nodes.get(0).xNode][nodes.get(0).yNode] = pathStart;

		for (int i = 0; i < nodes.size(); i++) {
			try {
				Maps node1 = nodes.get(i);
				Maps node2 = nodes.get(i + 1);

				int xRange = node2.xNode - node1.xNode;
				int yRange = node2.yNode - node1.yNode;

				int xPath = 0;
				int yPath = 0;

				if (yRange == 0) {
					yPath = node1.yNode;
					if (xRange < 0) {
						for (int x = node2.xNode; x <= Math.abs(xRange) + node2.xNode; x++) {
							field[x][yPath] = path;
						}
					}
					if (xRange > 0) {
						for (int x = node1.xNode + 1; x <= xRange + node1.xNode; x++) {
							field[x][yPath] = path;
						}
					}
				}
				if (xRange == 0) {
					xPath = node1.xNode;
					if (yRange < 0) {
						for (int y = node2.yNode; y <= Math.abs(yRange) + node2.yNode; y++) {
							field[xPath][y] = path;
						}
					}
					if (yRange > 0) {
						for (int y = node1.yNode + 1; y <= yRange + node1.yNode; y++) {
							field[xPath][y] = path;
						}
					}
				}

			} catch (IndexOutOfBoundsException e) {
				break;
			}
		}
		field[nodes.get(nodes.size() - 1).xNode][nodes.get(nodes.size() - 1).yNode] = pathEnd;
	}

	void moveEnemies(int i) {
		Enemies temp = enemies.get(i);
		int x = temp.x;
		int y = temp.y;
		int v = temp.v;
		int currentNode = temp.currentNode;
		int xNode = nodes.get(currentNode).xNode * boxW + (boxW / 2) - (enemies.get(i).height / 2);
		int yNode = nodes.get(currentNode).yNode * boxH + (boxH / 2) - (enemies.get(i).width / 2);

		if (x < xNode) {
			x = x + v;
		}
		if (x > xNode) {
			x = x - v;
		}

		if (y < yNode) {
			y = y + v;
		}
		if (y > yNode) {
			y = y - v;
		}

		if (x > xNode - 5 && x < xNode + 5) {
			if (y > yNode - 5 && y < yNode + 5)
				temp.currentNode++;
		}

		temp.x = x;
		temp.y = y;
	}

	void levelSelect() {
		lvl = JOptionPane.showOptionDialog(null, "Choose a level", "Level Selector", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, options, options[2]);
	}

	private class PlayingField extends JPanel {

		PlayingField() {
			this.setBackground(Color.BLACK); // light gray
			levelSelect();
			initBox();
			createMap();
			new Spawn().start();
			new Shoot().start();
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// drawGrid
			g2.setColor(Color.WHITE);
			for (int i = 0; i < SIZE; i++) {
				g2.drawLine(i * boxW, 0, i * boxW, WIN);
				g2.drawLine(0, i * boxH, WIN, i * boxH);
			}

			for (int x = 0; x < SIZE; x++) {
				for (int y = 0; y < SIZE; y++) {
					if (field[x][y] == path) {
						g2.setColor(Color.GRAY);
						g2.fillRect(x * boxW, y * boxH, boxW, boxH);
					}
					if (field[x][y] == pathStart) {
						g2.setColor(Color.GREEN);
						g2.fillRect(x * boxW, y * boxH, boxW, boxH);
					}
					if (field[x][y] == pathEnd) {
						g2.setColor(Color.RED);
						g2.fillRect(x * boxW, y * boxH, boxW, boxH);
					}
				}
			}

			// GRID DEBUG
			g2.setColor(Color.WHITE);
			for (int x = 0; x < SIZE; x++) {
				for (int y = 0; y < SIZE; y++) {
					g2.drawString(x + " " + y, x * boxW + 10, y * boxH + 20);
				}
			}

			for (int i = 0; i < enemies.size(); i++) {
				Enemies temp = enemies.get(i);
<<<<<<< HEAD
				g2.fillRect(temp.x, temp.y, temp.width, temp.height);
=======
				g2.fillRect(temp.x,temp.y,temp.width,temp.height);
			}

			//Draw Tower
			for (Tower t : towers) {
				g2.rotate(t.angle,t.x+t.width/2,t.y+t.height/2);
				g2.fillRect(t.x,t.y,t.width,t.height);
				//TODO Remove this later
				g2.setColor(Color.RED);
				g2.fillRect(t.x,t.y,5,5);
				
				g2.rotate(-t.angle,t.x+t.width/2,t.y+t.height/2);
				if (cursorPosX >= t.x && cursorPosX <= t.x+t.width && cursorPosY >= t.y && cursorPosY <= t.y+t.height) {
					g2.setColor(new Color(50,50,50,100));
					g2.fill(t.radius);
				}
>>>>>>> branch 'master' of https://github.com/damadam541/GridDefense.git
			}

		}

<<<<<<< HEAD
=======
		@Override
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {
			System.out.println("Click");
			System.out.println(new Point(towers.get(0).x,towers.get(0).y));
			cursorPosX = e.getX();
			cursorPosY = e.getY();
			System.out.printf("%d %d",cursorPosX,cursorPosY);
		}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

	}

	void removeHealth(int enemyIndex) {
		//Remove health and destroy enemies that reach the last node
		System.out.println("Ouchies");
		enemies.remove(enemyIndex);
>>>>>>> branch 'master' of https://github.com/damadam541/GridDefense.git
	}
	
	void deleteEnemy(int i) {
		enemies.remove(i);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
<<<<<<< HEAD
		for (int i = 0; i < enemies.size(); i++) {
			moveEnemies(i);
=======
		//Move the enemies and check if they reach the endpoint or get to 0 health
		for (int i = 0 ; i < enemies.size() ; i++) {
			int enemyIndex = enemies.get(i).moveEnemies(nodes,boxW,boxH,i);
			if (enemyIndex >= 0) removeHealth(enemyIndex);
			if (enemies.get(i).health == 0) deleteEnemy(i);
>>>>>>> branch 'master' of https://github.com/damadam541/GridDefense.git
		}

<<<<<<< HEAD
		gamePanel.repaint();	
=======
		//TODO find out how to create indivdual firerates
		

		gamePanel.repaint();
>>>>>>> branch 'master' of https://github.com/damadam541/GridDefense.git
	}

	class Spawn extends Thread {
		public void run() {
			while (enemies.size() < Math.pow(2, wave)) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				int spawnX = 0;
				int spawnY = 0;
				for (int x = 0; x < SIZE; x++) {
					for (int y = 0; y < SIZE; y++) {
						if (field[x][y] == pathStart) {
							if (y == 0 && field[x][y + 1] == path) {
								spawnX = x * boxW + (boxH / 2);
								spawnY = y * boxH + (boxW / 2) - boxH;
							}
							if (x == 0 && field[x + 1][y] == path) {
								spawnY = y * boxH + (boxW / 2);
								spawnX = x * boxW + (boxH / 2) - boxW;
							}
						}
					}
				}
<<<<<<< HEAD
				if (wave == 1) {
					enemies.add(new Enemies(spawnX, spawnY, "medium"));
				}
=======
				//TODO DEBUG remove later
				enemies.add(new MediumEnemies(spawnX,spawnY));
			}
		}
	}
	
	class Shoot extends Thread {
		
		int firerate;
		
		public void run() {
			while (true) {
				for (Tower t : towers) {
					t.shoot(enemies);
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {}
>>>>>>> branch 'master' of https://github.com/damadam541/GridDefense.git
			}
		}
	}
}
