import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;

public class Character {
    protected Image idle1, idle2, idle3, jab, straight, lowKick, highKick;
    protected Image currentImage;
    protected int x, y, originalX;
    protected boolean isAttacking;
    protected int moveAmount, moveDirection;
    protected Timer skillTimer;
    protected Timer idleTimer;
    protected Random random;
    protected int attackDistance; // 공격 시 이동한 거리
    protected int health; // 체력 추가
    protected int panelWidth; // 패널의 너비

    public Character(int startX, int startY, String prefix, int panelWidth) {
        this.idle1 = new ImageIcon("assets/" + prefix + "_idle1.png").getImage();
        this.idle2 = new ImageIcon("assets/" + prefix + "_idle2.png").getImage();
        this.idle3 = new ImageIcon("assets/" + prefix + "_idle3.png").getImage();
        this.jab = new ImageIcon("assets/" + prefix + "_jab.png").getImage();
        this.straight = new ImageIcon("assets/" + prefix + "_straight.png").getImage();
        this.lowKick = new ImageIcon("assets/" + prefix + "_lowkick.png").getImage();
        this.highKick = new ImageIcon("assets/" + prefix + "_highkick.png").getImage();
        this.currentImage = idle1;
        this.x = startX;
        this.y = startY;
        this.originalX = startX;
        this.isAttacking = false;
        this.moveAmount = 0;
        this.moveDirection = 0;
        this.random = new Random();
        this.attackDistance = 0; // 초기화
        this.health = 100; // 체력 초기화
        this.panelWidth = panelWidth; // 패널의 너비 설정

        this.skillTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isAttacking = false;
                moveAmount = attackDistance; // 이동한 거리만큼 되돌아감
                moveDirection = -moveDirection; // 반대 방향으로 이동
                attackDistance = 0; // 초기화
                skillTimer.stop();
            }
        });

        this.idleTimer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAttacking) {
                    switch (random.nextInt(3)) {
                        case 0:
                            currentImage = idle1;
                            break;
                        case 1:
                            currentImage = idle2;
                            break;
                        case 2:
                            currentImage = idle3;
                            break;
                    }
                }
            }
        });
        this.idleTimer.start();
    }

    public void draw(Graphics g) {
        g.drawImage(currentImage, x, y, null);
    }

    public void moveLeft() {
        if (x - 5 >= 0) { // 화면의 왼쪽 경계를 벗어나지 않도록
            x -= 5;
        }
    }

    public void moveRight() {
        if (x + currentImage.getWidth(null) + 5 <= panelWidth) { // 화면의 오른쪽 경계를 벗어나지 않도록
            x += 5;
        }
    }

    public void jab() {
        currentImage = jab;
        moveDirection = 4;
        moveAmount = 100;
        attackDistance = moveAmount; // 이동한 거리 저장
        isAttacking = true;
        startSkillTimer();
    }

    public void straight() {
        currentImage = straight;
        moveDirection = 4;
        moveAmount = 100;
        attackDistance = moveAmount; // 이동한 거리 저장
        isAttacking = true;
        startSkillTimer();
    }

    public void lowKick() {
        currentImage = lowKick;
        moveDirection = 2;
        moveAmount = 50;
        attackDistance = moveAmount; // 이동한 거리 저장
        isAttacking = true;
        startSkillTimer();
    }

    public void highKick() {
        currentImage = highKick;
        moveDirection = 2;
        moveAmount = 50;
        attackDistance = moveAmount; // 이동한 거리 저장
        isAttacking = true;
        startSkillTimer();
    }

    public void startSkillTimer() {
        if (!skillTimer.isRunning()) {
            skillTimer.start();
        } else {
            skillTimer.restart();
        }
    }

    public void update() {
        if (moveAmount > 0) {
            if (x + moveDirection >= 0 && x + currentImage.getWidth(null) + moveDirection <= panelWidth) {
                x += moveDirection;
                moveAmount -= Math.abs(moveDirection);
            } else {
                moveAmount = 0;
            }
        }
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }

    public void resetPosition() {
        x = originalX;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
    }

    // 충돌 감지 메서드 추가
    public boolean isCollidingWith(Character other) {
        return this.x < other.x + other.currentImage.getWidth(null) &&
               this.x + this.currentImage.getWidth(null) > other.x &&
               this.y < other.y + other.currentImage.getHeight(null) &&
               this.y + this.currentImage.getHeight(null) > other.y;
    }
}
