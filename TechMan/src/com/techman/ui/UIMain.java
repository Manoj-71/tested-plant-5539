package com.techman.ui;

import java.util.Scanner;

import com.techman.exception.NoRecordFoundException;
import com.techman.exception.SomethingWentWrongException;
/**
 * Displays the admin menu with available options.
 */

public class UIMain {
	static void displayAdminMenu() {
		System.out.println("╔═══════════════════════════════════════════════╗\r\n"
				+ "║                 Admin Menu                    ║\r\n"
				+ "╠═══════════════════════════════════════════════╣\r\n"
				+ "║ 0. Log out Admin                              ║\r\n"
				+ "║ 1. Add new Engineer                           ║\r\n"
				+ "║ 2. View all the Registered Engineers          ║\r\n"
				+ "║ 3. Delete An Engineer                         ║\r\n"
				+ "║ 4. View all the raised problems               ║\r\n"
				+ "║ 5. Assign problem to any Engineer             ║\r\n"
				+ "╠═══════════════════════════════════════════════╣");
		
	}
	/**
	 * Handles the admin menu selection and executes the appropriate action.
	 * 
	 * @param sc Scanner object used to read user input
	 * @throws SomethingWentWrongException If an error occurs during execution
	 * @throws NoRecordFoundException If no records are found during execution
	 */
	static void adminMenu(Scanner sc) throws SomethingWentWrongException, NoRecordFoundException {
		int choice = 0;
		do {
			displayAdminMenu();
			System.out.print("Enter selection ");
			choice = sc.nextInt();
			switch(choice) {
				case 0:
					System.out.println("Bye Bye admin");
					break;
				case 1:
					EngineerUI.addEngineerUI(sc);
					break;
				case 2:
					EngineerUI.ViewalltheRegisteredEngineers();
					break;
				case 3:
					EngineerUI.deleteEngineerUI(sc);
					break;
				case 4:
					ProblemUI.Viewalltheraisedproblems();
					break;
				case 5:
					ProblemUI.assignproblemtoanyEngineer(sc);
					break;
				default:
					System.out.println("Invalid Selection, try again");
			}
		}while(choice != 0);
	}
	/**
	 * Displays the login prompt for the admin user.
	 * 
	 * @param sc Scanner object used to read user input
	 * @throws SomethingWentWrongException If an error occurs during execution
	 * @throws NoRecordFoundException If no records are found during execution
	 */
	static void adminLogin(Scanner sc) throws SomethingWentWrongException, NoRecordFoundException {
		System.out.println("-------------------------------------------------------\r\n"
				+ "                       Login Prompt\r\n"
				+ "-------------------------------------------------------");
		System.out.print("Enter username ");
		String username = sc.next();
		System.out.print("Enter password ");
		String password = sc.next();
		
		if(username.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")) {
			adminMenu(sc);
		}else {
			System.out.println("Invalid Username or Password");
			System.out.println("Please try again");
			adminLogin(sc);
		}
	}
	/**

	Displays the user menu options for the logged in user.
	*/
	static void displayUserMenu() {
		System.out.println("1. View the problems assigned to him by HOD");
		System.out.println("2. Update the status of the problem addressed by him");
		System.out.println("3. View list of all the problems attended by him/her.");
		System.out.println("4. Update My Password");
		
		System.out.println("0. Logout");
	}
	/**

	This method allows an engineer to login to the system and access their menu options.

	It takes a Scanner object as input to read user input.

	If the login is not successful, the method returns without performing any actions.

	The method loops until the user chooses to logout.

	@paramsc the Scanner object to read user input

	@throws NoRecordFoundException if no record is found for the engineer

	@throws SomethingWentWrongException if an error occurs while performing the operation
	*/
	
	static void engineerLogin(Scanner sc) throws NoRecordFoundException, SomethingWentWrongException {
		if(!EngineerUI.login(sc))
			return;
		//you are here means login is successful
		int choice = 0;
		do {
			displayUserMenu();
			System.out.print("Enter selection ");
			choice = sc.nextInt();
			switch(choice) {
				case 1:
					EngineerUI.viewAllProblems();
					break;
				case 2:
					EngineerUI.updatestatusoftheproblem(sc);
					break;
				case 3:
					EngineerUI.viewlistofalltheproblemsattendedbyhim();
					break;
				case 4:
					EngineerUI.changeMyPassword(sc);
					break;
				case 0:
					EngineerUI.logout();
					break;
				default:
					System.out.println("Invalid Selection, try again");
			}
		}while(choice != 0);
	}
	/**

	This is the main class that drives the application flow. It provides the user with options to login as an HOD,

	engineer, or employee. It uses the Scanner class to read user input and the UserUI, ProductUI, OrderUI, and

	UserCategoryUI classes to display the appropriate UI for each user.

	@param args command line arguments (not used)

	@throws SomethingWentWrongException if there is an issue with the application flow

	@throws NoRecordFoundException if no record is found for the user
	*/
	public static void main(String[] args) throws SomethingWentWrongException, NoRecordFoundException {
		Scanner sc = new Scanner(System.in);
		/**

		This is the main method that drives the application flow. It provides the user with options to login as an HOD,
		engineer, or employee. It uses the Scanner class to read user input and the UserUI, ProductUI, OrderUI, and
		UserCategoryUI classes to display the appropriate UI for each user.
		@param args command line arguments (not used)
		@throws SomethingWentWrongException if there is an issue with the application flow
		@throws NoRecordFoundException if no record is found for the user
		*/
		int choice = 0;
		do {
			System.out.println("╔═════════════════════════════════════════════════════╗\r\n"
					+ "║                                                     ║\r\n"
					+ "║                 WELCOME TO TACHMAN!                 ║\r\n"
					+ "║                                                     ║\r\n"
					+ "║     Please select an option from the menu below:    ║\r\n"
					+ "║                                                     ║\r\n"
					+ "║            1. HOD Login          2. Engineer Login  ║\r\n"
					+ "║                                                     ║\r\n"
					+ "║            3. Employee Login      0. Exit           ║\r\n"
					+ "║                                                     ║\r\n"
					+ "╚═════════════════════════════════════════════════════╝");
			choice = sc.nextInt();
			switch(choice) {
				case 0:
					System.out.println("Thank you, Visit again");
					break;
				case 1:
					adminLogin(sc);
					break;
				case 2:
					engineerLogin(sc);
					break;
				case 3:
					emplyeeLogin(sc);
					break;
				default:
					System.out.println("Invalid Selection, try again");
			}
		}while(choice != 0);
		sc.close();
	}
	/**

	This class provides a method to login for an employee and displays a menu of options for them to select from.

	These options include registering a complaint, checking the status of their complaint, viewing a list of all the

	complaints they have raised, updating their password, and logging out.
	*/
	private static void emplyeeLogin(Scanner sc) throws SomethingWentWrongException, NoRecordFoundException {
		if(!EmployeeUI.login(sc))
			return;
		
		//you are here means login is successful
		int choice = 0;
		do {
			System.out.println("1. Register A Complaint");
			System.out.println("2. Status of their complain");
			System.out.println("3. View list of all the complains raised by him/her.");
			System.out.println("4. Update My Password");
			System.out.println("0. Logout");
			System.out.print("Enter selection ");
			choice = sc.nextInt();
			switch(choice) {
				case 1:
					EmployeeUI.registerAComplaint(sc);
					break;
				case 2:
					EngineerUI.statusoftheircomplain(sc);
					break;
				case 3:
					EngineerUI.viewlistofalltheproblemsattendedbyhim();
					break;
				case 4:
					EngineerUI.changePassword(sc);
					break;
				case 0:
					EmployeeUI.logout();
					break;
				default:
					System.out.println("Invalid Selection, try again");
			}
		}while(choice != 0);
	}

	
}
