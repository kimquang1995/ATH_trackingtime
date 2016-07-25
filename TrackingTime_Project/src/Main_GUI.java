import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

public class Main_GUI extends JFrame {

	/*
	 * public static void main(String[] args) { // TODO Auto-generated method
	 * stub EventQueue.invokeLater(new Runnable() { public void run() { try {
	 * Main_GUI frame = new Main_GUI(""); frame.setVisible(true); } catch
	 * (Exception e) { e.printStackTrace(); } } }); }
	 */
	// String[] week = new String[52];
	int iYearSelected = Calendar.getInstance().get(Calendar.YEAR);
	String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(Calendar
			.getInstance().getTime());
	JButton btnDate = new JButton(currentDate);
	JLabel lbldate = new JLabel();
	JDateChooser dateChooser = new JDateChooser();
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	DatabaseConnection db = new DatabaseConnection();
	JPanel panelTag = new JPanel();
	int id_User = 0;
	private JTable table;
	JButton btnCreatePlan;
	JButton btntimeLog;
	JButton btnManageTag;
	JButton btnStatistic;
	JButton btnLogout;
	JScrollPane scrollPane ;
	public Main_GUI(String userName, int IdUser) throws ParseException {
		try {
			db.Connect();
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		panelTag.setBounds(70, 270, 150, 300);
		add(panelTag);
		setTitle("Tracking Time");
		setBounds(100, 100, 820, 650);
		setLayout(null);

		 scrollPane = new JScrollPane();
		scrollPane.setFont(new Font("Roboto", Font.PLAIN, 12));
		scrollPane.setBorder(new MatteBorder(1, 4, 4, 1, (Color) new Color(192,
				192, 192)));
		scrollPane.setBounds(220, 273, 510, 295);
		this.add(scrollPane);
		table = new JTable();
		table.setDefaultEditor(Object.class, null);
		scrollPane.setViewportView(table);
		id_User = IdUser;
		// Panel Date
		dateChooser.setDate(dateFormat.parse(currentDate));
		dateChooser.setFont(new Font("Arial", Font.BOLD, 20));
		dateChooser.addPropertyChangeListener("date",
				new PropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						Date date = (Date) evt.getNewValue();
						currentDate = dateFormat.format(date).toString();
						try {
							GetTag_inPlan(currentDate);
							LoadTable(getQueryTimeLog(currentDate));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
		JPanel panelDate = new JPanel(new GridLayout(1, 7, 5, 0));

		JButton btnBack = new JButton("<");
		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				currentDate = Subtract_OneDate(currentDate);
				try {
					dateChooser.setDate(dateFormat.parse(currentDate));
					GetTag_inPlan(currentDate);
					LoadTable(getQueryTimeLog(currentDate));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		JButton btnNext = new JButton(">");
		btnNext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				currentDate = Add_OneDate(currentDate);
				try {
					dateChooser.setDate(dateFormat.parse(currentDate));
					GetTag_inPlan(currentDate);
					LoadTable(getQueryTimeLog(currentDate));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		panelDate.add(btnBack);

		panelDate.add(dateChooser);
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
		lbldate.setBounds(200, 150, 320, 50);
		lbldate.setFont(new Font("Arial", Font.BOLD, 18));
		this.add(lbldate);
		// Panel Action
		JPanel panelAction = new JPanel(new GridLayout(1, 5, 5, 0));
		btnCreatePlan = new JButton("Creat Plan");
		btnCreatePlan.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							Plan_GUI g_Plan = new Plan_GUI(id_User);
							g_Plan.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		btntimeLog = new JButton("Time Log");
		btntimeLog.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							TimeLog g_TimeLog = new TimeLog(currentDate,
									id_User);
							g_TimeLog.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});

		btnManageTag = new JButton("Manage Tag");
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
		btnStatistic = new JButton("Statistic");
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
		btnLogout = new JButton("Log out");
		btnLogout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Login_Interface lg = new Login_Interface();
				lg.setVisible(true);
				dispose();
				lg.flag = false;
			}
		});
		panelAction.add(btnCreatePlan);
		panelAction.add(btntimeLog);
		panelAction.add(btnManageTag);
		panelAction.add(btnStatistic);
		panelAction.add(btnLogout);
		panelAction.setBounds(70, 200, 660, 50);
		this.add(panelAction);

		// Panel Hours
		/*
		 * JPanel panelHours = new JPanel(new GridLayout(3, 1, 0, 5));
		 * panelHours.add(new JLabel("5")); panelHours.add(new JLabel("2"));
		 * panelHours.add(new JLabel("3")); panelHours.setBounds(400, 270, 100,
		 * 300); add(panelHours);
		 */

		try {
			GetTag_inPlan(currentDate);
			LoadTable(getQueryTimeLog(currentDate));
		} catch (Exception e) {
			// TODO: handle exception
		}
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

	public void GetTag_inPlan(String date) throws ParseException {
		int rowCount = 0;
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		Date inidate = new SimpleDateFormat("dd-MM-yyyy").parse(date);
		String dateSelect = formater.format(inidate);
		String queryTimeLog_Day = "select * from TimeLog where convert(varchar(10),Date, 120) = '"
				+ dateSelect + "' ";
		String selectQuery = "SELECT t.Name, SUM(s.Hour) as Hours,s.Start_Day,s.End_date  FROM Plans s "
				+ "INNER JOIN Tag t ON t.Id = s.Id_Tag where convert(varchar(10),s.Start_Day, 120) <= '"
				+ dateSelect
				+ "' "
				+ "and  convert(varchar(10),s.End_date, 120)>= '"
				+ dateSelect
				+ "' and s.Id_User='"
				+ id_User
				+ "'"
				+ "GROUP BY t.Name,s.Start_Day,s.End_date  "
				+ "ORDER BY Hours ";
		try {

			PreparedStatement query = db.getConnection().prepareStatement(
					selectQuery);
			PreparedStatement qTimeLog_Day = db.getConnection()
					.prepareStatement(queryTimeLog_Day);
			ResultSet rs = query.executeQuery();
			if (!rs.isBeforeFirst()) {
				panelTag.setEnabled(false);
				panelTag.removeAll();
				panelTag.validate();
				panelTag.repaint();
				btntimeLog.setEnabled(false);
				scrollPane.setVisible(false);
			} else {
				btntimeLog.setEnabled(true);
				scrollPane.setVisible(true);
				while (rs.next()) {
					rowCount++;					
				}
			}
			panelTag.removeAll();
			panelTag.setLayout(new GridLayout(rowCount, 1, 0, 5));
			// panelTag.setVisible(true);

			ResultSet rs1 = query.executeQuery();

			if (!rs1.isBeforeFirst()) {
				lbldate.setText("Not Plan for This Day");
			} else {
				SimpleDateFormat dt = new SimpleDateFormat(
						"yyyy-MM-dd hh:mm:ss");
				SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");
				while (rs1.next()) {

					panelTag.add(new JButton((rs1.getString("Name") + "--- "
							+ rs1.getString("Hours") + " Hours")));
					String start = dt1.format(dt.parse(rs1
							.getString("Start_Day")));
					String end = dt1
							.format(dt.parse(rs1.getString("End_date")));
					lbldate.setText("Plan from " + start + " to " + end);
				}
				panelTag.validate();
				panelTag.repaint();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getQueryTimeLog(String date) throws ParseException {

		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		Date inidate = new SimpleDateFormat("dd-MM-yyyy").parse(date);
		String dateSelect = formater.format(inidate);
		return "select t.Name as Tags,tl.Name,tl.Hours,convert(char(5), tl.Start_Time, 108) Start_Time,convert(char(5), tl.End_Time, 108) End_Time from TimeLog tl"
				+ " left join Tag t on t.Id = tl.Id_Tag "
				+ "where convert(varchar(10),Date, 120) = '"
				+ dateSelect
				+ "' " + "order by Tags";
	}

	public void LoadTable(String queryExe) {
		try {
			DefaultTableModel model = new DefaultTableModel(new String[] {
					"Tag", "Name", "Hours", "Start Time", "End Time" }, 0);
			PreparedStatement query = db.getConnection().prepareStatement(
					queryExe);
			ResultSet rs = query.executeQuery();
			Object[] row = new Object[5];
			while (rs.next()) {
				row[0] = rs.getString("Tags");
				row[1] = rs.getString("Name");
				row[2] = rs.getString("Hours");
				row[3] = rs.getString("Start_Time");
				row[4] = rs.getString("End_Time");
				model.addRow(row);
			}
			table.setModel(model);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void EnableButton() {
		btnDate.setEnabled(true);
		btnCreatePlan.setEnabled(true);
		btnLogout.setEnabled(true);
		btnManageTag.setEnabled(true);
		btnStatistic.setEnabled(true);
		btntimeLog.setEnabled(true);

	}

}
