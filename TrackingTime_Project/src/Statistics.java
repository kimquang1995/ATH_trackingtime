import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JYearChooser;

public class Statistics extends JFrame {
	DatabaseConnection db = new DatabaseConnection();
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	int id_user=0;
	/**
	 * Create the frame.
	 */
	JPanel mainPane = new JPanel(new BorderLayout());

	public Statistics(int id_User) throws ParseException {
		try {
			id_user = id_User;
			db.Connect();

		} catch (Exception e) {
			e.printStackTrace();
		}
		setTitle("Statistics");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 650);
		add(createMainPanel());
	}

	private JPanel createMainPanel() throws ParseException {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.add(createTitlePanel(), BorderLayout.NORTH);
		panel.add(LeftPanel(), BorderLayout.WEST);
		panel.add(CenterPanel(), BorderLayout.CENTER);
		panel.add(RightPanel(), BorderLayout.EAST);
		panel.add(BottomPanel(), BorderLayout.SOUTH);
		return panel;
	}

	// create Top panel
	private JPanel createTitlePanel() {
		JPanel panel = new JPanel();
		JLabel lblTitle = new JLabel("Statistics");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 50));
		panel.add(lblTitle);
		return panel;
	}

	// create Left panel
	private JPanel LeftPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1, 5, 5));
		return panel;
	}

	// create Center panel
	private JPanel CenterPanel() throws ParseException {
		JPanel panel = new JPanel(new GridLayout(2, 1, 5, 5));
		JPanel TOPpanel = new JPanel(new GridLayout(1, 2, 5, 5));
		JPanel sub_TOPpanel1 = new JPanel(new BorderLayout());
		JPanel sub_TOPpanel2 = new JPanel(new BorderLayout());
		JPanel BOTpanel = new JPanel(new GridLayout(1, 2, 5, 5));
		JPanel sub_BOTpanel1 = new JPanel(new BorderLayout());
		JPanel sub_BOTpanel2 = new JPanel(new BorderLayout());

		// Toppanel 1
		// ------------------------------------------------------------------------------------------------------------------
		JYearChooser yearChooser1 = new JYearChooser();
		JButton btnShow1 = new JButton("Statistic");

		JLabel lblTitile = new JLabel("CHOOSE YEAR");
		lblTitile.setFont(new Font("Arial", Font.BOLD, 20));
		JPanel sub_TOPpanel1_Title = new JPanel(new GridLayout(1, 3, 5, 5));
		sub_TOPpanel1_Title.add(lblTitile);
		sub_TOPpanel1_Title.add(yearChooser1);
		sub_TOPpanel1_Title.add(btnShow1);
		sub_TOPpanel1.add(sub_TOPpanel1_Title, BorderLayout.NORTH);
		sub_TOPpanel1.add(((JFrame) FrameOverView(yearChooser1.getYear()))
				.getContentPane(), BorderLayout.CENTER);
		btnShow1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int year = yearChooser1.getYear();
				JFrame chart = FrameOverView(year);
				sub_TOPpanel1.removeAll();
				sub_TOPpanel1.add(sub_TOPpanel1_Title, BorderLayout.NORTH);
				sub_TOPpanel1.add(chart.getContentPane(), BorderLayout.CENTER);
				sub_TOPpanel1.validate();
				sub_TOPpanel1.repaint();
			}
		});
		// ------------------------------------------------------------------------------------------------------------------
		// Toppanel 2
		JYearChooser yearChooser2 = new JYearChooser();
		JButton btnShow2 = new JButton("Statistic");
		JLabel lblTitile2 = new JLabel("CHOOSE YEAR");
		lblTitile2.setFont(new Font("Arial", Font.BOLD, 20));
		JPanel sub_TOPpanel2_Title = new JPanel(new GridLayout(1, 3, 5, 5));
		sub_TOPpanel2_Title.add(lblTitile2);
		sub_TOPpanel2_Title.add(yearChooser2);
		sub_TOPpanel2_Title.add(btnShow2);
		sub_TOPpanel2.add(sub_TOPpanel2_Title, BorderLayout.NORTH);
		sub_TOPpanel2.add(((JFrame) Frame_onTotalTime(yearChooser2.getYear()))
				.getContentPane(), BorderLayout.CENTER);
		btnShow2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int year = yearChooser2.getYear();
				JFrame chart = Frame_onTotalTime(year);
				sub_TOPpanel2.removeAll();
				sub_TOPpanel2.add(sub_TOPpanel2_Title, BorderLayout.NORTH);
				sub_TOPpanel2.add(chart.getContentPane(), BorderLayout.CENTER);
				sub_TOPpanel2.validate();
				sub_TOPpanel2.repaint();
			}
		});
		// ------------------------------------------------------------------------------------------------------------------
		// Botpanel 1
		JComboBox cbPlan = new JComboBox();
		FillPlantoCombobox(cbPlan);

		JLabel lblTitile3 = new JLabel("CHOOSE PLAN");
		lblTitile3.setFont(new Font("Arial", Font.BOLD, 20));
		JPanel sub_BOTpanel1_Title = new JPanel(new GridLayout(1, 3, 5, 5));
		JPanel sub_BOTpanel1_Center = new JPanel(new GridLayout(1, 2, 5, 5));
		sub_BOTpanel1_Title.add(lblTitile3);
		sub_BOTpanel1_Title.add(cbPlan);
		sub_BOTpanel1.add(sub_BOTpanel1_Title, BorderLayout.NORTH);
		sub_BOTpanel1.add(sub_BOTpanel1_Center, BorderLayout.CENTER);
		cbPlan.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String sub_Date = cbPlan.getSelectedItem().toString()
						.substring(0, 10);
				System.out.println(sub_Date);
				sub_BOTpanel1_Center.removeAll();
				//PLAN
				JFrame chart1 = FramePlanforWeek(sub_Date);
				sub_BOTpanel1_Center.add(chart1.getContentPane());
				// ACTUAL
				JFrame chart2 = FrameActualforWeek(sub_Date, Add_7day(sub_Date));
				sub_BOTpanel1_Center.add(chart2.getContentPane());
				sub_BOTpanel1_Center.validate();
				sub_BOTpanel1_Center.repaint();
			}
		});
		// ------------------------------------------------------------------------------------------------------------------
		// Botpanel 2
		JDateChooser DateChooser4_From = new JDateChooser();
		DateChooser4_From.setDate(dateFormat.parse(dateFormat.format(Calendar
				.getInstance().getTime())));
		JDateChooser DateChooser4_To = new JDateChooser();
		DateChooser4_To.setDate(dateFormat.parse(dateFormat.format(Calendar
				.getInstance().getTime())));
		JButton btnShow4 = new JButton("Statistic");
		JLabel lblTitile4_From = new JLabel("FROM");
		lblTitile4_From.setFont(new Font("Arial", Font.BOLD, 20));
		JLabel lblTitile4_To = new JLabel("TO");
		lblTitile4_To.setFont(new Font("Arial", Font.BOLD, 20));
		JPanel sub_BOTpanel2_Title = new JPanel(new GridLayout(1, 5, 5, 5));
		sub_BOTpanel2_Title.add(lblTitile4_From);
		sub_BOTpanel2_Title.add(DateChooser4_From);
		sub_BOTpanel2_Title.add(lblTitile4_To);
		sub_BOTpanel2_Title.add(DateChooser4_To);
		sub_BOTpanel2_Title.add(btnShow4);
		sub_BOTpanel2.add(sub_BOTpanel2_Title, BorderLayout.NORTH);
		sub_BOTpanel2.add(
				((JFrame) Frame_onTotalTimeWeek(
						dateFormat.format(DateChooser4_From.getDate()),
						dateFormat.format(DateChooser4_To.getDate())))
						.getContentPane(), BorderLayout.CENTER);
		btnShow4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					String from_date = dateFormat.format(DateChooser4_From
							.getDate());
					String to_date = dateFormat.format(DateChooser4_To
							.getDate());
					JFrame chart = Frame_onTotalTimeWeek(from_date, to_date);
					sub_BOTpanel2.removeAll();
					sub_BOTpanel2.add(sub_BOTpanel2_Title, BorderLayout.NORTH);
					sub_BOTpanel2.add(chart.getContentPane(),
							BorderLayout.CENTER);
					sub_BOTpanel2.validate();
					sub_BOTpanel2.repaint();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		TOPpanel.add(sub_TOPpanel1);
		TOPpanel.add(sub_TOPpanel2);
		BOTpanel.add(sub_BOTpanel1);
		BOTpanel.add(sub_BOTpanel2);
		panel.add(TOPpanel);
		panel.add(BOTpanel);

		return panel;
	}

	// create Right panel
	private JPanel RightPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1, 5, 5));
		// panel.add(new JButton("Right"));
		// panel.add(new JButton("Right"));
		return panel;
	}

	// create login button panel
	private JPanel BottomPanel() {
		JPanel panel = new JPanel();
		return panel;
	}

	public JFrame DrawChar(String title, DefaultPieDataset dataset) {
		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true,
				true, false);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
				"{0}: {1} ({2})", new DecimalFormat("0.0"), new DecimalFormat(
						"0.0%"));
		plot.setLabelGenerator(gen);
		return new ChartFrame(title, chart);
	}

	public JFrame FrameOverView(int year) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		String query = "select t.Name,sum(tl.Hours) as Hours from TimeLog tl "
				+ "Left join Tag t on t.Id = tl.Id_Tag"
				+ " where YEAR(tl.Date) =" + year + " and Id_User = "+id_user+"  group by t.Name";
		try {

			PreparedStatement select_query = db.getConnection()
					.prepareStatement(query);
			ResultSet rs = select_query.executeQuery();
			if (!rs.isBeforeFirst()) {
			} else {
				while (rs.next()) {
					dataset.setValue(rs.getString("Name"),
							Float.parseFloat(rs.getString("Hours")));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return DrawChar("OVERVIEW TAG FOR YEAR", dataset);
	}

	public JFrame Frame_onTotalTime(int year) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		float total_Hours = 0;
		String query = "select t.Name,sum(tl.Hours) as Hours from TimeLog tl "
				+ "Left join Tag t on t.Id = tl.Id_Tag"
				+ " where YEAR(tl.Date) =" + year + "and Id_User = "+id_user+" group by t.Name";
		try {

			PreparedStatement select_query = db.getConnection()
					.prepareStatement(query);
			ResultSet rs = select_query.executeQuery();
			if (!rs.isBeforeFirst()) {
			} else {
				while (rs.next()) {
					total_Hours += Float.parseFloat(rs.getString("Hours"));
				}
				dataset.setValue("Time Active", total_Hours);
				dataset.setValue("Total Time", 8760);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return DrawChar("STATISTIC ON TOTAL TIME YEAR", dataset);
	}

	public JFrame Frame_onTotalTimeWeek(String From_date, String To_date)
			throws ParseException {
		DefaultPieDataset dataset = new DefaultPieDataset();
		From_date = new SimpleDateFormat("yyyy-MM-dd")
				.format(new SimpleDateFormat("dd-MM-yyyy").parse(From_date));
		To_date = new SimpleDateFormat("yyyy-MM-dd")
				.format(new SimpleDateFormat("dd-MM-yyyy").parse(To_date));
		float total_Hours = (float) 0.0;
		int countDay = 0;
		String query = "select t.Name,sum(tl.Hours) as Hours from TimeLog tl "
				+ "Left join Tag t on t.Id = tl.Id_Tag "
				+ "where convert(varchar(10),tl.Date, 120) >=" + "'"
				+ From_date + "' " + "and "
				+ "convert(varchar(10),tl.Date, 120) <=" + "'" + To_date + "' and Id_User = "+id_user+" "
				+ "group by t.Name";
		try {

			PreparedStatement select_query = db.getConnection()
					.prepareStatement(query);
			ResultSet rs = select_query.executeQuery();
			if (!rs.isBeforeFirst()) {
			} else {
				while (rs.next()) {
					total_Hours += Float.parseFloat(rs.getString("Hours"));
				}
				dataset.setValue("Time Active", total_Hours);
				System.out.println(total_Hours);
				dataset.setValue("Total Time", (7 * 24));
				System.out.println(7 * 24);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return DrawChar("STATISTIC ON TOTAL TIME WEEK", dataset);
	}

	public JFrame FrameActualforWeek(String Start_date, String End_date) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		String query = "select t.Name,sum(tl.Hours) as Hours from TimeLog tl "
				+ "Left join Tag t on t.Id = tl.Id_Tag " + "where  "
				+ "replace(convert(varchar, tl.Date, 104), '.','-') >= " + "'"
				+ Start_date + "' " + "and "
				+ "replace(convert(varchar, tl.Date, 104), '.','-') <= " + "'"
				+ End_date + "' " + "and " + "tl.Id_User ="+id_user+" "
				+ " group by t.Name" + " order by t.Name";
		try {

			PreparedStatement select_query = db.getConnection()
					.prepareStatement(query);
			ResultSet rs = select_query.executeQuery();
			if (!rs.isBeforeFirst()) {
			} else {
				while (rs.next()) {
					dataset.setValue(rs.getString("Name"),
							Float.parseFloat(rs.getString("Hours")));
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return DrawChar("ACTUAL TAG FOR WEEK", dataset);
	}

	public JFrame FramePlanforWeek(String Start_date) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		String query = "SELECT T.Name,sum(p.Hour) as Hours FROM PLANS P "
				+ "LEFT JOIN TAG T ON T.Id = P.Id_Tag " + "where "
				+ "replace(convert(varchar, p.Start_Day, 104), '.','-') = "
				+ "'" + Start_date + "' " + "and " + "Id_User = "+id_user+" "
				+ "group by T.Name " + "order by T.Name ";
		try {
			PreparedStatement select_query = db.getConnection()
					.prepareStatement(query);
			ResultSet rs = select_query.executeQuery();
			if (!rs.isBeforeFirst()) {
			} else {
				while (rs.next()) {
					dataset.setValue(rs.getString("Name"),
							Float.parseFloat(rs.getString("Hours")));
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return DrawChar("PLAN FOR WEEK", dataset);
	}

	public static String Add_1day(String untildate) {
		String newday = "";
		// current format
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateFormat.parse(untildate));
			cal.add(Calendar.DATE, 1);
			newday = dateFormat.format(cal.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newday;
	}
	public static String Add_7day(String untildate) {
		String newday = "";
		// current format
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateFormat.parse(untildate));
			cal.add(Calendar.DATE, 7);
			newday = dateFormat.format(cal.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newday;
	}

	public void FillPlantoCombobox(JComboBox<String> cb) {
		try {
			PreparedStatement select_query = db
					.getConnection()
					.prepareStatement(
							"select "
									+ "replace(convert(varchar, Start_Day, 104), '.','-') as Start_Day,"
									+ "replace(convert(varchar, End_date, 104), '.','-')as End_date "
									+ "from Plans where Id_User = '"
									+ ""+id_user+"' "
									+ "group by Start_Day,End_date "
									+ "order by Start_Day");
			ResultSet rs = select_query.executeQuery();
			if (!rs.isBeforeFirst()) {
			} else {
				while (rs.next()) {
					cb.addItem(rs.getString("Start_Day") + "->"
							+ rs.getString("End_date"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
