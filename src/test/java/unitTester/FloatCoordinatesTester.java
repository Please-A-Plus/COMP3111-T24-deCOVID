package unitTester;


import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import comp3111.covid.FloatCoordinates;

public class FloatCoordinatesTester {
    FloatCoordinates testPointA;
    FloatCoordinates testPointB;
    FloatCoordinates testPointC;
    
    @Before
	public void setUp() throws Exception {
        // Date Input
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        LocalDate date = LocalDate.parse("03/25/2021", formatter);
        LocalDate dateNow = LocalDate.now();

        // Value Input
        Float zero = Float.valueOf(0);
        Float twoFromString = Float.parseFloat("2.42");

        // Test Items
        testPointA = new FloatCoordinates(date, zero);
        testPointB = new FloatCoordinates(date, twoFromString);
        testPointC = new FloatCoordinates(dateNow, zero);
	}

	@Test
	public void testZeroValue() {
		assertEquals(Float.valueOf(0), testPointA.getValue());
	}

    @Test
	public void testStringValue() {
		assertEquals(Float.valueOf("2.42"), testPointB.getValue());
	}

    @Test
	public void testCurrentDate() {
		assertEquals(LocalDate.now(), testPointC.getDate());
	}
}
