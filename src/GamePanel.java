import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

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
