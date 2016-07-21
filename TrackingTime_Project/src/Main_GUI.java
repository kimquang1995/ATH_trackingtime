import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main_GUI extends JFrame {

	/*
	 * public static void main(String[] args) { // TODO Auto-generated method
	 * stub EventQueue.invokeLater(new Runnable() { public void run() { try {
	 * Main_GUI frame = new Main_GUI(""); frame.setVisible(true); } catch
	 * (Exception e) { e.printStackTrace(); } } }); }
	 */
	String[] week = new String[52];
	int iYearSelected = Calendar.getInstance().get(Calendar.YEAR);
	String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(Calendar
			.getInstance().getTime());
	JButton btnDate = new JButton(currentDate);
	JLabel lbldate = new JLabel(currentDate);

	public Main_GUI(String userName) throws ParseException {
		setTitle("Tracking Time");
		setSize(750, 650);
		setLayout(null);
		// Panel Date
		JPanel panelDate = new JPanel(new GridLayout(1, 7, 5, 0));
		
		JButton btnBack = new JButton("<");
		btnBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				currentDate = Subtract_OneDate(currentDate);
				btnDate.setText(currentDate);
				lbldate.setText(currentDate);
			}
		});
		JButton btnNext = new JButton(">");		
		btnNext.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				currentDate = Add_OneDate(currentDate);
				btnDate.setText(currentDate);
				lbldate.setText(currentDate);
			}
		});
		panelDate.add(btnBack);
		
		panelDate.add(btnDate);
		panelDate.add(btnNext);
		panelDate.setBounds(150, 100, 450, 50);
		this.add(panelDate);
		// Label User
		JLabel lblUser = new JLabel("Hello " + userName);
		lblUser.setBounds(10, 30, 200, 50);
		lblUser.setFont(new Font("Arial", Font.ITALIC, 12));
		this.add(lblUser);
		// Label Title
		JLabel jTitle = new JLabel("Tracking Time");
		jTitle.setBounds(280, 30, 200, 50);
		jTitle.setFont(new Font("Arial", Font.BOLD, 28));
		this.add(jTitle);
		// Label WEEK
		/*
		 * JLabel lblWeek = new JLabel("Week 1"); lblWeek.setBounds(350, 60,
		 * 200, 50); lblWeek.setFont(new Font("Arial", Font.BOLD, 20));
		 * this.add(lblWeek);
		 */
		// Lable Time
		lbldate.setBounds(300, 150, 200, 50);
		lbldate.setFont(new Font("Arial", Font.BOLD, 28));
		this.add(lbldate);
		// Panel Tag
		JPanel panelTag = new JPanel(new GridLayout(3, 1, 0, 5));
		panelTag.add(new JButton("Relax 5/10"));
		panelTag.add(new JButton("Play 2/10"));
		panelTag.add(new JButton("Study 3/10"));
		panelTag.setBounds(70, 270, 100, 300);
		this.add(panelTag);
		// Panel Action
		JPanel panelAction = new JPanel(new GridLayout(1, 5, 5, 0));
		JButton btnCreatePlan = new JButton("Creat Plan");
		btnCreatePlan.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							Plan_GUI g_Plan = new Plan_GUI();
							g_Plan.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		JButton btntimeLog = new JButton("Time Log");
		btntimeLog.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							TimeLog g_TimeLog = new TimeLog(currentDate);
							g_TimeLog.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});

		JButton btnManageTag = new JButton("Manage Tag");
		btnManageTag.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							ManageTag g_Tag = new ManageTag();
							g_Tag.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		JButton btnStatistic = new JButton("Statistic");
		btnStatistic.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							Statistics g_Sta = new Statistics();
							g_Sta.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		JButton btnLogout = new JButton("Log out");
		btnLogout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Login_Interface lg = new Login_Interface();
				lg.setVisible(true);
				dispose();
			}
		});
		panelAction.add(btnCreatePlan);
		panelAction.add(btntimeLog);
		panelAction.add(btnManageTag);
		panelAction.add(btnStatistic);
		panelAction.add(btnLogout);
		panelAction.setBounds(70, 200, 600, 50);
		this.add(panelAction);

		// Panel Hours
		JPanel panelHours = new JPanel(new GridLayout(3, 1, 0, 5));
		panelHours.add(new JLabel("5"));
		panelHours.add(new JLabel("2"));
		panelHours.add(new JLabel("3"));
		panelHours.setBounds(400, 270, 100, 300);
		this.add(panelHours);

	}

	public String Add_OneDate(String currentday) {
		// String untildate = "1-1-" + year;
		String newday = "";
		// current format
		try {

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateFormat.parse(currentday));
			cal.add(Calendar.DATE, 1);
			newday = dateFormat.format(cal.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newday;
	}
	public String Subtract_OneDate(String currentday) {
		// String untildate = "1-1-" + year;
		String newday = "";
		// current format
		try {

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateFormat.parse(currentday));
			cal.add(Calendar.DATE, -1);
			newday = dateFormat.format(cal.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newday;
	}
	/*
	 * public static String Add_FirstWeek(int year) { String untildate = "1-1-"
	 * + year; String newday = ""; // current format try { SimpleDateFormat
	 * dateFormat = new SimpleDateFormat("dd-MM-yyyy"); Calendar cal =
	 * Calendar.getInstance(); cal.setTime(dateFormat.parse(untildate));
	 * cal.add(Calendar.DATE, 7); newday = dateFormat.format(cal.getTime()); }
	 * catch (ParseException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } return newday; }
	 * 
	 * public static String Add_LasttWeek(String untildate) { String newday =
	 * ""; // current format try { SimpleDateFormat dateFormat = new
	 * SimpleDateFormat("dd-MM-yyyy"); Calendar cal = Calendar.getInstance();
	 * cal.setTime(dateFormat.parse(untildate)); cal.add(Calendar.DATE, 7);
	 * newday = dateFormat.format(cal.getTime()); } catch (ParseException e) {
	 * // TODO Auto-generated catch block e.printStackTrace(); } return newday;
	 * }
	 * 
	 * private void FillComboboxWeek() { String iWeek1 = ""; for (int i = 0; i <
	 * 52; i++) { if (i == 0) { iWeek1 = Add_FirstWeek(iYearSelected); week[i] =
	 * "WEEK " + (i + 1) + " : " + "1-1-" + iYearSelected + "->" + iWeek1; }
	 * else { week[i] = "WEEK " + (i + 1) + " : " + iWeek1 + "->" +
	 * Add_LasttWeek(iWeek1); iWeek1 = Add_LasttWeek(iWeek1); } } }
	 */
}
