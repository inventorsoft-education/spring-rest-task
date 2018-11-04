package co.inventorsoft.academy.springresttask;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class EmailConsole {

	static private final String EMAIL_REGEXP = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
	static private final String DATE_FORMAT = "dd.MM.yyyy HH:mm";
	
	static private boolean isEmailValid(String emailAddr) {
		return Pattern.compile(EMAIL_REGEXP, Pattern.CASE_INSENSITIVE).matcher(emailAddr).find();
	}

	static private String inputEmail(Scanner scanner) {
		String emailAddr = scanner.nextLine();
		while ( ! isEmailValid(emailAddr) ) {
			System.out.println(" Email is invalid. Please input again : ");
			emailAddr = scanner.nextLine();			
		}
		return emailAddr;
	}	

	static private String inputText (Scanner scanner) { 
		StringBuilder input = new StringBuilder("");
		String line;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (line.isEmpty()) {
				break;
			}
			input.append(line).append("\n");
		}
		return input.toString();
	}
	
	static private boolean isDateValid(String dateString) {
		DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		df.setLenient(false);
		try {
			df.parse(dateString);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	static private Date inputDate(Scanner scanner) {
		String dateString = scanner.nextLine();
		while ( ! isDateValid(dateString) ) {
			System.out.printf(" Date is invalid. Please input again in format %s : \n", DATE_FORMAT);
			dateString = scanner.nextLine();			
		}
		try {
			return (new SimpleDateFormat(DATE_FORMAT)).parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}	

	static private boolean isInput(Scanner scanner) {
		System.out.println(" Do you want to input E-mail? (y - yes) : ");
		String s = scanner.nextLine();
		return (s.equalsIgnoreCase("y") || s.equalsIgnoreCase("yes"));
	}

	static public List<SimpleMailMessage> readFromConsole() {
		List<SimpleMailMessage> emails = new ArrayList<>(); 
		SimpleMailMessage email;
		Scanner scanner = new Scanner(System.in);
		while ( isInput(scanner) ) {
			email = new SimpleMailMessage();
			System.out.println("Please enter recipient's email address : ");
			email.setTo( inputEmail(scanner) ); 
			System.out.println("Please enter email subject : ");
			email.setSubject( scanner.nextLine() ); 
		    System.out.println("Please enter email text : ");
			email.setText( inputText(scanner) );
			System.out.printf("Please enter date to send in format %s : \n", DATE_FORMAT);
			email.setSentDate( inputDate(scanner) );
			emails.add(email);
		}
		scanner.close();
		return emails;
	}

	public List<SimpleMailMessage> getEmails() {
		return readFromConsole();
	}

}
