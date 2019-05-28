package com.test.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;

public class Ex08_CallableStatement {
	
	private static Connection conn;
	private static CallableStatement cstat;
	
	static {
		DBUtil util = new DBUtil();
		conn = util.connect();
	}
	
	public static void main(String[] args) {
		
		//1. Statement : 정적 쿼리
		//2. PreparedStatement : 동적 쿼리
		//3. CallableStatement : 프로시저 호출 전용
		
//		m1();
//		m2();
//		m3();
//		m4();
		m5();
	}

	private static void m5() {
		
		try {
			
			//presult out sys_refcursor(매개변수)
			String sql = "{ call procListTodo(?) }";
			cstat = conn.prepareCall(sql);
			
			cstat.registerOutParameter(1, OracleTypes.CURSOR);
			
			cstat.executeQuery();
			
			// select의 결과인 cursor가 자바의 ResultSet으로 반환
			ResultSet rs = (ResultSet)cstat.getObject(1);
			
			while(rs.next()) {
				System.out.println(rs.getString("title") + " : " + rs.getString("adddate"));
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
		
	}

	private static void m4() {
		
		//procCountSetTodo(out 안한일, out 한일, out 모두)
		String sql = "{ call procCountSetTodo(?, ?, ?) }";
		try {
			cstat = conn.prepareCall(sql);

			cstat.registerOutParameter(1, OracleTypes.NUMBER);
			cstat.registerOutParameter(2, OracleTypes.NUMBER);
			cstat.registerOutParameter(3, OracleTypes.NUMBER);
			
			cstat.executeQuery();
			
			int cnt1 = cstat.getInt(1);
			int cnt2 = cstat.getInt(2);
			int cnt3 = cstat.getInt(3);
			
			System.out.println(cnt1);
			System.out.println(cnt2);
			System.out.println(cnt3);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private static void m3() {
		
		//procDeleteTodo(in 삭제할 번호, out 결과)
		try {
			
			String sql = "{ call procDeleteTodo(?, ?) }";
			cstat = conn.prepareCall(sql);
			
			cstat.setString(1, "32");
			cstat.registerOutParameter(2, OracleTypes.NUMBER);
			
			cstat.executeUpdate();
			
			System.out.println(cstat.getInt(2));
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
	}

	private static void m2() {
		
		//procCompleteTodo(in 수정할 번호, out 결과)
		try {
			
			String sql = "{ call procCompelteTodo(?, ?) }";
			
			cstat = conn.prepareCall(sql);
			
			cstat.setString(1, "53");
			cstat.registerOutParameter(2, OracleTypes.NUMBER);
			
			cstat.executeUpdate();
			
			int result = cstat.getInt(2);
			
			System.out.println(result);
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}

	private static void m1() {
		
		//proAddTodo
		// - in : ptitle(VARCHAR2)
		// - OUT : presult(num)
		
		try {
			
			String sql = "{ call procAddTodo(?, ?) }";
			
			cstat = conn.prepareCall(sql);
			
			//in 매개변수
			cstat.setString(1, "할일 제목");
			
			//out 매개변수
			cstat.registerOutParameter(2, OracleTypes.NUMBER);
			
			cstat.executeQuery();
			
			//받아오기로 한 값 > out 매개변수(<> ResultSet)
			int result = cstat.getInt(2);
			
			System.out.println(result);
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}
	
	
}



















