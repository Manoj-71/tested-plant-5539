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
			PreparedStatement ps = conn.prepareStatement("INSERT INTO Engineers (email, password, category) VALUES (?,?, ?)");
			ps.setString(1, engineerDTO.getEmail());
			ps.setString(2, engineerDTO.getPassword());
			ps.setString(3, engineerDTO.getCategory());
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
			String query = "SELECT email, password, category FROM engineers";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			if(DBUtils.isResultSetEmpty(rs)) {
				throw new NoRecordFoundException("No Engineer found");
			}
			while(rs.next()) {
				list.add(new EngineerDTOImpl(rs.getString(1), rs.getString(2), rs.getString(3)));
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
	
}
