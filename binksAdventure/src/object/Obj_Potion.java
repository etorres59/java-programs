package object;

import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Obj_Potion extends SuperObject {

	GamePanel gp;
	// pickup delay for visibility
	public int pickupDelay = 30;

	// Floating Animation
	private int floatCounter = 0;
	private int floatHeight = 4;// up and down motion (px)
	private int floatSpeed = 16;// 

	// Sparkle Effect
	private int sparkleCounter = 0;
	private boolean sparkleVisible = true;

	public Obj_Potion(GamePanel gp) {
		this.gp = gp;
		name = "Potion";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/potion.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		if (pickupDelay > 0) {
			pickupDelay--;
		}
		// Floating
		floatCounter++;
		if (floatCounter >= floatSpeed * 2) {
			floatCounter = 0;
		}
	}

	public boolean canPickup() {
		return pickupDelay <= 0;
	}
	@Override
	public void draw(Graphics2D g2, GamePanel gp)
	{
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;

		// Floating offset
		int floatOffset = (int) (Math.sin((floatCounter / (floatSpeed * 2.0)) * Math.PI * 2) * floatHeight);

		// Draw potion
		g2.drawImage(image, screenX, screenY + floatOffset, gp.tileSize, gp.tileSize, null);

	}
}