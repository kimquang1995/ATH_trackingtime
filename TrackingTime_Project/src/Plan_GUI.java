import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DateFormatter;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.awt.Color;

import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
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

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Plan_GUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		
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
	}*/

	/**
	 * Create the frame.
	 */
	public Plan_GUI() {
		DatabaseConnection db = new DatabaseConnection();
		try {
			db.Connect();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 650, 650);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//create hours picker*********************************************************************************************************
	/*	Calendar calendar = Calendar.getInstance();
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
		spinner.setBounds(275, 150, 100, 30); */
		JLabel lblPlan = new JLabel("PLAN");
		lblPlan.setForeground(new Color(0, 204, 255));
		lblPlan.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblPlan.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlan.setBounds(250, 11, 140, 30);
		//close picker
	
		
		
		contentPane.add(lblPlan);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new MatteBorder(1, 4, 4, 1, (Color) Color.LIGHT_GRAY));
		scrollPane.setFont(new Font("Roboto", Font.PLAIN, 15));
		scrollPane.setBounds(60, 230, 510, 330);
		contentPane.add(scrollPane);
		
		JLabel lblYear = new JLabel("Start Day");
		lblYear.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblYear.setBounds(60, 70, 70, 30);
		contentPane.add(lblYear);
		
		JButton btnCreatePlan = new JButton("Create Plan");
		btnCreatePlan.setFont(new Font("Roboto", Font.PLAIN, 18));
		btnCreatePlan.setBounds(250, 472, 150, 30);
		contentPane.add(btnCreatePlan);
		
		JComboBox cbTag = new JComboBox();
		cbTag.setFont(new Font("Roboto", Font.PLAIN, 15));
		cbTag.setBounds(150, 120, 100, 30);
		contentPane.add(cbTag);
		try {			
			PreparedStatement query = db.getConnection().prepareStatement(
					"Select * from Tag");
			ResultSet rs = query.executeQuery();
			while (rs.next()) {
				cbTag.addItem(rs.getString("Name"));
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "No connection to server");
		}
		
		JLabel lblTags = new JLabel("Tags");
		lblTags.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTags.setBounds(60, 120, 50, 30);
		contentPane.add(lblTags);
		
		JLabel lblODayLa = new JLabel("o day la table voi 2 cot \"Tag\" va \"Total hours\"");
		lblODayLa.setBounds(231, 334, 218, 58);
		contentPane.add(lblODayLa);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton.setBounds(470, 70, 100, 30);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Delete");
		
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_1.setBounds(470, 170, 100, 30);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Update");
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_2.setBounds(470, 120, 100, 30);
		contentPane.add(btnNewButton_2);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				 char c = e.getKeyChar();
		           if ((!Character.isDigit(c) ||
		              (c == KeyEvent.VK_BACK_SPACE) ||
		              (c == KeyEvent.VK_DELETE))) {
		                e.consume();
		              }
			}
		});
		
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBounds(350, 120, 100, 30);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblHours = new JLabel("Hours");
		lblHours.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblHours.setBounds(280, 120, 50, 30);
		contentPane.add(lblHours);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(150, 70, 100, 30);
		contentPane.add(dateChooser);
	}
}
