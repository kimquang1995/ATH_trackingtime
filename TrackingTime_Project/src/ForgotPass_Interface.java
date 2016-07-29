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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPass_Interface extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Create the frame.
	 */
	public ForgotPass_Interface(String email) {
		setTitle("Get Password");
		SendEmail sendmail = new SendEmail();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		lblEmailAddress.setBounds(22, 78, 150, 25);
		contentPane.add(lblEmailAddress);

		textField = new JTextField();
		textField.setBounds(165, 78, 234, 23);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.setText(email);
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			DatabaseConnection db = new DatabaseConnection();

			public void actionPerformed(ActionEvent arg0) {

				try {
					String massage = "";
					String email = textField.getText().toString();
					Pattern patternEmail = Pattern
							.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
									+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
					Matcher matcherEmail = patternEmail.matcher(email);
					if (matcherEmail.find()) {
						db.Connect();
						PreparedStatement query = db.getConnection()
								.prepareStatement(
										"Select * from Users where Email like '"
												+ email + "'");
						ResultSet resultSet = query.executeQuery();
						if (resultSet != null) {
							while (resultSet.next()) {

								massage = "Dear " + resultSet.getString("Name")
										+ "\r\n" + "Your pass is: "
										+ resultSet.getString("Pass");
								sendmail.sendSSLMessage(email,
										"Forgot Password MAil", massage, email);
								System.out.println("Send thành công");
								JOptionPane.showMessageDialog(null,
										"Your pass was sent to your email !", "Information",
										JOptionPane.INFORMATION_MESSAGE);
								dispose();
							}
						} else
							JOptionPane.showMessageDialog(null,
									"Email Don't exist !", "Error",
									JOptionPane.ERROR_MESSAGE);

					} else {
						JOptionPane.showMessageDialog(null, "Email invalid!",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnOk.setBounds(119, 126, 93, 23);
		contentPane.add(btnOk);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(239, 126, 93, 23);
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
