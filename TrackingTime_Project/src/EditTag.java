import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;


public class EditTag extends JFrame {

	private JPanel contentPane;
	private JTextField txt_edit_name;
	private JTextField txt_edit_h;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditTag frame = new EditTag();
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
	public EditTag() {
		setTitle("EDIT TAGS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 371, 241);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("NAME");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(42, 21, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("HOUR");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(42, 81, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		txt_edit_name = new JTextField();
		txt_edit_name.setBounds(117, 11, 156, 38);
		contentPane.add(txt_edit_name);
		txt_edit_name.setColumns(10);
		
		txt_edit_h = new JTextField();
		txt_edit_h.setBounds(117, 71, 156, 38);
		contentPane.add(txt_edit_h);
		txt_edit_h.setColumns(10);
		
		JButton btn_edit_s = new JButton("SAVE");
		btn_edit_s.setBounds(37, 137, 62, 32);
		contentPane.add(btn_edit_s);
		
		JButton btn_edit_d = new JButton("DELETE");
		btn_edit_d.setBounds(117, 137, 86, 32);
		contentPane.add(btn_edit_d);
		
		JButton btn_edit_c = new JButton("CANCEL");
		btn_edit_c.setBounds(216, 137, 86, 32);
		contentPane.add(btn_edit_c);
	}

}
