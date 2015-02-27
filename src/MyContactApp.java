import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.naming.directory.SearchControls;


public class MyContactApp {

	static String fileName = "myc.txt";
	static int MAX_FILE_RECORDS = 100;
	static String LINE_SEPARATOR = ",";
	
	public static void main(String[] args) throws FileNotFoundException {
		//SHOW MENU
		
		System.out.println("######### MY CONTACTS ##########");
		showMenu();
		System.out.println("######### THANK YOU !!! ##########");
	}

	/**
	 * Method to display menu options
	 * @throws FileNotFoundException
	 */
	private static void showMenu() throws FileNotFoundException {

		System.out.println("######### MY CONTACTS ##########");
		int choice = 0;
		
		do {
			
			Scanner scan1 = new Scanner(System.in);
			System.out.println("Enter [1] Add, [2] Read, [3] Update, [4] Delete, [0] Exit");
			choice = scan1.nextInt();

			switch (choice) {
			case 1 :
				System.out.println("ADD NEW CONTACT INFORMATION : ------------------------------");
				add();//Create
				break;
			case 2 :
				System.out.println("READ CONTACT INFORMATION : ------------------------------");
				printFileContents();
				break;
			case 3 :
				System.out.println("UPDATE EXISTING CONTACT INFORMATION : ------------------------------");
				update();
				break;
			case 4 :
				System.out.println("DELETE EXISTING CONTACT INFORMATION : ------------------------------");
				delete();
				break;
			case 0 :
				return;
			}
		
		} while(choice != 0); //continue the loop till use enters the 0 for Exit
		
	}

	private static void printFileContents() throws FileNotFoundException {
		String[] lines = read();
		
		//print all the records
		for (int i = 0; i < lines.length; i++) {
			if(lines[i] != null) {
				System.out.println("CONTACT# " + i + ", LINE = " + lines[i]);
			}
		}
	}

	/**
	 * Method to delete file contents
	 * @throws FileNotFoundException 
	 */
	private static void delete() throws FileNotFoundException {
	System.out.println("\nTo delete a contact, enter the search keys (First name, Last name, Tel)");
	Contact contactToBeSearched = getUserInputs();
	boolean searchResult = searchContact(contactToBeSearched);
	
	if (searchResult) {
		System.out.println("\nDeleting Record");
		
		recreateFile(contactToBeSearched);
		
		System.out.println("\nRecord Successfully Deleted");
	} else {
		System.out.println("Record not found.");
	}
		printFileContents();
		
	}

	private static void update() throws FileNotFoundException {
	//Display search screen to take user input to be searched, all the 3 keys (firstName, lastName, tel should be matched !!!)
	System.out.println("\nTo update the information, Provide below Search keys (First name, Last name, Tel)");
	Contact contactToBeSearched = getUserInputs();
	boolean searchResult = searchContact(contactToBeSearched);
	
	if (searchResult) {
		//add new Contact info
		System.out.println("\nWill update the file contents with below new contact information... ");
		add();
		
		//FINALLY READ FILE CONTENTS and WRITE THOSE BACK AGAIN WITHOUT THE contactToBeSearched information
		recreateFile(contactToBeSearched);
	}
	
	//display latest updated file contents
		printFileContents();
	}
	
	private static void recreateFile(Contact contactToBeSearched) throws FileNotFoundException {
		String[] lines = read(); //read entire file into the String array
	
		//Write back to same file, without APPEND mode, and without old contactToBeSearched
		File file1 = new File(fileName);
		FileWriter fw = null;
		
		try {
			
			fw = new FileWriter(file1, false); //NO append !!!
			
			for (int recordIndex = 0; recordIndex < lines.length; recordIndex++) {
				//firstName, lastName, tel
				String cells[] = new String[3];
				
				if (lines[recordIndex] != null) {
					cells = lines[recordIndex].split(LINE_SEPARATOR); 
					String firstName = cells[0];
					String lastName = cells[1];
					String telName = cells[2];
					
					//do not add contactToBeSearched contact information which is to be replaced
					int keyFoundCount = 0;
					
					if (contactToBeSearched.getFirstName().equals(firstName) && contactToBeSearched.getLastName().equals(lastName) && contactToBeSearched.getTel().equals(telName)) {
						keyFoundCount = 1; //keyFoundCount++
					} 
					
					//do not add contactToBeSearched contact information which is to be replaced
					if (keyFoundCount != 1) {
						fw.write(firstName + LINE_SEPARATOR);
						fw.write(lastName + LINE_SEPARATOR);
						fw.write(telName + "\n");
					}

				}
			}
			
			fw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return
	 * @throws FileNotFoundException
	 */
	//private static void update(Contact currentContactInfo, Contact newContactInfo) throws FileNotFoundException {
	private static boolean searchContact(Contact contactToBeSearched) throws FileNotFoundException {
		
		//once we have user input for the search key, next step 
		//is to check each file record to compare with the above search keys
		
		//read existing file contents into the String array
		String[] lines = read(); 
		String firstName = "";
		String lastName = "";
		String telName = "";
		for (int recordIndex = 0; recordIndex < lines.length; recordIndex++) {

			//firstName, lastName, tel
			String cells[] = new String[3]; 
			
			//read each record and split it into 3 cells firstName, lastName, tel
			if (lines[recordIndex] == null) {
				lines[recordIndex] = ""; //to avoid NullPointerException
			}
			cells = lines[recordIndex].split(LINE_SEPARATOR); 
			
			if (cells.length == 3)  {
				firstName = cells[0];
				lastName = cells[1];
				telName = cells[2];
			}
			
			if (contactToBeSearched.getFirstName().equals(firstName) && contactToBeSearched.getLastName().equals(lastName) && contactToBeSearched.getTel().equals(telName)) {
				System.out.println("\nFOUND ALL THE 3 KEYS !!!");
				return true; //return true when all the keys found
			}
		}

		System.out.println("\n ALL THE 3 KEYS ARE NOT FOUND.");
		return false; //false if not matching all the 3 keys above
	}

	/**
	 * Read file contains and return the contents
	 * @return array of records from the file  
	 * @throws FileNotFoundException
	 */
	private static String[] read() throws FileNotFoundException {
		
		String[] lines = new String[MAX_FILE_RECORDS]; 
		
		File file1 = new File(fileName); 
		
		Scanner scan1 = new Scanner(file1);
		
		int recordIndex = 0;
		
		while(scan1.hasNextLine()) {
			lines[recordIndex] = scan1.nextLine();
			recordIndex++;			
		}

		return lines;
	}

	/**
	 * Method to append records
	 * @param line to add to the file
	 */
	private static void add() {

		Contact c1 = getUserInputs();
		
		File file1 = new File(fileName);
		
		try {
			
			FileWriter fw = new FileWriter(file1, true); //append
			
			fw.write(c1.getFirstName() + LINE_SEPARATOR);
			fw.write(c1.getLastName() + LINE_SEPARATOR);
			fw.write(c1.getTel() + "\n");

			fw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to get user inputs and return contact object
	 * @return Contact the contact information input by the user
	 */
	private static Contact getUserInputs() {
		Contact c1 = new Contact();
		
		Scanner scan1 = new Scanner(System.in);

		System.out.println("Enter First name = ");
		c1.setFirstName(scan1.next());
		
		System.out.println("Enter Last  name = ");
		c1.setLastName(scan1.next());
		
		System.out.println("Enter Telephone  = ");
		c1.setTel(scan1.next());
		
		return c1;
	}
}
