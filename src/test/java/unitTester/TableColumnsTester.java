package unitTester;

import static org.junit.Assert.*;

import java.security.PermissionCollection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import comp3111.tableColumns.*;

public class TableColumnsTester {
    ConfirmedCaseTable caseColumnTest;
    DeathCaseTable deathColumnTest;
    VaccinationTable vaccinationColumnTest;
    
    @Before
    public void setUp() throws Exception {
        String country = "HKG";
        String numCase = "1200";
        String numDeath = "1000";
        String numVaccination = "200";

        Float population = Float.valueOf(200000);
        Float numCasePerMillion = Float.valueOf(Float.valueOf(numCase)/population);
        Float numDeathPerMillion = Float.valueOf(Float.valueOf(numDeath)/population);
        Float numVaccinationPerMillion = Float.valueOf(Float.valueOf(numVaccination)/population);
        
        caseColumnTest = new ConfirmedCaseTable(country, numCase, numCasePerMillion.toString());
        deathColumnTest = new DeathCaseTable(country, numDeath, numDeathPerMillion.toString());
        vaccinationColumnTest = new VaccinationTable(country, numVaccination, numVaccinationPerMillion.toString());
    }

    @Test
	public void testCountryCase() {
		assertEquals("HKG", caseColumnTest.getCountry());
	}

    @Test
	public void testCountryDeath() {
		assertEquals("HKG", deathColumnTest.getCountry());
	}
    
    @Test
	public void testCountryVaccination() {
		assertEquals("HKG", vaccinationColumnTest.getCountry());
	}

    @Test
	public void testTotalCase() {
		assertEquals("1200", caseColumnTest.getTotalCases());
	}

    @Test
	public void testTotalDeath() {
		assertEquals("1000", deathColumnTest.getTotalDeaths());
	}
    
    @Test
	public void testTotalVaccination() {
		assertEquals("200", vaccinationColumnTest.getFullyVaccinated());
	}

    @Test
	public void testTotalCasePerMillion() {
		assertEquals("0.006", caseColumnTest.getTotalCasesPerMillion());
	}

    @Test
	public void testTotalDeathPerMillion() {
		assertEquals("0.005", deathColumnTest.getTotalDeathsPerMillion());
	}
    
    @Test
	public void testTotalVaccinationPerMillion() {
		assertEquals("0.001", vaccinationColumnTest.getRateOfVaccination());
	}

}