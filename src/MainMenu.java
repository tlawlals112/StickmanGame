import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel {
    private JFrame mainMenuFrame;
    private JButton startButton;
    private JButton optionButton;
    private JCheckBox musicCheckBox;

    public MainMenu(JFrame mainMenuFrame) {
        this.mainMenuFrame = mainMenuFrame;

        setLayout(new GridLayout(4, 1, 0, 10)); // 그리드 레이아웃 설정

        startButton = new JButton("Start");
        optionButton = new JButton("Option");
        musicCheckBox = new JCheckBox("Background Music", true);
        JButton exitButton = new JButton("Exit");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        optionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleMusicCheckbox(); // 옵션 버튼 누를 때 체크박스 보이기/숨기기
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitGame();
            }
        });

        add(startButton);
        add(optionButton);
        add(musicCheckBox);
        add(exitButton);

        musicCheckBox.setVisible(false); // 초기에는 체크박스 숨김
    }

    private void startGame() {
        mainMenuFrame.setVisible(false); // 메인 메뉴 창 숨기기

        boolean playMusic = musicCheckBox.isSelected();
        JFrame gameFrame = new JFrame("Game Frame");
        GamePanel gamePanel = new GamePanel(playMusic); // 게임 패널 생성
        gameFrame.add(gamePanel);
        gameFrame.setSize(800, 600);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setResizable(false);
        gameFrame.setVisible(true);
    }

    private void exitGame() {
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit Game", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void toggleMusicCheckbox() {
        musicCheckBox.setVisible(!musicCheckBox.isVisible()); // 보이기/숨기기 토글
    }

    public static void showMainMenu(JFrame frame) {
        MainMenu mainMenu = new MainMenu(frame);
        frame.setContentPane(mainMenu);
        frame.revalidate();
    }
}
