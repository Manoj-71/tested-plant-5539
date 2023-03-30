package com.techman.ui;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import com.techman.dao.EngineerDAO;
import com.techman.dao.EngineerDAOImpl;
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
		
	        String email;
	        do {
	            System.out.print("Enter email address: ");
	            email = sc.nextLine();

	            if (!isValidEmail(email)) {
	                System.out.println("Invalid email address. Please try again.");
	            }
	        } while (!isValidEmail(email));

	       
	        String password;
	        
	        do {
	        	System.out.print("Enter password: ");
	        	System.out.println("Note : Password must be at least 5 characters long and contain at least one uppercase letter, "
	        			+ "one lowercase letter, and one digit");
	        	password = sc.nextLine();

	            if (!isValidPassword(password)) {
	                System.out.println("Invalid password. Please try again.");
	            }
	        } while (!isValidPassword(password));
	        
	        
	        
	        int choice;
	        String category;
	        do {
	            System.out.println("Select a category:");
	            System.out.println("1. Hardware");
	            System.out.println("2. Software");
	            
	            choice = sc.nextInt();
	            
	            if (!isValidCategory(choice)) {
	                System.out.println("Invalid category. Please try again.");
	            }
	        } while (!isValidCategory(choice));
	        
	        category = getCategoryName(choice);

		//stuff data to DTO
	    EngineerDTO engineer = new EngineerDTOImpl(email, password,category);
		
	    EngineerDAO engineerDAO = new EngineerDAOImpl();
		try {
			engineerDAO.addEngineer(engineer);
			System.out.println("Engineer added successfully");			
		}catch(SomethingWentWrongException ex) {
			System.out.println(ex);
		}
	}
	
	
	public static void ViewalltheRegisteredEngineers() {
		EngineerDAO engineerDAO = new EngineerDAOImpl();
		try {
			List<EngineerDTO> engineerDTOs = engineerDAO.getEngineerList();
			Consumer<EngineerDTO> con = eng -> System.out.println("Username " + eng.getEmail() + " Password " + eng.getPassword() 
			+ " Category " + eng.getCategory());
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
		EngineerDAO emgDAO = new EngineerDAOImpl();
		
		try {
			//call the update employee method
			emgDAO.deleteEngineer(eid);
			//print success message
			System.out.println("Employee deleted successfully");
		}catch(SomethingWentWrongException ex) {
			//print error message if error if there
			System.out.println(ex);
		}
	}
}
