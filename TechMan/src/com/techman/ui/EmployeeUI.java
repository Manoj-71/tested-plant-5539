package com.techman.ui;

import java.util.Scanner;

import com.techman.dao.EmployeeDAO;
import com.techman.dao.EmployeeDAOImpl;
import com.techman.dto.ComplaintDTO;
import com.techman.dto.ComplaintDTOImpl;
import com.techman.exception.NoRecordFoundException;
import com.techman.exception.SomethingWentWrongException;

public class EmployeeUI {

	public static boolean login(Scanner sc) {
		System.out.print("Enter username ");
		String username = sc.next();
		System.out.print("Enter password ");
		String password = sc.next();
		EmployeeDAO employeeDAO = new EmployeeDAOImpl();
		try {
			employeeDAO.login(username, password);
		}catch(SomethingWentWrongException | NoRecordFoundException ex) {
			System.out.println(ex);
			return false;
		}
		return true;
	}

	public static void logout() {
		EmployeeDAO employeeDAO = new EmployeeDAOImpl();
		employeeDAO.logout();
	}
	
	private static boolean isValidCategory(int choice) {
        return choice == 1 || choice == 2;
    }
	
	private static String getCategoryName(int choice) {
        if (choice == 1) {
            return "Hardware";
        } else {
            return "Software";
        }
    }
	
	private static String getSubcategoryName(int choice, Scanner sc) {
	    switch (choice) {
	        case 1:
	            return "Printer";
	        case 2:
	            return "Keyboard";
	        case 3:
	            return "Monitor";
	        case 4:
	            System.out.print("Enter subcategory name: ");
	            return sc.nextLine();
	        default:
	            return "";
	    }
	}
	
	
	
	private static boolean isValidSubcategory(int choice) {
        return choice >= 1 && choice <= 4;
    }

    private static String getSubcategoryName(int choice) {
        switch (choice) {
            case 1:
                return "Printer";
            case 2:
                return "Keyboard";
            case 3:
                return "Monitor";
            default:
                return "";
        }
    }

    private static boolean isValidSubcategory(int choice, String subcategory) {
        if (choice == 4) {
            return subcategory.trim().length() > 0;
        } else {
            return isValidSubcategory(choice);
        }
    }

	public static void registerAComplaint(Scanner sc) {
		int choice;
        int category;
        do {
            System.out.println("Select a category:");
            System.out.println("1. Hardware");
            System.out.println("2. Software");
            
            choice = sc.nextInt();
            
            if (!isValidCategory(choice)) {
                System.out.println("Invalid category. Please try again.");
            }
        } while (!isValidCategory(choice));
        
        category = choice;
        // -=-=-=-=-=-=-=-
        int choice2;
        String subtype = null;
        do {
            System.out.println("Select a sub-category:");
            System.out.println("1. Printer");
            System.out.println("2. Keyboard");
            System.out.println("3. Monitor");
            System.out.println("4. Other");

            choice2 = sc.nextInt();
            sc.nextLine(); // consume the new line character left by nextInt()

            if (!isValidSubcategory(choice2)) {
                System.out.println("Invalid sub-category. Please try again.");
            } else if (choice2 == 4) {
                System.out.print("Enter sub-category name: ");
                subtype = sc.nextLine();
            } else {
                subtype = getSubcategoryName(choice2);
            }

        } while (!isValidSubcategory(choice2, subtype));
        
        EmployeeDAO employeeDAO=new EmployeeDAOImpl();
        
        ComplaintDTO complaintDTO=new ComplaintDTOImpl(category, subtype);
        
        try {
			int complaintId=employeeDAO.addComplaint(complaintDTO);
			System.out.println("Complaint registered with ID: " + complaintId);
			
		} catch (SomethingWentWrongException e) {
			
			e.printStackTrace();
		}
	}

}
