package view.manage;

import controller.CommonFrame;

public class CheckSoldOutFrame extends CommonFrame {
	public CheckSoldOutFrame() {
		super(600, 500, "품절확인");
	}

	public static void main(String[] args) {
		new CheckOrderFrame().setVisible(true);
	}
}
