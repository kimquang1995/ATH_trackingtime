import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class AddTag extends JFrame  implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent a) {
		JButton btnN = (JButton) a.getSource();
		if (btnN.getActionCommand().equals("SAVE")) {
			
	}
		if (btnN.getActionCommand().equals("CANCEL")) {
			this.setVisible(false);
		}
	}
	DatabaseConnection db = new DatabaseConnection();
	/*@Override
	public void actionPerformed( a) {
		JButton tmp = (JButton)a.getSource();
		if( tmp.getActionCommand().equals("CANCEL")){
			System.exit(0);
		}
		
		if (tmp.getActionCommand().equals("SAVE")) {
			System.exit(0);
			*/
	
	
	private JPanel contentPane;
	private JTextField textField;
	private JTextField txt_add_h;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddTag frame = new AddTag();
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
	public AddTag() {
		setTitle("ADD TAGS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 342, 234);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_add_name = new JLabel("Name");
		lbl_add_name.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbl_add_name.setBounds(24, 24, 51, 25);
		contentPane.add(lbl_add_name);
		
		JLabel lbl_add_hour = new JLabel("Hour");
		lbl_add_hour.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbl_add_hour.setBounds(24, 89, 51, 25);
		contentPane.add(lbl_add_hour);
		
		JTextField	txt_add_n = new JTextField();
		txt_add_n.setToolTipText("please input tag name");
		txt_add_n.setBounds(85, 21, 201, 35);
		contentPane.add(txt_add_n);
		txt_add_n.setColumns(10);
		
		txt_add_h = new JTextField();
		txt_add_h.setToolTipText("please input number");
		txt_add_h.setBounds(85, 86, 201, 35);
		contentPane.add(txt_add_h);
		txt_add_h.setColumns(10);
		
		JButton btn_add_s = new JButton("SAVE");
		btn_add_s.setBounds(85, 142, 89, 42);
		contentPane.add(btn_add_s);
		btn_add_s.addActionListener(this);
		
		JButton btn_Add_can = new JButton("CANCEL");
		btn_Add_can.setBounds(197, 142, 89, 42);
		contentPane.add(btn_Add_can);
		btn_Add_can.addActionListener(this);
		
	}
	
}
