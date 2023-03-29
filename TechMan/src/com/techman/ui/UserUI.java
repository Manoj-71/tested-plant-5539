package com.techman.ui;

import java.util.Scanner;

import com.techman.dao.UserDAO;
import com.techman.dao.UserDAOImpl;
import com.techman.exception.NoRecordFoundException;
import com.techman.exception.SomethingWentWrongException;


public class UserUI {
	static boolean login(Scanner sc) {
		System.out.print("Enter username ");
		String username = sc.next();
		System.out.print("Enter password ");
		String password = sc.next();
		UserDAO userDAO = new UserDAOImpl();
		try {
			userDAO.login(username, password);
		}catch(SomethingWentWrongException | NoRecordFoundException ex) {
			System.out.println(ex);
			return false;
		}
		return true;
	}
	
	static void logout() {
		UserDAO userDAO = new UserDAOImpl();
		userDAO.logout();
	}
}
