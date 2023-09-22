package Entity;

import Main.GamePanel;
import Main.KeyboardHandler;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{
    KeyboardHandler KeyboardHandler;
    public final int screenX;
    public final int screenY;
    private boolean incrementing = true;
    private int attackCounter = 0;

    public Player (GamePanel gp, KeyboardHandler KeyboardHandler) {
        super(gp);
        this.KeyboardHandler = KeyboardHandler;

        screenX = gp.screenWidth / 2 - gp.tileSize / 2;
        screenY = gp.screenHeight / 2 - gp.tileSize / 2;

        solidArea = new Rectangle();
        solidArea.x = 16;
        solidArea.y = 40;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 64;
        solidArea.height = 60;

        // Modify the solid area to make it smaller
        solidArea.x += 10;
        solidArea.y += 10;
        solidArea.width -= 5;
        solidArea.height -= 10;


        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 5;
        direction = "down";
        spriteNum = 2;

        // Character status
        maxLife = 6;
        life = maxLife;
    }

    public void getPlayerImage() {
        if (gp.isMale) {
            // Male Player
            up1 = setup("/Res/player/Male_back_left", gp.tileSize, gp.tileSize);
            up2 = setup("/Res/player/Male_back_stand", gp.tileSize, gp.tileSize);
            up3 = setup("/Res/player/Male_back_right", gp.tileSize, gp.tileSize);
            down1 = setup("/Res/player/Male_forward_left", gp.tileSize, gp.tileSize);
            down2 = setup("/Res/player/Male_stand", gp.tileSize, gp.tileSize);
            down3 = setup("/Res/player/Male_forward_right", gp.tileSize, gp.tileSize);
            left1 = setup("/Res/player/Male_left_handforward", gp.tileSize, gp.tileSize);
            left2 = setup("/Res/player/Male_left_stand", gp.tileSize, gp.tileSize);
            left3 = setup("/Res/player/Male_left_handback", gp.tileSize, gp.tileSize);
            right1 = setup("/Res/player/Male_right_handback", gp.tileSize, gp.tileSize);
            right2 = setup("/Res/player/Male_right_stand", gp.tileSize, gp.tileSize);
            right3 = setup("/Res/player/Male_right_handforward", gp.tileSize, gp.tileSize);
        } else {
            // Female Player
            up1 = setup("/Res/player/Female_back_left", gp.tileSize, gp.tileSize);
            up2 = setup("/Res/player/Female_back_stand", gp.tileSize, gp.tileSize);
            up3 = setup("/Res/player/Female_back_right", gp.tileSize, gp.tileSize);
            down1 = setup("/Res/player/Female_forward_left", gp.tileSize, gp.tileSize);
            down2 = setup("/Res/player/Female_stand", gp.tileSize, gp.tileSize);
            down3 = setup("/Res/player/Female_forward_right", gp.tileSize, gp.tileSize);
            left1 = setup("/Res/player/Female_left_handforward", gp.tileSize, gp.tileSize);
            left2 = setup("/Res/player/Female_left_stand", gp.tileSize, gp.tileSize);
            left3 = setup("/Res/player/Female_left_handback", gp.tileSize, gp.tileSize);
            right1 = setup("/Res/player/Female_right_handback", gp.tileSize, gp.tileSize);
            right2 = setup("/Res/player/Female_right_stand", gp.tileSize, gp.tileSize);
            right3 = setup("/Res/player/Female_right_handforward", gp.tileSize, gp.tileSize);
        }
    }

    public void getPlayerAttackImage() {
        // Male Player Attack
        attackUp1 = setup("/Res/player/Male_attack_up1", gp.tileSize, gp.tileSize * 2);
        attackUp2 = setup("/Res/player/Male_attack_up2", gp.tileSize, gp.tileSize * 2);
        attackDown1 = setup("/Res/player/Male_attack_down1", gp.tileSize, gp.tileSize * 2);
        attackDown2 = setup("/Res/player/Male_attack_down2", gp.tileSize, gp.tileSize * 2);
        attackLeft1 = setup("/Res/player/Male_attack_left1", gp.tileSize * 2, gp.tileSize);
        attackLeft2 = setup("/Res/player/Male_attack_left2", gp.tileSize * 2, gp.tileSize);
        attackRight1 = setup("/Res/player/Male_attack_right1", gp.tileSize * 2, gp.tileSize);
        attackRight2 = setup("/Res/player/Male_attack_right2", gp.tileSize * 2, gp.tileSize);

        // Female Player Attack
//        attackUp1 = setup("/Res/player/Female_attack_up1", gp.tileSize, gp.tileSize * 2);
//        attackUp2 = setup("/Res/player/Female_attack_up1", gp.tileSize, gp.tileSize * 2);
//        attackDown1 = setup("/Res/player/Female_attack_down1", gp.tileSize, gp.tileSize * 2);
//        attackDown2 = setup("/Res/player/Female_attack_down2", gp.tileSize, gp.tileSize * 2);
//        attackLeft1 = setup("/Res/player/Female_attack_left1", gp.tileSize * 2, gp.tileSize);
//        attackLeft2 = setup("/Res/player/Female_attack_left2", gp.tileSize * 2, gp.tileSize);
//        attackRight1 = setup("/Res/player/Female_attack_right1", gp.tileSize * 2, gp.tileSize);
//        attackRight2 = setup("/Res/player/Female_attack_right2", gp.tileSize * 2, gp.tileSize);
    }

    public void update() {
        System.out.println("Entering update method.");

        if (attacking) {
            System.out.println("Attacking method is called.");
            attacking();
        }

        if (KeyboardHandler.attackPressed) {
            attacking = true;
            System.out.println("Attack is triggered in update method.");
            attacking();
            KeyboardHandler.attackPressed = false;
            attackCounter = 0;

        } else if (KeyboardHandler.upPressed || KeyboardHandler.downPressed || KeyboardHandler.leftPressed || KeyboardHandler.rightPressed || KeyboardHandler.enterPressed) {
            if (KeyboardHandler.upPressed) {
                direction = "up";
            }
            if (KeyboardHandler.downPressed) {
                direction = "down";
            }
            if (KeyboardHandler.leftPressed) {
                direction = "left";
            }
            if (KeyboardHandler.rightPressed) {
                direction = "right";
            }

            // Check for collision
            collisionOn = false;
            gp.collisionChecker.checkTile(this);

            // Check for collision with objects
            int objIndex = gp.collisionChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // Check for collision with NPC
            int npcIndex = gp.collisionChecker.checkEntity(this, gp.npc);
            interactWithNpc(npcIndex);

            // Check for collision with monster
            int monsterIndex = gp.collisionChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            // Check event
            gp.eventHandler.checkEvent();

            // If collision is not detected, move the player
            if (!collisionOn && !KeyboardHandler.enterPressed) {

                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }
            gp.keyboardHandler.enterPressed = false;

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
        } else {
            spriteNum = 2;  // cease movement
        }

        // This needs to be outside of key if statement
        if(invincible){
            invincibleCounter++;
            if(invincibleCounter > 60){
                invincible = false;
                invincibleCounter = 0;
            }
        }
        System.out.println("Exiting update method.");

    }

    public void attacking() {
        System.out.println("Attacking method is called.");
        System.out.println("AttackCounter value: " + attackCounter);


        attackCounter += 5;
        if (attackCounter <= 5) {
            spriteNum = 1;
        }
        if (attackCounter > 5 && attackCounter <= 25) {
            spriteNum = 2;
        }
        if (attackCounter > 25) {
            spriteNum = 1;
            attackCounter = 0;
            attacking = false;
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {

        }
    }

    public void interactWithNpc(int i) {
        if (gp.keyboardHandler.enterPressed) {
            if (i != 999) {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
//            else {
//                attacking = true;
//            }
        }
    }

    public void contactMonster(int i) {
        if (i != 999) {
            if (!invincible) {
                life -= 1;
                invincible = true;

            }
        }
    }

    public void draw(Graphics2D g2) {
        System.out.println("Draw method is called.");

        BufferedImage image = null;

        int tempScreenX = screenX;
        int tempScreenY = screenY;

        System.out.println("Direction: " + direction);
        System.out.println("Attacking: " + attacking);
        System.out.println("SpriteNum: " + spriteNum);

        switch (direction) {
            case "up":
                if (attacking) {
                    System.out.println("Drawing attack animation for UP direction.");
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) {image = attackUp1;}
                    else if (spriteNum == 2) {image = attackUp2;}
                } else {
                    System.out.println("Drawing walk animation for UP direction.");
                    if (spriteNum == 1) {image = up1;}
                    else if (spriteNum == 2) {image = up2;}
                    else if (spriteNum == 3) {image = up3;}
                }
                break;
            case "down":
                if (attacking) {
                    System.out.println("Drawing attack animation for DOWN direction.");
                    if (spriteNum == 1) {image = attackDown1;}
                    else if (spriteNum == 2) {image = attackDown2;}
                } else {
                    System.out.println("Drawing walk animation for DOWN direction.");
                    if (spriteNum == 1) {image = down1;}
                    else if (spriteNum == 2) {image = down2;}
                    else if (spriteNum == 3) {image = down3;}
                }
                break;
            case "left":
                if (attacking) {
                    System.out.println("Drawing attack animation for LEFT direction.");
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) {image = attackLeft1;}
                    else if (spriteNum == 2) {image = attackLeft2;}
                } else {
                    System.out.println("Drawing walk animation for LEFT direction.");
                    if (spriteNum == 1) {image = left1;}
                    else if (spriteNum == 2) {image = left2;}
                    else if (spriteNum == 3) {image = left3;}
                }
                break;
            case "right":
                if (attacking) {
                    System.out.println("Drawing attack animation for RIGHT direction.");
                    if (spriteNum == 1) {image = attackRight1;}
                    else if (spriteNum == 2) {image = attackRight2;}
                } else {
                    System.out.println("Drawing walk animation for RIGHT direction.");
                    if (spriteNum == 1) {image = right1;}
                    else if (spriteNum == 2) {image = right2;}
                    else if (spriteNum == 3) {image = right3;}
                }
                break;
        }

        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);

        // Reset AlphaComposite
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }


}
