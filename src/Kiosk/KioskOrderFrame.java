package Kiosk;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

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
		new AddMenuTool().setVisible(true);
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
		orderBt.setBackground(Color.DARK_GRAY);
		orderBt.setForeground(Color.white);
		orderBt.setBounds(0, 0, this.getWidth(), 70);
		orderBtPn.add(orderBt);
		this.add(orderBtPn, BorderLayout.SOUTH);
		orderBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new BuyOrderFrame().setVisible(true);
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