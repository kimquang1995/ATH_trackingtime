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
	private String[] week = new String[52];
	DatabaseConnection db = new DatabaseConnection();
	int iCurentyear = Calendar.getInstance().get(Calendar.YEAR);
	int iCurentDate = Calendar.getInstance().get(Calendar.DATE);

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

		JButton btnWeeks = new JButton("Show");
		btnWeeks.setFont(new Font("Roboto", Font.PLAIN, 12));
		btnWeeks.setBounds(119, 150, 100, 30);

		btnWeeks.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int year = Integer
						.parseInt(cbYear.getSelectedItem().toString());
				int week = cbWeek.getSelectedIndex() + 1;

				try {
					DefaultPieDataset dataset1 = new DefaultPieDataset();
					String sQuery = "Select * from Plans where Year=" + year
							+ " and Week =" + week;
					PreparedStatement query = db.getConnection()
							.prepareStatement(sQuery);
					ResultSet rs = query.executeQuery();
					if (!rs.isBeforeFirst()) {
						dataset1.setValue("No Values", 0);
					}
					// float hours = Float.parseFloat(rs.getString("Hours"));

					else {
						while (rs.next()) {
							String sTagQuery = "Select * from Tag where Id = "
									+ Integer.parseInt(rs.getString("Id_Tag"));
							PreparedStatement Tagquery = db.getConnection()
									.prepareStatement(sTagQuery);

							ResultSet Tagrs = Tagquery.executeQuery();
							while (Tagrs.next()) {
								String x = Tagrs.getString("Name");
								dataset1.setValue(x,
										Float.parseFloat(rs.getString("Hour")));
							}
							// Create the custom label generator
						}
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

		JButton btnYears = new JButton("Show");
		btnYears.setFont(new Font("Roboto", Font.PLAIN, 12));
		btnYears.setBounds(410, 150, 100, 30);
		btnYears.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnYears.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					DefaultPieDataset dataset2 = new DefaultPieDataset();
					int year = Integer.parseInt(cbSta_Year.getSelectedItem()
							.toString());
					String Squery = "Select * from Tag";
					PreparedStatement query = db.getConnection()
							.prepareStatement(Squery);
					ResultSet rs = query.executeQuery();
					if (!rs.isBeforeFirst()) {
					} else {
						while (rs.next()) {
							String name = "";
							float hours = 0;
							name = rs.getString("Name").toString();
							String squery = "Select * from Plans where Year ='"
									+ year + "' " + "and Id_Tag ='"
									+ Integer.parseInt(rs.getString("Id"))
									+ "'";
							PreparedStatement queryPlan = db.getConnection()
									.prepareStatement(squery);
							ResultSet rsPlan = queryPlan.executeQuery();
							if (!rsPlan.isBeforeFirst()) {
								dataset2.setValue("No value", 0);
							} else {
								while (rsPlan.next()) {
									hours = hours
											+ Float.parseFloat(rsPlan
													.getString("Hour")
													.toString());
								}
								dataset2.setValue(name, hours);
							}

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
		lblStatistics.setFont(new Font("Roboto", Font.BOLD, 26));
		lblStatistics.setBounds(250, 11, 145, 30);
		contentPane.add(lblStatistics);
		FillComboboxWeek();

		JLabel lblByWeeks = new JLabel("From");
		lblByWeeks.setFont(new Font("Roboto", Font.PLAIN, 14));
		lblByWeeks.setBounds(10, 107, 40, 15);
		contentPane.add(lblByWeeks);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(60, 90, 100, 40);
		contentPane.add(dateChooser);
		
		
		
		JYearChooser yearChooser = new JYearChooser();
		yearChooser.getSpinner().setMinimumSize(new Dimension(50, 30));
		yearChooser.getSpinner().setPreferredSize(new Dimension(50, 30));
		yearChooser.setHorizontalAlignment(0);
		yearChooser.setStartYear(2010);
		yearChooser.setFont(new Font("Roboto", Font.PLAIN, 18));
		yearChooser.setBorder(new MatteBorder(1, 2, 2, 1, (Color) Color.LIGHT_GRAY));
		yearChooser.setBounds(400, 90, 100, 40);
		contentPane.add(yearChooser);
		yearChooser.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{yearChooser.getSpinner()}));
		
		JLabel lblTo = new JLabel("To");
		lblTo.setBounds(170, 108, 46, 14);
		contentPane.add(lblTo);
		
		JDateChooser dateChooser_1 = new JDateChooser();
		dateChooser_1.setBounds(191, 90, 100, 40);
		contentPane.add(dateChooser_1);
		
		
		// tag in
		// years-----------------------------------

	}

	public static String Add_FirstWeek(int year) {
		String untildate = "1-1-" + year;
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

	public static String Add_LasttWeek(String untildate) {
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

	private void FillComboboxWeek() {
		String iWeek1 = "";
		for (int i = 0; i < 52; i++) {
			if (i == 0) {
				iWeek1 = Add_FirstWeek(iYearSelected);
				week[i] = "WEEK " + (i + 1) + " : " + "1-1-" + iYearSelected
						+ "->" + iWeek1;
			} else {
				week[i] = "WEEK " + (i + 1) + " : " + iWeek1 + "->"
						+ Add_LasttWeek(iWeek1);
				iWeek1 = Add_LasttWeek(iWeek1);
			}
		}
	}
}
