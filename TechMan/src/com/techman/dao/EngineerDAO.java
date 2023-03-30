package com.techman.dao;

import java.util.List;

import com.techman.dto.EngineerDTO;
import com.techman.exception.NoRecordFoundException;
import com.techman.exception.SomethingWentWrongException;

public interface EngineerDAO {
	public void addEngineer(EngineerDTO catDTO) throws SomethingWentWrongException;

	public List<EngineerDTO> getEngineerList() throws SomethingWentWrongException,NoRecordFoundException;

	public void deleteEngineer(int eid)throws SomethingWentWrongException;

	
}
