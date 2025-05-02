package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {

	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];

	// Water animation variables
	BufferedImage[] waterFrames = new BufferedImage[4];
	int waterFrameIndex = 0;
	int waterAnimationCounter = 0;
	int waterAnimationSpeed = 10;

	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[10];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

		getTileImage();
		loadMap("/maps/world01.txt");
	}

	public void getTileImage() {

		try {

			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));

			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall1.png"));
			tile[1].collision = true;

			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall2.png"));

			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grassflower1.png"));

			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grassflower2.png"));

			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/dirt1.png"));

			tile[6] = new Tile();
			tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/dirtflower1.png"));

			tile[7] = new Tile();
			tile[7].image = ImageIO.read(getClass().getResourceAsStream("/tiles/dirtflower2.png"));

			// Load all water frames
			for (int i = 0; i < 4; i++) {
				waterFrames[i] = ImageIO.read(getClass().getResourceAsStream("/tiles/water" + (i + 1) + ".png"));
			}
			tile[8] = new Tile();
			tile[8].image = waterFrames[0];
			tile[8].collision = true;

			tile[9] = new Tile();
			tile[9].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grasstree1.png"));
			tile[9].collision = true;

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateWaterAnimation() {
		waterAnimationCounter++;
		if (waterAnimationCounter >= waterAnimationSpeed) {
			waterAnimationCounter = 0;
			waterFrameIndex = (waterFrameIndex + 1) % waterFrames.length;
			tile[8].image = waterFrames[waterFrameIndex];
		}
	}

	public void loadMap(String filePath) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			int col = 0;
			int row = 0;
			while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
				String line = br.readLine();

				while (col < gp.maxWorldCol) {
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);

					mapTileNum[col][row] = num;
					col++;
				}
				if (col == gp.maxWorldCol) {
					col = 0;
					row++;
				}

			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g2) {

		int worldCol = 0;
		int worldRow = 0;

		while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

			int tileNum = mapTileNum[worldCol][worldRow];

			int worldX = worldCol * gp.tileSize; // position on the map
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX; // where on the map things are draw
			int screenY = worldY - gp.player.worldY + gp.player.screenY;

			if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && // creates a boundary that only draws
					// tiles when they are positioned in it
					// l
					worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
					&& worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
					&& worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

				g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);

			}
			worldCol++;

			if (worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;

			}
		}

	}
}
