import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class ContactApp {

	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in);
		int choice = 0;

		do{
			System.out.println("Enter number: [1 = Add New Contact, 2 = Print Contacts, 3 Update Contact, 4 Delete Contact, 0 = Exit]");

			choice = scan.nextInt();
			switch(choice){
			case 1 : // Add new contact
				addToFile(scan);
				break;
			case 2 : //Print contacts
				readFromFile();
				break;
			case 3 : //Update contact
				//code code code
				break;
			case 4 : //Delete contact
				//code code code
				break;
			}
		}while(choice != 0);//end while loop

		scan.close();
	}
		
	public static void addToFile(Scanner scan) throws IOException {
		
		Contact c = new Contact();
		
		System.out.println("Enter First Name = ");
		c.setFirstName(scan.next());

		System.out.println("Enter Last Name = ");
		c.setLastName(scan.next());

		System.out.println("Enter Telephone = ");
		c.setTel(scan.next());
		
		System.out.println("Enter Email = ");
		c.setEmail(scan.next());
		
		//Call write method
		writeToFile(c.getFirstName(), c.getLastName(), c.getTel(), c.getEmail());
		
		System.out.println("Contact added to file!");
	}
	
	public static void writeToFile(String firstName, String lastName, String tel, String email) throws IOException {
		//write to file
		String fileName = "myc.csv";
		
		File file1 = new File(fileName);
		
		FileWriter fw = new FileWriter(file1, true);
		
		fw.write(firstName + ",");
		fw.write(lastName + ",");
		fw.write(tel + ",");
		fw.write(email + ",");
		
		fw.write("\n");
		
		fw.close();
	}
	
	public static void readFromFile() throws FileNotFoundException {
		//read from file
		String fileName = "myc.csv";
		
		File file1 = new File(fileName);
		
		Scanner scan = new Scanner(file1);
		
		String line;
		String cells[] = new String[4];
		
		while(scan.hasNext()) {
			line = scan.nextLine();
			cells = line.split(",");
			System.out.println(cells[0]);
			System.out.println(cells[1]);
			System.out.println(cells[2]);
			System.out.println(cells[3]);
		}
		scan.close();
	}

}