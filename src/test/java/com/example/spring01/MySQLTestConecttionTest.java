package com.example.spring01;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySQLTestConecttionTest {
	private static final Logger logger = 
			LoggerFactory.getLogger(MySQLTestConecttionTest.class);
	private static final String DRIVER=
			"com.mysql.cj.jdbc.Driver";
	private static final String URL =
			"jdbc:mysql://localhost:3306/java?characterEncoding=UTF-8&serverTimezone=UTC";	// ?useSSL=false&amp;serverTimezone=UTC
	private static final String USER = "java";
	private static final String PW = "java1234";
	
	@Test
	public void testConnection() throws Exception {
		Class.forName(DRIVER);
		// try ~ with 문 : 자동으로 close를 시켜줘서 finally절이 필요 없음 (java 1.7 이상)
		try (Connection conn =
					DriverManager.getConnection(URL, USER, PW)){
			System.out.println("MySQL에 연결되었습니다.");
			logger.info("MySQL에 연결되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
