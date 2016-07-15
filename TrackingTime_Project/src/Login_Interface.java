import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Login_Interface extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsername;
	private JButton btnForgotPassword;
	private JButton btnOk;
	private JPasswordField txtPass;
	public String userName;

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
	@SuppressWarnings("deprecation")
	public Login_Interface() {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 430, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTimeTracking = new JLabel("Time Tracking");
		lblTimeTracking.setFont(new Font("Times New Roman", Font.BOLD
				| Font.ITALIC, 40));
		lblTimeTracking.setBounds(84, 5, 247, 59);
		contentPane.add(lblTimeTracking);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC,
				40));
		lblLogin.setBounds(160, 40, 247, 59);
		contentPane.add(lblLogin);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC,
				20));
		lblEmail.setBounds(20, 91, 228, 23);
		contentPane.add(lblEmail);

		JLabel lblPass = new JLabel("Password");
		lblPass.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblPass.setBounds(10, 123, 228, 23);
		contentPane.add(lblPass);

		txtUsername = new JTextField();
		txtUsername.setBounds(99, 91, 228, 23);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);

		txtPass = new JPasswordField();
		txtPass.setBounds(99, 123, 228, 23);
		contentPane.add(txtPass);

		btnOk = new JButton("Login");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					String email = txtUsername.getText();
					String pass = txtPass.getText();
					if (email.length() > 0 && pass.length() > 0) {
						DatabaseConnection db = new DatabaseConnection();
						db.Connect();
						PreparedStatement query = db.getConnection()
								.prepareStatement(
										"Select * from Users where Email='"
												+ email + "'");
						ResultSet rs = query.executeQuery();

						if (!rs.isBeforeFirst()) {
							JOptionPane.showMessageDialog(null,
									"Email not exist ! Please Register",
									"Login Error", JOptionPane.ERROR_MESSAGE);
						} else {
							while (rs.next()) {
								if (pass.equals(rs.getString("Pass"))) {
									userName = rs.getString("Name")
											.toUpperCase();
									Main_frame mtag = new Main_frame(userName);
									mtag.setVisible(true);
									File file = new File("resources/userInfor.txt");
									System.out.println(file.getAbsolutePath());
									Charset charset = Charset.forName("UTF-8");
									String s = "";
									/*try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
									    writer.write(s, 0, s.length());
									} catch (IOException x) {
									    System.err.format("IOException: %s%n", x);
									}*/
								} else
									JOptionPane.showMessageDialog(null,
											"Login Fail", "Login Error",
											JOptionPane.ERROR_MESSAGE);
							}
						}
					} else
						JOptionPane.showMessageDialog(null,
								"Please Input Email and Password !",
								"Login Error", JOptionPane.ERROR_MESSAGE);

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.toString());
				}
				/*
				 * if (txtUsername.getText().equals("Type your Username here!")
				 * || pwdPassword.getText().equals("")) {
				 * 
				 * JOptionPane.showMessageDialog(null,
				 * "you need to enter your name and password", "Login Error",
				 * JOptionPane.OK_OPTION); }
				 * 
				 * if (txtUsername.getText().equals(USER) &&
				 * pwdPassword.getText().equals(PASS)){
				 * JOptionPane.showMessageDialog(null, "Logged", "Information",
				 * JOptionPane.INFORMATION_MESSAGE); }
				 * 
				 * else { JOptionPane.showMessageDialog(null, "Login Failure",
				 * "Information", JOptionPane.OK_OPTION);
				 * txtUsername.requestFocus(); }
				 */
			}
		});
		btnOk.setBounds(99, 155, 227, 50);
		contentPane.add(btnOk);

		JButton btnReg = new JButton("Register");
		btnReg.setBounds(99, 210, 113, 23);
		contentPane.add(btnReg);
		btnReg.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Register_Interface reg_inf = new Register_Interface();
				reg_inf.setVisible(true);
			}
		});

		btnForgotPassword = new JButton("Forgot Password");
		btnForgotPassword.setBounds(212, 210, 113, 23);
		contentPane.add(btnForgotPassword);
		btnForgotPassword.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ForgotPass_Interface forgot_Int = new ForgotPass_Interface();
				forgot_Int.setVisible(true);
			}
		});
		if (txtUsername.getText().length() == 0
				|| txtPass.getText().length() == 0) {
			btnOk.setEnabled(false);
		}
		txtUsername.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				if (txtUsername.getText().length() == 0
						|| txtPass.getText().length() == 0) {
					btnOk.setEnabled(false);
				} else {
					btnOk.setEnabled(true);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		txtPass.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				if (txtUsername.getText().length() == 0
						|| txtPass.getText().length() == 0) {
					btnOk.setEnabled(false);
				} else {
					btnOk.setEnabled(true);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

	}
}
