import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import java.awt.Component;


public class Statistics extends JFrame {

	private JPanel contentPane;
	ResultSet rs;
	DatabaseConnection db = new DatabaseConnection();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		//weeks -*--------------------------------------------********************************************************************************************
		
		JButton btnWeeks = new JButton("Weeks");
		btnWeeks.setBounds(146, 10, 84, 29);
		
		btnWeeks.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				try {		
					db.Connect();
					PreparedStatement query = db.getConnection().prepareStatement(
							"Select * from TimeLog");
					ResultSet rs = query.executeQuery();
				
					
					
						
						int hours = Integer.parseInt(rs.getString("Hour"));
						//String x = new String();
						//x = rs.getString("Name");
						DefaultPieDataset dataset1 = new DefaultPieDataset();
					    dataset1.setValue("fgh", hours);
					    dataset1.setValue("abc", 25);
					    dataset1.setValue("xyz", 25);
					    
					 // Create the custom label generator
				        
					    final PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator("{0} = {2}");
					    JFreeChart chart1 = ChartFactory.createPieChart("Weeks", dataset1, true, false, false);
					    PiePlot P = (PiePlot) chart1.getPlot();
				        P.setLabelGenerator(labelGenerator);
					    //P.setForegroundAlpha(0);
					    P.setCircular(true);
					    ChartPanel frame = new ChartPanel(chart1);
			            contentPane.setLayout(new java.awt.BorderLayout());
			            contentPane.add(frame, BorderLayout.SOUTH);
					    contentPane.validate();
					
					
				
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "No connection to server");
				}
				
				// lay du lieu tu db
			/*	DefaultPieDataset dataset1 = new DefaultPieDataset();
			    dataset1.setValue(tagname, 48);
			    dataset1.setValue("gdfg", 25);
			    dataset1.setValue("Work", 25);
			    dataset1.setValue("Sleep", 25); 
			 // Create the custom label generator
		        final PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator("{0} = {2}");
			    JFreeChart chart1 = ChartFactory.createPieChart("Weeks", dataset1, true, false, false);
			    PiePlot P = (PiePlot) chart1.getPlot();
		        P.setLabelGenerator(labelGenerator);
			    //P.setForegroundAlpha(0);
			    P.setCircular(true);
			    ChartPanel frame = new ChartPanel(chart1);
	            contentPane.setLayout(new java.awt.BorderLayout());
	            contentPane.add(frame, BorderLayout.SOUTH);
			    contentPane.validate();*/
			}
		}
		);
		
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		contentPane.add(btnWeeks);
		//years-------------------------------------------------*****************************************************************************************
		
		JButton btnYears = new JButton("Years");
		btnYears.setBounds(242, 10, 78, 29);
		btnYears.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnYears.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultPieDataset dataset2 = new DefaultPieDataset();
			    dataset2.setValue("Study", 23);
			    dataset2.setValue("Relax", 75);
			    dataset2.setValue("Work", 324);
			    dataset2.setValue("Sleep", 23);
			 // Create the custom label generator
		        final PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator("{0} = {2}");
			    JFreeChart chart2 = ChartFactory.createPieChart("Years", dataset2, true, false, false);
			    PiePlot P = (PiePlot) chart2.getPlot();
			    //P.setForegroundAlpha(0);
		        P.setLabelGenerator(labelGenerator);

			    P.setCircular(true);
			    ChartPanel frame = new ChartPanel(chart2);
			   // frame.setVisible(true);
			   // frame.setBounds(100, 300, 300, 200);
			    //frame.setSize(200,200);
			    //contentPane.add(frame);
			    contentPane.setLayout(new java.awt.BorderLayout());
	            //contentPane.add(frame, BorderLayout.AFTER_LAST_LINE);
	           //contentPane.add(frame, new Dimension (200,200));
			    //frame.setSize(200, 200);

			    contentPane.add(frame,BorderLayout.SOUTH);
			    contentPane.validate();
			}
		});
		contentPane.setLayout(null);

		contentPane.add(btnYears);
		//tag in years-----------------------------------*****************************************************************************************
		
		JButton btnTagInYears = new JButton("Tag in Years");
		btnTagInYears.setBounds(325, 10, 121, 29);
		btnTagInYears.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//create data
				DefaultPieDataset dataset3 = new DefaultPieDataset();
			    dataset3.setValue("Study", 324);
			    dataset3.setValue("Relax", 234);
			    dataset3.setValue("Work", 123);
			    dataset3.setValue("Sleep", 890);
			 // Create the custom label generator
		        final PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator("{0} = {2}");

			    JFreeChart chart3 = ChartFactory.createPieChart("Tag in Years", dataset3, true, false, false);
			    PiePlot P = (PiePlot) chart3.getPlot();
		        P.setLabelGenerator(labelGenerator);


			    //P.setForegroundAlpha(0);
			    P.setCircular(true);
			    ChartPanel frame = new ChartPanel(chart3);
			    
			   // frame.setVisible(true);
			    //frame.setBounds(500, 300, 300, 200);

			    //frame.setSize(300,200);
			    contentPane.setLayout(new java.awt.BorderLayout());
	            //contentPane.add(frame, BorderLayout.AFTER_LAST_LINE);
	           // contentPane.add(frame, new Dimension (200,200));
			   // frame.setSize(500, 500);
			    
			    contentPane.add(frame,BorderLayout.SOUTH);
			    contentPane.validate();
			    
			    
			}
		});
		contentPane.setLayout(null);

		contentPane.add(btnTagInYears);
		
		JLabel lblAthena = new JLabel("athena");
		lblAthena.setBounds(31, 25, 61, 16);
		contentPane.add(lblAthena);
		
		JLabel lblAbc = new JLabel("abc");
		lblAbc.setBounds(31, 106, 61, 16);
		contentPane.add(lblAbc);
	}
}
