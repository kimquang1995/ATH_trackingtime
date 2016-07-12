import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JToolBar;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextArea;


public class Main_frame extends JFrame implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton tmp= (JButton) e.getSource();
		if (tmp.getActionCommand().equals("Log Out")){
		System.exit(0);
	}
	if (tmp.getActionCommand().equals("TIME LOG")){
		new TimeLog().setVisible(true);	
	}}

	
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main_frame frame = new Main_frame(userName);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	} */

	/**
	 * Create the frame.
	 */
	public Main_frame(String userName) {
		
		setTitle("MainFrame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 619, 384);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl1 = new JLabel("TRACKING TIME");
		lbl1.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lbl1.setBounds(178, 11, 200, 50);
		contentPane.add(lbl1);
		
		JLabel lbl2 = new JLabel("Hello "+userName);
		lbl2.setBounds(22, 39, 200, 32);
		contentPane.add(lbl2);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(22, 220, 17, 98);
		contentPane.add(scrollBar);
		
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
		
		JButton btn_main_tag1 = new JButton("Tag1");
		btn_main_tag1.setBounds(42, 220, 117, 35);
		contentPane.add(btn_main_tag1);
		
		JButton btn_main_tag2 = new JButton("Tag2");
		btn_main_tag2.setBounds(42, 255, 117, 32);
		contentPane.add(btn_main_tag2);
		
		JButton btn_main_tag3 = new JButton("Tag3");
		btn_main_tag3.setBounds(42, 286, 117, 32);
		contentPane.add(btn_main_tag3);
		
		JLabel lbl_main_today = new JLabel("Today");
		lbl_main_today.setEnabled(false);
		lbl_main_today.setBounds(178, 183, 200, 32);
		contentPane.add(lbl_main_today);
		
		JLabel lbl_timetag1 = new JLabel("1h");
		lbl_timetag1.setBounds(178, 221, 200, 32);
		contentPane.add(lbl_timetag1);
		
		JLabel lbl_timetag2 = new JLabel("2h");
		lbl_timetag2.setBounds(178, 258, 200, 27);
		contentPane.add(lbl_timetag2);
		
		JLabel lbl_timetag3 = new JLabel("3h");
		lbl_timetag3.setBounds(178, 286, 200, 32);
		contentPane.add(lbl_timetag3);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(268, 220, 180, 89);
		contentPane.add(textArea);

}
}
