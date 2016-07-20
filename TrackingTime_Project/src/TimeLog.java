import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.regex.Matcher;
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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;



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
	/*public static void main(String[] args) {
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
	}*/

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
		setBounds(100, 100, 650, 650);
		contentPane1 = new JPanel();
		contentPane1.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane1);
		contentPane1.setLayout(null);

		JLabel lblTitle = new JLabel("Time Log");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
		lblTitle.setBounds(240, 13, 123, 30);
		contentPane1.add(lblTitle);

		JLabel lblDay = new JLabel("Day :");
		lblDay.setFont(new Font("Arial", Font.BOLD, 22));
		lblDay.setBounds(204, 55, 56, 30);
		contentPane1.add(lblDay);

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String strDate =sdf.format(date);
		JLabel lblChoseDay = new JLabel();
		lblChoseDay.setText(strDate);
		lblChoseDay.setFont(new Font("Arial", Font.BOLD, 22));
		lblChoseDay.setBounds(270, 55, 200, 30);
		contentPane1.add(lblChoseDay);

		JLabel lblTag = new JLabel("Tag");
		lblTag.setFont(new Font("Arial", Font.BOLD, 20));
		lblTag.setBounds(57, 125, 45, 30);
		contentPane1.add(lblTag);

		JLabel lblName = new JLabel("Task Name");
		lblName.setFont(new Font("Arial", Font.BOLD, 20));
		lblName.setBounds(57, 167, 111, 30);
		contentPane1.add(lblName);

		JLabel lblHours = new JLabel("Hours");
		lblHours.setFont(new Font("Arial", Font.BOLD, 20));
		lblHours.setBounds(57, 209, 65, 30);
		contentPane1.add(lblHours);

		JComboBox cmb = new JComboBox();
		cmb.setBounds(187, 128, 223, 25);
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
		txtName.setBounds(187, 170, 223, 25);
		contentPane1.add(txtName);
		txtName.setColumns(10);

		txtHours = new JTextField();
		txtHours.setBounds(187, 212, 223, 25);
		contentPane1.add(txtHours);
		txtHours.setColumns(10);
		// Add Button
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name = txtName.getText();
				
				String hourscheck = txtHours.getText().toString();
				boolean result = false;  
				Pattern patternName = Pattern.compile("^[a-zA-Z_0-9]+$");  
				Matcher matcherName = patternName.matcher(name); // Your String should come here
				Pattern patternHours = Pattern.compile("^[0-9\\.]+$");  
				Matcher matcherHours = patternHours.matcher(hourscheck);
				if(matcherName.find() && matcherHours.find() )  
				    result = true;// There is only Alphabets in your input string
				else{  
				    result = false;// your string Contains some number/special char etc..
				}
				if(result == true){
				try {
					
					if (id_tag != null) {
						Double hours = Double
								.parseDouble(txtHours.getText().toString());
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
						table.repaint();
						JOptionPane.showMessageDialog(null,"Thêm công việc thành công.", "Thành Công",  JOptionPane.INFORMATION_MESSAGE);
						Load("Select * from TimeLog Where Id_Tag LIKE '"
								+ id_tag + "'");
						txtName.setText(null);
						txtHours.setText(null);
					}else {
						JOptionPane.showMessageDialog(null,"Vui lòng chọn Tag bạn muốn thêm công việc.", "Lỗi",  JOptionPane.OK_OPTION);
						Load("Select * from TimeLog");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			
				}else{
					JOptionPane.showMessageDialog(null,"Tên công việc và số giờ không được để trống."+"\n"+"\n"+"Tên công việc chỉ được chứa ký tự chữ và số."+"\n"+"\n"+"Số giờ chỉ được chứa ký tự số và 1 dấu chấm.", "Lỗi", JOptionPane.OK_OPTION);
				}
			}
		});
		btnAdd.setBounds(442, 127, 100, 25);
		contentPane1.add(btnAdd);
		// Edit Button
		JButton btnEdit = new JButton("Edit");
		btnEdit.setEnabled(false);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtName.getText();
				
				String hourscheck = txtHours.getText().toString();
				boolean result = false;  
				Pattern patternName = Pattern.compile("^[a-zA-Z_0-9]+$");  
				Matcher matcherName = patternName.matcher(name); // Your String should come here
				Pattern patternHours = Pattern.compile("^[0-9\\.]+$");  
				Matcher matcherHours = patternHours.matcher(hourscheck);
				if(matcherName.find() && matcherHours.find() )  
				    result = true;// There is only Alphabets in your input string
				else{  
				    result = false;// your string Contains some number/special char etc..
				}
				if(result == true){
				
				try {
					Double hours = Double.parseDouble(txtHours.getText());
					PreparedStatement query = db.getConnection()
							.prepareStatement(
									"Update TimeLog set Name='" + name
											+ "',Hours='" + hours
											+ "' where id ='" + SelectedId
											+ "'");
					query.executeUpdate();
					table.repaint();
					JOptionPane.showMessageDialog(null,"Sửa công việc thành công.", "Thành Công",  JOptionPane.INFORMATION_MESSAGE);
					Load("Select * from TimeLog");
					txtName.setText(null);
					txtHours.setText(null);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				}else{
					JOptionPane.showMessageDialog(null,"Tên công việc và số giờ không được để trống."+"\n"+"\n"+"Tên công việc chỉ được chứa ký tự chữ và số."+"\n"+"\n"+"Số giờ chỉ được chứa ký tự số và 1 dấu chấm.", "Lỗi", JOptionPane.OK_OPTION);
				}
			}
		});
		btnEdit.setBounds(442, 169, 100, 25);
		contentPane1.add(btnEdit);

		// Delete Button

		JButton btnDelete = new JButton("Delete");
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa công việc này không?", "Xác Nhận",
				        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
				txtName.setText(null);
				txtHours.setText(null);
				try {

					PreparedStatement query = db.getConnection()
							.prepareStatement(
									"Delete from TimeLog where Id='"
											+ SelectedId + "'");
					query.executeUpdate();
					
	
					DefaultTableModel a = (DefaultTableModel) table.getModel();

					while (a.getRowCount() > 0) {
						for (int i = 0; i < a.getRowCount(); ++i) {
							a.removeRow(i);
						}
					}
					table.setModel(a);
					Load("Select * from TimeLog");
					JOptionPane.showMessageDialog(null,"Xóa công việc thành công.", "Thành Công",  JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				btnDelete.setEnabled(false);
			}}
		});
		btnDelete.setBounds(442, 211, 100, 25);
		contentPane1.add(btnDelete);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(57, 261, 482, 300);
		contentPane1.add(scrollPane);

		table = new JTable();
		table.setDefaultEditor(Object.class, null);
		scrollPane.setViewportView(table);
		Load("Select * from TimeLog");
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFont(new Font("Arial", Font.PLAIN, 18));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(String.class, centerRenderer);
		table.addMouseListener(new MouseListener() {

		
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
			DefaultTableModel model = new DefaultTableModel(new String[] {
					"Id", "Name", "Hours"}, 0);
			PreparedStatement query = db.getConnection().prepareStatement(
					queryExe);
			ResultSet rs = query.executeQuery();
			Object[] row = new Object[4];
			while (rs.next()) {
				row[0] = rs.getString("Id");
				row[1] = rs.getString("Name");
				row[2] = rs.getString("Hours");
		
				model.addRow(row);
			}
			table.setModel(model);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
