package unitTester;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import comp3111.covid.*;

public class TableFormTester {
	TableForm tableForm;
	
	@Before
	public void setUp() throws Exception {	
		LocalDate date = LocalDate.of(2020, 10, 11);
		List<String> iso_codes = new ArrayList<String>();
		iso_codes.add("HKG");
		tableForm = new TableForm("COVID_Dataset_v1.0.csv", date, iso_codes, "confirmed_cases");
	}

	@Test
	public void testTableRowNumber() {
		assertEquals(1, tableForm.generateReportConfirmedCases().size());
	}

}


