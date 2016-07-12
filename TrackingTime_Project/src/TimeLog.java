import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultCellEditor;
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
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Locale;

import net.proteanit.sql.DbUtils;
import net.sourceforge.jtds.jdbc.DateTime;

public class TimeLog extends JFrame {
	private JPanel contentPane1;
	private JTextField textField;
	private JTextField txtName;
	private JTextField txtHours;
	private JTable table;
	int row;
	private JPanel contentPane;
	ResultSet rs;
	String TagName;
	String SelectedId;
	DatabaseConnection db = new DatabaseConnection();
	String id_tag;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TimeLog frame = new TimeLog();
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
	public TimeLog() {
		try {
			db.Connect();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		setTitle("Time Log");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 562, 540);
		contentPane1 = new JPanel();
		contentPane1.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane1);
		contentPane1.setLayout(null);

		JLabel lblTitle = new JLabel("Time Log");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
		lblTitle.setBounds(213, 13, 123, 29);
		contentPane1.add(lblTitle);

		JLabel lblDay = new JLabel("Day :");
		lblDay.setFont(new Font("Arial", Font.BOLD, 22));
		lblDay.setBounds(167, 55, 56, 29);
		contentPane1.add(lblDay);

		JLabel lblChoseDay = new JLabel("New label");
		lblChoseDay.setFont(new Font("Arial", Font.BOLD, 22));
		lblChoseDay.setBounds(233, 55, 200, 29);
		contentPane1.add(lblChoseDay);

		JLabel lblTag = new JLabel("Tag");
		lblTag.setFont(new Font("Arial", Font.BOLD, 20));
		lblTag.setBounds(37, 123, 45, 29);
		contentPane1.add(lblTag);

		JLabel lblName = new JLabel("Task Name");
		lblName.setFont(new Font("Arial", Font.BOLD, 20));
		lblName.setBounds(37, 165, 111, 29);
		contentPane1.add(lblName);

		JLabel lblHours = new JLabel("Hours");
		lblHours.setFont(new Font("Arial", Font.BOLD, 20));
		lblHours.setBounds(37, 207, 65, 29);
		contentPane1.add(lblHours);

		JComboBox cmb = new JComboBox();
		cmb.setBounds(167, 128, 223, 22);
		cmb.addItem("All");
		contentPane1.add(cmb);
		try {			
			PreparedStatement query = db.getConnection().prepareStatement(
					"Select * from Tag");
			ResultSet rs = query.executeQuery();
			while (rs.next()) {
				cmb.addItem(rs.getString("Name"));
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
						while (subrs.next()) {
							id_tag = subrs.getString("Id");
							System.out.println(id_tag);
							Load("Select * from TimeLog Where Id_Tag LIKE '"
									+ id_tag + "'");							
						}
					} if(TagName.equals("All")){
						Load("Select * from TimeLog");
						id_tag=null;
					System.out.println(id_tag);
					}

				} catch (Exception e1) {
				}
			}
		});

		txtName = new JTextField();
		txtName.setBounds(167, 170, 223, 22);
		contentPane1.add(txtName);
		txtName.setColumns(10);

		txtHours = new JTextField();
		txtHours.setBounds(167, 212, 223, 22);
		contentPane1.add(txtHours);
		txtHours.setColumns(10);
		// Add Button
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String name = txtName.getText();
				Double hours = Double
						.parseDouble(txtHours.getText().toString());
				try {
					if (id_tag != null) {
						String now = LocalDate.now().toString();
						System.out.println(id_tag);
						int id_tagI = Integer.parseInt(id_tag);
						String queryS="Insert into TimeLog"
						+ "(Name,Hours,Date,Id_Tag)"
						+"values"
						+ "('"+name+"','"+hours+"','"+now+"','"+id_tagI+"')";
						PreparedStatement query = db.getConnection()
								.prepareStatement(queryS);
										
						query.executeUpdate();
						// ResultSet rs = query.executeQuery();
						// rs.close();
						// table.setModel(DbUtils.resultSetToTableModel(rs));
						table.repaint();
						JOptionPane.showMessageDialog(null, "Success");
						Load("Select * from TimeLog");
					}else {
						JOptionPane.showConfirmDialog(null, "All Sao ADD", "Error", 1);
						Load("Select * from TimeLog");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				txtName.setText(null);
				txtHours.setText(null);

			}
		});
		btnAdd.setBounds(422, 127, 97, 25);
		contentPane1.add(btnAdd);
		// Edit Button
		JButton btnEdit = new JButton("Edit");
		btnEdit.setEnabled(false);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtName.getText();
				Double hours = Double.parseDouble(txtHours.getText());
				try {

					PreparedStatement query = db.getConnection()
							.prepareStatement(
									"Update TimeLog set Name='" + name
											+ "',Hours='" + hours
											+ "' where id ='" + SelectedId
											+ "'");
					query.executeUpdate();
					// rs.close();
					// table.setModel(DbUtils.resultSetToTableModel(rs));
					table.repaint();
					JOptionPane.showMessageDialog(null, "Success");
					Load("Select * from TimeLog");
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				txtName.setText(null);
				txtHours.setText(null);
			}
		});
		btnEdit.setBounds(422, 169, 97, 25);
		contentPane1.add(btnEdit);

		// Delete Button

		JButton btnDelete = new JButton("Delete");
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtName.setText(null);
				txtHours.setText(null);
				try {

					PreparedStatement query = db.getConnection()
							.prepareStatement(
									"Delete from TimeLog where Id='"
											+ SelectedId + "'");
					query.executeUpdate();
					JOptionPane.showMessageDialog(null, "Success");
					DefaultTableModel a = (DefaultTableModel) table.getModel();

					while (a.getRowCount() > 0) {
						for (int i = 0; i < a.getRowCount(); ++i) {
							a.removeRow(i);
						}
					}
					table.setModel(a);
					Load("Select * from TimeLog");

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
		btnDelete.setBounds(422, 211, 97, 25);
		contentPane1.add(btnDelete);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(37, 261, 482, 194);
		contentPane1.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		Load("Select * from TimeLog");
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFont(new Font("Arial", Font.PLAIN, 18));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(String.class, centerRenderer);
		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				btnEdit.setEnabled(true);
				btnDelete.setEnabled(true);
				row = table.getSelectedRow();
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				String SelectedName = model.getValueAt(row, 1).toString();
				Double SelectedHours = Double.parseDouble(model.getValueAt(row,
						2).toString());
				txtName.setText(SelectedName);
				txtHours.setText(SelectedHours.toString());
				SelectedId = (model.getValueAt(row, 0)).toString();
			}
		});
		/*
		 * ListSelectionModel SelectionModel = table.getSelectionModel();
		 * SelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		 * SelectionModel.addListSelectionListener(new ListSelectionListener() {
		 * public void valueChanged(ListSelectionEvent e) {
		 * btnEdit.setEnabled(true); btnDelete.setEnabled(true); row =
		 * table.getSelectedRow(); DefaultTableModel model = (DefaultTableModel)
		 * table.getModel(); String SelectedName = model.getValueAt(row,
		 * 1).toString(); Double SelectedHours =
		 * Double.parseDouble(model.getValueAt(row,2).toString());
		 * txtName.setText(SelectedName);
		 * txtHours.setText(SelectedHours.toString()); SelectedId =
		 * (model.getValueAt(row, 0)).toString(); }
		 * 
		 * });
		 */
	}

	public void Load(String queryExe) {
		try {
			DefaultTableModel model = new DefaultTableModel(new String[] {
					"Id", "Name", "Hours", "Id_Tag" }, 0);
			PreparedStatement query = db.getConnection().prepareStatement(
					queryExe);
			ResultSet rs = query.executeQuery();
			Object[] row = new Object[4];
			while (rs.next()) {
				row[0] = rs.getString("Id");
				row[1] = rs.getString("Name");
				row[2] = rs.getString("Hours");
				row[3] = rs.getString("Id_Tag");
				model.addRow(row);
			}
			table.setModel(model);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
