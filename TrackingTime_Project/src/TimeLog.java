import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class TimeLog extends JFrame {
	private JPanel contentPane1;
	private JTextField txtName;
	private JTable table;
	Date d1 = null, d2 = null;
	int row;
	ResultSet rs;
	String TagName;
	String SelectedId;
	DatabaseConnection db = new DatabaseConnection();
	String id_tag;
	SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
	int id_User;
	String dtbquery;
	String queryLoad;

	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { TimeLog frame = new TimeLog();
	 * frame.setVisible(true); } catch (Exception e) { e.printStackTrace(); } }
	 * }); }
	 */

	/**
	 * Create the frame.
	 */
	public TimeLog(String selectDate, int Id_user) {
		try {

			db.Connect();
			contentPane1 = new JPanel();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date seleDate = sdf.parse(selectDate);
			SimpleDateFormat dtb = new SimpleDateFormat("yyyy-MM-dd");
			dtbquery = dtb.format(seleDate);
			JLabel lblChoseDay = new JLabel();
			lblChoseDay.setText("For Day: " + dtbquery);
			lblChoseDay.setFont(new Font("Roboto", Font.BOLD, 20));
			lblChoseDay.setBounds(230, 50, 200, 50);
			contentPane1.add(lblChoseDay);
			id_User = Id_user;
			queryLoad = "select Id,Name,Hours,Id_User,  convert(char(5), Start_Time, 108) Start_Time,convert(char(5), End_Time, 108) "
					+ "End_Time from TimeLog where Date ='"
					+ dtbquery
					+ "' and id_User='" + id_User + "'";

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		setTitle("Time Log");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 650, 650);

		contentPane1.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane1);
		contentPane1.setLayout(null);

		JLabel lblTitle = new JLabel("TIMELOG");
		lblTitle.setForeground(new Color(0, 204, 255));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Roboto", Font.BOLD, 26));
		lblTitle.setBounds(250, 11, 145, 30);
		contentPane1.add(lblTitle);

		JLabel lblTag = new JLabel("Tag");
		lblTag.setHorizontalAlignment(SwingConstants.LEFT);
		lblTag.setFont(new Font("Roboto", Font.PLAIN, 16));
		lblTag.setBounds(60, 120, 100, 30);
		contentPane1.add(lblTag);

		JLabel lblName = new JLabel("Content");
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		lblName.setFont(new Font("Roboto", Font.PLAIN, 16));
		lblName.setBounds(60, 170, 100, 30);
		contentPane1.add(lblName);

		JLabel lblStart = new JLabel("Start");
		lblStart.setHorizontalAlignment(SwingConstants.LEFT);
		lblStart.setFont(new Font("Roboto", Font.PLAIN, 16));
		lblStart.setBounds(60, 220, 100, 30);
		contentPane1.add(lblStart);

		JLabel lblEnd = new JLabel("End");
		lblEnd.setHorizontalAlignment(SwingConstants.LEFT);
		lblEnd.setFont(new Font("Roboto", Font.PLAIN, 16));
		lblEnd.setBounds(60, 270, 100, 30);
		contentPane1.add(lblEnd);

		JComboBox cmb = new JComboBox();
		cmb.setFont(new Font("Roboto", Font.PLAIN, 15));
		cmb.setBounds(187, 120, 223, 30);
		cmb.addItem("All");
		contentPane1.add(cmb);
		try {
			PreparedStatement query = db.getConnection().prepareStatement(
					"Select * from Tag");
			ResultSet rs = query.executeQuery();
			if (!rs.isBeforeFirst()) {

			} else {

				while (rs.next()) {
					cmb.addItem(rs.getString("Name"));
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "No connection to server");
		}

		cmb.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				TagName = cmb.getSelectedItem().toString();
				try {
					if (TagName != "All") {
						PreparedStatement subquery = db.getConnection()
								.prepareStatement(
										"Select top 1 * from Tag where Name LIKE '"
												+ TagName + "'");
						ResultSet subrs = subquery.executeQuery();
						if (!subrs.isBeforeFirst()) {
						} else {
							while (subrs.next()) {
								id_tag = subrs.getString("Id");
								System.out.println(id_tag);
								Load("select Id,Name,Hours,Id_User,  convert(char(5), Start_Time, 108) Start_Time,convert(char(5), End_Time, 108) "
										+ "End_Time from TimeLog where Date ='"
										+ dtbquery
										+ "' and id_User='"
										+ id_User
										+ "' and Id_Tag= '"
										+ id_tag
										+ "'");
							}
						}
					} else {
						Load(queryLoad);
						id_tag = null;
					}
				} catch (Exception e1) {
				}
			}
		});

		txtName = new JTextField();
		txtName.setFont(new Font("Roboto", Font.PLAIN, 15));
		txtName.setBounds(187, 170, 223, 30);
		contentPane1.add(txtName);
		txtName.setColumns(10);

		/*
		 * txtHours = new JTextField(); txtHours.setBounds(187, 212, 50, 25);
		 * contentPane1.add(txtHours); txtHours.setColumns(10);
		 */

		Vector hour = new Vector();
		for (int i = 0; i <= 23; i++) {
			hour.add(i);
		}

		Vector min = new Vector();
		for (int i = 0; i <= 59; i++) {
			min.add(i);
		}

		JComboBox cmbSHour = new JComboBox(hour);
		cmbSHour.setFont(new Font("Roboto", Font.PLAIN, 15));
		cmbSHour.setBounds(187, 220, 50, 30);
		contentPane1.add(cmbSHour);

		JComboBox cmbSMin = new JComboBox(min);
		cmbSMin.setFont(new Font("Roboto", Font.PLAIN, 15));
		cmbSMin.setBounds(270, 220, 50, 30);
		contentPane1.add(cmbSMin);

		JComboBox cmbEHour = new JComboBox(hour);
		cmbEHour.setFont(new Font("Roboto", Font.PLAIN, 15));
		cmbEHour.setBounds(187, 270, 50, 30);
		contentPane1.add(cmbEHour);

		JComboBox cmbEMin = new JComboBox(min);
		cmbEMin.setFont(new Font("Roboto", Font.PLAIN, 15));
		cmbEMin.setBounds(270, 270, 50, 30);
		contentPane1.add(cmbEMin);

		JLabel lblDot1 = new JLabel(":");
		lblDot1.setBounds(252, 220, 50, 25);
		contentPane1.add(lblDot1);

		JLabel lblDot2 = new JLabel(":");
		lblDot2.setBounds(252, 270, 50, 25);
		contentPane1.add(lblDot2);
		// Add Button
		JButton btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("Roboto", Font.PLAIN, 15));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String srHour = (cmbSHour.getSelectedItem().toString() + ":"
						+ cmbSMin.getSelectedItem().toString() + ":" + "00");
				String erHour = (cmbEHour.getSelectedItem().toString() + ":"
						+ cmbEMin.getSelectedItem().toString() + ":" + "00");
				try {
					d1 = format.parse(srHour);
				} catch (java.text.ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					d2 = format.parse(erHour);
				} catch (java.text.ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				double diff = d2.getTime() - d1.getTime();
				double diffHours = diff / (60 * 60 * 1000);
				String name = txtName.getText();
				boolean result = false;
				// Pattern patternName = Pattern.compile("^[a-zA-Z_0-9]+$");
				// Matcher matcherName = patternName.matcher(name); // Your
				// String should come here
				int sHours = Integer.parseInt(cmbSHour.getSelectedItem()
						.toString());
				int eHours = Integer.parseInt(cmbEHour.getSelectedItem()
						.toString());
				int sMin = Integer.parseInt(cmbSMin.getSelectedItem()
						.toString());
				int eMin = Integer.parseInt(cmbEMin.getSelectedItem()
						.toString());
				if ((sHours < eHours) || (sHours == eHours && sMin < eMin))
					result = true;
				else
					result = false;
				if (result == true) {
					try {

						if (id_tag != null) {

							int id_tagI = Integer.parseInt(id_tag);
							String queryS = "Insert into TimeLog"
									+ "(Name,Hours,Date,Id_Tag,Start_Time,End_Time,Id_User)"
									+ "values" + "('" + name + "','"
									+ diffHours + "','" + dtbquery + "','"
									+ id_tagI + "','" + sHours + ":" + sMin
									+ "','" + eHours + ":" + eMin + "','"
									+ id_User + "')";
							PreparedStatement query = db.getConnection()
									.prepareStatement(queryS);
							query.executeUpdate();
							table.repaint();
							JOptionPane.showMessageDialog(null,
									"Insert Comleted.", "Successful",
									JOptionPane.INFORMATION_MESSAGE);
							Load(queryLoad);
							txtName.setText(null);
							cmb.setSelectedIndex(0);

						} else {
							JOptionPane.showMessageDialog(null,
									"Please Select Tag", "Error",
									JOptionPane.OK_OPTION);
							Load(queryLoad);

						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				} else {
					JOptionPane.showMessageDialog(null, "Content mustn't Null."
							+ "\n" + "End Time must greater than Start Time",
							"Error", JOptionPane.OK_OPTION);
				}
				table.getColumnModel().getColumn(0)
						.setCellRenderer(new DateCellRenderer());
			}
		});
		btnAdd.setBounds(470, 120, 100, 30);
		contentPane1.add(btnAdd);
		// Edit Button
		JButton btnEdit = new JButton("Edit");
		btnEdit.setFont(new Font("Roboto", Font.PLAIN, 15));
		btnEdit.setEnabled(false);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				double diff = d2.getTime() - d1.getTime();
				double diffHours = diff / (60 * 60 * 1000);
				String name = txtName.getText();
				boolean result = false;
				Pattern patternName = Pattern.compile("^[a-zA-Z_0-9]+$");
				// Matcher matcherName = patternName.matcher(name); // Your
				// String should come here
				int sHours = Integer.parseInt(cmbSHour.getSelectedItem()
						.toString());
				int eHours = Integer.parseInt(cmbEHour.getSelectedItem()
						.toString());
				int sMin = Integer.parseInt(cmbSMin.getSelectedItem()
						.toString());
				int eMin = Integer.parseInt(cmbEMin.getSelectedItem()
						.toString());
				if ((sHours < eHours) || (sHours == eHours && sMin < eMin))
					result = true;
				else
					result = false;

				if (result == true) {

					try {
						String srHour = (cmbSHour.getSelectedItem().toString()
								+ ":" + cmbSMin.getSelectedItem().toString()
								+ ":" + "00");
						String erHour = (cmbEHour.getSelectedItem().toString()
								+ ":" + cmbEMin.getSelectedItem().toString()
								+ ":" + "00");
						try {
							d1 = format.parse(srHour);
						} catch (java.text.ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							d2 = format.parse(erHour);
						} catch (java.text.ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						PreparedStatement query = db.getConnection()
								.prepareStatement(
										"Update TimeLog set Name='" + name
												+ "',Hours='" + diffHours
												+ "',Start_Time='" + srHour
												+ "',End_Time='" + erHour
												+ "' where id ='" + SelectedId
												+ "'");
						query.executeUpdate();
						table.repaint();
						JOptionPane.showMessageDialog(null, "Edit Completed",
								"Successful", JOptionPane.INFORMATION_MESSAGE);
						Load(queryLoad);
						txtName.setText(null);

					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Content mustn't Null."
							+ "\n" + "End Time must greater than Start Time",
							"Error", JOptionPane.OK_OPTION);
				}
			}
		});
		btnEdit.setBounds(470, 170, 100, 30);
		contentPane1.add(btnEdit);

		// Delete Button

		JButton btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Roboto", Font.PLAIN, 15));
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.getColumnModel().getColumn(0)
						.setCellRenderer(new DateCellRenderer());
				if (JOptionPane.showConfirmDialog(null, "Are You Sure?",
						"Delete", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					table.getColumnModel().getColumn(0)
							.setCellRenderer(new DateCellRenderer());
					txtName.setText(null);
					try {

						PreparedStatement query = db.getConnection()
								.prepareStatement(
										"Delete from TimeLog where Id='"
												+ SelectedId + "'");
						query.executeUpdate();
						table.getColumnModel().getColumn(0)
								.setCellRenderer(new DateCellRenderer());
						DefaultTableModel a = (DefaultTableModel) table
								.getModel();

						while (a.getRowCount() > 0) {
							for (int i = 0; i < a.getRowCount(); ++i) {
								a.removeRow(i);
								table.getColumnModel()
										.getColumn(0)
										.setCellRenderer(new DateCellRenderer());
							}
						}
						table.setModel(a);
						table.getColumnModel().getColumn(0)
								.setCellRenderer(new DateCellRenderer());
						Load(queryLoad);
						table.getColumnModel().getColumn(0)
								.setCellRenderer(new DateCellRenderer());
						JOptionPane.showMessageDialog(null, "Delete Completed",
								"Successful", JOptionPane.INFORMATION_MESSAGE);
						table.getColumnModel().getColumn(0)
								.setCellRenderer(new DateCellRenderer());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					btnDelete.setEnabled(false);
					table.getColumnModel().getColumn(0)
							.setCellRenderer(new DateCellRenderer());
				}
			}
		});
		btnDelete.setBounds(470, 220, 100, 30);
		contentPane1.add(btnDelete);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setFont(new Font("Roboto", Font.PLAIN, 12));
		scrollPane.setBorder(new MatteBorder(1, 4, 4, 1, (Color) new Color(192,
				192, 192)));
		scrollPane.setBounds(60, 320, 510, 250);
		contentPane1.add(scrollPane);

		table = new JTable();
		table.setDefaultEditor(Object.class, null);
		scrollPane.setViewportView(table);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFont(new Font("Arial", Font.PLAIN, 18));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(String.class, centerRenderer);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Load(queryLoad);
		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				btnEdit.setEnabled(true);
				btnDelete.setEnabled(true);
				row = table.getSelectedRow();
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				String SelectedName = model.getValueAt(row, 1).toString();
				String S = model.getValueAt(row, 2).toString() + ":00";
				String E = model.getValueAt(row, 3).toString() + ":00";
				txtName.setText(SelectedName);

				try {
					d1 = format.parse(S);
				} catch (java.text.ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					d2 = format.parse(E);
				} catch (java.text.ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				int rSHours = d1.getHours();
				cmbSHour.setSelectedItem(rSHours);
				int rSMin = d1.getMinutes();
				cmbSMin.setSelectedItem(rSMin);
				int rEHours = d2.getHours();
				cmbEHour.setSelectedItem(rEHours);
				int rEMin = d2.getMinutes();
				cmbEMin.setSelectedItem(rEMin);
				SelectedId = (model.getValueAt(row, 0)).toString();
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

	}

	public void Load(String queryExe) {
		try {
			DefaultTableModel model = new DefaultTableModel(
					new String[] {"Serial Number", "Name", "Start Time",
							"End Time", "Hours" }, 0);
			PreparedStatement query = db.getConnection().prepareStatement(
					queryExe);
			ResultSet rs = query.executeQuery();
			Object[] row = new Object[5];
			if (!rs.isBeforeFirst()) {
				table.setModel(model);
			} else {
				
				while (rs.next()) {
					row[0] = rs.getString("Id");
					row[1] = rs.getString("Name");
					row[2] = rs.getString("Start_Time");
					row[3] = rs.getString("End_Time");
					row[4] = rs.getString("Hours");
					model.addRow(row);
				}
				table.setModel(model);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public class DateCellRenderer extends DefaultTableCellRenderer {
		public DateCellRenderer() {
			super();
		}

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocused, int row,
				int col) {
			if (col == 5) {
				if (value instanceof Date) {
					// value = df.format(value);
				}
			}
			if (col == 0) {
				value = row + 1;
			}

			return super.getTableCellRendererComponent(table, value,
					isSelected, hasFocused, row, col);
		}
	}
}
