package unitTester;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import comp3111.covid.*;
import comp3111.covid.DataAnalysis;

public class CovidRecordErrorInputTester {
    CovidRecord covidRecord;
	
	@Before
	public void setUp() throws Exception {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
		
		String iso_code = "AFG";
		String location = "Afghanistan";
		LocalDate recDate = LocalDate.parse("2/24/2020", formatter);
		Long population = Long.valueOf(38928341);
		
		Long totalCases = Long.valueOf(1);
		Long newCases = Long.valueOf(1);
		Float totalCasesPerMillion = (float) 0.026;
		Float newCasesPerMillion = (float) 0;

		//Death Data Parsing
		Long totalDeaths = DataAnalysis.parseLongWithDefault("");
		Long newDeaths = DataAnalysis.parseLongWithDefault("");
		Float totalDeathsPerMillion = DataAnalysis.parseFloatWithDefault("");
		Float newDeathsPerMillion = DataAnalysis.parseFloatWithDefault("");
		
		//TODO: parse vaccination data
		
		ConfirmedCaseRecord confirmedCaseRecord = new ConfirmedCaseRecord(totalCases, newCases, totalCasesPerMillion, newCasesPerMillion);
		ConfirmedDeathRecord confirmedDeathRecord = new ConfirmedDeathRecord(totalDeaths, newDeaths, totalDeathsPerMillion, newDeathsPerMillion);
		VaccinationRecord vaccinationRecord = null;
		
		covidRecord = new CovidRecord(iso_code, location, recDate, population, confirmedCaseRecord, confirmedDeathRecord, vaccinationRecord);
		
	}

	@Test
	public void assertTotalDeathErrorInput() {
		assertEquals(Long.valueOf(0), covidRecord.confirmedDeathRecord.getTotalDeaths());
	}

	@Test
	public void assertTotalDeathsPerMillionErrorInput() {
		assertEquals(Float.valueOf(0), covidRecord.confirmedDeathRecord.getTotalDeathsPerMillion());
	}

	@Test
	public void assertNewDeathErrorInput() {
		assertEquals(Long.valueOf(0), covidRecord.confirmedDeathRecord.getNewDeaths());
	}

	@Test
	public void assertNewDeathPerMillionErrorInput() {
		assertEquals(Float.valueOf(0), covidRecord.confirmedDeathRecord.getNewDeathsPerMillion());
	}
}
