package object;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_Heart extends Entity {
    GamePanel gp;
    public OBJ_Heart(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_pickupOnly;
        name = "Heart";
        value = 1;
        down1 = setup("/Res/objects/heart_full", gp.tileSize, gp.tileSize);

        image = setup("/Res/objects/heart_full", gp.tileSize, gp.tileSize);
        image2 = setup("/Res/objects/heart_half", gp.tileSize, gp.tileSize);
        image3 = setup("/Res/objects/heart_empty", gp.tileSize, gp.tileSize);
    }
    public void use(Entity entity) {
        gp.playSE(2);
        gp.ui.addMessage("Life + " + value + "!");
        entity.life += value;
    }
}
