package view.kiosk;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import controller.CommonFrame;
import view.login.LoginFrame;

public class KioskOrderFrame extends CommonFrame implements ActionListener {
	JPanel menuPn = new JPanel(new GridLayout());
	JScrollPane scroll = new JScrollPane(menuPn, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	JButton[] menu;
	JButton[] bt = new JButton[4];
	ImageIcon[] imgIcon;
	String[][] menuName;
	int[] menuCnt = {17, 6, 17, 15};
	String[] menuType = "hamburger,chicken,disert,drink".split(",");

	public KioskOrderFrame() throws Exception {
		super(600, 800, "주문하기");
		AddMenuTool menu = new AddMenuTool();
		menu.setVisible(true);
		this.setLayout(new BorderLayout());

		//메뉴 선택
		String[] imageName = "햄버거,치킨,디저트,음료".split(",");
		JPanel btPn = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
		for(int i = 0; i < bt.length; i++) {
			bt[i] = new JButton(imageName[i]);
			setRedColor(bt[i]);
			bt[i].setPreferredSize(new Dimension(100, 50));
			btPn.add(bt[i]);
		}

		menuImg(17, "./image/hamburger/버거");

		//이미지 띄우기
		for(int i = 0; i < bt.length; i++) {
			bt[i].addActionListener(e -> {
				imageName[0] = "버거";
				for(int j = 0; j < bt.length; j++) {
					if(e.getSource() == bt[j]) {
						menuImg(menuCnt[j], "./image/" + menuType[j] + "/" + imageName[j]);
					}
				}
			});
		}

		//메뉴 이름
		for (int i = 0; i < menuType.length; i++) {
			menuName = new String[menuType.length][menuCnt[i]];
			for(int j = 0; j < menuCnt[i]; j++) {
				menuName[i][j] = Files.readAllLines(Paths.get("./txt/" + menuType[i] + "_menu.txt")).get(Integer.parseInt(imgIcon[j].getDescription().replaceAll("[^\\d]", "")) - 1);
			}
		}

		this.add(btPn, BorderLayout.NORTH);

		//주문 버튼
		JPanel orderBtPn = new JPanel(null);
		orderBtPn.setPreferredSize(new Dimension(this.getWidth(), 70));
		JButton orderBt = new JButton("주문");
		JButton beforeBt = new JButton("이전");

		beforeBt.setForeground(Color.white);
		beforeBt.setBackground(Color.red);
		beforeBt.setBounds(0, 0, this.getWidth() / 2, 70);
		orderBtPn.add(beforeBt);

		orderBt.setBackground(Color.DARK_GRAY);
		orderBt.setForeground(Color.white);
		orderBt.setBounds(300, 0, this.getWidth() / 2, 70);
		orderBtPn.add(orderBt);

		this.add(orderBtPn, BorderLayout.SOUTH);

		//주문버튼 클릭
		orderBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menu.setLocationRelativeTo(null);
				dispose();
				int yesOrNo = JOptionPane.showConfirmDialog(null, "주문버튼을 클릭하셨습니다.", "이대로 주문하시겠습니까?", JOptionPane.YES_NO_OPTION);
				if(yesOrNo == JOptionPane.YES_OPTION) {
					menu.dispose();
					try {
						new BuyOrderFrame().setVisible(true);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				else {
					try {
						menu.dispose();
						new KioskOrderFrame().setVisible(true);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		//이전버튼 클릭
		beforeBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int state =	JOptionPane.showConfirmDialog(null, "주문 내용은 모두 삭제됩니다.", "주문을 취소합니다.", JOptionPane.YES_NO_OPTION);
				if(state == JOptionPane.YES_OPTION) {
					menu.dispose();
					updateSQL("DELETE FROM `order` WHERE o_id = ?", LoginFrame.id);
					updateSQL("DELETE FROM `manage` WHERE m_id = ?", LoginFrame.id);
					dispose();
					new LoginFrame().setVisible(true);
				}
			}
		});
	}

	public void menuImg(int size, String img) {
		this.remove(menuPn);
		this.remove(scroll);
		menu = new JButton[size];
		menuPn = new JPanel(new GridLayout(size % 2 == 0 ? size / 2 : size / 2 + 1, 2, 80, 80));
		menuPn.setBorder(new EmptyBorder(70, 70, 70, 70));
		scroll = new JScrollPane(menuPn, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.getVerticalScrollBar().setUnitIncrement(10);

		imgIcon = new ImageIcon[menu.length];
		for(int i = 0; i < imgIcon.length; i++) {
			imgIcon[i] = new ImageIcon(img + (i+1) + ".PNG");
			menu[i] = new JButton(imgIcon[i]);
			menu[i].setPreferredSize(new Dimension(100, 200));
			menuPn.add(menu[i]);
			menu[i].addActionListener(this);
		}

		this.add(scroll, BorderLayout.CENTER);
		this.pack();
		this.setSize(new Dimension(600, 800));
	}

	//추가하기
	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i = 0; i < menu.length; i++) {
			if(e.getSource() == menu[i]) {
				try {
					new SelectOrderFrame(imgIcon[i]).setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}