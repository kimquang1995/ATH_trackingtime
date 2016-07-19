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

public class Statistics extends JFrame {

	private JPanel contentPane;
	private String[] year = new String[100];
	private String[] week = new String[52];
	DatabaseConnection db = new DatabaseConnection();
	private JComboBox cbWeek;
	private JComboBox cbYear;
	private JComboBox cbSta_Year;

	/**
	 * Launch the application.
	 */
/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Statistics frame = new Statistics();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		// contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		// weeks
		// -*--------------------------------------------

		JButton btnWeeks = new JButton("Show");
		btnWeeks.setBounds(192, 73, 110, 25);

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
		btnYears.setBounds(366, 73, 78, 25);
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
		cbSta_Year = new JComboBox();
		cbSta_Year.setModel(new DefaultComboBoxModel(year));
		cbSta_Year.setBounds(500, 73, 70, 25);
		contentPane.add(cbSta_Year);

		contentPane.add(btnYears);

		JLabel lblStatistics = new JLabel("STATISTICS");
		lblStatistics.setForeground(new Color(60, 179, 113));
		lblStatistics.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatistics.setFont(new Font("Corbel", Font.BOLD, 25));
		lblStatistics.setBounds(343, 11, 129, 29);
		contentPane.add(lblStatistics);

		// WEEK
		cbWeek = new JComboBox();
		cbWeek.setName("");
		cbWeek.setBounds(83, 108, 220, 25);
		contentPane.add(cbWeek);
		cbYear = new JComboBox();
		cbYear.setModel(new DefaultComboBoxModel(year));
		cbYear.setBounds(83, 73, 95, 25);
		contentPane.add(cbYear);
		cbYear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String iWeek1 = "";
					String iWeek2 = "";
					int iYearSelected = Integer.parseInt(cbYear
							.getSelectedItem().toString());
					for (int i = 0; i < 52; i++) {
						if (i == 0) {
							iWeek1 = Add_FirstWeek(iYearSelected);
							week[i] = "WEEK " + (i + 1) + " : " + "1-1-"
									+ iYearSelected + "->" + iWeek1;
						} else {
							week[i] = "WEEK " + (i + 1) + " : " + iWeek1 + "->"
									+ Add_LasttWeek(iWeek1);
							iWeek1 = Add_LasttWeek(iWeek1);
						}
					}

					cbWeek.setModel(new DefaultComboBoxModel(week));

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();

				}
			}
		});

		JLabel lblByWeeks = new JLabel("By Weeks");
		lblByWeeks.setFont(new Font("Corbel", Font.PLAIN, 14));
		lblByWeeks.setBounds(83, 50, 70, 14);
		contentPane.add(lblByWeeks);

		JLabel lblByYears = new JLabel("By Years");
		lblByYears.setFont(new Font("Corbel", Font.PLAIN, 14));
		lblByYears.setBounds(500, 50, 57, 14);
		contentPane.add(lblByYears);
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
}
