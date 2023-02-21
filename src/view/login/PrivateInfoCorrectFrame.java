package view.login;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import controller.CommonFrame;

public class PrivateInfoCorrectFrame extends CommonFrame implements ActionListener {
	JRadioButton[] rd = new JRadioButton[4];

	public PrivateInfoCorrectFrame() throws Exception {
		super(800, 600, "개인정보 동의서");

		JButton[] button = new JButton[2];
		JLabel label = new JLabel("개인정보동의서");
		String[] str = {"이전 단계", "다음 단계"};
		JTextArea[] tf = new JTextArea[2];
		ButtonGroup[] group = new ButtonGroup[2];
		
		JPanel panel = new JPanel(null) {
			@Override
			public void paint(Graphics g) {
				super.paint(g);

				//그림이 그려지는 부분을 부드럽게 실행하여 그림
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				g.setColor(Color.black);
				g.drawRect(30, 60, 350, 390);
				g.drawRect(410, 60, 350, 390);
			}
		};
		panel.setBounds(0, 0, getWidth(), getHeight());

		add(this.setBounds(label, 260, 0, 400, 50));
		label.setFont(new Font("한컴 말랑말랑 Bold", Font.BOLD, 40));
		for(int i = 0; i < 2; i++) {
			panel.add(this.setBounds(button[i] = new JButton(str[i]), 50 + i * 390, 500, 300, 50));
			button[i].setForeground(Color.white);

			tf[i] = new JTextArea(Files.readString(Paths.get("./txt/private" + (i+1) + ".txt")));
			tf[i].setEditable(false);
			tf[i].setFont(new Font("HY견명조", Font.PLAIN, 14));
			JScrollPane scroll = new JScrollPane(tf[i]);
			panel.add(this.setBounds(scroll, 30 + i * 380, 60, 350, 390));	
			group[i] = new ButtonGroup();
		}

		str[0] = "동의합니다.";
		str[1] = "동의하지 않습니다.";
		
		for(int i = 0; i < 4; i++) {
			if(i % 2 == 0) {
				panel.add(this.setBounds(rd[i] = new JRadioButton(str[0]), 45 + i * 200, 465, 150, 20));
				group[0].add(rd[i / 2]);
			}
			else {
				panel.add(this.setBounds(rd[i] = new JRadioButton(str[1]), i * 200, 465, 150, 20));
				group[1].add(rd[2]);
				group[1].add(rd[3]);
			}
		}
		
		button[0].setBackground(Color.DARK_GRAY);
		button[1].setBackground(Color.red);

		//이전 단계
		button[0].addActionListener(e -> {
			this.dispose();
			new LoginFrame().setVisible(true);
		});
		button[1].addActionListener(this);
		add(panel);

	}

	//다음 단계
	@Override
	public void actionPerformed(ActionEvent e) {
		if(rd[0].isSelected() == true && rd[2].isSelected() == true) {
			this.dispose();
			new SignUpFrame().setVisible(true);
		}
		
		else {
			JOptionPane.showMessageDialog(null, "동의하지 않았습니다.");
		}

	}
}
