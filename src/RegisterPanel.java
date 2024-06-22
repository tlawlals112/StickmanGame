import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterPanel extends JPanel {
    private UserManager userManager;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton backButton;

    public RegisterPanel(UserManager userManager) {
        this.userManager = userManager;

        setLayout(new GridLayout(3, 2, 10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel usernameLabel = new JLabel("New Username:");
        add(usernameLabel);

        usernameField = new JTextField();
        add(usernameField);

        JLabel passwordLabel = new JLabel("New Password:");
        add(passwordLabel);

        passwordField = new JPasswordField();
        add(passwordField);

        registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                boolean result = userManager.addUser(username, password);

                if (result) {
                    JOptionPane.showMessageDialog(RegisterPanel.this, "Registration successful! You can now login.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    backToLogin();
                } else {
                    JOptionPane.showMessageDialog(RegisterPanel.this, "Registration failed. Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(registerButton);

        backButton = new JButton("Back to Login");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToLogin();
            }
        });
        add(backButton);
    }

    private void backToLogin() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        LoginPanel loginPanel = new LoginPanel(userManager);
        frame.setContentPane(loginPanel);
        frame.revalidate();
    }
}
