import javax.swing.JFrame;

public class Game {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Stickman Fighting Game");
        boolean playMusicDefault = true; // 기본적으로 배경음악을 재생할지 여부를 저장할 변수
        GamePanel gamePanel = new GamePanel(playMusicDefault); // 배경음악 On/Off 상태를 전달
        frame.add(gamePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
