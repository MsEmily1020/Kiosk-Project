package view.manage;

import controller.CommonFrame;

public class CheckOrderFrame extends CommonFrame {
	public CheckOrderFrame() {
		super(600, 500, "주문확인");
		
	}

	public static void main(String[] args) {
		new CheckOrderFrame().setVisible(true);
	}
}
