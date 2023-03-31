package com.techman.ui;

import java.util.Scanner;

import com.techman.exception.NoRecordFoundException;
import com.techman.exception.SomethingWentWrongException;


public class UIMain {
	static void displayAdminMenu() {
		System.out.println("0. Log out Admin");
		System.out.println("1. Add new Engineer");
		System.out.println("2. View all the Registered Engineers.");
		System.out.println("3. Delete An Engineer");
		System.out.println("4. View all the raised problems.");
		System.out.println("5. Assign problem to any Engineer.");
		
	}
	
	static void adminMenu(Scanner sc) {
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
//					SELECT U.username, P.pro_name
//					FROM orders O INNER JOIN product P ON 
//					O.product_id = P.id INNER JOIN user U ON
//					U.id = O.user_id;
//					Create an object of OrderDTO
//					new OrderDTO(new ProductDTO(null, pro_name, null, null), new UserDTO(null, username, null));
					break;
				default:
					System.out.println("Invalid Selection, try again");
			}
		}while(choice != 0);
	}
	
	static void adminLogin(Scanner sc) {
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
	static void displayUserMenu() {
		System.out.println("1. View the problems assigned to him by HOD");
		System.out.println("2. Update the status of the problem addressed by him");
		System.out.println("3. View list of all the problems attended by him/her.");
		System.out.println("4. Update My Password");
		
		System.out.println("0. Logout");
	}
	
	static void engineerLogin(Scanner sc) {
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
					//EngineerUI.viewAllProblems();
					break;
				case 2:
//					EngineerUI.updatestatusoftheproblem(sc);
					break;
				case 3:
					//EngineerUI.Viewlistofalltheproblemsattendedbyhim();
					break;
				case 4:
					//EngineerUI.changePassword();
					break;
				case 0:
					EngineerUI.logout();
					break;
				default:
					System.out.println("Invalid Selection, try again");
			}
		}while(choice != 0);
	}
	
	public static void main(String[] args) throws SomethingWentWrongException, NoRecordFoundException {
		Scanner sc = new Scanner(System.in);
		//user CategoryUI, ProductUI, OrderUI and 
		// UserUI class here  
		int choice = 0;
		do {
			System.out.println("1. HOD Login\n2. Engineer Login\n3. "
					+ "Emplyee LogIn Up\n0. Exit");
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
