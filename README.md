# Reference
[1] https://github.com/pacampbell/Game "java 2D game library"

# 지원 Operating Systems 및 실행 방법

## 지원 Operating Systems
|OS| 지원 여부 |
|-----|--------|
|windows | :o:  |
| Linux  | :x: |
|MacOS  | :o:  |

## 실행 방법
### Windows, macOS

1. Windows에서는 명령프롬프트 or Shell, MacOs에서는 터미널을 킨다.
2. https://www.oracle.com/kr/java/technologies/javase/javase8-archive-downloads.html 에서 OS에 맞는 java jdk를 다운받는다.
3. github에서 파일을 다운받고 cmd or shell or 터미널에서 해당 폴더에 접속한다.
4. 해당 폴더 안에 있는 stickmanGame.jar 파일을 아래와 같은 명령어로 실행한다.
```
java -jar stickmanGame.jar
```

# 실행 예시
![stickman_ex](https://github.com/tlawlals112/oss_personal_project_phase1/assets/164158373/799945dc-c220-4882-a9df-eb56945fe1ce)


# 코드 설명
.
```
├── assets/
│   ├── e_stickman_idle1.png
│   ├── e_stickman_idle2.png
│   ├── e_stickman_idle3.png
│   ├── e_stickman_jab.png
│   ├── e_stickman_straight.png
│   ├── e_stickman_lowkick.png
│   ├── e_stickman_highkick.png
│   ├── stickman_idle1.png
│   ├── stickman_idle2.png
│   ├── stickman_idle3.png
│   ├── stickman_jab.png
│   ├── stickman_straight.png
│   ├── stickman_lowkick.png
│   ├── stickman_highkick.png
│   └── game_over.png
├── musics/
│   └── InGame.wav
├── Character.java
├── Enemy.java
├── Game.java
├── GamePanel.java
├── MainMenu.java
└── SoundPlayer.java
```
## Character 클래스
* Character 클래스는 플레이어와 적 캐릭터의 공통적인 동작과 속성을 정의합니다.

### 주요 필드
* Image idle1, idle2, idle3, jab, straight, lowKick, highKick: 캐릭터의 다양한 동작 이미지를 저장합니다.
* Image currentImage: 현재 화면에 표시될 이미지입니다.
* int x, y, originalX: 캐릭터의 현재 위치와 초기 위치를 저장합니다.
* boolean isAttacking: 캐릭터가 공격 중인지 여부를 나타냅니다.
* int moveAmount, moveDirection: 캐릭터의 이동 양과 방향을 저장합니다.
* Timer skillTimer, idleTimer: 캐릭터의 공격 및 대기 동작을 제어하는 타이머입니다.
* Random random: 대기 동작을 랜덤하게 선택하는데 사용됩니다.
* int attackDistance: 공격 시 이동한 거리를 저장합니다.
* int health: 캐릭터의 체력을 저장합니다.
* int panelWidth: 패널의 너비를 저장합니다.
* boolean invincible: 캐릭터가 무적 상태인지 여부를 나타냅니다.
* Timer invincibilityTimer: 무적 지속 시간을 측정하기 위한 타이머입니다.

### 주요 메서드
* Character(int startX, int startY, String prefix, int panelWidth): 캐릭터의 초기 위치와 이미지를 설정합니다.
* void draw(Graphics g): 현재 이미지를 화면에 그립니다.
* void moveLeft(), void moveRight(): 캐릭터를 좌우로 이동시킵니다.
* void jab(), void straight(), void lowKick(), void highKick(): 각각의 공격 기술을 실행합니다.
* void startSkillTimer(): 스킬 타이머를 시작합니다.
* void update(): 캐릭터의 상태를 업데이트합니다.
* boolean isCollidingWith(Character other): 다른 캐릭터와 충돌했는지 여부를 확인합니다.
* void takeDamage(int damage): 데미지를 받아 체력을 감소시킵니다.
* void setInvincible(): 캐릭터를 무적 상태로 설정합니다.
* boolean isInvincible(): 캐릭터가 무적 상태인지 여부를 반환합니다.

## Enemy 클래스
* Enemy 클래스는 Character 클래스를 상속받아 적 캐릭터의 동작을 정의합니다.

### 주요 메서드
* Enemy(int startX, int startY, String prefix, int panelWidth): 적 캐릭터의 초기 위치와 이미지를 설정합니다.
* void update(): 적 캐릭터의 상태를 업데이트하고, 랜덤하게 동작을 선택합니다.
* void jab(), void straight(), void lowKick(), void highKick(): 각각의 공격 기술을 실행하며, 이동 방향은 왼쪽으로 설정합니다.

## Game 클래스
* Game 클래스는 게임의 메인 진입점입니다.

### 주요 메서드
* public static void main(String[] args): 게임 창을 생성하고 GamePanel을 추가합니다.

## GamePanel 클래스
* GamePanel 클래스는 게임의 주요 로직을 처리하는 패널입니다.

### 주요 필드
* Character player: 플레이어 캐릭터를 저장합니다.
* Enemy enemy: 적 캐릭터를 저장합니다.
* Timer timer: 게임 루프를 제어하는 타이머입니다.
* boolean gameOver: 게임 종료 여부를 나타냅니다.
* JButton backButton: 게임 종료 후 메인 메뉴로 돌아가는 버튼입니다.
* SoundPlayer bgmPlayer: 배경 음악을 재생하는 객체입니다.

### 주요 메서드
* GamePanel(): 게임 패널을 초기화하고, 타이머와 키 리스너를 설정합니다.
* protected void paintComponent(Graphics g): 캐릭터와 체력 바를 그립니다.
* public void actionPerformed(ActionEvent e): 게임 루프에서 주기적으로 호출되어 캐릭터 상태를 업데이트합니다.
* private void checkCollisions(): 플레이어와 적의 충돌을 체크하고, 데미지를 처리합니다.
* private int getAttackDamage(Character character): 공격 기술에 따른 데미지를 반환합니다.
* public void keyPressed(KeyEvent e): 키 입력에 따라 플레이어 캐릭터를 이동시키거나 공격합니다.
* private void returnToMainMenu(): 메인 메뉴로 돌아갑니다.

## MainMenu 클래스
* MainMenu 클래스는 게임의 메인 메뉴를 구현합니다.

### 주요 메서드
* MainMenu(JFrame mainMenuFrame): 메인 메뉴 패널을 초기화하고 버튼을 추가합니다.
* private void startGame(): 게임을 시작합니다.
* private void showOptionDialog(): 옵션 다이얼로그를 표시합니다.
* private void exitGame(): 게임 종료를 확인하고 종료합니다.
* public static void main(String[] args): 메인 메뉴 창을 생성합니다.

## SoundPlayer 클래스
* SoundPlayer 클래스는 배경 음악을 재생하는 기능을 제공합니다.

### 주요 메서드
* SoundPlayer(String filePath): 오디오 파일을 로드합니다.
* public void play(): 음악을 재생하고 반복 재생합니다.
* public void stop(): 음악 재생을 중지합니다.


# TODO List
* 회원가입, 로그인 시스템 추가하기
* 타격시 넉백, 사운드 등 다른 옵션 추가하기
* 랭킹 옵션 추가하기
* 키 커스터마이징 시스템 추가하기
