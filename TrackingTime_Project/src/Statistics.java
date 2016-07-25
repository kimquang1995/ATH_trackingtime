import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.omg.CORBA.PUBLIC_MEMBER;

import com.toedter.calendar.JDayChooser;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import com.toedter.components.JSpinField;
import com.toedter.calendar.JYearChooser;

import javax.swing.border.MatteBorder;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.Component;
import java.awt.Dimension;

public class Statistics extends JFrame {

	private JPanel contentPane;
	private String[] year = new String[100];
	DatabaseConnection db = new DatabaseConnection();
	int iCurentyear = Calendar.getInstance().get(Calendar.YEAR);
	int iCurentDate = Calendar.getInstance().get(Calendar.DATE);
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	JDateChooser dateChooser_Start;
	JDateChooser dateChooser_To;
	JYearChooser yearChooser;
	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { Statistics frame = new
	 * Statistics(); frame.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }
	 */
	/**
	 * Create the frame.
	 */
	public Statistics() {
		try {
			db.Connect();

		} catch (Exception e) {
			e.printStackTrace();
		}
		setTitle("Statistics");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 650, 650);
		contentPane = new JPanel();
		// contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		// weeks
		// -*--------------------------------------------

		JButton btnWeeks = new JButton("Show by Duration");
		btnWeeks.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnWeeks.setBounds(90, 150, 150, 30);

		btnWeeks.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				try {
					DefaultPieDataset dataset1 = new DefaultPieDataset();
					String sQuery = getQueryTimeLog(
							dateFormat.format(dateChooser_Start.getDate()),
							dateFormat.format(dateChooser_To.getDate()));
					PreparedStatement query = db.getConnection()
							.prepareStatement(sQuery);
					ResultSet rs = query.executeQuery();
					if (!rs.isBeforeFirst()) {
						dataset1.setValue("No Values", 0);
					}
					// float hours = Float.parseFloat(rs.getString("Hours"));

					else {
						while (rs.next()) {
							String x = rs.getString("Tags");
							dataset1.setValue(x,
									Float.parseFloat(rs.getString("Hours")));
						}
						// Create the custom label generator
					}

					final PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
							"{0} = {2}");
					JFreeChart chart1 = ChartFactory.createPieChart("Weeks",
							dataset1, true, false, false);
					PiePlot P = (PiePlot) chart1.getPlot();
					P.setLabelGenerator(labelGenerator);
					// P.setForegroundAlpha(0);
					P.setCircular(true);
					ChartPanel frame = new ChartPanel(chart1);
					contentPane.setLayout(new java.awt.BorderLayout());
					contentPane.add(frame, BorderLayout.SOUTH);
					contentPane.validate();
				}

				catch (Exception e1) {
					System.out.println(e1.toString());
				}

			}
		});

		contentPane.setLayout(null);
		contentPane.setLayout(null);
		contentPane.add(btnWeeks);
		// years-------------------------------------------------

		JButton btnYears = new JButton("Show by Year");
		btnYears.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnYears.setBounds(400, 150, 150, 30);
		btnYears.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnYears.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				try {
					DefaultPieDataset dataset2 = new DefaultPieDataset();
					String Squery = "select t.Name as Tags,Sum(tl.Hours) as Hours from TimeLog tl"
							+ " left join Tag t on t.Id = tl.Id_Tag "
							+ "where YEAR(Date) >= '"
							+ yearChooser.getValue()
							+ "' group by t.Name " + "order by Tags";
					PreparedStatement query = db.getConnection()
							.prepareStatement(Squery);
					ResultSet rs = query.executeQuery();
					if (!rs.isBeforeFirst()) {
						dataset2.setValue("No value", 0);
					} else {
						while (rs.next()) {
							dataset2.setValue(rs.getString("Tags"), Float
									.parseFloat(rs.getString("Hours")
											.toString()));
						}

						final PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
								"{0} = {2}");
						JFreeChart chart2 = ChartFactory.createPieChart(
								"Years", dataset2, true, false, false);
						PiePlot P = (PiePlot) chart2.getPlot();
						// P.setForegroundAlpha(0);
						P.setLabelGenerator(labelGenerator);

						P.setCircular(true);
						ChartPanel frame = new ChartPanel(chart2);
						// frame.setVisible(true);
						// frame.setBounds(100, 300, 300, 200);
						// frame.setSize(200,200);
						// contentPane.add(frame);
						contentPane.setLayout(new java.awt.BorderLayout());
						// contentPane.add(frame,
						// BorderLayout.AFTER_LAST_LINE);
						// contentPane.add(frame, new Dimension (200,200));
						// frame.setSize(200, 200);

						contentPane.add(frame, BorderLayout.SOUTH);
						contentPane.validate();

					}
					// /---------------------------------------------

					// Create the custom label generator

				} catch (Exception e2) {
					System.out.println(e2.toString());
				}
			}
		});
		
		contentPane.setLayout(null);

		int startYear = 2000;
		for (int i = 0; i < 100; i++) {
			if (i == 0) {
				year[i] = "Years";
			} else
				year[i] = "" + (startYear + i);

		}

		contentPane.add(btnYears);

		JLabel lblStatistics = new JLabel("STATISTICS");
		lblStatistics.setForeground(new Color(0, 204, 255));
		lblStatistics.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatistics.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblStatistics.setBounds(250, 11, 150, 30);
		contentPane.add(lblStatistics);

		dateChooser_Start = new JDateChooser();
		dateChooser_Start.setBorder(new MatteBorder(0, 2, 2, 0,
				(Color) Color.LIGHT_GRAY));
		dateChooser_Start.setFont(new Font("Tahoma", Font.PLAIN, 12));
		dateChooser_Start.setBounds(40, 90, 100, 30);
		try {
			dateChooser_Start.setDate(dateFormat.parse(SubTrac_7day(dateFormat
					.format(Calendar.getInstance().getTime()))));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		contentPane.add(dateChooser_Start);

		yearChooser = new JYearChooser();
		yearChooser.getSpinner().setFont(new Font("Tahoma", Font.PLAIN, 11));
		yearChooser.getSpinner().setMinimumSize(new Dimension(50, 30));
		yearChooser.getSpinner().setPreferredSize(new Dimension(50, 30));
		yearChooser.setHorizontalAlignment(0);
		yearChooser.setStartYear(2010);
		yearChooser.setFont(new Font("Tahoma", Font.PLAIN, 15));
		yearChooser.setBorder(new MatteBorder(0, 2, 2, 0,
				(Color) Color.LIGHT_GRAY));
		yearChooser.setBounds(400, 90, 150, 30);
		contentPane.add(yearChooser);
		yearChooser.setFocusTraversalPolicy(new FocusTraversalOnArray(
				new Component[] { yearChooser.getSpinner() }));

		JLabel lblTo = new JLabel("----");
		lblTo.setFont(new Font("Roboto", Font.PLAIN, 16));
		lblTo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTo.setBounds(140, 90, 50, 30);
		contentPane.add(lblTo);

		dateChooser_To = new JDateChooser();
		dateChooser_To.setBorder(new MatteBorder(0, 2, 2, 0,
				(Color) Color.LIGHT_GRAY));
		dateChooser_To.setFont(new Font("Tahoma", Font.PLAIN, 12));
		dateChooser_To.setBounds(191, 90, 100, 30);
		dateChooser_To.setDate(Calendar.getInstance().getTime());
		contentPane.add(dateChooser_To);

		// tag in
		// years-----------------------------------

	}


	public static String SubTrac_7day(String untildate) {
		String newday = "";
		// current format
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateFormat.parse(untildate));
			cal.add(Calendar.DATE, -7);
			newday = dateFormat.format(cal.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newday;
	}

	public String getQueryTimeLog(String Start_date, String End_Date)
			throws ParseException {

		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		Date inidate1 = new SimpleDateFormat("dd-MM-yyyy").parse(Start_date);
		Date inidate2 = new SimpleDateFormat("dd-MM-yyyy").parse(End_Date);
		String Start_date_1 = formater.format(inidate1);
		String End_Date_1 = formater.format(inidate2);
		return "select t.Name as Tags,Sum(tl.Hours) as Hours from TimeLog tl"
				+ " left join Tag t on t.Id = tl.Id_Tag "
				+ "where convert(varchar(10),Date, 120) >= '" + Start_date_1
				+ "' " + "and convert(varchar(10),Date, 120)<= '" + End_Date_1
				+ "' group by t.Name " + "order by Tags";
	}
}
