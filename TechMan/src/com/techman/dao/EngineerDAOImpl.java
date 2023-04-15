package com.techman.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.techman.dto.ComplainStatusDTO;
import com.techman.dto.ComplainStatusDTOImpl;
import com.techman.dto.EngineerDTO;
import com.techman.dto.EngineerDTOImpl;
import com.techman.exception.NoRecordFoundException;
import com.techman.exception.SomethingWentWrongException;



public class EngineerDAOImpl implements EngineerDAO {
	@Override
	public void addEngineer(EngineerDTO engineerDTO) throws SomethingWentWrongException {
		Connection conn = null;
		try {
			conn = DBUtils.getConnectionTodatabase();
			PreparedStatement ps = conn.prepareStatement("INSERT INTO Engineers (email, password, dept_id) VALUES (?,?, ?)");
			ps.setString(1, engineerDTO.getEmail());
			ps.setString(2, engineerDTO.getPassword());
			ps.setInt(3, engineerDTO.getCategory());
			ps.executeUpdate();
		}catch(ClassNotFoundException | SQLException ex) {
			throw new SomethingWentWrongException("Unable to add Engineer");
		}finally {
			try {
				DBUtils.closeConnection(conn);
			}catch(SQLException ex) {
				
			}
		}
	}

	@Override
	public List<EngineerDTO> getEngineerList() throws SomethingWentWrongException, NoRecordFoundException {
		Connection conn = null;
		List<EngineerDTO> list = new ArrayList<>();
		try {
			conn = DBUtils.getConnectionTodatabase();
			String query = "SELECT email, password, dept_id FROM engineers";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			if(DBUtils.isResultSetEmpty(rs)) {
				throw new NoRecordFoundException("No Engineer found");
			}
			while(rs.next()) {
				list.add(new EngineerDTOImpl(rs.getString(1), rs.getString(2), rs.getInt(3)));
			}
			
		}catch(ClassNotFoundException | SQLException ex) {
			throw new SomethingWentWrongException("Unable find the record now, try again later");
		}finally {
			try {
				DBUtils.closeConnection(conn);					
			}catch(SQLException ex) {
				
			}
		}
		return list;
	}

	@Override
	public void deleteEngineer(int eid) throws SomethingWentWrongException {
		Connection conn = null;
		try {
			conn = DBUtils.getConnectionTodatabase();
			String query = "DELETE FROM Engineers WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, eid);
			ps.executeUpdate();
		}catch(ClassNotFoundException | SQLException ex) {
			throw new SomethingWentWrongException("+-------------------------------------+\r\n"
					+ 							  "| Unable to delete the record now,    |\r\n"
					+ 							  "+-------------------------------------+");
		}finally {
			try {
				DBUtils.closeConnection(conn);					
			}catch(SQLException ex) {
				
			}
		}
	}

	@Override
	public void login(String username, String password) throws SomethingWentWrongException, NoRecordFoundException {
		Connection conn = null;
		try {
			conn = DBUtils.getConnectionTodatabase();
			String query = "SELECT id FROM engineers WHERE email = ? AND "
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
			throw new SomethingWentWrongException("Unable to find such account");
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
	public List<ComplainStatusDTO> viewAllProblemsAssignedToHim()
			throws SomethingWentWrongException, NoRecordFoundException {
		Connection conn = null;
		List<ComplainStatusDTO> list = new ArrayList<>();
		try {
			conn = DBUtils.getConnectionTodatabase();
			String query="select com_id,department_name,subtype,status from Complaints C inner join"
					+ " departments D on C.type=D.dept_id inner join engineers E on C.eng_id=E.id AND eng_id=?";
					
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, LoggedInUser.loggedInUserId);
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
				String status;
				if (rs.getInt(4)==0) {
					status="Pending";
				}else {
					status="Solved";
				}
//				String email;
//				if (rs.getString(5).equals("jane@example.com")) {
//					email="None";
//				}else {
//					email=rs.getString(5);
//				}
				list.add(new ComplainStatusDTOImpl(rs.getInt(1), rs.getString(2), 
						rs.getString(3),status, null));
				
			}
			
		}catch(ClassNotFoundException | SQLException ex) {
			throw new SomethingWentWrongException("Unable to find any complaint");
		}finally {
			try {
				DBUtils.closeConnection(conn);
			}catch(SQLException ex) {
				
			}
		}
		
		return list;
	}

	@Override
	public void updateStatusOfProblem(int comId, int status) throws SomethingWentWrongException {
		
		Connection conn = null;
		try {
			conn = DBUtils.getConnectionTodatabase();
			String query = "UPDATE Complaints set status=? WHERE com_id=? AND eng_id=?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, status);
			ps.setInt(2, comId);
			ps.setInt(3, LoggedInUser.loggedInUserId);
			
			ps.executeUpdate();
			
		}catch(ClassNotFoundException | SQLException ex) {
			throw new SomethingWentWrongException("Unable to Update complaint status");
		}finally {
			try {
				DBUtils.closeConnection(conn);
			}catch(SQLException ex) {
				
			}
		}
		
	}

	@Override
	public List<ComplainStatusDTO> viewlistofalltheproblems()
			throws SomethingWentWrongException, NoRecordFoundException {
		Connection conn = null;
		List<ComplainStatusDTO> list = new ArrayList<>();
		try {
			conn = DBUtils.getConnectionTodatabase();
			String query="select com_id,department_name,subtype,status,email from Complaints C inner join"
					+ " departments D on C.type=D.dept_id inner join engineers E on C.eng_id=E.id WHERE status=1 AND emp_id=?";
					
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
			throw new SomethingWentWrongException("Unable to find any complaint");
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
			String query="select password from engineers where id=?";
					
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
			String query="UPDATE engineers SET password = ? WHERE id = ?";
					
					
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
