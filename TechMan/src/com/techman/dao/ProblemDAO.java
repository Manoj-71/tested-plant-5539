package com.techman.dao;

import java.util.List;

import com.techman.dto.ComplainStatusDTO;
import com.techman.exception.NoRecordFoundException;
import com.techman.exception.SomethingWentWrongException;

public interface ProblemDAO {

	List<ComplainStatusDTO> getAllTheComplaints()throws SomethingWentWrongException,NoRecordFoundException;

	void assignProblemToEngineer(int cid, int eid)throws SomethingWentWrongException;

}
