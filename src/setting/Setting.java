package setting;

import java.sql.DriverManager;

import controller.CommonFrame;

public class Setting {
	public static void main(String[] args) {
		try {
			CommonFrame.infoMsg("셋팅 성공");
			setup();
		} catch (Exception e) {
			CommonFrame.errorMsg("셋팅 실패");
		}
	}

	public static void setup() throws Exception /*-> 컴퓨터가 해결 = 기본 빨간줄*/{
		var con = DriverManager.getConnection(  
				"jdbc:mysql://localhost/?allowLoadLocalInfile=true",
				"root",
				"1234");
		var stmt = con.createStatement();

		stmt.execute("SET GLOBAL local_infile = true;");
		stmt.execute("DROP SCHEMA IF EXISTS `q1206`");
		stmt.execute("CREATE SCHEMA `q1206` DEFAULT CHARACTER SET utf8;");

		System.out.println("q1206 DB 생성");

		stmt.execute("CREATE TABLE `q1206`.`user` ("
				+ "u_no INT PRIMARY KEY AUTO_INCREMENT,"
				+ "u_user VARCHAR(10),"
				+ "u_id VARCHAR(10),"
				+ "u_pw VARCHAR(15),"
				+ "u_birth VARCHAR(15),"
				+ "u_email VARCHAR(30),"
				+ "u_gender INT,"
				+ "u_phone VARCHAR(30)"
				+ ")");
		
		System.out.println("user DB 생성");
		
		stmt.execute("CREATE TABLE `q1206`.`manage` ("
				+ "m_no INT PRIMARY KEY AUTO_INCREMENT,"
				+ "m_id VARCHAR(10),"
				+ "m_name VARCHAR(20),"
				+ "m_price INT,"
				+ "m_cnt INT"
				+ ")");
		
		System.out.println("manage DB 생성");
	}
}
