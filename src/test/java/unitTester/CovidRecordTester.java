package unitTester;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import comp3111.covid.*;
import comp3111.covidEntity.ConfirmedCaseRecord;
import comp3111.covidEntity.ConfirmedDeathRecord;
import comp3111.covidEntity.CovidRecord;
import comp3111.covidEntity.VaccinationRecord;

public class CovidRecordTester {
	CovidRecord covidRecord;
	
	@Before
	public void setUp() throws Exception {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
		
		String iso_code = "HKG";
		String location = "Hong Kong";
		LocalDate recDate = LocalDate.parse("11/21/2020", formatter);
		Long population = Long.valueOf(5000000);
		
		Long totalCases = Long.valueOf(4000);
		Long newCases = Long.valueOf(200);
		Float totalCasesPerMillion = Float.valueOf(totalCases/population);
		Float newCasesPerMillion = Float.valueOf(newCases/population);

		//Death Data Parsing
		Long totalDeaths = Long.valueOf(3000);
		Long newDeaths = Long.valueOf(200);
		Float totalDeathsPerMillion = Float.valueOf(totalDeaths/population);
		Float newDeathsPerMillion = Float.valueOf(newDeaths/population);
		Long vaccinated = Long.valueOf(2000);
		Float vaccinatedRate = Float.valueOf(vaccinated / population);
		
		//TODO: parse vaccination data
		
		ConfirmedCaseRecord confirmedCaseRecord = new ConfirmedCaseRecord(totalCases, newCases, totalCasesPerMillion, newCasesPerMillion);
		ConfirmedDeathRecord confirmedDeathRecord = new ConfirmedDeathRecord(totalDeaths, newDeaths, totalDeathsPerMillion, newDeathsPerMillion);
		VaccinationRecord vaccinationRecord = new VaccinationRecord(vaccinated);
		
		covidRecord = new CovidRecord(iso_code, location, recDate, population, confirmedCaseRecord, confirmedDeathRecord, vaccinationRecord);
		
	}

	@Test
	public void assertIsoCode() {
		assertEquals("HKG", covidRecord.iso_code);
	}

	@Test
	public void assertLocation() {
		assertEquals("Hong Kong", covidRecord.location);
	}

	@Test
	public void assertDate() {
		assertEquals(21, covidRecord.date.getDayOfMonth());
	}

	@Test
	public void assertMonth() {
		assertEquals(11, covidRecord.date.getMonthValue());
	}

	@Test
	public void assertYear() {
		assertEquals(2020, covidRecord.date.getYear());
	}

	@Test
	public void assertPopulation() {
		assertEquals(Long.valueOf(5000000), covidRecord.population);
	}

	@Test
	public void assertTotalDeaths(){
		assertEquals(Long.valueOf(3000), covidRecord.confirmedDeathRecord.getTotalDeaths());
	}

	@Test
	public void assertNewDeaths(){
		assertEquals(Long.valueOf(200), covidRecord.confirmedDeathRecord.getNewDeaths());
	}


}


