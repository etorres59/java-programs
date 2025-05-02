package main;

import object.Obj_Chest;
import object.Obj_Door;
import object.Obj_Key;
import object.Obj_Potion;
import object.Obj_gemOfSpeed;

public class AssetSetter {
	GamePanel gp;
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
public void setObject() {
	gp.obj[0] = new Obj_Key();
	gp.obj[0].worldX = 35 * gp.tileSize;
	gp.obj[0].worldY = 44 * gp.tileSize;
	
	gp.obj[1] = new Obj_Key();
	gp.obj[1].worldX = 7 * gp.tileSize;
	gp.obj[1].worldY = 15 * gp.tileSize;
	
	gp.obj[2] = new Obj_Chest();
	gp.obj[2].worldX = 19 * gp.tileSize;
	gp.obj[2].worldY = 12 * gp.tileSize;
	
	gp.obj[3] = new Obj_Door();
	gp.obj[3].worldX = 19 * gp.tileSize;
	gp.obj[3].worldY = 16 * gp.tileSize;
	
	gp.obj[4] = new Obj_gemOfSpeed();
	gp.obj[4].worldX = 41 * gp.tileSize;
	gp.obj[4].worldY = 38 * gp.tileSize;
	
	
	

	}
}