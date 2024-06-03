import java.awt.Graphics;
import java.util.Random;

public class Enemy extends Character {
    private Random random;

    public Enemy(int startX, int startY, String prefix) {
        super(startX, startY, "e_" + prefix); // 이미지 파일명에 "e_"를 추가
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

    @Override
    public void jab() {
        currentImage = jab;
        moveDirection = -4; // 왼쪽으로 이동
        moveAmount = 100;
        attackDistance = moveAmount; // 이동한 거리 저장
        isAttacking = true;
        startSkillTimer();
        // 체력 감소 로직 추가
        takeDamage(10);
    }

    @Override
    public void straight() {
        currentImage = straight;
        moveDirection = -4; // 왼쪽으로 이동
        moveAmount = 100;
        attackDistance = moveAmount; // 이동한 거리 저장
        isAttacking = true;
        startSkillTimer();
        // 체력 감소 로직 추가
        takeDamage(15);
    }

    @Override
    public void lowKick() {
        currentImage = lowKick;
        moveDirection = -2; // 왼쪽으로 이동
        moveAmount = 50;
        attackDistance = moveAmount; // 이동한 거리 저장
        isAttacking = true;
        startSkillTimer();
        // 체력 감소 로직 추가
        takeDamage(8);
    }

    @Override
    public void highKick() {
        currentImage = highKick;
        moveDirection = -2; // 왼쪽으로 이동
        moveAmount = 50;
        attackDistance = moveAmount; // 이동한 거리 저장
        isAttacking = true;
        startSkillTimer();
        // 체력 감소 로직 추가
        takeDamage(12);
    }
}
