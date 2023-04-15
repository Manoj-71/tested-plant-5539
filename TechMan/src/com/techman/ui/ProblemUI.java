package com.techman.ui;

import java.util.List;
import java.util.Scanner;

import com.techman.dao.ProblemDAO;
import com.techman.dao.ProblemDAOImpl;
import com.techman.dto.ComplainStatusDTO;
import com.techman.exception.NoRecordFoundException;
import com.techman.exception.SomethingWentWrongException;

public class ProblemUI {

	public static void Viewalltheraisedproblems() throws SomethingWentWrongException, NoRecordFoundException {
		ProblemDAO problemDAO=new ProblemDAOImpl();
		
		List<ComplainStatusDTO> list=problemDAO.getAllTheComplaints();
		list.forEach(System.out::println);
	}

	public static void assignproblemtoanyEngineer(Scanner sc) throws SomethingWentWrongException{
		System.out.println("Enter Complaint Id");
		int cid=sc.nextInt();
		System.out.println("Enter Engineer Id");
		int eid=sc.nextInt();
		
		ProblemDAO problemDAO=new ProblemDAOImpl();
		
		problemDAO.assignProblemToEngineer(cid,eid);
		System.out.println("Complant Assign successfully");
	}

}
