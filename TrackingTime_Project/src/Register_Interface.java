import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.mail.MessagingException;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Register_Interface extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { Register_Interface frame = new
	 * Register_Interface(); frame.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }
	 */
	/**
	 * Create the frame.
	 */
	public Register_Interface() {
		setTitle("Register Acccount");
		SendEmail sendmail = new SendEmail();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 457, 229);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblPleaseEnterYour = new JLabel(
				"Please enter your email address!");
		lblPleaseEnterYour.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblPleaseEnterYour.setBounds(32, 23, 392, 25);
		contentPane.add(lblPleaseEnterYour);

		JLabel lblEmailAddress = new JLabel("Email address");
		lblEmailAddress.setFont(new Font("SimSun", Font.PLAIN, 20));
		lblEmailAddress.setBounds(22, 80, 150, 25);
		contentPane.add(lblEmailAddress);

		JLabel lblName = new JLabel("Your Name");
		lblName.setFont(new Font("SimSun", Font.PLAIN, 20));
		lblName.setBounds(22, 110, 150, 25);
		contentPane.add(lblName);

		JTextField txtName = new JTextField();
		txtName.setBounds(165, 110, 234, 23);
		contentPane.add(txtName);
		txtName.setColumns(10);

		JTextField txtEmail = new JTextField();
		txtEmail.setBounds(165, 80, 234, 23);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);

		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			DatabaseConnection db = new DatabaseConnection();

			public void actionPerformed(ActionEvent arg0) {
				try {
					String name = txtName.getText();
					String email = txtEmail.getText().trim();
					String pass = "123";
					String massage;
					db.Connect();
					PreparedStatement query = db.getConnection()
							.prepareStatement(
									"Select * from Users where Email='"
											+ email + "'");
					ResultSet rs = query.executeQuery();
					if (!rs.isBeforeFirst()) {
						String Squery = "Insert into Users"
								+ "(Email,Pass,Name,Status)" + "values" + "('"
								+ email + "','" + pass + "','" + name
								+ "','True'" + ")";
						PreparedStatement exQuery = db.getConnection()
								.prepareStatement(Squery);
						exQuery.executeUpdate();
						System.out.println("Insert Complete");
						massage = "Dear " + name + "\r\n" + "Your pass is: "
								+ pass;
						sendmail.sendSSLMessage(email, "Register Account",
								massage, email);
						System.out.println("Send Mail Complete");
					} else {
						JOptionPane.showMessageDialog(null,
								"Email Exist! Please Input Other Email",
								"Register Error", JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnOk.setBounds(165, 140, 90, 23);
		contentPane.add(btnOk);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(305, 140, 93, 23);
		contentPane.add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
	}
}
