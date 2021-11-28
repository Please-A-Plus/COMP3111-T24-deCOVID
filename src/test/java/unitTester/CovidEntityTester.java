package unitTester;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import comp3111.covidEntity.ConfirmedCaseRecord;
import comp3111.covidEntity.ConfirmedDeathRecord;
import comp3111.covidEntity.CovidRecord;
import comp3111.covidEntity.VaccinationRecord;

public class CovidEntityTester {
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
        Float backup = Float.valueOf(1);
		
		//TODO: parse vaccination data
		
		ConfirmedCaseRecord confirmedCaseRecord = new ConfirmedCaseRecord(totalCases, newCases, totalCasesPerMillion, newCasesPerMillion);
		ConfirmedDeathRecord confirmedDeathRecord = new ConfirmedDeathRecord(totalDeaths, newDeaths, totalDeathsPerMillion, newDeathsPerMillion);
		VaccinationRecord vaccinationRecord = new VaccinationRecord(vaccinated, backup);
		
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
	public void assertTotalCases(){
		assertEquals(Long.valueOf(4000), covidRecord.confirmedCaseRecord.totalCases);
	}

	@Test
	public void assertNewCases(){
		assertEquals(Long.valueOf(200), covidRecord.confirmedCaseRecord.newCases);
	}

	@Test
	public void assertTotalCasesPerMillionCases(){
		assertEquals(Float.valueOf(4000/5000000), covidRecord.confirmedCaseRecord.totalCasesPerMillion);
	}

	@Test
	public void assertNewCasesPerMillionCases(){
		assertEquals(Float.valueOf(200/5000000), covidRecord.confirmedCaseRecord.newCasesPerMillion);
	}

	@Test
	public void assertTotalDeaths(){
		assertEquals(Long.valueOf(3000), covidRecord.confirmedDeathRecord.totalDeaths);
	}

	@Test
	public void assertNewDeaths(){
		assertEquals(Long.valueOf(200), covidRecord.confirmedDeathRecord.newDeaths);
	}

	@Test
	public void assertTotalDeathsPerMillionCases(){
		assertEquals(Float.valueOf(3000/5000000), covidRecord.confirmedDeathRecord.totalDeathsPerMillion);
	}

	@Test
	public void assertNewDeathsPerMillionCases(){
		assertEquals(Float.valueOf(200/5000000), covidRecord.confirmedDeathRecord.newDeathsPerMillion);
	}
	
	@Test
	public void assertFullyVaccinated(){
		assertEquals(Long.valueOf(2000), covidRecord.vaccinationRecord.fullyVaccinated);
	}
}

