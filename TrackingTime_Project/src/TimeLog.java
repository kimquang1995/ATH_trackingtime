import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import java.util.Arrays;

import net.proteanit.sql.DbUtils;

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
	DatabaseConnection db = new DatabaseConnection();
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
			db.Connect();
			PreparedStatement query = db.getConnection().prepareStatement("Select * from Tag");
			ResultSet rs = query.executeQuery();
			while(rs.next())
			{
				cmb.addItem(rs.getString("Name"));
			}
			
		} catch (Exception e) {
			  JOptionPane.showMessageDialog(null, "ERROR");
		}
		
		cmb.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
            	TagName = cmb.getSelectedItem().toString();
            	try
                {
            		
            		db.Connect();
            		PreparedStatement subquery = db.getConnection().prepareStatement("Select Id from Tag where Name LIKE '"+TagName+"%'");
        			ResultSet subrs = subquery.executeQuery();
        			PreparedStatement query = db.getConnection().prepareStatement("Select Name,Hours,Id_Tag from TimeLog Where Id_Tag LIKE '"+subrs+"%'");
        			ResultSet rs = query.executeQuery();
        			table.setModel(DbUtils.resultSetToTableModel(rs));
        			table.repaint();
                   /* while(rs.next())
                    {  
                        Object[] row = new Object[columns];
                        for (int i = 1; i <= columns; i++)
                        {  
                            row[i - 1] = rs.getObject(i);
                        }
                        ((DefaultTableModel) table.getModel()).insertRow(rs.getRow()-1,row);
                    }*/
                }
                catch(Exception e1)
                {
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

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				
				String name = txtName.getText();
				Double hours = Double
						.parseDouble(txtHours.getText().toString());
				try {
					db.Connect();
					PreparedStatement query = db.getConnection().prepareStatement("Insert into TimeLog (Name,Hours,Date,Id_Tag) values ('"+name+"','"+hours+"',GetDate(),1)");
					ResultSet rs = query.executeQuery();
					rs.close();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					table.repaint();
        			JOptionPane.showMessageDialog(null, "Success");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
    			
				txtName.setText(null);
				txtHours.setText(null);

			}
		});
		btnAdd.setBounds(422, 127, 97, 25);
		contentPane1.add(btnAdd);

		JButton btnEdit = new JButton("Edit");
		btnEdit.setEnabled(false);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtName.getText();
				Double hours = Double.parseDouble(txtHours.getText());
				try {
					db.Connect();
					PreparedStatement query = db.getConnection().prepareStatement("Update TimeLog set Name='"+name+"',Hours='"+hours+"'");
					ResultSet rs = query.executeQuery();
					rs.close();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					table.repaint();
        			JOptionPane.showMessageDialog(null, "Success");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
    			
				txtName.setText(null);
				txtHours.setText(null);
			}
		});
		btnEdit.setBounds(422, 169, 97, 25);
		contentPane1.add(btnEdit);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtName.setText(null);
				txtHours.setText(null);
				try {
					db.Connect();
					PreparedStatement query = db.getConnection().prepareStatement("Delete from TimeLog where Name='"+txtName.getText().toString()+"'");
					ResultSet rs = query.executeQuery();
					rs.close();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					table.repaint();
        			JOptionPane.showMessageDialog(null, "Success");
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
		try {
			db.Connect();
			PreparedStatement query = db.getConnection().prepareStatement("Select Name,Hours,Id_Tag from TimeLog");
			ResultSet rs = query.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			for (Class c: Arrays.asList(String.class, String.class, String.class)) {
			    TableCellEditor ce = table.getDefaultEditor(c);
			    if (ce instanceof DefaultCellEditor) {
			            ((DefaultCellEditor) ce).setClickCountToStart(Integer.MAX_VALUE);
			    }
			    
			}
			 
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFont(new Font("Arial", Font.PLAIN, 18));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(String.class, centerRenderer);
		ListSelectionModel SelectionModel = table.getSelectionModel();
		SelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		SelectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				btnEdit.setEnabled(true);
				btnDelete.setEnabled(true);
				row = table.getSelectedRow();
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				String SelectedName = (String) model.getValueAt(row, 0);
				Double SelectedHours = (Double) model.getValueAt(row, 1);
				txtName.setText(SelectedName);
				txtHours.setText(SelectedHours.toString());

			}
		});

	}
}
