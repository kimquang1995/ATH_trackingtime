import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;


public class Login_Interface extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsername;
	private JButton btnForgotPassword;
	private JButton btnOk;
	private JButton btnCancel;
	private JPasswordField pwdPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login_Interface frame = new Login_Interface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login_Interface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTimeTracking = new JLabel("Time Tracking");
		lblTimeTracking.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 40));
		lblTimeTracking.setBounds(84, 23, 247, 59);
		contentPane.add(lblTimeTracking);
		
		txtUsername = new JTextField();
		txtUsername.setText("Username");
		txtUsername.setBounds(99, 91, 228, 23);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		btnForgotPassword = new JButton("Forgot Password");
		btnForgotPassword.setBounds(212, 155, 134, 23);
		contentPane.add(btnForgotPassword);
		
		btnOk = new JButton("Ok");
		btnOk.setBounds(68, 187, 134, 23);
		contentPane.add(btnOk);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(212, 187, 134, 23);
		contentPane.add(btnCancel);
		
		pwdPassword = new JPasswordField();
		pwdPassword.setText("Password");
		pwdPassword.setBounds(99, 123, 228, 23);
		contentPane.add(pwdPassword);
		
		JButton button = new JButton("Register");
		button.setBounds(68, 155, 134, 23);
		contentPane.add(button);
	}
}
