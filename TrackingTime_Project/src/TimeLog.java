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
import java.time.LocalDate;
import java.util.Date;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
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
	Date d1 = null,d2 = null;
	int row;
	private JPanel contentPane;
	ResultSet rs;
	String TagName;
	String SelectedId;
	DatabaseConnection db = new DatabaseConnection();
	String id_tag;
	SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");  

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
	public TimeLog(String selectDate) {
		try {
			db.Connect();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		setTitle("Time Log");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		SimpleDateFormat sdf = new SimpleDateFormat(selectDate);
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

		JLabel lblStart = new JLabel("Start");
		lblStart.setFont(new Font("Arial", Font.BOLD, 20));
		lblStart.setBounds(57, 209, 65, 30);
		contentPane1.add(lblStart);

		JLabel lblEnd = new JLabel("End");
		lblEnd.setFont(new Font("Arial", Font.BOLD, 20));
		lblEnd.setBounds(57, 251, 65, 30);
		contentPane1.add(lblEnd);
		
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
				table.getColumnModel().getColumn(0).setCellRenderer(new DateCellRenderer());
			}
		});

		txtName = new JTextField();
		txtName.setBounds(187, 170, 223, 25);
		contentPane1.add(txtName);
		txtName.setColumns(10);

	/*	txtHours = new JTextField();
		txtHours.setBounds(187, 212, 50, 25);
		contentPane1.add(txtHours);
		txtHours.setColumns(10);*/
		
		Vector hour = new Vector();
		for(int i=0;i<=23;i++){
			hour.add(i);
		}
		
		Vector min = new Vector();
		for(int i=0;i<=59;i++){
			min.add(i);
		}
		
		JComboBox cmbSHour = new JComboBox(hour);
		cmbSHour.setBounds(187, 212, 50, 25);
		contentPane1.add(cmbSHour);
		
		JComboBox cmbSMin = new JComboBox(min);
		cmbSMin.setBounds(270, 212, 50, 25);
		contentPane1.add(cmbSMin);
		
		
		JComboBox cmbEHour = new JComboBox(hour);
		cmbEHour.setBounds(187, 254, 50, 25);
		contentPane1.add(cmbEHour);
		
		JComboBox cmbEMin = new JComboBox(min);
		cmbEMin.setBounds(270, 254, 50, 25);
		contentPane1.add(cmbEMin);
		
		JLabel lblDot1 = new JLabel(":");
		lblDot1.setBounds(252, 212, 50, 25);
		contentPane1.add(lblDot1);
		
		JLabel lblDot2 = new JLabel(":");
		lblDot2.setBounds(252, 254, 50, 25);
		contentPane1.add(lblDot2);
		// Add Button
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String srHour = (cmbSHour.getSelectedItem().toString()+":"+cmbSMin.getSelectedItem().toString()+":"+"00");
				String erHour = (cmbEHour.getSelectedItem().toString()+":"+cmbEMin.getSelectedItem().toString()+":"+"00");
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
				Pattern patternName = Pattern.compile("^[a-zA-Z_0-9]+$");  
				Matcher matcherName = patternName.matcher(name); // Your String should come here
				int sHours = Integer.parseInt(cmbSHour.getSelectedItem().toString());
				int eHours = Integer.parseInt(cmbEHour.getSelectedItem().toString());
				int sMin = Integer.parseInt(cmbSMin.getSelectedItem().toString());
				int eMin = Integer.parseInt(cmbEMin.getSelectedItem().toString());
				if(matcherName.find()&&sHours<eHours )  
				    result = true;// There is only Alphabets in your input string
				if(matcherName.find()&&sHours>eHours && sMin<eMin)
					result = true;
				if(matcherName.find()&&sHours>eHours && sMin>eMin)
					result = false;
				
				if(result == true){
				try {
					
					if (id_tag != null) {
					
						String now = LocalDate.now().toString();
						System.out.println(id_tag);
						int id_tagI = Integer.parseInt(id_tag);
						String queryS="Insert into TimeLog"
						+ "(Name,Hours,Date,Id_Tag,Start_Time,End_Time)"
						+"values"
						+ "('"+name+"','"+diffHours+"','"+now+"','"+id_tagI+"','"+sHours+":"+sMin+"','"+eHours+":"+eMin+"')";
						PreparedStatement query = db.getConnection()
								.prepareStatement(queryS);
										
						query.executeUpdate();
						table.repaint();
						JOptionPane.showMessageDialog(null,"Thêm công việc thành công.", "Thành Công",  JOptionPane.INFORMATION_MESSAGE);
						Load("Select * from TimeLog Where Id_Tag LIKE '"
								+ id_tag + "'");
						txtName.setText(null);
					
					}else {
						JOptionPane.showMessageDialog(null,"Vui lòng chọn Tag bạn muốn thêm công việc.", "Lỗi",  JOptionPane.OK_OPTION);
						Load("Select * from TimeLog");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			
				}else{
					JOptionPane.showMessageDialog(null,"Tên công việc không được để trống."+"\n"+"\n"+"Tên công việc chỉ được chứa ký tự chữ và số."+"\n"+"\n"+"Giờ bắt đầu phải nhỏ hơn giờ kết thúc.", "Lỗi", JOptionPane.OK_OPTION);
				}
				table.getColumnModel().getColumn(0).setCellRenderer(new DateCellRenderer());
			}
		});
		btnAdd.setBounds(442, 127, 100, 25);
		contentPane1.add(btnAdd);
		// Edit Button
		JButton btnEdit = new JButton("Edit");
		btnEdit.setEnabled(false);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String srHour = (cmbSHour.getSelectedItem().toString()+":"+cmbSMin.getSelectedItem().toString()+":"+"00");
				String erHour = (cmbEHour.getSelectedItem().toString()+":"+cmbEMin.getSelectedItem().toString()+":"+"00");
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
				Pattern patternName = Pattern.compile("^[a-zA-Z_0-9]+$");  
				Matcher matcherName = patternName.matcher(name); // Your String should come here
				int sHours = Integer.parseInt(cmbSHour.getSelectedItem().toString());
				int eHours = Integer.parseInt(cmbEHour.getSelectedItem().toString());
				int sMin = Integer.parseInt(cmbSMin.getSelectedItem().toString());
				int eMin = Integer.parseInt(cmbEMin.getSelectedItem().toString());
				if(matcherName.find()&&sHours<eHours )  
				    result = true;// There is only Alphabets in your input string
				if(matcherName.find()&&sHours>eHours && sMin<eMin)
					result = true;
				if(matcherName.find()&&sHours>eHours && sMin>eMin)
					result = false;
				
				if(result == true){
				
				try {
					
					PreparedStatement query = db.getConnection()
							.prepareStatement(
									"Update TimeLog set Name='" + name
											+ "',Hours='" + diffHours
											+ "',Start_Time='"+srHour
											+ "',End_Time='"+erHour
											+ "' where id ='" + SelectedId
											+ "'");
					query.executeUpdate();
					table.repaint();
					JOptionPane.showMessageDialog(null,"Sửa công việc thành công.", "Thành Công",  JOptionPane.INFORMATION_MESSAGE);
					Load("Select * from TimeLog");
					txtName.setText(null);
				
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				}else{
					JOptionPane.showMessageDialog(null,"Tên công việc không được để trống."+"\n"+"\n"+"Tên công việc chỉ được chứa ký tự chữ và số."+"\n"+"\n"+"Giờ bắt đầu phải nhỏ hơn giờ kết thúc.", "Lỗi", JOptionPane.OK_OPTION);
				}
				table.getColumnModel().getColumn(0).setCellRenderer(new DateCellRenderer());
			}
		});
		btnEdit.setBounds(442, 169, 100, 25);
		contentPane1.add(btnEdit);

		// Delete Button

		JButton btnDelete = new JButton("Delete");
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.getColumnModel().getColumn(0).setCellRenderer(new DateCellRenderer());
				if (JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa công việc này không?", "Xác Nhận",
				        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
					table.getColumnModel().getColumn(0).setCellRenderer(new DateCellRenderer());
				txtName.setText(null);
				txtHours.setText(null);
				try {

					PreparedStatement query = db.getConnection()
							.prepareStatement(
									"Delete from TimeLog where Id='"
											+ SelectedId + "'");
					query.executeUpdate();
					
					table.getColumnModel().getColumn(0).setCellRenderer(new DateCellRenderer());
					DefaultTableModel a = (DefaultTableModel) table.getModel();

					while (a.getRowCount() > 0) {
						for (int i = 0; i < a.getRowCount(); ++i) {
							a.removeRow(i);
							table.getColumnModel().getColumn(0).setCellRenderer(new DateCellRenderer());
						}
					}
					table.setModel(a);
					table.getColumnModel().getColumn(0).setCellRenderer(new DateCellRenderer());
					Load("Select * from TimeLog");
					table.getColumnModel().getColumn(0).setCellRenderer(new DateCellRenderer());
					JOptionPane.showMessageDialog(null,"Xóa công việc thành công.", "Thành Công",  JOptionPane.INFORMATION_MESSAGE);
					table.getColumnModel().getColumn(0).setCellRenderer(new DateCellRenderer());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				btnDelete.setEnabled(false);
				table.getColumnModel().getColumn(0).setCellRenderer(new DateCellRenderer());
			}}
		});
		btnDelete.setBounds(442, 211, 100, 25);
		contentPane1.add(btnDelete);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(57, 303, 482, 250);
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
		table.getColumnModel().getColumn(0).setCellRenderer(new DateCellRenderer());
		table.addMouseListener(new MouseListener() {

		
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				btnEdit.setEnabled(true);
				btnDelete.setEnabled(true);
				row = table.getSelectedRow();
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				String SelectedName = model.getValueAt(row, 1).toString();
				String S = model.getValueAt(row, 2).toString();
				String E = model.getValueAt(row, 3).toString();
			
				
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
				 
				 int rSHours =  d1.getHours();
				 cmbSHour.setSelectedItem(rSHours);
				 int rSMin =  d1.getMinutes();
				 cmbSMin.setSelectedItem(rSMin);
				 int rEHours =  d2.getHours();
				 cmbEHour.setSelectedItem(rEHours);
				 int rEMin =  d2.getMinutes();
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
			DefaultTableModel model = new DefaultTableModel(new String[] {
					"Serial Number", "Name", "Start Time","End Time","Hours"}, 0);
			PreparedStatement query = db.getConnection().prepareStatement(
					queryExe);
			ResultSet rs = query.executeQuery();
			Object[] row = new Object[5];
			while (rs.next()) {
				row[0] = rs.getString("Id");
				row[1] = rs.getString("Name");
				row[2] = rs.getString("Start_Time");
				row[3] = rs.getString("End_Time");
				row[4] = rs.getString("Hours");
				model.addRow(row);
			}
			table.setModel(model);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	public class DateCellRenderer extends DefaultTableCellRenderer{
		public DateCellRenderer(){
			super();
		}
		@Override
		public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected,
			boolean hasFocused,
			int row,int col){
			if(col==5){
				if(value instanceof Date){
					//value = df.format(value);
				}
			}
			if(col==0){
				value = row+1;
			}
			
			return super.getTableCellRendererComponent(table, value, isSelected, hasFocused, row, col);
		}
	}
}
