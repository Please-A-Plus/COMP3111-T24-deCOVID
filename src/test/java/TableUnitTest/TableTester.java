package TableUnitTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TableTester {
	int[] arrAscending, arrDescending;
	
	@Before
	public void setUp() throws Exception {
		arrAscending = new int[] {1, 2, 3, 4, 5};
		arrDescending = new int[] {5, 4, 3, 2, 1};
	}

	@Test
	public void computeOneWithValidInput() {
		assertEquals(1, 1);
	}
		
	/*
	@Test
	public void isEvenNumReturnFalse() {
		assertTrue(Library.isEvenNumber(1));
	}	
	*/
}


