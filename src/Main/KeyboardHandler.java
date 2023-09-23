package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardHandler implements KeyListener {

    GamePanel gp;

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, attackPressed;

    // Debug
    boolean checkDrawTime = false;

    // Boolean variable for key pressed
    private boolean cPressed = false;
    private boolean spacePressed = false;

    public KeyboardHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // Title state
        if (gp.gameState == gp.titleState) {
            titleState(code);
        }

        // Play state
        if (gp.gameState == gp.playState) {
            playState(code);

            // Handle 'C' key press for character state
            if (code == KeyEvent.VK_C && !cPressed) {
                cPressed = true;
                gp.gameState = gp.characterState;
            }

            // Handle 'Space' key press for pause state
            if (code == KeyEvent.VK_SPACE && !spacePressed) {
                spacePressed = true;
                gp.gameState = gp.pauseState;
            }
        }

        // Pause state
        if (gp.gameState == gp.pauseState) {
            pauseState(code);

            // Handle 'Space' key press to return to play state
            if (code == KeyEvent.VK_SPACE && !spacePressed) {
                spacePressed = true;
                gp.gameState = gp.playState;
            }
        }

        // Dialogue state
        if (gp.gameState == gp.dialogueState) {
            dialogueState(code);
        }

        // Character state
        if (gp.gameState == gp.characterState) {
            characterState(code);

            // Handle 'C' key press to return to play state
            if (code == KeyEvent.VK_C && !cPressed) {
                cPressed = true;
                gp.gameState = gp.playState;
            }
        }
    }



    public void titleState(int code) {
        if (gp.ui.titleScreenState == 0) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    gp.ui.titleScreenState = 1;
                }
                if (gp.ui.commandNum == 1) {
                    // Implement load game later
                }
                if (gp.ui.commandNum == 2) {
                    System.exit(0);
                }
            }
        }
        else if (gp.ui.titleScreenState == 1) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    // You are a male player
                    gp.isMale = true;
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
                    gp.player.getPlayerImage();
                }
                if (gp.ui.commandNum == 1) {
                    // You are a female player
                    gp.isMale = false;
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
                    gp.player.getPlayerImage();
                }
                if (gp.ui.commandNum == 2) {
                    gp.ui.titleScreenState = 0;
                }
            }
        }

    }

    public void playState(int code) {
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_SPACE) {
            gp.gameState = gp.pauseState;
        }
        if (code == KeyEvent.VK_C) {
            gp.gameState = gp.characterState;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if (code == KeyEvent.VK_J) {
            attackPressed = true;
        }

        // Debug
        if (code == KeyEvent.VK_T) {
            checkDrawTime = !checkDrawTime;
        }
    }

    public void pauseState(int code) {
//        if (code == KeyEvent.VK_SPACE) {
//            gp.gameState = gp.playState;
//        }
    }

    public void dialogueState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            gp.gameState = gp.playState;
        }
    }

    public void characterState(int code) {
//        if (code == KeyEvent.VK_C) {
//            gp.gameState = gp.playState;
//        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_J) {
            attackPressed = false;
        }
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
        if (code == KeyEvent.VK_C) {
            cPressed = false;
        }

    }
}
