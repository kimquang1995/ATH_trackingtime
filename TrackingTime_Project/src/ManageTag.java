import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
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
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import javax.swing.border.MatteBorder;

public class ManageTag extends JFrame implements ActionListener {
	private JPanel contentPane;
	private JTextField txt_tag_name;
	private JTable table;
	DatabaseConnection db = new DatabaseConnection();
	ResultSet rs;
	JButton btn_tag_delete;
	String cbdata;
	JComboBox cbb_tag_status;
	String SelectedId;
	int row;
	int Status;

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btemp = (JButton) e.getSource();
		if (btemp.getActionCommand().equals("Add")) {
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			boolean reslt = false;
			String name = txt_tag_name.getText();
			row = table.getSelectedRow();
			
			// cbdata = cbb_tag_status.getSelectedItem().toString();
			Pattern patternname = Pattern.compile(".*\\D.*");
			Matcher matchername = patternname.matcher(name);
			if (matchername.find()) {
				reslt = true;
			} else {
				JOptionPane.showMessageDialog(null, "Please Input Again!",
						"Error", JOptionPane.ERROR_MESSAGE);

			}
			if (reslt == true) {

				try {

					String sql = "Insert into Tag" + "(Name)" + "values" + "('"
							+ name + "')";//+"ON DUPLICATE UPDATE Name= values+('"+name+"')";
					PreparedStatement query = db.getConnection()
							.prepareStatement(sql);
					query.executeUpdate();
					table.repaint();
					txt_tag_name.setText("");
					
					//
					JOptionPane.showMessageDialog(null,
							"Insert Completed.", "Successful",
							JOptionPane.INFORMATION_MESSAGE);
					Load("Select * from Tag");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					// }
				}

			} else {
				JOptionPane.showMessageDialog(null,
						"Please Input Tag");
			}
		}

		if (btemp.getActionCommand().equals("Edit")) {
			boolean reslt = false;
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			String name = txt_tag_name.getText();
			SelectedId = (model.getValueAt(row, 0)).toString();
			// row = table.getSelectedRow();
			// SelectedId = (model.getValueAt(row, 2)).toString();
			// String cb1=cbb_tag_status.getSelectedItem().toString();

			Pattern patternname = Pattern.compile(".*\\D.*");
			Matcher matchername = patternname.matcher(name);
			if (matchername.find()) {
				reslt = true;
			} else {
				JOptionPane.showMessageDialog(null, "Please Input Again",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
			if (reslt == true) {

				try {
					PreparedStatement query = db
							.getConnection()
							.prepareStatement(
									"Update Tag set Name='" +name+ "' where Id='" +SelectedId +"'");
					query.executeUpdate();
					table.repaint();
					JOptionPane.showMessageDialog(null,
							"Edit Completed", "Successful",
							JOptionPane.INFORMATION_MESSAGE);
					Load("Select * from Tag");
					txt_tag_name.setText(null);

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		if (btemp.getActionCommand().equals("Delete")) {

			if (JOptionPane.showConfirmDialog(null,
					"Are you sure", "Delete",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				int row = table.getSelectedRow();
			
				DefaultTableModel a = (DefaultTableModel) table.getModel();
				
				String selected = a.getValueAt(row, 0).toString();
				if (txt_tag_name.getText() != null) {
					if (row >= 0) {
						try {

							PreparedStatement ps = db.getConnection()
									.prepareStatement(
											"delete from Tag where Id='"
													+ selected + "'");
							ps.executeUpdate();
							table.setModel(a);
							Load("Select * from Tag");
							JOptionPane.showMessageDialog(null,
									"Delete Completed", "Successful",
									JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null,
									"Can not delete this Tag \r\n Because of Using", "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Please Select Tag Need Delete", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	/**
	 * Create the frame.
	 */
	public ManageTag() {
		setTitle("Manage Tag");
		setAutoRequestFocus(false);
		
		try {
			db.Connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		// add label

		JLabel lblNewLabel = new JLabel("MANAGE TAG");
		lblNewLabel.setForeground(new Color(0, 204, 255));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(140, 10, 165, 30);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		contentPane.add(lblNewLabel);

		JLabel lblStatus = new JLabel("Name:");
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus.setBounds(58, 70, 60, 30);
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(lblStatus);
		/*
		 * if(cbb_tag_status.getSelectedItem().equals("True")){ status=1; }else{
		 * status=0; }
		 */

		txt_tag_name = new JTextField();
		txt_tag_name.setFont(new Font("Roboto", Font.PLAIN, 15));
		txt_tag_name.setBounds(120, 70, 250, 30);
		contentPane.add(txt_tag_name);
		txt_tag_name.setColumns(10);
		// add buton
		JButton btn_tag_add = new JButton("Add");
		btn_tag_add.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_tag_add.setBounds(60, 110, 100, 30);
		contentPane.add(btn_tag_add);
		// add action for btn add
		btn_tag_add.addActionListener(this);

		JButton btn_tag_update = new JButton("Edit");
		btn_tag_update.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_tag_update.setBounds(165, 110, 100, 30);
		contentPane.add(btn_tag_update);
		// /add action for btn update
		btn_tag_update.addActionListener(this);

		JButton btn_tag_delete = new JButton("Delete");
		btn_tag_delete.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_tag_delete.setBounds(270, 110, 100, 30);
		contentPane.add(btn_tag_delete);
		// add action for btn delete
		btn_tag_delete.addActionListener(this);
		// addscrollpane
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new MatteBorder(1, 4, 4, 1, (Color) Color.LIGHT_GRAY));
		scrollPane.setFont(new Font("Roboto", Font.PLAIN, 15));
		scrollPane.setBounds(60, 150, 310, 150);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				btn_tag_delete.setEnabled(true);
				btn_tag_update.setEnabled(true);
				row = table.getSelectedRow();
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				String SelectedName = model.getValueAt(row, 1).toString();

				txt_tag_name.setText(SelectedName);//

				SelectedId = (model.getValueAt(row, 0)).toString();

			}
		});
		table.setRowSelectionAllowed(true);
		table.setDefaultEditor(Object.class, null);

		scrollPane.setColumnHeaderView(table);
		scrollPane.setViewportView(table);
		Load("Select * from Tag");
		// connect data
		PreparedStatement query;
		try {
			query = db.getConnection().prepareStatement("Select * from Tag");
			ResultSet rs = query.executeQuery();
			while (rs.next()) {

			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No connection to server",
					"Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

	public void Load(String queryExe) {
		try {
			DefaultTableModel model = new DefaultTableModel(new String[] {
					"Id", "Name" }, 0);
			PreparedStatement query = db.getConnection().prepareStatement(
					queryExe);
			ResultSet rs = query.executeQuery();
			Object[] row = new Object[2];
			while (rs.next()) {
				row[0] = rs.getString("Id");
				row[1] = rs.getString("Name");
				model.addRow(row);
			}
			table.setModel(model);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
