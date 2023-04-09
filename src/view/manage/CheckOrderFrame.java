package view.manage;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.CommonFrame;

public class CheckOrderFrame extends CommonFrame {
	static DefaultTableModel model = new DefaultTableModel("No,아이디,메뉴,가격,수량".split(","), 0) {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	static JTable table = new JTable(model);
	static JScrollPane scroll = new JScrollPane(table);

	public CheckOrderFrame() {
		super(600, 500, "주문확인");

		var beforeBt = new JButton("이전");
		beforeBt.setBackground(Color.darkGray);
		beforeBt.setForeground(Color.white);
		add(setBounds(beforeBt, 20, 20, 100, 30));

		beforeBt.addActionListener(e -> {
			dispose();
			new ManageFrame().setVisible(true);
		});

		initTable();

		int[] columnSize = {20, 60, 200, 50, 20};
		for(int i = 0; i < columnSize.length; i++) table.getColumnModel().getColumn(i).setPreferredWidth(columnSize[i]);
		table.getTableHeader().setReorderingAllowed(false);      

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if(e.getClickCount() == 2) {
					int state = JOptionPane.showConfirmDialog(null, "주문 확인 되었나요?", "삭제", JOptionPane.YES_NO_OPTION);
					if(state == JOptionPane.YES_OPTION) {
						updateSQL("DELETE FROM manage WHERE m_no = ?", model.getValueAt(row, 0));
						model.removeRow(row);
						
						infoMsg("삭제가 완료되었습니다.");
						
					}
				}
			}
		});
		add(setBounds(scroll, 0, 100, 590, 300));
	}

	public static void initTable() {
		model.setRowCount(0);
		try (var rs = getResulSet("SELECT * FROM manage")) {
			while(rs.next()) {
				model.addRow(new Object[] {
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
				});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		table = new JTable(model);
		scroll = new JScrollPane(table);
	}

	public static void main(String[] args) {
		new CheckOrderFrame().setVisible(true);
	}
}
