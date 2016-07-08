import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
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

public class Statistics_demo extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Statistics_demo frame = new Statistics_demo();
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
	public Statistics_demo() {
		setTitle("Statistics");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//weeks -*--------------------------------------------
		
		JButton btnWeeks = new JButton("Weeks");
		btnWeeks.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultPieDataset dataset1 = new DefaultPieDataset();
			    dataset1.setValue("Study", 25);
			    dataset1.setValue("Relax", 25);
			    dataset1.setValue("Work", 25);
			    dataset1.setValue("Sleep", 25);
			 // Create the custom label generator
		        final PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator("{0} = {2}");
			    JFreeChart chart1 = ChartFactory.createPieChart("Weeks", dataset1, false, false, false);
			    PiePlot P = (PiePlot) chart1.getPlot();
		        P.setLabelGenerator(labelGenerator);

			    //P.setForegroundAlpha(0);
			    P.setCircular(true);
			    ChartFrame frame = new ChartFrame("Statistics in Weeks",chart1);
			    frame.setVisible(true);
			    frame.setSize(350,350);
			}
		});
		btnWeeks.setBounds(27, 36, 115, 25);
		contentPane.add(btnWeeks);
		//years-------------------------------------------------
		
		JButton btnYears = new JButton("Years");
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
			    JFreeChart chart2 = ChartFactory.createPieChart("Years", dataset2, false, false, false);
			    PiePlot P = (PiePlot) chart2.getPlot();
			    //P.setForegroundAlpha(0);
		        P.setLabelGenerator(labelGenerator);

			    P.setCircular(true);
			    ChartFrame frame = new ChartFrame("Statistics in Years",chart2);
			    frame.setVisible(true);
			    frame.setSize(350,350);
			}
		});
		btnYears.setBounds(149, 36, 120, 25);
		contentPane.add(btnYears);
		//tag in years-----------------------------------
		
		JButton btnTagInYears = new JButton("Tag in Years");
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

			    JFreeChart chart3 = ChartFactory.createPieChart("Tag in Years", dataset3, false, false, false);
			    PiePlot P = (PiePlot) chart3.getPlot();
		        P.setLabelGenerator(labelGenerator);


			    //P.setForegroundAlpha(0);
			    P.setCircular(true);
			    ChartFrame frame = new ChartFrame("Statistics Tag in Years",chart3);
			    frame.setVisible(true);
			    frame.setSize(350,350);
			    
			}
		});
		btnTagInYears.setBounds(279, 36, 120, 25);
		contentPane.add(btnTagInYears);
	}
}