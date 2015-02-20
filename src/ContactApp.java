import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class ContactApp {

	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in);
		
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
		//Call read method
		readFromFile();
		
		System.out.println("FINISH.");
		scan.close();
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