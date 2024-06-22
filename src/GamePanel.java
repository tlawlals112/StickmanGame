import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Character player;
    private Enemy enemy;
    private Timer timer;
    private boolean gameOver;
    private JButton backButton;
    private static SoundPlayer bgmPlayer; // static으로 변경
    private boolean playMusic; // 배경음악 On/Off 상태를 저장할 변수 추가
    private int timeElapsed; // 경과 시간을 저장할 변수 추가
    private Timer gameTimer; // 게임 타이머 변수 추가

    public GamePanel(boolean playMusic) { // 배경음악 On/Off 상태를 매개변수로 받는 생성자 추가
        this.player = new Character(100, 400, "stickman", 800);
        this.enemy = new Enemy(600, 400, "stickman", 800);
        this.timer = new Timer(20, this);
        this.timer.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        this.gameOver = false;
        this.playMusic = playMusic; // 전달받은 배경음악 On/Off 상태 저장
        this.timeElapsed = 0; // 경과 시간 초기화

        // 게임 타이머 초기화 (1000ms 간격으로 업데이트)
        this.gameTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTimer();
            }
        });
        this.gameTimer.start(); // 게임 타이머 시작

        // 배경음악 재생 설정
        bgmPlayer = new SoundPlayer("musics/InGame.wav");
        if (playMusic) {
            bgmPlayer.play(); // 배경음악 On 상태인 경우 재생
        }

        // "Back to Main Menu" 버튼 생성 및 초기화
        backButton = new JButton("Back to Main Menu");
        backButton.setBounds(300, 500, 200, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnToMainMenu();
            }
        });
        add(backButton);
    }

    // 경과 시간을 업데이트하는 메서드
    private void updateTimer() {
        timeElapsed++;
        repaint(); // 화면 갱신
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

            // 경과 시간을 화면에 그리기
            g.drawString("Time Elapsed: " + timeElapsed + " seconds", 10, 20);
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
            gameTimer.stop(); // 게임 종료 시 타이머 정지
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
        bgmPlayer.stop(); // 게임 패널에서 메인 메뉴로 돌아갈 때 배경 음악 정지
        gameTimer.stop(); // 메인 메뉴로 돌아갈 때 타이머 정지

        JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        currentFrame.dispose();
        JFrame mainMenuFrame = new JFrame("Main Menu");
        mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenuFrame.setSize(400, 300);
        mainMenuFrame.setResizable(false);
        mainMenuFrame.setLocationRelativeTo(null);
        mainMenuFrame.add(new MainMenu(mainMenuFrame));
        mainMenuFrame.setVisible(true);
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
