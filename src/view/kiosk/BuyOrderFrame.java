package view.kiosk;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import controller.CommonFrame;
import view.login.LoginFrame;
import vo.MenuVO;

public class BuyOrderFrame extends CommonFrame {
	public static ArrayList<MenuVO> mvo = new ArrayList<>();
	static int orderCnt;
	public BuyOrderFrame() throws Exception {
		super(600, 800, "주문창");
		
		for(var m : mvo) {
			updateSQL("INSERT INTO q1206.manage (m_id, m_name, m_price, m_cnt) "
					+ "VALUES (?, ?, ?, ?)",
					LoginFrame.id,
					m.getName(),
					m.getPrice(),
					m.getCnt()
					);
		}
		
		int sum = 0;
		for(var vo : mvo)
			sum += vo.getPrice() * vo.getCnt();

		JLabel lb1 = new JLabel("총 금액 : ");
		lb1.setFont(new Font("HY견명조", Font.BOLD, 20));
		lb1.setBounds(50, 40, 100, 50);
		this.add(lb1);

		//총 금액
		JLabel priceLb = new JLabel(String.valueOf(sum));
		priceLb.setFont(new Font("HY견명조", Font.BOLD, 20));
		priceLb.setForeground(Color.red);
		priceLb.setHorizontalAlignment(JLabel.CENTER);
		priceLb.setBounds(450, 40, 100, 50);
		this.add(priceLb);

		JLabel lb2 = new JLabel("주문번호 : ");
		lb2.setFont(new Font("HY견명조", Font.BOLD, 50));
		lb2.setBounds(80, 50, 600, 300);
		this.add(lb2);

		//주문번호
		JLabel orderNumLb = new JLabel(String.valueOf(++orderCnt));
		orderNumLb.setFont(new Font("HY견명조", Font.BOLD, 50));
		orderNumLb.setForeground(Color.red);
		orderNumLb.setHorizontalAlignment(JLabel.CENTER);
		orderNumLb.setSize(840, 400);
		this.add(orderNumLb);

		JLabel correctLb = new JLabel("__________________");
		correctLb.setFont(new Font("HY견명조", Font.BOLD, 50));
		correctLb.setBounds(50, 0, 600, 150);
		this.add(correctLb);

		JLabel lb3 = new JLabel("영수증을 받아가주세요.");
		lb3.setFont(new Font("HY견명조", Font.PLAIN, 20));
		lb3.setBounds(180, 270, 600, 100);
		this.add(lb3);

		JLabel lb4 = new JLabel("<html><body><center> 결제 완료되었습니다. <br><br> 이용해 주셔서 감사합니다. </center></body></html>");
		lb4.setFont(new Font("HY견명조", Font.PLAIN, 20));
		lb4.setBounds(180, 580, 600, 80);
		this.add(lb4);

		JLabel lb5 = new JLabel("<html><body><center> 카드를 회수해주시기 바랍니다. <br> 결제 화면은 자동으로 닫힙니다. </center></body></html>");
		lb5.setFont(new Font("HY견명조", Font.PLAIN, 16));
		lb5.setForeground(Color.red);
		lb5.setBounds(180, 660, 600, 100);
		this.add(lb5);

		ImageIcon card = new ImageIcon("./image/kioskCardImg.PNG");
		JLabel cardLb = new JLabel();
		cardLb.setIcon(card);
		this.add(this.setBounds(cardLb, 40, 320, card.getIconWidth(), card.getIconHeight()));

		this.getContentPane().setBackground(Color.white);
		
		KioskOrderFrame.mvo.clear();
		
		Timer time = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				dispose();
				new LoginFrame().setVisible(true);
			}
		};

		time.schedule(task, 8000);
	}
}