package Kiosk;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class LoginFrame extends CommonFrame implements ActionListener {
	JTextField[] tf = new JTextField[2];
	static String name;
	static String id;
	
	public LoginFrame() {
		super(700, 550, "키오스크");

		JLabel[] label = new JLabel[2];
		JLabel img = new JLabel();
		JButton[] bt = new JButton[2];
		JButton login = new JButton("로그인");
		String[] str = {"아이디", "비밀번호", "회원가입", "관리자 로그인", "아직 회원이 아니세요?", "관리자는 로그인해주세요."};
		ImageIcon logo = new ImageIcon("./image/LoginLogo.png");

		for(int i = 0; i < 2 ; i++) {
			add(this.setBounds(label[i] = new JLabel(str[i]), 300, 140 + i * 50, 80, 80));
			add(this.setBounds(bt[i] = new JButton(str[i + 2]), 375, 350 + i * 60, 250, 40));
			setDarkGrayColor(bt[i]);
			add(this.setBounds(tf[i] = new JTextField(), 380, 160 + i * 50, 190, 40));
			add(this.setBounds(label[i] = new JLabel(str[i + 4]), 100, 325 + i * 60, 300, 100));
			label[i].setFont(new Font("한컴 말랑말랑 Bold", Font.BOLD, 17));
		}

		//회원가입
		bt[0].addActionListener(e -> {
			this.dispose();
			try {
				new PrivateInfoCorrectFrame().setVisible(true);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		//관리자 로그인
		bt[1].addActionListener(e -> {
			this.dispose();
		});

		img.setIcon(logo);
		add(this.setBounds(img, 50, 100, logo.getIconWidth(), logo.getIconHeight()));

		add(this.setBounds(img = new JLabel("로그인"), 270, 30, 300, 50));
		img.setFont(new Font("한컴 말랑말랑 Bold", Font.BOLD, 50));

		add(this.setBounds(login, 580, 160, 90, 85));
		setRedColor(login);
		login.addActionListener(this);
		login.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					
				}
			}
		});
	}

	//로그인
	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i = 0; i < 2; i++)
			if (tf[i].getText().length() == 0) {
				errorMsg("빈칸이 존재합니다.");
				return;
			}

		try (var rs = getResulSet("SELECT * FROM user WHERE u_id = ? AND u_pw = ?", tf[0].getText(), tf[1].getText())) {

			if (rs.next() == false) {
				errorMsg("회원 정보가 일치하지 않습니다.");

				tf[0].setText("");
				tf[1].setText("");
				tf[0].grabFocus();
				return;
			}

			id = rs.getString("u_id");
			name = rs.getString("u_user");
			infoMsg(name + "님 환영합니다." );
			this.dispose();
			new KioskOrderFrame().setVisible(true);

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
