package com.techman.dao;

import java.util.List;

import com.techman.dto.ComplainStatusDTO;
import com.techman.dto.EngineerDTO;
import com.techman.exception.NoRecordFoundException;
import com.techman.exception.SomethingWentWrongException;

public interface EngineerDAO {
	public void addEngineer(EngineerDTO catDTO) throws SomethingWentWrongException;

	public List<EngineerDTO> getEngineerList() throws SomethingWentWrongException,NoRecordFoundException;

	public void deleteEngineer(int eid)throws SomethingWentWrongException;

	public void login(String username, String password)throws 
	SomethingWentWrongException,
	NoRecordFoundException;

	public void logout();

	public List<ComplainStatusDTO> viewAllProblemsAssignedToHim()throws 
	SomethingWentWrongException,NoRecordFoundException;

	public void updateStatusOfProblem(int comId, int status)throws SomethingWentWrongException;

	public List<ComplainStatusDTO> viewlistofalltheproblems()
	throws SomethingWentWrongException,NoRecordFoundException;

	public String getPassword()throws SomethingWentWrongException,
	NoRecordFoundException;

	public void updatePassword(String newPassword)throws SomethingWentWrongException;
}
