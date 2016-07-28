import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.List;

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
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.awt.Color;

import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
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
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.color.CMMException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//import TimeLog.DateCellRenderer;

import com.toedter.calendar.JDateChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Plan_GUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtHours;
	int row;
	String tag, hours;
	String chosserDay;
	DefaultTableModel model;
	DatabaseConnection db = new DatabaseConnection();
	int id_user;
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	JButton btnAdd;
	JButton btnDelete;
	JButton btnUpdate;
	JButton btnCreatePlan;
	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 * 
	 * @throws ParseException
	 */
	public Plan_GUI(int id_User) throws ParseException {
		setTitle("CREATE PLAN");
		id_user = id_User;

		model = new DefaultTableModel(new String[] { "Tag", "Hours" }, 0);
		DatabaseConnection db = new DatabaseConnection();
		try {
			db.Connect();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// setBounds(100, 100, 380, 130);
		setBounds(100, 100, 560, 500);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel lblPlan = new JLabel("CREATE PLAN");
		lblPlan.setForeground(new Color(0, 204, 255));
		lblPlan.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblPlan.setHorizontalAlignment(SwingConstants.CENTER);
		// lblPlan.setBounds(120, 10, 150, 30);
		lblPlan.setBounds(200, 10, 150, 30);
		// close picker

		JLabel lblStar_Day = new JLabel("Start Day");
		lblStar_Day.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblStar_Day.setBounds(60, 50, 70, 30);
		contentPane.add(lblStar_Day);

		//
		JLabel lblTags = new JLabel("Tag");
		lblTags.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTags.setBounds(60, 100, 50, 30);
		contentPane.add(lblTags);
		// --------------------------------------------

		JComboBox cbTag = new JComboBox();
		cbTag.setFont(new Font("Roboto", Font.PLAIN, 15));
		cbTag.setBounds(150, 100, 200, 30);
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

		JDateChooser StartDay = new JDateChooser();
		StartDay.setBounds(150, 50, 200, 30);
		contentPane.add(StartDay);
		chosserDay = (dateFormat.format(Calendar
				.getInstance().getTime()));
		StartDay.setDate(dateFormat.parse(dateFormat.format(Calendar
				.getInstance().getTime())));
		StartDay.addPropertyChangeListener("date",
				new PropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						Date date = (Date) evt.getNewValue();
						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"dd-MM-yyyy");
						chosserDay = dateFormat.format(date).toString();
						try {
							if(model.getRowCount()!=0)
							{
								btnCreatePlan.setEnabled(true);
							}
							else
							{
								btnCreatePlan.setEnabled(false);
							}
							if(CheckDuplicasePlan(chosserDay)==true)
							{
								btnAdd.setEnabled(false);
							}
							else
							{
								btnAdd.setEnabled(true);
							}
						} catch (HeadlessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						for (int i = model.getRowCount() - 1; i >= 0; i--) {
							model.removeRow(i);
						}
						System.out.println(chosserDay);
					}
				});

		JLabel lblHours = new JLabel("Hours");
		lblHours.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblHours.setBounds(60, 150, 50, 30);
		contentPane.add(lblHours);
		//
		txtHours = new JTextField();
		txtHours.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if ((!Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					e.consume();
				}
			}
		});

		txtHours.setHorizontalAlignment(SwingConstants.CENTER);
		txtHours.setBounds(150, 150, 200, 30);
		contentPane.add(txtHours);
		txtHours.setColumns(10);

		contentPane.add(lblPlan);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new MatteBorder(1, 4, 4, 1,
				(Color) Color.LIGHT_GRAY));
		scrollPane.setFont(new Font("Roboto", Font.PLAIN, 15));
		scrollPane.setBounds(60, 200, 420, 200);
		contentPane.add(scrollPane);

		JTable table = new JTable();
		table.setDefaultEditor(Object.class, null);
		scrollPane.setViewportView(table);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFont(new Font("Arial", Font.PLAIN, 18));
		table.setModel(model);
		// Create a new table instance

		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				// btnEdit.setEnabled(true);
				// btnDelete.setEnabled(true);
				row = table.getSelectedRow();
				tag = model.getValueAt(row, 0).toString();
				hours = model.getValueAt(row, 1).toString();
				cbTag.setSelectedItem(tag);
				txtHours.setText(hours);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAdd.setBounds(380, 50, 100, 30);
		contentPane.add(btnAdd);
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(txtHours.getText().length()!=0)
				{
				String cmb = cbTag.getSelectedItem().toString();
				String hours = txtHours.getText();
				model.addRow(new Object[] { cmb, hours });
				txtHours.setText(null);
				}
				if(model.getRowCount()!=0)
				{
					btnCreatePlan.setEnabled(true);
				}
				else
				{
					btnCreatePlan.setEnabled(false);
				}
			}

		});
		btnDelete = new JButton("Delete");

		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDelete.setBounds(380, 150, 100, 30);
		contentPane.add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int row = table.getSelectedRow();
				model.removeRow(row);
				txtHours.setText(null);
				if(model.getRowCount()!=0)
				{
					btnCreatePlan.setEnabled(true);
				}
				else
				{
					btnCreatePlan.setEnabled(false);
				}
			}
		});

		btnUpdate = new JButton("Update");
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnUpdate.setBounds(380, 100, 100, 30);
		contentPane.add(btnUpdate);
		btnUpdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(txtHours.getText().length()!=0)
				{
				model.setValueAt(txtHours.getText(), row, 1);
				}

			}
		});
		btnCreatePlan = new JButton("Create Plan");
		btnCreatePlan.setFont(new Font("Roboto", Font.PLAIN, 18));
		btnCreatePlan.setBounds(200, 420, 150, 30);
		btnCreatePlan.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					boolean flag = false;
					SimpleDateFormat formatDate = new SimpleDateFormat(
							"yyyy-MM-dd");
					String Start_day = formatDate.format(new SimpleDateFormat(
							"dd-MM-yyyy").parse(chosserDay.toString()));
					String End_day = formatDate.format(new SimpleDateFormat(
							"dd-MM-yyyy").parse(Add_7day(chosserDay.toString())));

					for (int i = 0; i < model.getRowCount(); i++) {
						String id_Tag = model.getValueAt(i, 0).toString();
						String Hour = model.getValueAt(i, 1).toString();
						PreparedStatement Tag_query = db.getConnection()
								.prepareStatement(
										"Select * from Tag where Name ='"
												+ id_Tag + "'");
						ResultSet rs = Tag_query.executeQuery();
						while (rs.next()) {
							id_Tag = rs.getString("Id");
						}
						String query = "insert into Plans(Start_Day,End_date,Id_Tag,Hour,Id_User) values ('"
								+ Start_day
								+ "','"
								+ End_day
								+ "','"
								+ id_Tag
								+ "','" + Hour + "','" + id_user + "')";
						PreparedStatement Insert_query = db.getConnection()
								.prepareStatement(query);
						Insert_query.executeUpdate();
					}
					JOptionPane.showMessageDialog(null, "Insert Completed.",
							"Successful", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Insert Fail.",
							"Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(btnCreatePlan);
		if(model.getRowCount()!=0)
		{
			btnCreatePlan.setEnabled(true);
		}
		else
		{
			btnCreatePlan.setEnabled(false);
		}
		if(CheckDuplicasePlan(chosserDay)==true)
		{
			btnAdd.setEnabled(false);
		}
		else
		{
			btnAdd.setEnabled(true);
		}
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

	public static boolean CheckDuplicasePlan(String Start_Day)
			throws HeadlessException, ParseException {
		boolean flag = false;
		DatabaseConnection db = new DatabaseConnection();
		String Start_D = "";
		String dateSelect = "";
		try {
			db.Connect();
			for (int i = 0; i < 7; i++) {
				dateSelect = new SimpleDateFormat("yyyy-MM-dd")
						.format(new SimpleDateFormat("dd-MM-yyyy")
								.parse(Start_Day));
				String query = "SELECT * FROM Plans where convert(varchar(10),Start_Day, 120) <= '"
						+ dateSelect + "' and "
								+ "convert(varchar(10),End_date, 120) >= '"
						+ dateSelect + "'";
				PreparedStatement select_query = db.getConnection()
						.prepareStatement(query);
				ResultSet rs = select_query.executeQuery();
				if (!rs.isBeforeFirst()) {
				} else {
					while (rs.next()) {
						Start_D = new SimpleDateFormat("dd-MM-yyyy")
								.format(new SimpleDateFormat("yyyy-MM-dd")
										.parse(rs.getString("Start_Day")));
					
					}
					flag = true;
				}
				Start_Day = Add_1day(Start_Day);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (flag) {
			JOptionPane.showMessageDialog(
					null,"Have a Day is exist in Plan from "
							+ Start_D
							+ " to "
							+ Add_7day(Start_D), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return flag;
	}

}