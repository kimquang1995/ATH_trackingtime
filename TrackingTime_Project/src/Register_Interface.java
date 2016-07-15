import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

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
				String name = txtName.getText().trim();
				String email = txtEmail.getText().trim();
				boolean result;  
				Pattern patternName = Pattern.compile("^[a-zA-Z_\\s]+$");  
				Matcher matcherName = patternName.matcher(name);
				Pattern patternEmail = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
						  + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
				Matcher matcherEmail = patternEmail.matcher(email);
				if(matcherName.find() && matcherEmail.find() )  
				    result = true;
				else result = false;
				
				if(result == true){
				try {
					
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
						JOptionPane.showMessageDialog(null,
								"Đăng ký thành công."+"\n"+"\n"+"Mật khẩu sẽ được gửi đến Email của bạn.",
								"Thành công", JOptionPane.INFORMATION_MESSAGE);
						
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
				}}else{
					JOptionPane.showMessageDialog(null,"Địa chỉ Email và Tên không được trống."+"\n"+"\n"+"Địa chỉ Email phải có định dạng abc@abc.abc"+"\n"+"\n"+"Tên chỉ chứa ký tự chữ và khoảng trắng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
