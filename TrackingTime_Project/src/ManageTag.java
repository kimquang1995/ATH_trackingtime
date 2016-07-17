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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ManageTag extends JFrame implements ActionListener {
	private JPanel contentPane;
	private JTextField txt_tag_name;
	private JTable table;
	DatabaseConnection db = new DatabaseConnection();
	ResultSet rs;
	JButton btn_tag_delete;
	String cbdata;
	JComboBox cbb_tag_status;
	int row;
	int Status;
	String SelectedId;

	public static void main(String[] a) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManageTag frame = new ManageTag("username");
					
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
	public ManageTag(String username) {
		setAlwaysOnTop(true);

		try {
			db.Connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//
		// setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("ADD NEW TAGS");
	
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
		setBounds(100, 100, 373, 479);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		// add label
		JLabel lblHello = new JLabel("Hello " + username);
		lblHello.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblHello.setBounds(243, 11, 90, 25);
		contentPane.add(lblHello);

		JLabel lblNewLabel = new JLabel("MANAGE TAG");
		lblNewLabel.setBounds(82, 27, 251, 60);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		contentPane.add(lblNewLabel);

		JLabel lblName = new JLabel("NAME");
		lblName.setBounds(10, 92, 53, 31);
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPane.add(lblName);

		JLabel lblStatus = new JLabel("STATUS");
		lblStatus.setBounds(10, 144, 66, 14);
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPane.add(lblStatus);
		// add combobox
		JComboBox cbb_tag_status = new JComboBox();
		cbb_tag_status.setBounds(82, 140, 94, 25);
		contentPane.add(cbb_tag_status);
		cbb_tag_status.insertItemAt("False",0);
		cbb_tag_status.insertItemAt("True",1);
		
		/*
		 * if(cbb_tag_status.getSelectedItem().equals("True")){ status=1; }else{
		 * status=0; }
		 */
		ImageIcon iadd = new ImageIcon("Images/Managetag_add.png");
		ImageIcon iaupdate = new ImageIcon("Images/Managetag_Update.png");
		ImageIcon idelete = new ImageIcon("Images/Managetag_Delete.png");
		txt_tag_name = new JTextField();

		txt_tag_name.setBounds(82, 96, 137, 25);
		contentPane.add(txt_tag_name);
		txt_tag_name.setColumns(10);
		// add buton
		JButton btn_tag_add = new JButton(iadd);
		btn_tag_add.setBounds(10, 176, 90, 71);
		contentPane.add(btn_tag_add);
		
		// add action for btn add
		btn_tag_add.addActionListener(this);

		JButton btn_tag_update = new JButton(iaupdate);
		
		btn_tag_update.setBounds(111, 176, 86, 71);
		contentPane.add(btn_tag_update);
		// /add action for btn update
		btn_tag_update.addActionListener(this);

		JButton btn_tag_delete = new JButton(idelete);
		
		btn_tag_delete.setBounds(207, 176, 90, 71);
		contentPane.add(btn_tag_delete);
		// add action for btn delete
		btn_tag_delete.addActionListener(this);
		// addscrollpane
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 258, 287, 179);
		contentPane.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				btn_tag_delete.setEnabled(true);
				btn_tag_update.setEnabled(true);
				row = table.getSelectedRow();
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				String SelectedName = model.getValueAt(row, 1).toString();
				String cbbselect = model.getValueAt(row, 2).toString();
				txt_tag_name.setText(SelectedName);//
				cbb_tag_status.setSelectedItem(cbbselect);
				SelectedId = (model.getValueAt(row, 0)).toString();
			}
		});
		table.setRowSelectionAllowed(true);
		table.setDefaultEditor(Object.class, null);

		scrollPane.setColumnHeaderView(table);
		scrollPane.setViewportView(table);
		Load("Select * from Tag");
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		// connect data
		
	}

	public void Load(String queryExe) {
		try {
			DefaultTableModel model = new DefaultTableModel(new String[] {
					"Id", "Name", "Status" }, 0);
			PreparedStatement query = db.getConnection().prepareStatement(
					queryExe);
			ResultSet rs = query.executeQuery();
			Object[] row = new Object[3];
			while (rs.next()) {
				row[0] = rs.getString("Id");
				row[1] = rs.getString("Name");
				row[2] = rs.getString("Status");

				model.addRow(row);
			}
			table.setModel(model);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btemp = (JButton) e.getSource();
		
		if (btemp.getActionCommand().equals("ADD")) {
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			boolean reslt = false;
			String name = txt_tag_name.getText();
			row = table.getSelectedRow();
			SelectedId = (model.getValueAt(row, 2)).toString();
			// String cb1=cbb_tag_status.getSelectedItem().toString();
			 
			Pattern patternname = Pattern.compile(".*\\D.*");
			Matcher matchername = patternname.matcher(name);
			if (matchername.find()) {
				reslt = true;
			} else {
				reslt = false;
			}
			if (reslt == true) {
			//	if(cb1==("0")){
				try {

					String sql = "Insert into Tag" + "(Name,Status)" + "values" + "('"+name+"','"+SelectedId+"')";
					PreparedStatement query = db.getConnection()
							.prepareStatement(sql);
					query.executeUpdate();
					table.repaint();
					//
					JOptionPane.showMessageDialog(null,
							"Thêm công việc thành công.", "Thành Công",
							JOptionPane.INFORMATION_MESSAGE);
					Load("Select * from Tag");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null,"Vui lòng nhập tên hoạt động cần thêm");
					 }
				}

		}
		
		if (btemp.getActionCommand().equals("UPDATE")) {
			boolean reslt = false;
			String name = txt_tag_name.getText();
			//row = table.getSelectedRow();
			//SelectedId = (model.getValueAt(row, 2)).toString();
		//	 String cb1=cbb_tag_status.getSelectedItem().toString();
			 
			Pattern patternname = Pattern.compile(".*\\D.*");
			Matcher matchername = patternname.matcher(name);
			if (matchername.find()) {
				reslt = true;
			} else {
				reslt = false;
			}
			if (reslt == true) {
				
				try {
					PreparedStatement query = db.getConnection()
							.prepareStatement(
									"Update Tag set Name='" + name
											+ "',Status='" + 0
											+ "' where id ='" + SelectedId
											+ "'");
					query.executeUpdate();
					table.repaint();
					JOptionPane.showMessageDialog(null,"Sửa công việc thành công.", "Thành Công",  JOptionPane.INFORMATION_MESSAGE);
					Load("Select * from Tag");
					txt_tag_name.setText(null);
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					 }}
				}

			 else {
				JOptionPane.showMessageDialog(null,
						"Vui lòng nhập tên hoạt động cần thêm");
			
		}
		if (btemp.getActionCommand().equals("DELETE")) {

			if (JOptionPane.showConfirmDialog(null,
					"Bạn có chắc muốn xóa hoạt động này?", "Xác Nhận",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				int row = table.getSelectedRow();
				DefaultTableModel a = (DefaultTableModel) table.getModel();

				String selected = a.getValueAt(row, 0).toString();
				if (txt_tag_name.getText() != null) {
					if (row >= 0) {

						a.removeRow(row);

						try {

							PreparedStatement ps = db.getConnection()
									.prepareStatement(
											"delete from Tag where Id='"
													+ selected + "' ");
							ps.executeUpdate();

							table.setModel(a);
							Load("Select * from Tag");
							JOptionPane.showMessageDialog(null,
									"Xóa hoạt động thành công", "Thành Công",
									JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Vui Lòng chọn tên hoạt động cần xóa", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
}
