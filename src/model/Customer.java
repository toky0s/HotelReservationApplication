package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer {
	private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	private String firstName;
	private String lastName;
	private String email;
	
	public Customer(String firstName, String lastName, String email) {
		if (isValidEmail(email)) {
			this.email = email;
		}
		else {
			throw new IllegalArgumentException("Invalid email address");
		}
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}
	
	@Override
	public String toString() {
		return "{Customer: First name: " + firstName + ", last name: " + lastName + ", email: "+ email + "}";
	}

	@Override
	public int hashCode() {
		return email.hashCode();
	}

	public static boolean isValidEmail(String email) {
        // Compile the regex
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        // Create a matcher for the input email
        Matcher matcher = pattern.matcher(email);
        // Return whether the email matches the regex
        return matcher.matches();
    }
}
