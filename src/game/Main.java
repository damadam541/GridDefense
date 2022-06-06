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

	Object[] options = {"Level 1","Level 2","Level 3"};
	int lvl = 0;

	ArrayList<Enemies> enemies = new ArrayList<Enemies>();

	static ArrayList<Maps> nodes = new ArrayList<Maps>();

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

	void createMap() {

		if (lvl == 0) Maps.lvl1();
		if (lvl == 1) Maps.lvl2();
		if (lvl == 2) Maps.lvl3();

		field[nodes.get(0).xNode][nodes.get(0).yNode] = pathStart;

		for (int i = 0 ; i < nodes.size() ; i++) {
			try {
				Maps node1 = nodes.get(i);
				Maps node2 = nodes.get(i+1);

				int xRange = node2.xNode-node1.xNode;
				int yRange = node2.yNode-node1.yNode;

				int xPath = 0;
				int yPath = 0;

				if (yRange == 0) {
					yPath = node1.yNode;
					if (xRange < 0) {
						for (int x = node2.xNode ; x <= Math.abs(xRange)+node2.xNode ; x++) {
							field[x][yPath] = path;
						}
					}
					if (xRange > 0) {
						for (int x = node1.xNode+1 ; x <= xRange+node1.xNode ; x++) {
							field[x][yPath] = path;
						}
					}
				}
				if (xRange == 0) {
					xPath = node1.xNode;
					if (yRange < 0) {
						for (int y = node2.yNode ; y <= Math.abs(yRange)+node2.yNode ; y++) {
							field[xPath][y] = path;
						}
					}
					if (yRange > 0) {
						for (int y = node1.yNode+1 ; y <= yRange+node1.yNode ; y++) {
							field[xPath][y] = path;
						}
					}
				}

			} catch (IndexOutOfBoundsException e) {
				break;
			}
		}
		field[nodes.get(nodes.size()-1).xNode][nodes.get(nodes.size()-1).yNode] = pathEnd;
	}

	void moveEnemies(int i) {
		Enemies temp = enemies.get(i);
		int x = temp.x;
		int y = temp.y;
		int v = temp.v;
		int currentNode = temp.currentNode;
		int xNode = nodes.get(currentNode).xNode*boxW+(boxW/2)-(enemies.get(i).height/2);
		int yNode = nodes.get(currentNode).yNode*boxH+(boxH/2)-(enemies.get(i).width/2);

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

		if (x > xNode-5 && x < xNode+5) {
			if (y > yNode-5 && y < yNode+5) temp.currentNode++;
		}

		temp.x = x;
		temp.y = y;
	}

	void levelSelect() {
		lvl = JOptionPane.showOptionDialog(null,"Choose a level","Level Selector",JOptionPane.YES_NO_CANCEL_OPTION , JOptionPane.INFORMATION_MESSAGE, null, options, options[2]);
	}

	private class PlayingField extends JPanel {

		PlayingField(){
			this.setBackground(Color.BLACK); //light gray
			levelSelect();
			initBox();
			createMap();
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
				Enemies temp = enemies.get(i);
				g2.fillRect(temp.x,temp.y,temp.width,temp.height);
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
							if (y == 0 && field[x][y+1] == path) {
								spawnX = x*boxW+(boxH/2);
								spawnY = y*boxH+(boxW/2)-boxH;
							}
							if (x == 0 && field[x+1][y] == path) {
								spawnY = y*boxH+(boxW/2);
								spawnX = x*boxW+(boxH/2)-boxW;
							}
						}
					}
				}
				if (wave == 1) {
					enemies.add(new Enemies(spawnX,spawnY,"medium"));
				}
			}
		}
	}
}
