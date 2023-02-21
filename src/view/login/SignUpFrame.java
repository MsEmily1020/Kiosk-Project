package view.login;

import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import controller.CommonFrame;

public class SignUpFrame extends CommonFrame {
	public SignUpFrame() {
		super(800, 800, "회원가입");
		JPanel panel = new JPanel(null);
		panel.setBorder(new TitledBorder(new LineBorder(Color.black), "회원가입"));

		this.add(this.setBounds(panel, 20, 20, 750, 710));

		String[] labelList = "이름,아이디,비밀번호,생년월일,이메일,성별,전화번호".split(",");
		JTextField[] tfList = new JTextField[6];
		for(int i = 0; i < tfList.length; i++) tfList[i] = new JTextField();
		JComboBox<String> cbList = new JComboBox<>("naver.com,outlook.com,daum.com,gmail.com,nate.com,kebi.com,yahoo.com,korea.com,empal.com,hanmail.net".split(","));
		panel.add(this.setBounds(cbList, 525, 405, 200, 40));
		JRadioButton[] rdoList = new JRadioButton[2];
		JLabel[] label = new JLabel[7];

		for(int i = 0; i < labelList.length; i++) {
			label[i] = new JLabel(labelList[i]);
			panel.add(this.setBounds(label[i], 150, 40 + 90 * i, 150, 40));
			label[i].setFont(new Font("한컴 말랑말랑 Bold", Font.BOLD, 30));

			//이름,아이디,비밀번호,생년월일
			if(i <= 3) {
				panel.add(this.setBounds(tfList[i], 280, 40 + 90 * i, 200, 40));
			}

			//이메일
			else if(i == 4) {
				panel.add(this.setBounds(tfList[i], 280, 45 + 90 * i, 200, 40));
				panel.add(this.setBounds(new JLabel("@"), 490, 400, 290, 30));
			}

			else if(i == 5) {
				//성별
				rdoList[0] = new JRadioButton("남");
				rdoList[1] = new JRadioButton("여");

				var group = new ButtonGroup();
				rdoList[0].setSelected(true);

				for(int j = 0; j < 2; j++) {
					group.add(rdoList[j]);
					panel.add(this.setBounds(rdoList[j], 280 + j * 80, 50 + 85 * i, 80, 80));
				}

				//전화번호
				panel.add(this.setBounds(tfList[i], 280, 580, 200, 40));
			}
		}

		var btSubmit = new JButton("가입");

		btSubmit.addActionListener(e -> {
			//빈칸
			for(int i = 0; i < tfList.length; i++)
				if(tfList[i].getText().length() == 0) { 
					errorMsg("빈칸이 존재합니다.");
					return;
				}

			//중복 아이디
			try(var rs = getResulSet("SELECT * FROM q1206.user WHERE u_id = ?",tfList[1].getText())){
				if(rs.next()) {
					errorMsg("이미 존재하는 아이디");
					return;
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			//비밀번호 형식
			if(tfList[2].getText().length() < 4) { 
				errorMsg("비밀번호의 길이는 4자리 이상입니다.");
				return;
			}
			else if(tfList[2].getText().matches(".*[0-9].*") == false) {
				errorMsg("비밀번호에 0~9 숫자가 들어가야 합니다.");
				return;
			}
			else if(tfList[2].getText().matches(".*[a-zA-Z].*") == false) {
				errorMsg("비밀번호에 a~z 또는 A~Z 알파벳이 들어가야 합니다.");
				return;
			}

			else if(tfList[2].getText().matches(".*[!@#$].*") == false) {
				errorMsg("비밀번호에 !@#$의 특수문자가 들어가야 합니다.");
				return;
			}

			//생년월일 형식
			var sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf.setLenient(false);
			try {
				var date = sdf.parse(tfList[3].getText());
				var now = new Date();

				if(date.compareTo(now) > 0) {
					throw new ParseException("", 0);
				}

			} catch (ParseException e1) {
				errorMsg("생년월일 형식이 맞지 않습니다. ex 2020-10-20");
				return;
			}

			if(!(tfList[5].getText().matches("\\d{3}-\\d{4}-\\d{4}"))) {
				errorMsg("전화번호 형식이 맞지 않습니다. ex 010-1234-5678");
				return;
			}

			updateSQL("INSERT INTO q1206.user (u_user, u_id, u_pw, u_birth, u_email, u_gender, u_phone) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)",
					tfList[0].getText(), //이름
					tfList[1].getText(), //아이디
					tfList[2].getText(), //비밀번호
					tfList[3].getText(), //생년월일
					tfList[4].getText() + "@" + cbList.getSelectedItem(), //이메일
					rdoList[0].isSelected() ? 1 : 2, //성별
					tfList[5].getText() //전화번호
					);

			infoMsg("회원가입이 완료되었습니다.");
			this.dispose();
			new LoginFrame().setVisible(true);
		});

		btSubmit.setBackground(Color.red);
		panel.add(setBounds(btSubmit, 300, 640, 150, 60));
	}
}
