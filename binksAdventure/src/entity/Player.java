package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import object.Obj_Potion;

public class Player extends Entity {

	GamePanel gp;
	KeyHandler keyH;

	public final int screenX;
	public final int screenY;
	int hasKey = 0;
	int hasPotion = 0;
	boolean hasGemOfSpeed = false;

	public Player(GamePanel gp, KeyHandler keyH) {

		this.gp = gp;
		this.keyH = keyH;

		// fixes player in center
		screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

		// player collision box
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY= solidArea.y;		
		solidArea.width = 32;
		solidArea.height = 32;

		setDefaultValues();
		getPlayerImage();

	}

	public void setDefaultValues() {
		//players spawned position
		worldX = gp.tileSize * 33;
		worldY = gp.tileSize * 18;
		speed = 4;
		direction = "down";
	}

	public String getDirection() {
		return direction;
	}

	public void getPlayerImage() {

		try {

			up1 = ImageIO.read(getClass().getResourceAsStream("/player/BinkUp1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/BinkUp2.png"));
			upIdle = ImageIO.read(getClass().getResourceAsStream("/player/BinkUpIdle.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/BinkDown1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/BinkDown2.png"));
			downIdle = ImageIO.read(getClass().getResourceAsStream("/player/BinkDownIdle.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/BinkLeft1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/BinkLeft2.png"));
			leftIdle = ImageIO.read(getClass().getResourceAsStream("/player/BinkLeftIdle.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/BinkRight1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/BinkRight2.png"));
			rightIdle = ImageIO.read(getClass().getResourceAsStream("/player/BinkRightIdle.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true
				|| keyH.rightPressed == true) {

			if (keyH.upPressed == true) {
				direction = "up";

			} else if (keyH.downPressed == true) {
				direction = "down";

			} else if (keyH.leftPressed == true) {
				direction = "left";

			} else if (keyH.rightPressed == true) {
				direction = "right";

			}
			// CHECK TILE COLLISION
			collisionOn = false;
			gp.cChecker.checkTile(this);
			 
			//CHECK OBJECT COLLISION
			 int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			// if collision is false player can move
			if (collisionOn == false) {
				switch (direction) {
				case "up":
					worldY -= speed;
					break;
				case "down":
					worldY += speed;
					break;
				case "left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
				}
			}

			spriteCounter++;

			if (spriteCounter > 12) {
				if (spriteNum == 1) {
					spriteNum = 2;
				} else if (spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
	}
	public void pickUpObject(int i) {
		
		if(i != 999) {
			String objectName = gp.obj[i].name;
			
			switch(objectName) {
			case "Key":
				hasKey++;
				gp.obj[i] = null;
				System.out.println("Keys: " + hasKey);
				break;
			case "Door":
				if(hasKey > 0) {
					hasKey--;
					gp.obj[i] = null;
				}
				System.out.println("Keys: " + hasKey);
				break;
			case "Chest":
				if(hasKey > 0) {
					// Save the chest's position
					int chestX = gp.obj[i].worldX;
					int chestY = gp.obj[i].worldY;

					// Remove chest
					
					hasKey--;
					gp.obj[i] = null;
					// Create potion in same position
					Obj_Potion potion = new Obj_Potion(gp);
					potion.worldX = chestX;
					potion.worldY = chestY;
					gp.obj[i] = potion;

					System.out.println("Potion appeared!");
				}
				System.out.println("Keys: " + hasKey);
				break;
			case "Potion":
				Obj_Potion potion = (Obj_Potion)gp.obj[i];
				
				if(potion.canPickup()) {
					hasPotion++;
					gp.obj[i] = null;
					System.out.println("You've found a Heath Potion!");
				}
				
				break;
				
			case "gemOfSpeed":
				hasGemOfSpeed = true;
				speed += 2;
				gp.obj[i] = null;	
				System.out.println("Gem of Speed Found! Speed Increased!");
				break;
			}
			
		}
	}

	public void draw(Graphics2D g2) {
		BufferedImage image = null;

		if (!keyH.upPressed && !keyH.downPressed && !keyH.leftPressed && !keyH.rightPressed) {
			String lastDirection = getDirection();
			switch (lastDirection) {
			case "up":
				image = upIdle;
				break;
			case "down":
				image = downIdle;
				break;
			case "left":
				image = leftIdle;
				break;
			case "right":
				image = rightIdle;
				break;
			}
		} else {
			switch (direction) {
			case "up":// up walking
				if (spriteNum == 1) {
					image = up1;
				}
				if (spriteNum == 2) {
					image = up2;
				}

				break;

			case "down":// down walking
				if (spriteNum == 1) {
					image = down1;
				}
				if (spriteNum == 2) {
					image = down2;
				}
				break;

			case "left":// left walking
				if (spriteNum == 1) {
					image = left1;
				}
				if (spriteNum == 2) {
					image = left2;
				}
				break;

			case "right": // right walking
				if (spriteNum == 1) {
					image = right1;
				}
				if (spriteNum == 2) {
					image = right2;
				}
				break;

			}
		}

		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

	}
}