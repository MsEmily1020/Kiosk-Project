package view.kiosk;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import controller.CommonFrame;
import view.login.LoginFrame;
import vo.MenuVO;

public class SelectOrderFrame extends CommonFrame implements ActionListener {
	String price;
	JLabel priceLb = new JLabel();
	String menuName = "";
	int cnt = 1;
	public SelectOrderFrame(ImageIcon img) throws Exception {
		super(600, 800, "추가하기");
		String imgPoint = img.getDescription();
		String[] menu = "hamburger,chicken,disert,drink".split(",");

		//메뉴 라벨, 이미지, 설명, 추가메뉴 추가
		JLabel menuLb;
		for(int i = 0; i < menu.length; i++) {
			switch(imgPoint.indexOf(menu[i])) {
			case -1 :
				continue;
			default :
				//설명
				menuName = Files.readAllLines(Paths.get("./exp/" + menu[i] + "_exp.txt")).get(Integer.parseInt(imgPoint.replaceAll("[^\\d]", "")) - 1);
				this.add(setBounds(menuLb = new JLabel(("<html><body><center>" + menuName.substring(0, menuName.length() / 10 * 5) + "<br> <br>" + menuName.substring(menuName.length() > 20 ? menuName.length() / 10 * 5 : 0, menuName.length()) + "</center></body></html>")), 0, 250, 600, 300));
				menuLb.setHorizontalAlignment(JLabel.CENTER);

				//메뉴 이름
				menuName = Files.readAllLines(Paths.get("./txt/" + menu[i] + "_menu.txt")).get(Integer.parseInt(imgPoint.replaceAll("[^\\d]", "")) - 1);
				this.add(setBounds(menuLb = new JLabel(menuName), 0, 30, 600, 50));
				menuLb.setHorizontalAlignment(JLabel.CENTER);
				menuLb.setFont(new Font("한컴 말랑말랑 Bold", Font.BOLD, 30));

				//가격, 수량
				JLabel quantityLb = new JLabel("1");
				this.add(setBounds(quantityLb, 200, 510, 200, 50));
				quantityLb.setOpaque(true);
				quantityLb.setHorizontalAlignment(JLabel.CENTER);
				quantityLb.setBackground(Color.white);

				price = Files.readAllLines(Paths.get("./price/" + menu[i] + "_price.txt")).get(Integer.parseInt(imgPoint.replaceAll("[^\\d]", "")) - 1);
				priceLb.setText("<" + price + "원>");

				JButton[] plusMinus = new JButton[2];
				String[] pM = "-,+".split(",");
				for(int j = 0; j < 2; j++) {
					plusMinus[j] = new JButton(pM[j]);
					plusMinus[j].addActionListener(e -> {
						cnt = Integer.parseInt(quantityLb.getText());
						for(int k = 0; k < 2; k++)
							if (e.getSource() == plusMinus[k])
								if(k == 0) {
									if(--cnt == 0) {
										errorMsg("1개 이상으로 해주세요.");
										return;
									}
									quantityLb.setText(Integer.toString(cnt));
								}
								else quantityLb.setText(Integer.toString(++cnt));
						priceLb.setText("<" + (Integer.parseInt(price) * cnt) + "원>");
					});
					this.add(setBounds(plusMinus[j], 150 + j * 250, 510, 50, 50));	
				}
				plusMinus[0].setBackground(Color.darkGray);
				plusMinus[0].setForeground(Color.white);
				plusMinus[1].setBackground(Color.red);
				plusMinus[1].setForeground(Color.white);

				this.add(setBounds(priceLb, 0, 450, 600, 50));
				priceLb.setHorizontalAlignment(JLabel.CENTER);

				break;
			}
		}
		this.add(setBounds(menuLb = new JLabel(img), 175, 100, 250, 250));

		//추가버튼
		JButton add = new JButton("추가");
		this.add(setBounds(add, 420, 660, 140, 70));
		setRedColor(add);
		add.addActionListener(this);

		//이전버튼
		JButton cancel = new JButton("이전");
		this.add(setBounds(cancel, 30, 660, 140, 70));
		setDarkGrayColor(cancel);
		cancel.addActionListener(e -> {
			this.dispose();
		});
	}

	//추가하기
	@Override
	public void actionPerformed(ActionEvent e) {
		BuyOrderFrame.dao.insert(new MenuVO(LoginFrame.id, menuName, Integer.parseInt(price), cnt));
		try {
			AddMenuTool.createDB();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		this.dispose();
	}
}