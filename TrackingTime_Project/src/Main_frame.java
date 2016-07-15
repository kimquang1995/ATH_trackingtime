
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;


public class Main_frame extends JFrame implements ActionListener {
private JPanel contentPane1;
private JTable table;
DatabaseConnection db = new DatabaseConnection();

@Override
	public void actionPerformed(ActionEvent e) {
		JButton tmp= (JButton) e.getSource();
		if (tmp.getActionCommand().equals("Log Out")){
			Login_Interface lg= new Login_Interface();
			Main_frame cur=new Main_frame(""+lg);
			this.dispose();
			lg.setVisible(true);			
	}
	if (tmp.getActionCommand().equals("TIME LOG")){
		new TimeLog().setVisible(true);	
	}}

	
	private JPanel contentPane;
	private JTable jtable;
	private MenuBar menuBar;

	/**
	 * Create the frame.
	 */
	public Main_frame(String userName) {
		try {
			db.Connect();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setTitle("MainFrame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 619, 384);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		menuBar = new MenuBar();
		Menu menu = new Menu("A Menu");
		menu.getAccessibleContext().setAccessibleDescription(
		        "The only menu in this program that has menu items");
		menuBar.add(menu);
		
		
		JLabel lbl1 = new JLabel("TRACKING TIME");
		lbl1.setBounds(178, 11, 200, 50);
		lbl1.setFont(new Font("Tahoma", Font.PLAIN, 25));
		contentPane.add(lbl1);
		
		JLabel lbl2 = new JLabel("Hello "+userName);
		lbl2.setBounds(22, 39, 200, 32);
		contentPane.add(lbl2);
		
		JPanel panel = new JPanel();
		panel.setBounds(20, 72, 573, 63);
		contentPane.add(panel);
		
		JButton btn_main_addtag = new JButton("ADD TAGS");
		btn_main_addtag.setBounds(22, 146, 123, 35);
		contentPane.add(btn_main_addtag);
		btn_main_addtag.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AddTag addtag=new AddTag();
				addtag.show();
				
			}
		});
		
		JButton btn_main_tlog = new JButton("TIME LOG");
		btn_main_tlog.setBounds(171, 146, 117, 35);
		contentPane.add(btn_main_tlog);
		btn_main_tlog.addActionListener(this);
		
		JButton btn_main_sta = new JButton("STATISTIC");
		btn_main_sta.setBounds(305, 146, 134, 35);
		contentPane.add(btn_main_sta);
		
		JButton btn_main_lout = new JButton("Log Out");
		btn_main_lout.setBounds(476, 146, 117, 35);
		contentPane.add(btn_main_lout);
		btn_main_lout.addActionListener(this);
		JLabel lbl_main_today = new JLabel("Today");
		lbl_main_today.setBounds(178, 183, 200, 32);
		lbl_main_today.setEnabled(false);
		contentPane.add(lbl_main_today);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(104, 207, -52, 63);
		contentPane.add(scrollPane);
		
		jtable = new JTable();
		jtable.setColumnSelectionAllowed(true);
		jtable.setCellSelectionEnabled(true);
		jtable.setBounds(245, 207, -203, 114);
		contentPane.add(jtable);

}
}
