package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.Obj_Potion;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
	// screen settings
	final int originalTileSize = 16; // 16x16 tile
	final int scale = 3; // 3x scale
	// upscaling tile size
	public final int tileSize = originalTileSize * scale; // 48x48 tile

	public final int maxScreenCol = 16; // 16 tiles across
	public final int maxScreenRow = 12; // 12 tiles down
	// screen size in pixels (upscaled)
	public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

	// World Settings
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;

	// FPS
	int FPS = 60;

	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;// game thread automatically calls run()
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);

	public Player player = new Player(this, keyH);

	public SuperObject obj[] = new SuperObject[10];// amount of objects that can be displayed at one time

//	// set player default position
//	int playerX = 100;
//	int playerY = 100;
//	int playerSpeed = 4;

	// Game panel Constructor
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); // for smoother rendering
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void setupGame() {
		aSetter.setObject();
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	// Delta Game Loop method

	public void run() {
		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;

		while (gameThread != null) {// while gamethread is running update and repaint

			currentTime = System.nanoTime();

			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if (delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;

			}
			if (timer >= 1000000000) {
				//System.out.println("FPS" + drawCount);
				drawCount = 0;
				timer = 0;

			}
		}

	}

	public void update() {

		player.update();
		tileM.updateWaterAnimation();
		
		for(int i = 0 ; i< obj.length; i++) {
			if (obj[i] != null && obj[i] instanceof Obj_Potion) {
				((Obj_Potion)obj[i]).update();
			}
		}

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		// Tile
		tileM.draw(g2);

		// OBJECTS
		for (int i = 0; i < obj.length; i++) {// scan to see what object to display
			if (obj[i] != null) {
				obj[i].draw(g2, this);
			}
		}

		// player drawn last!
		player.draw(g2);

		g2.dispose();
	}
}
