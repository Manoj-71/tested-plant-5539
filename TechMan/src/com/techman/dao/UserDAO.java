package com.techman.dao;

import com.techman.exception.NoRecordFoundException;
import com.techman.exception.SomethingWentWrongException;

public interface UserDAO {
	public void login(String username, String password) 
			throws SomethingWentWrongException, NoRecordFoundException;
	public void logout();
}
