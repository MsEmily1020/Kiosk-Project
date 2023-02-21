package view.kiosk;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.CommonFrame;
import view.login.LoginFrame;

public class AddMenuTool extends CommonFrame {
	static DefaultTableModel model;
	
	public AddMenuTool() throws Exception {
		super(410, 400, "주문정보");
		this.setLocation(500, 500);

		model = new DefaultTableModel("번호,주문명,가격,수량,db번호".split(","), 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		var table = new JTable(model);
		table.add(new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
		table.removeColumn(table.getColumnModel().getColumn(4));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					int state =	JOptionPane.showConfirmDialog(null, "정말로 삭제 하시겠습니까?", "행을 삭제하기", JOptionPane.YES_NO_OPTION);
					if(state == JOptionPane.YES_OPTION) {
						int row = table.getSelectedRow();
						updateSQL("DELETE FROM `order` WHERE o_no = ?", model.getValueAt(row, 4));
						updateSQL("DELETE FROM `manage` WHERE m_no = ?", model.getValueAt(row, 4));
						model.removeRow(row);
						try {
							createDB();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		
		table.getTableHeader().setReorderingAllowed(false); //드래그 x
		table.getTableHeader().setResizingAllowed(false); //크기 변화 x

		int[] widthList = {60, 250, 100, 100};

		for(int i = 0; i < widthList.length; i++)
			table.getColumnModel().getColumn(i).setPreferredWidth(widthList[i]);

		add(setBounds(new JScrollPane(table), 0, 0, 400, 400));
		createDB();
	}
	
	static public void createDB() throws Exception {
		model.setRowCount(0);
		
		try(var rs = getResulSet("SELECT o_no, o_name, o_price, o_cnt\r\n"
				+ "FROM `order`\r\n"
				+ "WHERE o_id = ?", LoginFrame.id)) {

			int id = 0;
			
			while(rs.next()) {
				model.addRow(new Object[] {
						++id,
						rs.getString(2),
						rs.getInt(3),
						rs.getInt(4),
						rs.getInt(1),
				});
			}
		}
	}
}