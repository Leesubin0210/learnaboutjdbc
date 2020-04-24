package com.hansin.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		List <user> list = new ArrayList <> ();
		list.add(new user(1, "a", "010-1111-1111", "a@gmail.com", "Seoul"));
		list.add(new user(2, "b", "010-2222-2222", "b@gmail.com", "Busan"));
		list.add(new user(3, "c", "010-3333-3333", "c@gmail.com", "LA"));
		list.add(new user(4, "d", "010-4444-4444", "d@gmail.com", "Washington D.C"));
		list.add(new user(5, "e", "010-5555-5555", "e@gmail.com", "Barcelona"));
		
		String jdbc_driver = "com.mysql.cj.jdbc.Driver"; 
		String jdbc_url = "jdbc:mysql://localhost:3306/databasetest?serverTimezone=UTC"; 
		
		Connection con = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql2 = "insert into addressbook (id, name, tel, email, address) values(?,?,?,?,?)";
		String sqllookup = "select * from addressbook";
		String sql3 = "update addressbook set email = replace(email, '@gmail.com', '@naver.com')";
		String sql4 = "delete from addressbook order by id desc limit 2";
				
		try {
			Class.forName(jdbc_driver).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Can't connected JDBC Driver");
		}
		
		//1번 
		try {
			con = DriverManager.getConnection(jdbc_url, "root", "331726");
			System.out.println("Connection Successed");
			System.out.println("");
			
			stmt = con.createStatement();
			
			StringBuilder sb = new StringBuilder();
			String sql1 = sb.append("create table if not exists addressbook(")
					.append("id int,")
					.append("name varchar(45),")
					.append("tel varchar(45),")
					.append("email varchar(60),")
					.append("address varchar(60));").toString();
			
			stmt.execute(sql1);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		//2번
		try {
			pstmt = con.prepareStatement(sql2);
			for(Iterator <user> iterator = list.iterator(); iterator.hasNext();){
				user user = (user) iterator.next();
				pstmt.setInt(1, user.getId());
				pstmt.setString(2, user.getName());
				pstmt.setString(3, user.getTel());
				pstmt.setString(4, user.getEmail());
				pstmt.setString(5, user.getAddress());
				pstmt.addBatch();
			}
			int[] updateCount = pstmt.executeBatch();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Can't insert arrays into addressbook table");
		}
		
		//2번 조회
		System.out.println("#2번 결과 조회");
		try {
			rs = stmt.executeQuery(sqllookup);
			while(rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String tel = rs.getString(3);
				String email = rs.getString(4);
				String address = rs.getString(5);
				
				System.out.println("id = " + id + " name= " + name + " tel= " + tel + " email= " + email + " address= " + address);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//3번 
		try {
			for(Iterator <user> iterator = list.iterator(); iterator.hasNext();){
				user user = (user) iterator.next();
				pstmt = con.prepareStatement(sql3);
				pstmt.addBatch();
			}
			int[] updateCount = pstmt.executeBatch();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//3번 조회
		System.out.println();
		System.out.println("#3번 결과 조회");
		try {
			rs = stmt.executeQuery(sqllookup);
			while(rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String tel = rs.getString(3);
				String email = rs.getString(4);
				String address = rs.getString(5);
				
				System.out.println("id = " + id + " name= " + name + " tel= " + tel + " email= " + email + " address= " + address);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//4번
		try {
			stmt.execute(sql4);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		//4번 조회
		System.out.println();
		System.out.println("#4번 결과 조회");
		try {
			rs = stmt.executeQuery(sqllookup);
			while(rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String tel = rs.getString(3);
				String email = rs.getString(4);
				String address = rs.getString(5);
				
				System.out.println("id = " + id + " name= " + name + " tel= " + tel + " email= " + email + " address= " + address);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//ResultSet, Statement, Connection close
		try {
			rs.close();
			stmt.close();
			if(con !=null && !con.isClosed())
				con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
