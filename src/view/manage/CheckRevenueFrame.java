package view.manage;

import controller.CommonFrame;

public class CheckRevenueFrame extends CommonFrame {
	public CheckRevenueFrame() {
		super(600, 700, "매출확인");
	}

	public static void main(String[] args) {
		new CheckRevenueFrame().setVisible(true);
	}
}
