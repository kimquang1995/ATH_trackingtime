import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Login_Interface extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsername;
	private JButton btnForgotPassword;
	private JButton btnOk;
	private JButton btnCancel;
	private JTextField pwdPassword;

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
	private static String USER = "ThanhPhuc1610";
	private static String PASS = "thanhphuc1610";
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
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (txtUsername.getText().equals("Type your Username here!") || pwdPassword.getText().equals("")) {
				
				JOptionPane.showMessageDialog(null, "you need to enter your name and password", "Login Error", JOptionPane.OK_OPTION);
				}
				
				if (txtUsername.getText().equals(USER) && pwdPassword.getText().equals(PASS)){
				JOptionPane.showMessageDialog(null, "Logged", "Information", JOptionPane.INFORMATION_MESSAGE);
				}
				
				else {
				JOptionPane.showMessageDialog(null, "Login Failure", "Information", JOptionPane.OK_OPTION);
				txtUsername.requestFocus();
				}
			}
		});
		btnOk.setBounds(68, 187, 134, 23);
		contentPane.add(btnOk);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(212, 187, 134, 23);
		contentPane.add(btnCancel);
		
		
		
		pwdPassword = new JTextField();
		pwdPassword.setText("Password");
		pwdPassword.setBounds(99, 123, 228, 23);
		contentPane.add(pwdPassword);
		
		JButton button = new JButton("Register");
		button.setBounds(68, 155, 134, 23);
		contentPane.add(button);
	}
}
