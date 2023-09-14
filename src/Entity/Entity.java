package Entity;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Entity {
    GamePanel gp;
    public int worldX, worldY;
    public int speed;

    public BufferedImage up1, up2, up3, down1, down2, down3, left1, left2, left3, right1, right2, right3;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    private boolean incrementing = true;
    public int actionLockCounter = 0;
    String[] dialogues = new String[20];
    int dialogueIndex = 0;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() {}
    public void speak() {}
    public void update() {

        setAction();

        collisionOn = false;
        gp.collisionChecker.checkTile(this);
        gp.collisionChecker.checkObject(this, false);
        gp.collisionChecker.checkPlayer(this);

        // If collision is not detected, move the NPC
        if (!collisionOn) {

            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }

        // Animate the player
        spriteCounter++;
        if (spriteCounter > 15) {
            if (incrementing) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 3;
                } else if (spriteNum == 3) {
                    spriteNum = 2;
                    incrementing = false;  // Start decrementing
                }
            } else {
                if (spriteNum == 2) {
                    spriteNum = 1;
                    incrementing = true;  // Start incrementing again
                }
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            switch (direction) {
                case "up" -> {
                    if (spriteNum == 1) {
                        image = up1;
                    } else if (spriteNum == 2) {
                        image = up2;
                    } else if (spriteNum == 3) {
                        image = up3;
                    }
                }
                case "down" -> {
                    if (spriteNum == 1) {
                        image = down1;
                    } else if (spriteNum == 2) {
                        image = down2;
                    } else if (spriteNum == 3) {
                        image = down3;
                    }
                }
                case "left" -> {
                    if (spriteNum == 1) {
                        image = left1;
                    } else if (spriteNum == 2) {
                        image = left2;
                    } else if (spriteNum == 3) {
                        image = left3;
                    }
                }
                case "right" -> {
                    if (spriteNum == 1) {
                        image = right1;
                    } else if (spriteNum == 2) {
                        image = right2;
                    } else if (spriteNum == 3) {
                        image = right3;
                    }
                }
            }

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }

    }

    public BufferedImage setup(String imagePath) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
