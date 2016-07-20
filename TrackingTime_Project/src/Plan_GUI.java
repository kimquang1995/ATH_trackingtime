import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DateFormatter;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Font;
import java.util.Calendar;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.JComboBox;
import javax.swing.SpinnerModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Plan_GUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
			
		     
				try {
					Plan_GUI frame = new Plan_GUI();
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
	public Plan_GUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 650, 650);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//create hours picker*********************************************************************************************************
		Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 24); // 24 == 12 PM == 00:00:00
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        SpinnerDateModel model = new SpinnerDateModel();
        model.setValue(calendar.getTime());

        JSpinner spinner = new JSpinner(model);
        spinner.setFont(new Font("Roboto", Font.PLAIN, 14));

        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "HH:mm:ss");
        DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
        formatter.setAllowsInvalid(false); // this makes what you want
        formatter.setOverwriteMode(true);

        spinner.setEditor(editor);
		spinner.setBounds(275, 150, 100, 30);
		JLabel lblPlan = new JLabel("PLAN");
		lblPlan.setForeground(new Color(0, 204, 255));
		lblPlan.setFont(new Font("Roboto", Font.BOLD, 26));
		lblPlan.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlan.setBounds(250, 11, 140, 30);
		//close picker
	
		
		
		contentPane.add(lblPlan);
		contentPane.add(spinner);
		
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Roboto", Font.PLAIN, 12));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"2016", "2017", "2018"}));
		comboBox.setBounds(105, 90, 100, 30);
		contentPane.add(comboBox);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setFont(new Font("Roboto", Font.PLAIN, 12));
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		comboBox_1.setBounds(275, 90, 100, 30);
		contentPane.add(comboBox_1);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setFont(new Font("Roboto", Font.PLAIN, 12));
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4"}));
		comboBox_2.setBounds(445, 90, 100, 30);
		contentPane.add(comboBox_2);
		
		JLabel lblYear = new JLabel("Year");
		lblYear.setFont(new Font("Roboto", Font.PLAIN, 17));
		lblYear.setHorizontalAlignment(SwingConstants.CENTER);
		lblYear.setBounds(50, 90, 50, 30);
		contentPane.add(lblYear);
		
		JLabel lblMonth = new JLabel("Month");
		lblMonth.setFont(new Font("Roboto", Font.PLAIN, 16));
		lblMonth.setHorizontalAlignment(SwingConstants.CENTER);
		lblMonth.setBounds(215, 90, 50, 30);
		contentPane.add(lblMonth);
		
		JLabel lblWeek = new JLabel("Week");
		lblWeek.setFont(new Font("Roboto", Font.PLAIN, 17));
		lblWeek.setHorizontalAlignment(SwingConstants.CENTER);
		lblWeek.setBounds(385, 90, 50, 30);
		contentPane.add(lblWeek);
		
		JButton btnCreatePlan = new JButton("Create Plan");
		btnCreatePlan.setFont(new Font("Roboto", Font.PLAIN, 18));
		btnCreatePlan.setBounds(250, 472, 150, 30);
		contentPane.add(btnCreatePlan);
		
		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setFont(new Font("Roboto", Font.PLAIN, 15));
		comboBox_3.setModel(new DefaultComboBoxModel(new String[] {"Study", "Work ", "Relax"}));
		comboBox_3.setBounds(105, 150, 100, 30);
		contentPane.add(comboBox_3);
		
		JLabel lblTags = new JLabel("Tags");
		lblTags.setFont(new Font("Roboto", Font.PLAIN, 17));
		lblTags.setBounds(55, 150, 50, 30);
		contentPane.add(lblTags);
		
		JLabel lblODayLa = new JLabel("o day la table voi 2 cot \"Tag\" va \"Total hours\"");
		lblODayLa.setBounds(231, 334, 218, 58);
		contentPane.add(lblODayLa);
		
		JButton btnNewButton = new JButton("ADD");
		btnNewButton.setBounds(105, 210, 100, 30);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("DELETE");
		btnNewButton_1.setBounds(275, 210, 100, 30);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("UPDATE");
		btnNewButton_2.setBounds(445, 210, 100, 30);
		contentPane.add(btnNewButton_2);
	}
}
