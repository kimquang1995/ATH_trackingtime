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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Login_Interface extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsername;
	private JButton btnForgotPassword;
	private JButton btnOk;
	private JButton btnCancel;
	private JTextField pwdPassword;
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
		txtUsername.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				txtUsername.setText("");
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				if(txtUsername.getText().length()==0)
				txtUsername.setText("Username");
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
		txtUsername.setBounds(99, 91, 228, 23);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		
		
		btnForgotPassword = new JButton("Forgot Password");
		btnForgotPassword.setBounds(212, 155, 134, 23);
		contentPane.add(btnForgotPassword);
		
		btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {			
					DatabaseConnection db = new DatabaseConnection();
					db.Connect();
					PreparedStatement query = db.getConnection().prepareStatement(
							"Select * from Users where Email='"+txtUsername.getText()+"'");
					ResultSet rs = query.executeQuery();
					while (rs.next()) {
						if( pwdPassword.getText().equals(rs.getString("Pass")))
						{
							userName=rs.getString("Name").toUpperCase();
							Main_frame mtag = new Main_frame(userName);
							mtag.setVisible(true);
						}
						else
							JOptionPane.showMessageDialog(null, "Login Fail", "Login Error", JOptionPane.OK_OPTION);
					}

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.toString());
				}
				/*if (txtUsername.getText().equals("Type your Username here!") || pwdPassword.getText().equals("")) {
				
				JOptionPane.showMessageDialog(null, "you need to enter your name and password", "Login Error", JOptionPane.OK_OPTION);
				}
				
				if (txtUsername.getText().equals(USER) && pwdPassword.getText().equals(PASS)){
				JOptionPane.showMessageDialog(null, "Logged", "Information", JOptionPane.INFORMATION_MESSAGE);
				}
				
				else {
				JOptionPane.showMessageDialog(null, "Login Failure", "Information", JOptionPane.OK_OPTION);
				txtUsername.requestFocus();
				}*/
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
		
		
		
		pwdPassword = new JPasswordField();
		pwdPassword.setText("Password");
		pwdPassword.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				pwdPassword.setText("");
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				if(pwdPassword.getText().length()==0)
				pwdPassword.setText("Password");
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
		pwdPassword.setBounds(99, 123, 228, 23);
		contentPane.add(pwdPassword);
		
		JButton button = new JButton("Register");
		button.setBounds(68, 155, 134, 23);
		contentPane.add(button);
	}
}
