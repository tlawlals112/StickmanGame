import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Character player;
    private Enemy enemy;
    private Timer timer;
    private boolean gameOver; // 게임 종료 여부를 나타내는 변수
    private JButton backButton; // 게임이 종료된 후에 메인 메뉴로 돌아가는 버튼
    private SoundPlayer bgmPlayer; 

    public GamePanel() {
        this.player = new Character(100, 400, "stickman", 800); // 패널의 너비를 전달
        this.enemy = new Enemy(600, 400, "stickman", 800); // 적도 동일하게 설정
        this.timer = new Timer(20, this); // 20 milliseconds to update movement and idle image
        this.timer.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        this.gameOver = false; // 게임 종료 변수 초기화
        
        this.bgmPlayer = new SoundPlayer("musics/InGame.wav"); // 배경음악 파일 경로 지정
        this.bgmPlayer.play(); // 배경음악 재생

        // "Back to Main Menu" 버튼 생성 및 초기화
        backButton = new JButton("Back to Main Menu");
        backButton.setBounds(300, 500, 200, 50); // 버튼 위치와 크기 설정
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnToMainMenu(); // 메인 메뉴로 돌아가는 메서드 호출
            }
        });
        add(backButton); // 패널에 버튼 추가
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!gameOver) { // 게임이 종료되지 않은 경우에만 캐릭터 및 체력 바를 그립니다.
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
        } else {
            // 게임 종료 시 "Game Over" 이미지를 표시합니다.
            ImageIcon gameOverImage = new ImageIcon("assets/game_over.png");
            g.drawImage(gameOverImage.getImage(), 300, 200, null);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) { // 게임이 종료되지 않은 경우에만 업데이트합니다.
            player.update();
            enemy.update();
            checkCollisions(); // 충돌 체크
            repaint();
        }
    }

    private void checkCollisions() {
        if (player.isAttacking() && player.isCollidingWith(enemy)) {
            enemy.takeDamage(getAttackDamage(player));
        }
        if (enemy.isAttacking() && enemy.isCollidingWith(player)) {
            player.takeDamage(getAttackDamage(enemy));
        }

        // 플레이어 또는 적의 체력이 0 이하인 경우 게임 종료 처리를 합니다.
        if (player.getHealth() <= 0 || enemy.getHealth() <= 0) {
            gameOver = true; // 게임 종료 상태로 설정
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
        // 게임 종료 상태에서는 키 입력을 무시합니다.
        if (!gameOver) {
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
    }

    // 게임 종료 후 메인 메뉴로 돌아가는 메서드
    private void returnToMainMenu() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this); // 현재 패널의 부모 프레임 가져오기
        frame.getContentPane().removeAll(); // 기존의 컴포넌트 제거
        frame.add(new MainMenu(frame)); // 새로운 MainMenu 패널 추가
        frame.repaint(); // 프레임 다시 그리기
        frame.revalidate(); // 프레임 다시 유효화
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
