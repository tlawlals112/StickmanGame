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
        this.player = new Character(100, 400, "stickman");
        this.enemy = new Enemy(600, 400, "stickman"); // 적도 동일한 stickman 이미지를 사용하도록 설정
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
        // 체력 표시 추가
        drawHealthBars(g);
    }

    private void drawHealthBars(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(50, 50, player.getHealth() * 2, 20); // 플레이어 체력 바
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
        repaint();
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
            enemy.takeDamage(10); // 적 체력 감소
        } else if (code == KeyEvent.VK_S) {
            player.straight();
            enemy.takeDamage(15); // 적 체력 감소
        } else if (code == KeyEvent.VK_Z) {
            player.lowKick();
            enemy.takeDamage(8); // 적 체력 감소
        } else if (code == KeyEvent.VK_X) {
            player.highKick();
            enemy.takeDamage(12); // 적 체력 감소
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
