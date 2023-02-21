package controller;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CommonFrame extends JFrame {
	ImageIcon logo = new ImageIcon("./image/LotteriaLogo.png");

	static Connection con;
	static Statement stmt;
	
	static {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/q1206", "root", "1234");
			stmt = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public CommonFrame(int width, int height, String title) {
		setIconImage(logo.getImage());
		setLayout(null);
		setSize(width, height);
		setTitle(title);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public JComponent setBounds(JComponent comp, int x, int y, int width, int height) {
		comp.setBounds(x, y, width, height);
		comp.setFont(new Font("한컴 말랑말랑 Bold", Font.BOLD, 15));
		return comp;
	}
	
	public JComponent setRedColor(JComponent comp) {
		comp.setBackground(Color.red);
		comp.setForeground(Color.white);
		return comp;
	}

	public JComponent setDarkGrayColor(JComponent comp) {
		comp.setBackground(Color.darkGray);
		comp.setForeground(Color.white);
		return comp;
	}
	
	public static void errorMsg(String text) {
		JOptionPane.showMessageDialog(null, text, "경고", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void infoMsg(String text) {
		JOptionPane.showMessageDialog(null, text, "정보", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static ResultSet getResulSet(String sql, Object... paramter) {
		try {
			var pstmt = con.prepareStatement(sql);
		 
			for (int i = 0; i < paramter.length; i++) {
				pstmt.setObject(i + 1, paramter[i]);
			}
			
			//SELECT 전용
			return pstmt.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static ResultSet updateSQL(String sql, Object... paramter) {
		try {
			var pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			for (int i = 0; i < paramter.length; i++) {
				pstmt.setObject(i + 1, paramter[i]);
			}
			
			//INSER, UPDATE, DELETE 데이터 변경
			pstmt.executeUpdate();
			
			return pstmt.getGeneratedKeys();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}