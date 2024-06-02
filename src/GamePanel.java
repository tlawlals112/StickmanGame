import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.Timer;

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
    private Timer timer;
    private boolean isAttacking;
    private int idleIndex;

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
        this.isAttacking = false;
        this.idleIndex = 0;
        this.timer = new Timer(500, this); // 500 milliseconds to toggle idle state
        this.timer.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
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
            switch (idleIndex) {
                case 0:
                    currentImage = stickmanIdle1;
                    idleIndex = 1;
                    break;
                case 1:
                    currentImage = stickmanIdle2;
                    idleIndex = 2;
                    break;
                case 2:
                    currentImage = stickmanIdle3;
                    idleIndex = 0;
                    break;
            }
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        isAttacking = true;
        if (code == KeyEvent.VK_LEFT) {
            stickmanX -= 5;
        } else if (code == KeyEvent.VK_RIGHT) {
            stickmanX += 5;
        } else if (code == KeyEvent.VK_A) {
            currentImage = stickmanJab;
        } else if (code == KeyEvent.VK_S) {
            currentImage = stickmanStraight;
        } else if (code == KeyEvent.VK_Z) {
            currentImage = stickmanLowKick;
        } else if (code == KeyEvent.VK_X) {
            currentImage = stickmanHighKick;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_S || code == KeyEvent.VK_Z || code == KeyEvent.VK_X) {
            isAttacking = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
