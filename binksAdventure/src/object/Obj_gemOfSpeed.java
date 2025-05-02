package object;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Obj_gemOfSpeed extends SuperObject{
		public Obj_gemOfSpeed() {
			name = "gemOfSpeed";
			try {
				image = ImageIO.read(getClass().getResourceAsStream("/objects/gem1.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
//			solidArea = new Rectangle();
//			solidArea.x = 16;
//			solidArea.y = 16;
//			solidAreaDefaultX = solidArea.x;
//			solidAreaDefaultY= solidArea.y;		
//			solidArea.width = 32;
//			solidArea.height = 32;
		}

	}