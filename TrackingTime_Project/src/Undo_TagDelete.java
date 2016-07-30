import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.xml.crypto.Data;

public class Undo_TagDelete extends JFrame {
	DatabaseConnection db = new DatabaseConnection();
	String SelectedId;
	DefaultTableModel model;
	JTable table;

	public Undo_TagDelete() {
		try {
			db.Connect();
		} catch (Exception e) {
			// TODO: handle exception
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setFont(new Font("Roboto", Font.PLAIN, 12));
		scrollPane.setBorder(new MatteBorder(1, 4, 4, 1, (Color) new Color(192,
				192, 192)));
		scrollPane.setBounds(1, 40, 280, 100);
		add(scrollPane);

		table = new JTable();
		table.setDefaultEditor(Object.class, null);
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		setTitle("Undo Tag");
		setSize(300, 200);
		setLayout(null);
		Load("Select * from Tag where Status='" + false + "'");
		JButton btnUndo = new JButton("Undo");
		btnUndo.setBounds(100, 10, 100, 20);
		add(btnUndo);
		btnUndo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {

					PreparedStatement query = db
							.getConnection()
							.prepareStatement(
									"Update Tag set Status='" + true
											+ "' where Id='" + SelectedId + "'");
					query.executeUpdate();
					Load("Select * from Tag where Status='" + false + "'");
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int row = table.getSelectedRow();
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				SelectedId = (model.getValueAt(row, 0)).toString();
				System.out.println(SelectedId);
			}
		});
	}

	public void Load(String queryExe) {
		try {
			model = new DefaultTableModel(new String[] { "Id", "No", "Name" },
					0);
			PreparedStatement query = db.getConnection().prepareStatement(
					queryExe);
			ResultSet rs = query.executeQuery();
			Object[] row = new Object[3];
			int i = 1;
			if (!rs.isBeforeFirst()) {
				table.setModel(model);
			} else {
				while (rs.next()) {
					row[0] = rs.getString("Id");
					row[1] = i;
					row[2] = rs.getString("Name");
					model.addRow(row);
					i++;
				}
				table.setModel(model);
				table.getColumnModel().getColumn(0).setMinWidth(0);
				table.getColumnModel().getColumn(0).setMaxWidth(0);
				table.getColumnModel().getColumn(0).setWidth(0);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
