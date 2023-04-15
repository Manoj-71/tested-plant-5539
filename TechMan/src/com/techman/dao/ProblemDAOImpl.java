package com.techman.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.techman.dto.ComplainStatusDTO;
import com.techman.dto.ComplainStatusDTOImpl;
import com.techman.exception.NoRecordFoundException;
import com.techman.exception.SomethingWentWrongException;

public class ProblemDAOImpl implements ProblemDAO {

	@Override
	public List<ComplainStatusDTO> getAllTheComplaints() throws SomethingWentWrongException, NoRecordFoundException {
		Connection conn = null;
		List<ComplainStatusDTO> list = new ArrayList<>();
		try {
			conn = DBUtils.getConnectionTodatabase();
			String query="select com_id,department_name,subtype,status,email from Complaints C inner join"
					+ " departments D on C.type=D.dept_id inner join engineers E on C.eng_id=E.id";
					
			PreparedStatement ps = conn.prepareStatement(query);
			
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
				String status;
				if (rs.getInt(4)==0) {
					status="Pending";
				}else {
					status="Solved";
				}
				String email;
				if (rs.getString(5).equals("jane@example.com")) {
					email="None";
				}else {
					email=rs.getString(5);
				}
				list.add(new ComplainStatusDTOImpl(rs.getInt(1), rs.getString(2), 
						rs.getString(3),status, email));
				
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
	public void assignProblemToEngineer(int cid, int eid) throws SomethingWentWrongException {
		Connection conn = null;
		
		try {
			conn = DBUtils.getConnectionTodatabase();
			String query="UPDATE Complaints set eng_id=? where com_id=?";
					
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, eid);
			ps.setInt(2, eid);
			ps.executeUpdate();
			
			
		}catch(ClassNotFoundException | SQLException ex) {
			throw new SomethingWentWrongException("Unable to assign any engineer");
		}finally {
			try {
				DBUtils.closeConnection(conn);
			}catch(SQLException ex) {
				
			}
		}
		
	}
	
}
