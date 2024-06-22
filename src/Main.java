import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Login System");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 300);
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);

                UserManager userManager = new UserManager();
                // 여기에 초기 사용자 추가 코드 추가
                userManager.addUser("user1", "password1");
                userManager.addUser("user2", "password2");

                LoginPanel loginPanel = new LoginPanel(userManager);

                frame.setContentPane(loginPanel);
                frame.setVisible(true);
            }
        });
    }
}
