import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    private UserManager userManager;

    public LoginPanel(UserManager userManager) {
        this.userManager = userManager;

        setLayout(new GridLayout(3, 2, 10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel usernameLabel = new JLabel("Username:");
        add(usernameLabel);

        usernameField = new JTextField();
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        add(passwordLabel);

        passwordField = new JPasswordField();
        add(passwordField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (userManager.loginUser(username, password)) {
                    JOptionPane.showMessageDialog(LoginPanel.this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(LoginPanel.this);
                    MainMenu.showMainMenu(frame);
                } else {
                    JOptionPane.showMessageDialog(LoginPanel.this, "Login failed. Please check your username and password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(loginButton);

        registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterPanel registerPanel = new RegisterPanel(userManager);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(LoginPanel.this);
                frame.setContentPane(registerPanel);
                frame.revalidate();
            }
        });
        add(registerButton);
    }
}
