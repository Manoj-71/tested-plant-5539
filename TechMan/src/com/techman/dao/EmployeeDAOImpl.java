package com.techman.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.techman.dto.ComplainStatusDTO;
import com.techman.dto.ComplainStatusDTOImpl;
import com.techman.dto.ComplaintDTO;
import com.techman.exception.NoRecordFoundException;
import com.techman.exception.SomethingWentWrongException;

public class EmployeeDAOImpl implements EmployeeDAO {

	@Override
	public void login(String username, String password) throws SomethingWentWrongException, NoRecordFoundException {
		Connection conn = null;
		try {
			conn = DBUtils.getConnectionTodatabase();
			String query = "SELECT emp_id FROM employees WHERE username = ? AND "
					+ "password = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if(DBUtils.isResultSetEmpty(rs)) {
				throw new NoRecordFoundException("Invalid username or password");
			}
			
			rs.next();
			LoggedInUser.loggedInUserId = rs.getInt(1);
		}catch(ClassNotFoundException | SQLException ex) {
			throw new SomethingWentWrongException("Unable find employee try again later");
		}finally {
			try {
				DBUtils.closeConnection(conn);
			}catch(SQLException ex) {
				
			}
		}
	}

	@Override
	public void logout() {
		LoggedInUser.loggedInUserId = 0;
	}

	@Override
	public int addComplaint(ComplaintDTO complaintDTO) throws SomethingWentWrongException {
		int com_id = 0;
		Connection conn = null;
		try {
			conn = DBUtils.getConnectionTodatabase();
			String query = "INSERT INTO complaints (type,subtype,emp_id,eng_id) "
					+ "VALUES (?, ?, ?,?)";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, complaintDTO.getType());
			ps.setString(2, complaintDTO.getSub_type());
			ps.setInt(3, LoggedInUser.loggedInUserId);
			ps.setInt(4, 2);
			
			ps.executeUpdate();
			
			com_id= getTheGeneratedId();
			
		}catch(ClassNotFoundException | SQLException ex) {
			throw new SomethingWentWrongException("Unable to add complaint");
		}finally {
			try {
				DBUtils.closeConnection(conn);
			}catch(SQLException ex) {
				
			}
		}
		
		return com_id;
	}

	private int getTheGeneratedId() throws SomethingWentWrongException {
		int id=0;
		Connection conn = null;
		try {
			conn = DBUtils.getConnectionTodatabase();
			String query="SELECT com_id FROM complaints "
					+ "ORDER BY com_id DESC limit 1";
					
			PreparedStatement ps = conn.prepareStatement(query);
			
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
				id=rs.getInt(1);
			}
			
		}catch(ClassNotFoundException | SQLException ex) {
			throw new SomethingWentWrongException("Unable to add complaint");
		}finally {
			try {
				DBUtils.closeConnection(conn);
			}catch(SQLException ex) {
				
			}
		}
		
		
		return id;
	}

	@Override
	public ComplainStatusDTO getStatusOfComplaint(int com_id) throws SomethingWentWrongException {
		
		Connection conn = null;
		ComplainStatusDTO complainStatusDTO = null;
		try {
			conn = DBUtils.getConnectionTodatabase();
			String query="select com_id,department_name,subtype,status,email from Complaints C inner join"
					+ " departments D on C.type=D.dept_id inner join engineers E on C.eng_id=E.id AND com_id=?;";
					
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, com_id);
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
				String status;
				if (rs.getInt(4)==0) {
					status="Pending";
				}else {
					status="Solved";
				}
				complainStatusDTO=new ComplainStatusDTOImpl(rs.getInt(1), rs.getString(2), 
						rs.getString(3),status, rs.getString(5));
			}
			
		}catch(ClassNotFoundException | SQLException ex) {
			throw new SomethingWentWrongException("Unable to add complaint");
		}finally {
			try {
				DBUtils.closeConnection(conn);
			}catch(SQLException ex) {
				
			}
		}
		
		return complainStatusDTO;
		
	}

	@Override
	public List<ComplainStatusDTO> viewlistofalltheproblems() throws SomethingWentWrongException, NoRecordFoundException {
		Connection conn = null;
		List<ComplainStatusDTO> list = new ArrayList<>();
		try {
			conn = DBUtils.getConnectionTodatabase();
			String query="select com_id,department_name,subtype,status,email from Complaints C inner join"
					+ " departments D on C.type=D.dept_id inner join engineers E on C.eng_id=E.id emp_id=?";
					
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, LoggedInUser.loggedInUserId);
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
				String status;
				if (rs.getInt(4)==0) {
					status="Pending";
				}else if (rs.getInt(4)==1) {
					status="In Progress";
				}else {
					status="Solved";
				}
				list.add(new ComplainStatusDTOImpl(rs.getInt(1), rs.getString(2), 
						rs.getString(3),status, rs.getString(5)));
				
			}
			
		}catch(ClassNotFoundException | SQLException ex) {
			throw new SomethingWentWrongException("Unable to add complaint");
		}finally {
			try {
				DBUtils.closeConnection(conn);
			}catch(SQLException ex) {
				
			}
		}
		
		return list;
		
	}

	@Override
	public String getPassword() throws SomethingWentWrongException, NoRecordFoundException {
		Connection conn = null;
		String oldpassword = null;
		try {
			conn = DBUtils.getConnectionTodatabase();
			String query="select password from employees where emp_id=?";
					
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, LoggedInUser.loggedInUserId);
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
				oldpassword=rs.getString(1);
				
			}
			
		}catch(ClassNotFoundException | SQLException ex) {
			throw new SomethingWentWrongException("Unable to add complaint");
		}finally {
			try {
				DBUtils.closeConnection(conn);
			}catch(SQLException ex) {
				
			}
		}
		
		
		return oldpassword;
	}

	@Override
	public void updatePassword(String newPassword) throws SomethingWentWrongException {
		Connection conn = null;
		
		try {
			conn = DBUtils.getConnectionTodatabase();
			String query="UPDATE employees SET password = ? WHERE emp_id = ?";
					
					
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, newPassword);
			ps.setInt(2, LoggedInUser.loggedInUserId);
			ps.executeUpdate();
			
		}catch(ClassNotFoundException | SQLException ex) {
			throw new SomethingWentWrongException("Unable to add complaint");
		}finally {
			try {
				DBUtils.closeConnection(conn);
			}catch(SQLException ex) {
				
			}
		}
		
	}
	
	
	
}
