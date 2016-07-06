import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.DefaultComboBoxModel;

public class TimeLog extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField txtName;
	private JTextField txtHours;
	private JTable table;

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
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("Time Log");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
		lblTitle.setBounds(213, 13, 123, 29);
		contentPane.add(lblTitle);
		
		JLabel lblDay = new JLabel("Day :");
		lblDay.setFont(new Font("Arial", Font.BOLD, 22));
		lblDay.setBounds(167, 55, 56, 29);
		contentPane.add(lblDay);
		
		JLabel lblChoseDay = new JLabel("New label");
		lblChoseDay.setFont(new Font("Arial", Font.BOLD, 22));
		lblChoseDay.setBounds(233, 55, 200, 29);
		contentPane.add(lblChoseDay);
		
		JLabel lblTag = new JLabel("Tag");
		lblTag.setFont(new Font("Arial", Font.BOLD, 20));
		lblTag.setBounds(37, 123, 45, 29);
		contentPane.add(lblTag);
		
		JLabel lblName = new JLabel("Task Name");
		lblName.setFont(new Font("Arial", Font.BOLD, 20));
		lblName.setBounds(37, 165, 111, 29);
		contentPane.add(lblName);
		
		JLabel lblHours = new JLabel("Hours");
		lblHours.setFont(new Font("Arial", Font.BOLD, 20));
		lblHours.setBounds(37, 207, 65, 29);
		contentPane.add(lblHours);
		
		JComboBox cmb = new JComboBox();
		cmb.setModel(new DefaultComboBoxModel(new String[] {"H\u1ECDc", "Ch\u01A1i"}));
		cmb.setBounds(167, 128, 223, 22);
		contentPane.add(cmb);
		
		txtName = new JTextField();
		txtName.setBounds(167, 170, 223, 22);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		txtHours = new JTextField();
		txtHours.setBounds(167, 212, 223, 22);
		contentPane.add(txtHours);
		txtHours.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				String tag = cmb.getSelectedItem().toString();
				String name = txtName.getText();
				Double hours = Double.parseDouble(txtHours.getText().toString());
				Object[] row ={name,tag,hours};
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(row);
				txtName.setText(null);
				txtHours.setText(null);
			
			}
		});
		btnAdd.setBounds(422, 127, 97, 25);
		contentPane.add(btnAdd);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.setEnabled(false);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				String name = txtName.getText();
				Double hours = Double.parseDouble(txtHours.getText());
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.setValueAt(name, row, 0);
				model.setValueAt(hours, row, 2);
			}
		});
		btnEdit.setBounds(422, 169, 97, 25);
		contentPane.add(btnEdit);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtName.setText(null);
				txtHours.setText(null);
				int row = table.getSelectedRow();
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.setValueAt(null, row, 0);
				model.setValueAt(null, row, 2);
				model.setValueAt(null, row, 1);
				//model.removeRow(row);
			}
		});
		btnDelete.setBounds(422, 211, 97, 25);
		contentPane.add(btnDelete);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(37, 261, 482, 194);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Task Name", "Tag", "Hours"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				true, true, false
			};
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFont(new Font("Arial", Font.PLAIN, 18));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table.setDefaultRenderer(String.class, centerRenderer);
		ListSelectionModel SelectionModel = table.getSelectionModel();
		SelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		SelectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				btnEdit.setEnabled(true);
				btnDelete.setEnabled(true);
				int row = table.getSelectedRow();
				DefaultTableModel model = (DefaultTableModel) table.getModel();
	            String SelectedName = (String) model.getValueAt(row, 0);
	            Double SelectedHours = (Double) model.getValueAt(row, 2);
		        txtName.setText(SelectedName);
		        txtHours.setText(SelectedHours.toString());
			}
		});
		
		
	}
}
