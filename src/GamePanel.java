import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Font;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Character player;
    private Enemy enemy;
    private Timer timer;

    public GamePanel() {
        this.player = new Character(100, 400, "stickman", 800); // 패널의 너비를 전달
        this.enemy = new Enemy(600, 400, "stickman", 800); // 적도 동일하게 설정
        this.timer = new Timer(20, this); // 20 milliseconds to update movement and idle image
        this.timer.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        player.draw(g);
        enemy.draw(g);

        // 플레이어 체력 바
        g.setColor(Color.RED);
        g.fillRect(50, 50, player.getHealth() * 2, 20); // 플레이어 체력 바
        g.setColor(Color.RED);
        g.fillRect(550, 50, enemy.getHealth() * 2, 20); // 적 체력 바

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Player Health: " + player.getHealth(), 50, 45);
        g.drawString("Enemy Health: " + enemy.getHealth(), 550, 45);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        player.update();
        enemy.update();
        checkCollisions(); // 충돌 체크
        repaint();
    }

    private void checkCollisions() {
        if (player.isAttacking() && player.isCollidingWith(enemy)) {
            enemy.takeDamage(getAttackDamage(player));
        }
        if (enemy.isAttacking() && enemy.isCollidingWith(player)) {
            player.takeDamage(getAttackDamage(enemy));
        }
    }

    private int getAttackDamage(Character character) {
        if (character.currentImage == character.jab) {
            return 10;
        } else if (character.currentImage == character.straight) {
            return 15;
        } else if (character.currentImage == character.lowKick) {
            return 8;
        } else if (character.currentImage == character.highKick) {
            return 12;
        }
        return 0;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_LEFT) {
            player.moveLeft();
        } else if (code == KeyEvent.VK_RIGHT) {
            player.moveRight();
        } else if (code == KeyEvent.VK_A) {
            player.jab();
        } else if (code == KeyEvent.VK_S) {
            player.straight(); 
        } else if (code == KeyEvent.VK_Z) {
            player.lowKick();
        } else if (code == KeyEvent.VK_X) {
            player.highKick();
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
}
