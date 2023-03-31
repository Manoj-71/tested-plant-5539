package com.techman.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
			throw new SomethingWentWrongException("Unable to delete the record now, try again later");
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
			String query = "SELECT id FROM engineers WHERE username = ? AND "
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
			throw new SomethingWentWrongException("Unable to add category");
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
	
}
