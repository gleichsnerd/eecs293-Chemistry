import static org.junit.Assert.*;

import org.junit.Test;


public class ChemistryTest {


	@Test
	public void testSingleElement(){
		assertTrue(Chemistry.isSyntaxCorrect("O"));
		assertTrue(Chemistry.isSyntaxCorrect("Br"));
		assertTrue(Chemistry.isSyntaxCorrect("Umm"));
		
		assertFalse(Chemistry.isSyntaxCorrect("o"));
		assertFalse(Chemistry.isSyntaxCorrect("Uhhhhhhhhhh"));
		
	}

	@Test
	public void testManyElements(){
		assertTrue(Chemistry.isSyntaxCorrect("OH"));
		assertTrue(Chemistry.isSyntaxCorrect("BrH"));
		assertTrue(Chemistry.isSyntaxCorrect("BrHe"));
		assertTrue(Chemistry.isSyntaxCorrect("UmmOhh"));
		
		assertFalse(Chemistry.isSyntaxCorrect("ErrrrrUhh"));
		assertFalse(Chemistry.isSyntaxCorrect("UmmUhhhhhhhhh"));
	}

	@Test
	public void testMultiples(){
		assertTrue(Chemistry.isSyntaxCorrect("O2"));
		assertTrue(Chemistry.isSyntaxCorrect("H2O"));
		assertTrue(Chemistry.isSyntaxCorrect("H2O2"));
		assertTrue(Chemistry.isSyntaxCorrect("Hi2Ho4"));
		
		assertFalse(Chemistry.isSyntaxCorrect("o5"));
		assertFalse(Chemistry.isSyntaxCorrect("O1"));
		assertFalse(Chemistry.isSyntaxCorrect("O1o"));
		assertFalse(Chemistry.isSyntaxCorrect("O0"));
		assertFalse(Chemistry.isSyntaxCorrect("H1H8"));
		assertFalse(Chemistry.isSyntaxCorrect("L8RH8R0"));
	}


	@Test
	public void testParentheses(){
		assertTrue(Chemistry.isSyntaxCorrect("(NaCl)2"));
		assertTrue(Chemistry.isSyntaxCorrect("(Na2Cl)4"));
		assertTrue(Chemistry.isSyntaxCorrect("(Na2Cl4)6OH"));
		
		assertFalse(Chemistry.isSyntaxCorrect("(NaCl"));
		assertFalse(Chemistry.isSyntaxCorrect("Boo)"));
		assertFalse(Chemistry.isSyntaxCorrect("(H2O)"));
	}

	@Test
	public void testNestedParentheses(){
		assertTrue(Chemistry.isSyntaxCorrect("((OH2)3Pr)4"));
		assertTrue(Chemistry.isSyntaxCorrect("((OH2)3Pr)4LOL3"));
		assertTrue(Chemistry.isSyntaxCorrect("((OH2)3(Hgg)4Pr)4"));
		
		assertFalse(Chemistry.isSyntaxCorrect("(((((((((((((WhOoPs)"));
		assertFalse(Chemistry.isSyntaxCorrect("((NaCl)(Oh)2)2"));
		
	}

	@Test
	public void testInvalidinput(){
		try{
			Chemistry.isSyntaxCorrect(null);
			fail("Exception should be thrown on null input");
		} catch(IllegalArgumentException e) {};
		
		try{
			Chemistry.isSyntaxCorrect("");
			fail("Exception should be thrown on empty string input");
		} catch(IllegalArgumentException e) {};
		
		try{
			Chemistry.isSyntaxCorrect("Hi ThErE1");
			fail("Exception should be thrown on whitespace in input");
		} catch(IllegalArgumentException e) {};
	}

	@Test
	public void testMain() {
		String[] args = new String[0];
		
		try {
			Chemistry.main(args);
			fail("Exception should be thrown for empty args");
		} catch(IllegalArgumentException e) {};
		
		args = new String[]{"NaCl", null, "H2O"};
	}

}
