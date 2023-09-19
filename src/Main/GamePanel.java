package Main;

import Entity.Entity;
import Entity.Player;
import Entity.NPC_Tigger;
import tile.TileManager;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable{

    // Configurations of display window
    final int originalTileSize = 32; // 32x32 pixels
    final int scale= 3; // 3x scale
    public final int tileSize = originalTileSize * scale; // 96x96 pixels
    public final int maxScreenColumns = 16; // 16 tiles wide
    public final int maxScreenRows = 9; // 9 tiles tall
    public final int screenWidth = tileSize * maxScreenColumns; // 1536 pixels wide
    public final int screenHeight = tileSize * maxScreenRows; // 864 pixels tall

    // World Settings
    public final int maxWorldColumns = 50;
    public final int maxWorldRows = 50;
    int FPS = 60; // Frames per second

    // System Settings
    TileManager tileManager = new TileManager(this);
    public KeyboardHandler keyboardHandler = new KeyboardHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eventHandler = new EventHandler(this);
    Thread gameThread;

    // Entities & Objects
    public Player player = new Player(this, keyboardHandler);
    public Entity[] obj = new Entity[10];
    public Entity[] npc = new Entity[10];
    ArrayList<Entity> entityList = new ArrayList<>();

    // Game State
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;

    // Title image
    public NPC_Tigger npcTigger;

    // Customizing the gender of the role
    public boolean isMale = true;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyboardHandler);
        this.setFocusable(true);
        npcTigger = new NPC_Tigger(this);
    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
//        playMusic(0);
        gameState = titleState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
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
                // System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }

        }
    }

    public void update() {

        if (gameState == playState) {

            // Update the player
            player.update();

            // Update the NPC
            for (Entity entity : npc) {
                if (entity != null) {
                    entity.update();
                }
            }
        }
        if (gameState == pauseState) {
            // Do nothing
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2= (Graphics2D) g;

        // Debug
        long drawStart = 0;
        if (keyboardHandler.checkDrawTime) {
            drawStart = System.nanoTime();
        }

        // Title screen
        if (gameState == titleState) {
            ui.draw(g2);
        }
        // Others
        else {
            // Tile
            tileManager.draw(g2);
            // Add entities to the list
            entityList.add(player);
            for (Entity entity : npc) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            for (Entity entity : obj) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            // Sort
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    return Integer.compare(e1.worldY, e2.worldY);
                }
            });
            // Draw entities
            for (Entity entity : entityList) {
                entity.draw(g2);
            }
            // Empty entity list
            for (int i = 0; i < entityList.size(); i++) {
                entityList.remove(i);
            }
            // UI
            ui.draw(g2);
        }

        // Debug
        if (keyboardHandler.checkDrawTime) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.WHITE);
            g2.drawString("Draw Time: " + passed, 10, 400);
            System.out.println("Draw Time: " + passed);
        }

        g2.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }

}
