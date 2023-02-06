package com.memberone;

import java.sql.*;
import java.util.Vector;

import javax.sql.*;
import javax.naming.*;

public class StudentDAO {

	private Connection getConnection() {
		
		Connection conn = null;
		
		try {
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/myoracle");
			conn = ds.getConnection();
			
		} catch (Exception e) {
			System.out.println("Connection 생성 실패!");
		}
		
		return conn;
	}
	
	// idCheck
	public boolean idCheck(String id) {
		boolean result = true;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			conn = getConnection();
		
			String sql = "select * from student where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(!rs.next()) result = false;
			
		} catch(SQLException ss) {
			ss.printStackTrace();
		} finally {
			if(rs != null)
				try{
					rs.close();
				}catch(SQLException ex){}
			
			if(pstmt != null)
				try{
					pstmt.close();
				}catch(SQLException ex){}
			
			if(conn != null)
				try{
					conn.close();
				}catch(SQLException ex){}
		}
		
		return result;
	} // end idCheck
	
	// zipcodeRead (동으로 검색)
	public Vector<ZipCodeVO> zipcodeRead(String dong){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Vector<ZipCodeVO> vecList = new Vector<ZipCodeVO>();
		
		try {
			
			conn = getConnection();
		
			String sql = "select * from zipcode where dong like '" +dong+ "%'" ;
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ZipCodeVO tempZipcode = new ZipCodeVO();
				tempZipcode.setZipcode(rs.getString("zipcode"));
				tempZipcode.setSido(rs.getString("sido"));
				tempZipcode.setGugun(rs.getString("gugun"));
				tempZipcode.setDong(rs.getString("dong"));
				tempZipcode.setRi(rs.getString("ri"));
				tempZipcode.setBunji(rs.getString("bunji"));
				vecList.addElement(tempZipcode);
				
			}
			
		} catch(SQLException ss) {
			ss.printStackTrace();
		} finally {
			if(rs != null)
				try{
					rs.close();
				}catch(SQLException ex){}
			
			if(pstmt != null)
				try{
					pstmt.close();
				}catch(SQLException ex){}
			
			if(conn != null)
				try{
					conn.close();
				}catch(SQLException ex){}
		}
		
		return vecList;
	} // end zipcodeRead
	
	//실제 데이터베이스 회원 데이터를 저장하기 위하여 메소드를 추가한다.
	public boolean memberInsert(StudentVO vo) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		
		try {
			
			conn = getConnection();
			
			String sql = "insert into student values(?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPass());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getPhone1());
			pstmt.setString(5, vo.getPhone2());
			pstmt.setString(6, vo.getPhone3());
			pstmt.setString(7, vo.getEmail());
			pstmt.setString(8, vo.getZipcode());
			pstmt.setString(9, vo.getAddress1());
			pstmt.setString(10, vo.getAddress2());
						
			int count = pstmt.executeUpdate();
			if(count > 0) flag = true;
					
		} catch(SQLException ss) {
			ss.printStackTrace();
		} finally {
			if(rs != null)
				try{
					rs.close();
				}catch(SQLException ex){}
			
			if(pstmt != null)
				try{
					pstmt.close();
				}catch(SQLException ex){}
			
			if(conn != null)
				try{
					conn.close();
				}catch(SQLException ex){}
		}
		
		return flag;
	} // end 메소드
	
}
