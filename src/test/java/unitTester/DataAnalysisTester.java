package unitTester;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import comp3111.covid.DataAnalysis;
import org.apache.commons.csv.*;

public class DataAnalysisTester {	

	@Before
	public void setUp() throws Exception {
	}


	@Test
	public void testNullLongParser() {
		assertEquals(Long.valueOf(0), DataAnalysis.parseLongWithDefault(""));
	}

	@Test
	public void testLongParser() {
		assertEquals(Long.valueOf(123456789), DataAnalysis.parseLongWithDefault("123456789"));
	}

	@Test
	public void testNullFloatParser() {
		assertTrue(Math.abs(DataAnalysis.parseFloatWithDefault("") - (float) 0) < (float) 0.001);
	}

	@Test
	public void testFloatParser() {
		assertTrue(Math.abs(DataAnalysis.parseFloatWithDefault("123.456") - (float) 123.456) < (float) 0.001);
	}
	
	@Test
	public void testCountriedDict() {
		DataAnalysis.initCountriesDict(null);
		assertTrue(!DataAnalysis.countriesDict.isEmpty());
	}

	@Test
	public void testTotalDeathMap() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
		LocalDate recDate = LocalDate.parse("05/21/2020", formatter);
		assertTrue(!DataAnalysis.getTotalDeath("COVID_Dataset_v1.0.csv", recDate).isEmpty());
	}

}
