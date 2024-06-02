import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Image stickmanIdle1;
    private Image stickmanIdle2;
    private Image stickmanIdle3;
    private Image stickmanJab;
    private Image stickmanStraight;
    private Image stickmanLowKick;
    private Image stickmanHighKick;
    private Image currentImage;
    private int stickmanX, stickmanY;
    private int originalX;
    private Timer timer;
    private boolean isAttacking;
    private int idleIndex;
    private Timer skillTimer;
    private int moveAmount;
    private int moveDirection;
    private Random random;

    public GamePanel() {
        this.stickmanIdle1 = new ImageIcon("assets/stickman_idle1.png").getImage();
        this.stickmanIdle2 = new ImageIcon("assets/stickman_idle2.png").getImage();
        this.stickmanIdle3 = new ImageIcon("assets/stickman_idle3.png").getImage();
        this.stickmanJab = new ImageIcon("assets/stickman_jab.png").getImage();
        this.stickmanStraight = new ImageIcon("assets/stickman_straight.png").getImage();
        this.stickmanLowKick = new ImageIcon("assets/stickman_lowkick.png").getImage();
        this.stickmanHighKick = new ImageIcon("assets/stickman_highkick.png").getImage();
        this.currentImage = stickmanIdle1;
        this.stickmanX = 100;
        this.stickmanY = 400;
        this.originalX = stickmanX;
        this.isAttacking = false;
        this.idleIndex = 0;
        this.moveAmount = 0;
        this.moveDirection = 0;
        this.random = new Random();
        this.timer = new Timer(20, this); // 20 milliseconds to update movement and idle image
        this.timer.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        // Timer for skill effect duration
        this.skillTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Reset to idle state after 1 second
                isAttacking = false;
                moveAmount = 0;
                stickmanX = originalX;
                currentImage = stickmanIdle1;
                skillTimer.stop(); // Stop the timer after skill effect duration
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(currentImage, stickmanX, stickmanY, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Toggle between idle images if no action is being performed
        if (!isAttacking) {
            int rand = random.nextInt(3);
            switch (rand) {
                case 0:
                    currentImage = stickmanIdle1;
                    break;
                case 1:
                    currentImage = stickmanIdle2;
                    break;
                case 2:
                    currentImage = stickmanIdle3;
                    break;
            }
        }
        
        // Move the stickman if moveAmount is not zero
        if (moveAmount > 0) {
            stickmanX += moveDirection;
            moveAmount -= Math.abs(moveDirection);
        }
        
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        isAttacking = true;
        originalX = stickmanX;
        if (code == KeyEvent.VK_LEFT) {
            moveDirection = -2; // Increased speed for smoother movement
            moveAmount = 25;
        } else if (code == KeyEvent.VK_RIGHT) {
            moveDirection = 2; // Increased speed for smoother movement
            moveAmount = 25;
        } else if (code == KeyEvent.VK_A) {
            currentImage = stickmanJab;
            moveDirection = 4; // Move forward for jab
            moveAmount = 100; // Move forward for jab
            startSkillTimer(); // Start the skill timer when using skill
        } else if (code == KeyEvent.VK_S) {
            currentImage = stickmanStraight;
            moveDirection = 4; // Move forward for straight
            moveAmount = 100; // Move forward for straight
            startSkillTimer(); // Start the skill timer when using skill
        } else if (code == KeyEvent.VK_Z) {
            currentImage = stickmanLowKick;
            moveDirection = 2; // Move forward for low kick
            moveAmount = 50; // Move forward for low kick
            startSkillTimer(); // Start the skill timer when using skill
        } else if (code == KeyEvent.VK_X) {
            currentImage = stickmanHighKick;
            moveDirection = 2; // Move forward for high kick
            moveAmount = 50; // Move forward for high kick
            startSkillTimer(); // Start the skill timer when using skill
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // No need to change anything in keyReleased method
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No need to change anything in keyTyped method
    }

    private void startSkillTimer() {
        // Start the skill timer if it's not already running
        if (!skillTimer.isRunning()) {
            skillTimer.start();
        } else {
            // Restart the skill timer if it's already running
            skillTimer.restart();
        }
    }
}
