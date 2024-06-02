import java.awt.Graphics;
import java.util.Random;

public class Enemy extends Character {
    private Random random;

    public Enemy(int startX, int startY, String prefix) {
        super(startX, startY, prefix);
        this.random = new Random();
    }

    @Override
    public void update() {
        super.update();
        if (!isAttacking()) {
            int action = random.nextInt(100);
            if (action < 5) {
                moveLeft();
            } else if (action < 10) {
                moveRight();
            } else if (action < 15) {
                jab();
            } else if (action < 20) {
                straight();
            } else if (action < 25) {
                lowKick();
            } else if (action < 30) {
                highKick();
            }
        }
    }

    public void startSkillTimer() {
        super.startSkillTimer();
    }

    @Override
    public void jab() {
        currentImage = jab;
        moveDirection = -4; // 왼쪽으로 이동
        moveAmount = 100;
        isAttacking = true;
        startSkillTimer();
    }

    @Override
    public void straight() {
        currentImage = straight;
        moveDirection = -4; // 왼쪽으로 이동
        moveAmount = 100;
        isAttacking = true;
        startSkillTimer();
    }

    @Override
    public void lowKick() {
        currentImage = lowKick;
        moveDirection = -2; // 왼쪽으로 이동
        moveAmount = 50;
        isAttacking = true;
        startSkillTimer();
    }

    @Override
    public void highKick() {
        currentImage = highKick;
        moveDirection = -2; // 왼쪽으로 이동
        moveAmount = 50;
        isAttacking = true;
        startSkillTimer();
    }
}
