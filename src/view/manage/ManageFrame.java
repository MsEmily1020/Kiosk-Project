package view.manage;

import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.CommonFrame;

public class ManageFrame extends CommonFrame {
	public ManageFrame() {
		super(800, 600, "관리자 프로그램");
		
		JLabel title = new JLabel("관리자", 0);
		title.setFont(new Font("한컴 말랑말랑 Bold", Font.BOLD, 50));
		title.setBounds(0, 20, 800, 100);
		add(title);
		
		DefaultTableModel model = new DefaultTableModel("No,이름,아이디,생년월일,이메일,성별,전화번호".split(","), 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		JTable table = new JTable(model);
		JScrollPane scroll = new JScrollPane(table);
		int[] columnSize = {50, 60, 150, 100, 150, 60, 100};
		for(int i = 0; i < columnSize.length; i++) table.getColumnModel().getColumn(i).setPreferredWidth(columnSize[i]);
		table.getTableHeader().setReorderingAllowed(false);      
		
		try (var rs = getResulSet("SELECT * FROM user")) {
			int id = 0;
			while(rs.next()) {
				model.addRow(new Object[] {
						++id,
						rs.getString(2),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getInt(7) == 1 ? "남자" : "여자",
						rs.getString(8)
					});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		add(setBounds(scroll, 100, 150, 600, 250));
		
		String[] bt = "주문확인,품절확인,매출확인".split(",");
		for (int i = 0; i < bt.length; i++) {
			var buttons = new JButton(bt[i]);
			add(setBounds(buttons, 130 + i * 200, 440, 120, 50));
			if(i % 2 == 0) buttons.setBackground(Color.darkGray);
			else buttons.setBackground(Color.red);
			buttons.setForeground(Color.white);
			
			buttons.addActionListener(e -> {
				if(buttons.getText().equals("주문확인")) {
					this.dispose();
					new CheckOrderFrame().setVisible(true);
				}
				
				else if(buttons.getText().equals("품절확인")) {
					this.dispose();
					new CheckSoldOutFrame().setVisible(true);
				}
				
				else {
					this.dispose();
					new CheckRevenueFrame().setVisible(true);
				}
			});
		}
	}
	
	public static void main(String[] args) {
		new ManageFrame().setVisible(true);
	}
}
