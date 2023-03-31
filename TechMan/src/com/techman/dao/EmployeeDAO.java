package com.techman.dao;

import java.util.List;

import com.techman.dto.ComplainStatusDTO;
import com.techman.dto.ComplaintDTO;
import com.techman.exception.NoRecordFoundException;
import com.techman.exception.SomethingWentWrongException;

public interface EmployeeDAO {
	public void login(String username, String password)throws 
	SomethingWentWrongException,
	NoRecordFoundException;

	public void logout();

	public int addComplaint(ComplaintDTO complaintDTO)
	throws SomethingWentWrongException;

	public ComplainStatusDTO getStatusOfComplaint(int com_id)
	throws SomethingWentWrongException,NoRecordFoundException;

	public List<ComplainStatusDTO> viewlistofalltheproblems()throws SomethingWentWrongException,NoRecordFoundException;

	public String getPassword() throws SomethingWentWrongException,NoRecordFoundException;

	public void updatePassword(String newPassword)throws SomethingWentWrongException;
}
