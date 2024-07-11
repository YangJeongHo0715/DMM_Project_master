package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBconnectDAO {
	Connection conn; //선언

	public void getCon() {
		try {

			Class.forName("org.mariadb.jdbc.Driver");
			String url="jdbc:mariadb://localhost:3306/dmm"; //데이타베이스명 : DMM
			String user="root";
			String password="1234";

			conn = DriverManager.getConnection(url, user, password); //초기화

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
