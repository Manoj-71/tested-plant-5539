package com.techman.ui;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import com.techman.dao.EmployeeDAO;
import com.techman.dao.EmployeeDAOImpl;
import com.techman.dao.EngineerDAO;
import com.techman.dao.EngineerDAOImpl;
import com.techman.dto.ComplainStatusDTO;
import com.techman.dto.ComplaintDTO;
import com.techman.dto.EngineerDTO;
import com.techman.dto.EngineerDTOImpl;
import com.techman.exception.NoRecordFoundException;
import com.techman.exception.SomethingWentWrongException;




public class EngineerUI {
	
	private static boolean isValidEmail(String email) {
        String pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(pattern, email);
    }
	public static boolean isValidPassword(String password) {
	    if (password.length() < 5) {
	        return false;
	    }
	    boolean hasUpperCase = false;
	    boolean hasLowerCase = false;
	    boolean hasDigit = false;
	    for (char c : password.toCharArray()) {
	        if (Character.isUpperCase(c)) {
	            hasUpperCase = true;
	        } else if (Character.isLowerCase(c)) {
	            hasLowerCase = true;
	        } else if (Character.isDigit(c)) {
	            hasDigit = true;
	        }
	    }
	    return hasUpperCase && hasLowerCase && hasDigit;
	}
	
	private static boolean isValidCategory(String category) {
		String[] values= {"Hardware","Software"};
        for (String value : values) {
            if (value.equalsIgnoreCase(category)) {
                return true;
            }
        }
        return false;
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
	
	static void addEngineerUI(Scanner sc) {
		System.out.println("-------------------------------------------------------\r\n"
				+ "                     Registration Prompt\r\n"
				+ "-------------------------------------------------------");
	        String email;
	        sc.nextLine();
	        do {
	            System.out.print("Enter email address: ");
	            email = sc.nextLine();

	            if (!isValidEmail(email)) {
	                System.out.println("Invalid email address. Please try again.");
	            }
	        } while (!isValidEmail(email));

	       
	        String password;
	        
	        do {
	        	System.out.println("Enter password: (Note: Password must be at least 5 characters long and contain at least one uppercase letter, one lowercase letter, and one digit)");
	        	
	        	password = sc.nextLine();

	            if (!isValidPassword(password)) {
	                System.out.println("Invalid password. Please try again.");
	            }
	        } while (!isValidPassword(password));
	        
	        
	        
	        int choice;
	        int category;
	        do {
	            System.out.println("============================================\r\n"
	            		+ "              Select a Category\r\n"
	            		+ "============================================");
	            System.out.println("1. Hardware");
	            System.out.println("2. Software");
	            
	            choice = sc.nextInt();
	            
	            if (!isValidCategory(choice)) {
	                System.out.println("Invalid category. Please try again.");
	            }
	        } while (!isValidCategory(choice));
	        
	        category = choice;

		//stuff data to DTO
	    EngineerDTO engineer = new EngineerDTOImpl(email, password,category);
		
	    EngineerDAO engineerDAO = new EngineerDAOImpl();
		try {
			engineerDAO.addEngineer(engineer);
			System.out.println("============================================\r\n"
					+ "         Engineer Added Successfully\r\n"
					+ "============================================");			
		}catch(SomethingWentWrongException ex) {
			System.out.println(ex);
		}
	}
	
	
	public static void ViewalltheRegisteredEngineers() {
		EngineerDAO engineerDAO = new EngineerDAOImpl();
		try {
			List<EngineerDTO> engineerDTOs = engineerDAO.getEngineerList();
			
			System.out.println("+----------------------------------------------------+\r\n"
					+          "|                   Engineer List                    |\r\n"
					+          "+-----------------------+-----------------------+----+");
			System.out.println("+-----------------------+-----------------------+----------+\r\n"
					+ 		   "| Username              | Password              |Department|\r\n"
					+ 		   "+-----------------------+-----------------------+----------+");
			Consumer<EngineerDTO> con = eng -> {	
				System.out.print("| "+eng.getEmail() + "      |" + eng.getPassword() + "             | " + (eng.getCategory() == 1 ? "Hardware  |" : "Software  |"));
				System.out.println();
			};
			engineerDTOs.forEach(con);
		}catch(SomethingWentWrongException | NoRecordFoundException ex) {
			System.out.println(ex.getMessage());
		}
	}
	public static void deleteEngineerUI(Scanner sc) {
		int eid;
        do {
            System.out.print("Enter Engineer id: ");
            try {
                eid = sc.nextInt();
                break; // exit loop if input is valid
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.next(); // consume invalid input
            }
        } while (true);
		
		//take an object of DAO
		EngineerDAO engDAO = new EngineerDAOImpl();
		
		try {
			//call the update employee method
			engDAO.deleteEngineer(eid);
			//print success message
			System.out.println("Engineer deleted successfully");
		}catch(SomethingWentWrongException ex) {
			//print error message if error if there
			System.out.println(ex.getMessage());
		}
	}
	public static boolean login(Scanner sc) {
		sc.nextLine();
		System.out.print("Enter username ");
		String username = sc.nextLine();
		System.out.print("Enter password ");
		String password = sc.nextLine();
		EngineerDAO engineerDAO = new EngineerDAOImpl();
		try {
			engineerDAO.login(username, password);
		}catch(SomethingWentWrongException | NoRecordFoundException ex) {
			System.out.println(ex);
			return false;
		}
		return true;
	}
	
	static void logout() {
		EngineerDAO engineerDAO = new EngineerDAOImpl();
		engineerDAO.logout();
	}
	public static void statusoftheircomplain(Scanner sc) {
		System.out.print("Enter complaint ID ");
		int com_id = sc.nextInt();
		
		EmployeeDAO employeeDAO = new EmployeeDAOImpl();
		try {
			ComplainStatusDTO complainStatusDTO=employeeDAO.getStatusOfComplaint(com_id);
			System.out.println(complainStatusDTO);
		}catch(SomethingWentWrongException | NoRecordFoundException ex) {
			System.out.println(ex);
			
		}
	}
	public static void viewlistofalltheproblemsattendedbyhim() {
		EngineerDAO engineerDAO = new EngineerDAOImpl();
		try {
			 List<ComplainStatusDTO> list=engineerDAO.viewAllProblemsAssignedToHim();
			 list.forEach(System.out::println);
		}catch(SomethingWentWrongException | NoRecordFoundException ex) {
			System.out.println(ex);
			
		}
	}
	public static void changePassword(Scanner sc) throws SomethingWentWrongException, NoRecordFoundException {
		System.out.print("Enter Your Old Password ");
		sc.nextLine();
		String oldPassword = sc.nextLine();
		EmployeeDAO employee=new EmployeeDAOImpl();
		
		while (!oldPassword.equals(employee.getPassword())) {
		    System.out.println("Incorrect old password. Please try again.");
		    System.out.print("Enter old password: ");
		    oldPassword = sc.nextLine();
		}
		System.out.print("Enter new password: ");
		String newPassword = sc.nextLine();
		
		System.out.print("Re-enter new password: ");
		String renewPassword = sc.nextLine();
		
		while (!newPassword.equals(renewPassword)) {
		    System.out.println("Incorrect old password. Please try again.");
		    System.out.print("Enter new password: ");
			newPassword = sc.nextLine();
			
			System.out.print("Re-enter new password: ");
			renewPassword = sc.nextLine();
		}
		
		
		
		
		EmployeeDAO employeeDAO = new EmployeeDAOImpl();
		try {
			employeeDAO.updatePassword(newPassword);
			System.out.println("Password changed successfully.");
		}catch(SomethingWentWrongException ex) {
			System.out.println(ex);
			
		}
	}
	public static void viewAllProblems() {
		EngineerDAO engineerDAO=new EngineerDAOImpl();
		
		try {
			List<ComplainStatusDTO>list=engineerDAO.viewAllProblemsAssignedToHim();
			System.out.println(list);
		} catch (SomethingWentWrongException | NoRecordFoundException e) {
			
			e.printStackTrace();
		}
	}
	public static void updatestatusoftheproblem(Scanner sc) throws NoRecordFoundException {
		
		System.out.print("Enter Complain Id ");
		int comId = sc.nextInt();
		System.out.println("Enter Selection For Status ");
		System.out.println("1. In Progress");
		System.out.println("2. Solved");
		int status = sc.nextInt();
		EngineerDAO engineerDAO = new EngineerDAOImpl();
		try {
			engineerDAO.updateStatusOfProblem(comId, status);
			System.out.println("Problem Status updated successfully");
		}catch(SomethingWentWrongException ex) {
			System.out.println(ex);
			
		}
	}
	public static void changeMyPassword(Scanner sc) throws SomethingWentWrongException, NoRecordFoundException {
		System.out.print("Enter Your Old Password ");
		sc.nextLine();
		String oldPassword = sc.nextLine();
		EngineerDAO engineerDAO=new EngineerDAOImpl();
		
		while (!oldPassword.equals(engineerDAO.getPassword())) {
		    System.out.println("Incorrect old password. Please try again.");
		    System.out.print("Enter old password: ");
		    oldPassword = sc.nextLine();
		}
		System.out.print("Enter new password: ");
		String newPassword = sc.nextLine();
		
		System.out.print("Re-enter new password: ");
		String renewPassword = sc.nextLine();
		
		while (!newPassword.equals(renewPassword)) {
		    System.out.println("You have entered two different password. Please try again.");
		    System.out.print("Enter new password: ");
			newPassword = sc.nextLine();
			
			System.out.print("Re-enter new password: ");
			renewPassword = sc.nextLine();
		}

		try {
			engineerDAO.updatePassword(newPassword);
			System.out.println("Password changed successfully.");
		}catch(SomethingWentWrongException ex) {
			System.out.println(ex);
			
		}
	}
}
