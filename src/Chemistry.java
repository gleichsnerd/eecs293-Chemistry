import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Chemistry - Programming Assignment 10
 * @author Adam Gleichsner
 * Static class that checks a string for valid formula syntax,
 * but not valid chemistry.
 * Error Codes:
 * 1 - Null input
 * 2 - Illegal number of arguments
 * 3 - Illegal input
 * 4 - Data corruption
 */
public class Chemistry {

	/* Let's take a look at these regex patterns
	 * When looking at a piece of a chemical formula, it fits into one of two cases:
	 * 
	 * Case 1 - Non-parenthesized
	 * In this case, we're only checking for valid symbols (One capital, up to 2 lowercase)
	 * and element count (none or 2 and higher, since just 0 or 1 is pointless). For this, we fit the
	 * pattern of:
	 * [A-Z][a-z]{0,2}([2-9]|[1-9]+[0-9]+)*
	 * 
	 * Case 2 - Parenthesized
	 * Here, we can have repeated occurrences of case 1 contained within parentheses. In order
	 * for parentheses to be valid, they must have a count at the end (otherwise why have them?).
	 * In order to handle nested parentheses, we can't simply use regex. Instead, we remove them from
	 * the formula by first finding all generic cases of single parenthesis use (i.e. (sometextinhere))
	 * and then checking if it fits the valid pattern:
	 * \(([A-Z][a-z]{0,2}([2-9]|[1-9]+[0-9]+)*)+\)([2-9]|[1-9]+[0-9]+)+
	 * 
	 * If it matches, then we remove it. If not, then it's invalid and we should indicate that.
	 * 
	 * We check the final string (if it's not null) against the simple pattern by appending \A and 
	 * \Z to the ends, effectively binding the pattern to the entire string so we don't have any
	 * loose characters that may be incorrect. In the case of a formula that is entirely parenthesized,
	 * the return will be an empty string, so we also check for that.
	 * 
	 * We make the regex strings private and final so they can't be accessed or changed at any point.
	 */
	private static final String CORRECT_SIMPLE_SYNTAX_PATTERN = "\\A([A-Z][a-z]{0,2}([2-9]|[1-9]+[0-9]+)*)+\\Z";
	private static final String GENERIC_PARENTHESES_PATTERN = "\\(([A-z]*[0-9]*)+\\)\\d*";
	private static final String CORRECT_PARENTHESES_PATTERN = "\\(([A-Z][a-z]{0,2}([2-9]|[1-9]+[0-9]+)*)+\\)([2-9]|[1-9]+[0-9]+)+";
	private static final boolean DEBUG = false;
	/**
	 * Public front of the checking process. Takes any String input and will
	 * return "T" if valid and "F" if now. Mostly here in case the specs
	 * actually want T & F instead of boolean true and false
	 * @param formula - input string formula to check syntax of
	 * @return String - "T" if valid, "F" otherwise
	 */
	public static String checkSyntax(String formula) {
		//Declare as final for security, ya know
		final String TRUE_OUTPUT = "T";
		final String FALSE_OUTPUT = "F";
		boolean isValid = Chemistry.isSyntaxCorrect(formula);
		
		if (isValid)
			return TRUE_OUTPUT;
		else
			return FALSE_OUTPUT;
	}
	
	/**
	 * Public method that actually does the checking against the defined regex
	 * patter. Returns true if syntactically valid, false otherwise
	 * @param formula - input string to check syntax of
	 * @return boolean - true if syntactically valid, false otherwise
	 */
	public static boolean isSyntaxCorrect(String formula) {	
		try {
			throwIfInputIsNull(formula);
			throwIfInvalidString(formula);
		} catch (IllegalArgumentException e){
			System.err.println("Error: input is invalid");
			System.exit(3);
		} 
		
		//Just in case
		if (DEBUG)
			assert formula != null: "Formula to process is null"; 
			
		//We need to remove parentheses in case they're nested
		if (containsParentheses(formula))
			formula = removeParentheses(formula);
		
		//If our parentheses statements weren't invalid and we either have correct remaining formula
		//or we've already processed everything
		if (formulaIsCorrect(formula))
			return true;
		else
			return false;
	}
	
	//Private methods
	
	/**
	 * Private helper that checks if the formula post processing is correct
	 * @param formula
	 * @return boolean - true if valid, false otherwise
	 */
	private static boolean formulaIsCorrect (String formula) {
		if (formula != null && (formula.matches(CORRECT_SIMPLE_SYNTAX_PATTERN) || formula.equals("")))
			return true;
		else
			return false;
	}
	
	/**
	 * Private helper to check if a formula contains parentheses to remove.
	 * Compares the formula against a generic pattern to grab both
	 * correct and incorrect cases.
	 * @param formula - string to search
	 * @return boolean - true if parenthses found, false otherwise
	 */
	private static boolean containsParentheses (String formula) {
		//Create a pattern and matcher to check the string
		Pattern p = Pattern.compile(GENERIC_PARENTHESES_PATTERN);
		Matcher m = p.matcher(formula);

		//If we find any parentheses, then we need to take them out
		if(m.find()) {
			if (DEBUG)
				System.out.println("Parentheses acknowledged. Prepare to remove all parentheses.");
			return true;
		} else
			return false;
	}
	
	/**
	 * Private helper that loops through the formula and removes all valid
	 * parentheses.
	 * @param formula - string to remove parenthesis statements from
	 * @return String - string without valid parenthesis statements, null if invalid statement found
	 */
	private static String removeParentheses (String formula) {
		//Create pattern and matcher to find all parenthesis cases, correct and incorrect
		Pattern p = Pattern.compile(GENERIC_PARENTHESES_PATTERN);
		Matcher m = p.matcher(formula);
		
		//While there is still a generic parenthesis case
		while (m.find()) {
			if(DEBUG)
				System.out.println(m.group() + " was found as a parenthesis statement.");
			//If it's correct, remove it
			if(m.group().matches(CORRECT_PARENTHESES_PATTERN)) {
				formula = formula.replace(m.group(), "");
				if(DEBUG)
					System.out.println(m.group() + " is a correct pattern. Modified formula is now " + formula);
			} else
				return null;
			//Reset the matcher with the newly edited string
			m = p.matcher(formula);
		}
		return formula;
	}
	
	/**
	 * Private helper that checks if formula is a valid string with
	 * characters and no whitespace, throws exception if invalid
	 * @param formula - input string to check if valid string
	 * @throws IllegalArgumentException
	 */
	private static void throwIfInvalidString(String formula) throws IllegalArgumentException{
		if (formula.isEmpty())
			throw new IllegalArgumentException("Error: input is empty string");
		else {
			//Check each character for whitespace since we have no way
			//to infer what the intention of said whitespace was
			for (char c: formula.toCharArray()) {
				if (Character.isWhitespace(c))
					throw new IllegalArgumentException("Error: input contains illegal whitespace");
			}
		}
	}
	
	/**
	 * Private helper that will throw exception if any input is a null object
	 * @param objects - objects to check
	 * @throws IllegalArgumentException
	 */
	private static void throwIfInputIsNull(Object... objects) throws IllegalArgumentException {
		for (Object obj: objects) {
			if (obj == null)
				throw new IllegalArgumentException("Error: an argument is null");
		}
	}

	/**
	 * Main method can take multiple strings as input and check each one
	 * for valid syntax. Will throw exception if no arguments are given.
	 * @param args - Array of strings to check
	 * @throws IllegalArgumentException
	 */
	public static void main(String[] args) throws IllegalArgumentException {
		try {
			throwIfInputIsNull((Object[])args);
		} catch (IllegalArgumentException e) {
			System.err.println("Error: an argument is null");
			System.exit(1);
		}
		
		String checkedOut = " ";
		if (args.length == 0) {
			System.err.println("Error: Invalid number of arguments");
			System.exit(2);
		} else {
			for(String formula: args) {
				checkedOut = Chemistry.checkSyntax(formula);
				System.out.println(formula + " : " + checkedOut);
			}
		}
	}

}
