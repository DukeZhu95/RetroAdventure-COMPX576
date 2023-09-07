package Main;

import Entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    // Configurations of display window
    final int originalTileSize = 32; // 32x32 pixels
    final int scale= 3; // 3x scale
    public final int tileSize = originalTileSize * scale; // 96x96 pixels
    public final int maxScreenColumns = 16; // 16 tiles wide
    public final int maxScreenRows = 9; // 9 tiles tall
    public final int ScreenWidth = tileSize * maxScreenColumns; // 1536 pixels wide
    public final int ScreenHeight = tileSize * maxScreenRows; // 864 pixels tall

    // World Settings
    public final int maxWorldColumns = 50;
    public final int maxWorldRows = 50;
    int FPS = 60; // Frames per second

    // System Settings
    TileManager tileManager = new TileManager(this);
    KeyboardHandler keyboardHandler = new KeyboardHandler();
    Sound sound = new Sound();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    Thread gameThread;

    // Entities & Objects
    public Player player = new Player(this, keyboardHandler);
    public SuperObject[] obj = new SuperObject[10];

    public GamePanel() {
        this.setPreferredSize(new Dimension(ScreenWidth, ScreenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyboardHandler);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();
        playMusic(0);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
//    public void run() {
//        double drawInterval = 1000000000.0 / FPS; // 0.01666 seconds
//        double nextDrawTime = System.nanoTime() + drawInterval;
//
//        while (gameThread != null) {
//
//            update();
//
//            repaint();
//
//            try {
//                double remainingTime = nextDrawTime - System.nanoTime();
//                remainingTime = remainingTime / 1000000; // Convert to milliseconds
//
//                if (remainingTime < 0) {
//                    remainingTime = 0;
//                }
//
//                Thread.sleep((long) remainingTime);
//
//                nextDrawTime += drawInterval;
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }

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

        player.update();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2= (Graphics2D) g;

        // Tile
        tileManager.draw(g2);

        // Object
        for (SuperObject superObject : obj) {
            if (superObject != null) {
                superObject.draw(g2, this);
            }
        }

        // Player
        player.draw(g2);

        g2.dispose();
    }

    public void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic() {
        sound.stop();
    }

    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }

}
