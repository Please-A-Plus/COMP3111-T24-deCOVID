package unitTester;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import comp3111.covid.DataAnalysis;
import comp3111.covidEntity.CovidRecord;
import comp3111.tableColumns.VaccinationTable;

import org.apache.commons.csv.*;

public class DataAnalysisTester {
	CovidRecord testRecordArray[] = new CovidRecord[3];
	int countRecord = 0;
	LocalDate testDate;
	HashMap<String, CovidRecord> testCasesTable;
	HashMap<String, VaccinationTable> testVaccinationTable;

	@Before
	public void setUp() throws Exception {
		DataAnalysis.initCountriesDict(null);
		for (CSVRecord rec : DataAnalysis.getFileParser("COVID_Dataset_v1.0.csv")) {
			CovidRecord covidRecord = DataAnalysis.parseDataset(rec);
			if(countRecord < 3){
				testRecordArray[countRecord] = covidRecord;
				countRecord++;}
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        testDate = LocalDate.parse("2/22/2021", formatter);
		List<String> listCountryCodeTest=new ArrayList<String>();  
		listCountryCodeTest.add("LSO");
		listCountryCodeTest.add("SLV");
		testCasesTable = DataAnalysis.getCasesTable("COVID_Dataset_v1.0.csv", testDate, listCountryCodeTest);
		testVaccinationTable = DataAnalysis.getVaccinationTable("COVID_Dataset_v1.0.csv", testDate, listCountryCodeTest);
	}

	@Test
	public void testCountriedDict() {
		assertTrue(!DataAnalysis.countriesDict.isEmpty());
	}

	@Test
	public void testParsing(){
		assertEquals("Afghanistan", testRecordArray[1].location);
	}

	@Test
	public void testTotalCasesTable(){
		assertEquals(Long.valueOf(59235), testCasesTable.get("SLV").confirmedCaseRecord.totalCases);
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

}
