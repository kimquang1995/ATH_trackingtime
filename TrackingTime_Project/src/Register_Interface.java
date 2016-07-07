import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Register_Interface extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register_Interface frame = new Register_Interface();
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
	public Register_Interface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 457, 229);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPleaseEnterYour = new JLabel("Please enter your email address!");
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
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		btnOk.setBounds(119, 126, 93, 23);
		contentPane.add(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(239, 126, 93, 23);
		contentPane.add(btnCancel);
	}
}
