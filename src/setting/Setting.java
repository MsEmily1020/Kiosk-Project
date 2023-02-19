package setting;

import java.sql.DriverManager;

import Kiosk.CommonFrame;

public class Setting {
	public static void main(String[] args) {
		try {
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

		try {
			stmt.execute("DROP DATABASE `q1206`");
			System.out.println(";;q1206 제거");
		} catch(Exception e) {
			System.out.println("q1206 존재하지 않음");
		}

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
		
		stmt.execute("CREATE TABLE `q1206`.`order` ("
				+ "o_no INT PRIMARY KEY AUTO_INCREMENT,"
				+ "o_id VARCHAR(10),"
				+ "o_user VARCHAR(10)," //user 이름
				+ "o_name VARCHAR(20)," //주문 이름
				+ "o_price INT,"		//해당 주문 가격
				+ "o_cnt INT"			//해당 주문 갯수
				+ ")");

		System.out.println("order DB 생성");
		
		CommonFrame.infoMsg("셋팅 성공");
	}
}
