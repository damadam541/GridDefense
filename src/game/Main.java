package game;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
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

	int wave = 3;

	int lvl = 0;

	Player player;

	ArrayList<Enemies> enemies = new ArrayList<Enemies>();

	static ArrayList<Maps> nodes = new ArrayList<Maps>();

	ArrayList<Tower> towers = new ArrayList<Tower>();

	int cursorPosX = 0;
	int cursorPosY = 0;
	
	Rectangle radius;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Main();
			}
		});
	}

	Main(){
		this.setTitle("The Tower Defense");
		this.setSize(WIN, WIN);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		gamePanel = new PlayingField();
		this.add(gamePanel);

		player = new Player();
		this.add(player, BorderLayout.EAST);

		gamePanel.setPreferredSize(new Dimension(WIN,WIN));
		this.pack();
		this.setLocationRelativeTo(null);

		this.setVisible(true);
		timer = new Timer(timerSpeed,this);
		timer.start();

		//TODO Remove debug towers when done
		towers.add(new RangeTower(4,5,10,10,boxW,boxH));
		towers.add(new RangeTower(2,3,10,10,boxW,boxH));

	}

	void initBox() {
		//Calculate box size
		boxW = (int) ((WIN/SIZE) + 0.5);
		boxH = (int) ((WIN/SIZE) + 0.5);
	}

	void levelSelect() {
		//Allow player to select a level
		//TODO maybe create a proper GUI start menu
		Object[] options = {"The Gate","[Insert Name]","[Insert Name]"};
		lvl = JOptionPane.showOptionDialog(null,"Choose a level","Level Selector",JOptionPane.YES_NO_CANCEL_OPTION , JOptionPane.INFORMATION_MESSAGE, null, options, options[2]);
		if (lvl == -1) System.exit(0);
	}

	void createMap() {

		//Call the correct method to add the necessary path nodes to the arraylist
		switch (lvl) {
		case 0: Maps.lvl1(nodes);
		break;
		case 1: Maps.lvl2(nodes);
		break;
		case 2: Maps.lvl3(nodes);
		break;
		}

		//Initialize the starting path
		field[nodes.get(0).xNode][nodes.get(0).yNode] = pathStart;

		//Connect two nodes together with paths until the ending node
		for (int i = 0 ; i < nodes.size() ; i++) {
			try {
				Maps node1 = nodes.get(i);
				Maps node2 = nodes.get(i+1);

				int xRange = node2.xNode-node1.xNode;
				int yRange = node2.yNode-node1.yNode;

				int xPath = 0;
				int yPath = 0;

				//TODO These comments may be wrong, if so, correct them
				//Calculate if path should go in the X direction
				if (yRange == 0) {
					yPath = node1.yNode;
					//Calculate if path should go in the -X direction
					if (xRange < 0) {
						for (int x = node2.xNode ; x <= Math.abs(xRange)+node2.xNode ; x++) {
							field[x][yPath] = path;
						}
					}
					//Calculate if path should go in the +X direction
					if (xRange > 0) {
						for (int x = node1.xNode+1 ; x <= xRange+node1.xNode ; x++) {
							field[x][yPath] = path;
						}
					}
				}

				//Calculate if path should go in the Y direction
				if (xRange == 0) {
					xPath = node1.xNode;
					//Calculate if path should go in the -Y direction
					if (yRange < 0) {
						for (int y = node2.yNode ; y <= Math.abs(yRange)+node2.yNode ; y++) {
							field[xPath][y] = path;
						}
					}
					//Calculate if path should go in the +Y direction
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

		//Initialize the ending node
		field[nodes.get(nodes.size()-1).xNode][nodes.get(nodes.size()-1).yNode] = pathEnd;

	}

	private class PlayingField extends JPanel implements MouseListener {

		PlayingField(){
			this.setBackground(Color.BLACK);
			addMouseListener(this);
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

			//Draw Grid
			g2.setColor(Color.WHITE);
			for (int i = 0; i < SIZE ; i++) {
				g2.drawLine(i*boxW, 0 , i*boxW, WIN);
				g2.drawLine(0, i*boxH, WIN , i*boxH);
			}

			//Draw Path
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
			//TODO Delete this later
			g2.setColor(Color.WHITE);
			for (int x = 0 ; x < SIZE ; x++) {
				for (int y = 0 ; y < SIZE ; y ++) {
					g2.drawString(x+" "+y, x*boxW+10, y*boxH+20);
				}
			}

			//Draw Enemies
			for (int i = 0 ; i < enemies.size() ; i++) {
				Enemies temp = enemies.get(i);
				g2.fillRect(temp.x,temp.y,temp.width,temp.height);
			}

			//Draw Tower
			for (int i = 0 ; i < towers.size() ; i++) {
				Tower temp = towers.get(i);
				g2.fillRect(temp.x,temp.y,temp.width,temp.height);

				if (cursorPosX >= temp.x && cursorPosX <= temp.x+temp.width && cursorPosY >= temp.y && cursorPosY <= temp.y+temp.height) {
					g2.setColor(new Color(50,50,50,100));
					g2.fillRect(temp.x-(temp.range/2-temp.width/2), temp.y-(temp.range/2-temp.height/2), temp.range, temp.range);
				} 
			}

		}

		@Override
		public void mouseClicked(MouseEvent e) {
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			System.out.println("Click");
			System.out.println(new Point(towers.get(0).x,towers.get(0).y));
			cursorPosX = e.getX();
			cursorPosY = e.getY();
			System.out.printf("%d %d",cursorPosX,cursorPosY);
		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

	}

	void removeHealth(int enemyIndex) {
		//Remove health and destroy enemies that reach the last node
		System.out.println("Ouchies");
		enemies.remove(enemyIndex);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//
		for (int i = 0 ; i < enemies.size() ; i++) {
			int enemyIndex = enemies.get(i).moveEnemies(nodes,boxW,boxH,i);
			if (enemyIndex >= 0) removeHealth(enemyIndex);
		}

		//TODO find out how to create indivdual firerates
		for (Tower t : towers) {
			t.shoot(enemies);
		}


		gamePanel.repaint();
	}

	//Sets spawn coordinates for enemies
	class Spawn extends Thread {
		public void run() {
			//TODO change enemy spawn algorithm
			while (enemies.size() < Math.pow(2, wave)) {
				try {
					Thread.sleep(500);
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
				//TODO DEBUG remove later
				enemies.add(new BigEnemies(spawnX,spawnY));
			}
		}
	}
}
