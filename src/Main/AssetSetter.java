package Main;

import Entity.NPC_Tigger;
import Main.monster.MON_GreenSlime;
import object.*;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        int i = 0;
//        gp.obj[i] = new OBJ_Coin(gp);
//        gp.obj[i].worldX = gp.tileSize * 25;
//        gp.obj[i].worldY = gp.tileSize * 23;
//        i++;
//        gp.obj[i] = new OBJ_Coin(gp);
//        gp.obj[i].worldX = gp.tileSize * 21;
//        gp.obj[i].worldY = gp.tileSize * 19;
//        i++;
//        gp.obj[i] = new OBJ_Coin(gp);
//        gp.obj[i].worldX = gp.tileSize * 26;
//        gp.obj[i].worldY = gp.tileSize * 21;
//        i++;
//        gp.obj[i] = new OBJ_Sword_Normal(gp);
//        gp.obj[i].worldX = gp.tileSize * 33;
//        gp.obj[i].worldY = gp.tileSize * 21;
//        i++;
//        gp.obj[i] = new OBJ_Shield_Normal(gp);
//        gp.obj[i].worldX = gp.tileSize * 35;
//        gp.obj[i].worldY = gp.tileSize * 21;
//        i++;
//        gp.obj[i] = new OBJ_Potion_Red(gp);
//        gp.obj[i].worldX = gp.tileSize * 22;
//        gp.obj[i].worldY = gp.tileSize * 27;
//        i++;
//        gp.obj[i] = new OBJ_Heart(gp);
//        gp.obj[i].worldX = gp.tileSize * 22;
//        gp.obj[i].worldY = gp.tileSize * 29;

    }

    public void setNPC() {

        gp.npc[0] = new NPC_Tigger(gp);
        gp.npc[0].worldX = gp.tileSize * 21;
        gp.npc[0].worldY = gp.tileSize * 21;

    }

    public void setMonster() {
        int i = 0;
        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize * 23;
        gp.monster[i].worldY = gp.tileSize * 36;
        i++;
        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize * 23;
        gp.monster[i].worldY = gp.tileSize * 37;
        i++;
        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize * 24;
        gp.monster[i].worldY = gp.tileSize * 37;
        i++;
        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize * 34;
        gp.monster[i].worldY = gp.tileSize * 42;
        i++;
        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize * 38;
        gp.monster[i].worldY = gp.tileSize * 42;
        i++;

    }
}
