// CSI2110/CSI2510 Assignment#4 2018 
// Lucia Moura, Robert Laganiere
// This Tester class is to be used, and not be changed
// Its main program tests part 1 and part 2 reading the required files.
// For debugging you may temporarily comment out part 2

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Tester {
	
	static PasswordCracker cracker; // methods from Password Cracker used in other methods
	
	// reads common words file and returns a list with these words
	static ArrayList<String> readCommonPasswords(String fileName)  {
		    
		    BufferedReader buffer = null;
			FileReader reader = null;
			ArrayList<String> words=new ArrayList<String>(10000);
			
		    try {
				reader = new FileReader(fileName);
				buffer = new BufferedReader(reader);
				String plainPassword;
				while ((plainPassword = buffer.readLine()) != null) {
					words.add(plainPassword);
				}
		   }
		   
		   catch (Exception e) {
			  System.out.println("Something Wrong Reading "+fileName);
		   }
		   
		   finally {
				try {
					if (buffer != null)
						buffer.close();
					if (reader != null)
						reader.close();
				} catch (IOException ex) {
				}
		   }

		    return words;
			
	   }

	// Input is a file with user account info (username with their encrypted passwords)
	// and a database that has already been populated with common passwords and their variants
	// This method prints for each account the original password (or<not found!>) if not
	// in the database
	static void findPasswords(String fileName, DatabaseInterface database) {
		
		BufferedReader buffer = null;
		FileReader reader = null;
		
	    try {
			reader = new FileReader(fileName);
			buffer = new BufferedReader(reader);
			String currentLine;
			String username, cryptic, secret;
			while ((currentLine = buffer.readLine()) != null) {
				// here we expect in each line: username encrypted_password
				String[] arr = currentLine.split(" "); 
				if (arr.length!=2) System.out.println("Something Wrong in the Input!");
				else {
				  username=arr[0]; // read username
				  cryptic=arr[1].toUpperCase(); // read password hexa for (Sha1) and capitalize to match our capitalized SHA1
				  System.out.print("user: "+String.format("%15s",username)+" passwd: ");
				  secret=cracker.crackPassword(cryptic, database); // **** call to your method crackPassword in 
				                                                   // **** class PasswordCracker
				  if (secret.equals("")){
				  	System.out.println("<not found!>"); // *** crackPassword returns "" if not found

				  }
				  else System.out.println(secret);
				}
			}

	     }
	     catch (Exception e) {
	    	e.printStackTrace();
	    	e.printStackTrace();
		  System.out.println("Something Wrong in findPasswords");
	     }
		
	}

	public static void main(String[] args) {
		
		System.out.print("Reading easy passwords from file commonPwd10K.txt\n");
		ArrayList<String> commonPasswords = readCommonPasswords("commonPwd10K.txt");
		
		System.out.print("Enter Accounts Filename (default is leakedAccounts.txt) > ");
		Scanner scan = new Scanner(System.in);
		String nextLine = scan.nextLine();
		String leakedAccountsFile;
		if (nextLine.equals("")) leakedAccountsFile=new String("leakedAccounts.txt");
		else   leakedAccountsFile=nextLine;
		//common pass = commonPwd and and LeakedAccountsFile(Strin) = leaked accounts
		cracker =new PasswordCracker();
		
		System.out.println("\n***** Part 1: Testing StandardDatabase *****");
		
		DatabaseStandard db1=new DatabaseStandard(); //**** testing your class DatabaseStandard
		
		long time,afterTime;
		time=System.nanoTime();
		cracker.createDatabase(commonPasswords,db1); // **** call to your method createDatabase in 
                                                     // **** class PasswordCracker
		afterTime=System.nanoTime();

		System.out.println("Standard Database Creation Time is "+(afterTime-time)/1000000.0
				+" miliseconds\n " ); // grab stats about how long it took to created DatabaseStandard
		System.out.println(db1.size()  + "size of DB");

		db1.printStatistics(); // grab stats from the class DatabaseStandard you programmed
		
		System.out.print("\nPress any key to crack usernames in file:"+leakedAccountsFile+" > ");
		scan.nextLine();

		findPasswords(leakedAccountsFile, db1); // call our method above to crack and print the passwords 
		
		System.out.print("\nWant to test DatabaseMine? (y/n)>");  // want to test of part 2 ?

		Scanner scan3 = new Scanner(System.in);
		String ans = scan3.nextLine();
		
	    if (ans.equals("y") || ans.equals("Y")) {
		
	    	System.out.println("\n***** Part 2: Testing DatabaseMine *****");
		
	    	
	    	DatabaseMine db2=new DatabaseMine(917333); //**** testing your class DatabaseMine
		
	    	time=System.nanoTime();
	    	cracker.createDatabase(commonPasswords,db2);// **** call to your method createDatabase in 
            											// **** class PasswordCracker
	    	afterTime=System.nanoTime();
	    	
			System.out.println("DatabaseMine Creation Time is "+(afterTime-time)/1000000.0
					+" miliseconds\n"); // grab stats about how long it took to created DatabaseMine
		
	    	db2.printStatistics(); // grab stats about the class DatabaseMine you programmed
	    	
		
	    	System.out.print("\nPress any key to crack usernames in file:"+leakedAccountsFile+" > ");
			nextLine = scan.nextLine();
			
	    	findPasswords(leakedAccountsFile, db2); // call our method above to crack and print the passwords 
	    	
	    	
	    																										
		
	    }
	    System.out.println("End of Program.");
	   

	}

}
