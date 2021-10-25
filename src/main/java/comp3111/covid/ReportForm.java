package comp3111.covid;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.print.attribute.standard.MediaSize.ISO;

public class ReportForm {
	private static String dataset;
	private static LocalDate date;
	private static List<String> ISOCodes;
	private static String mode;

	// Report form constructor
	public ReportForm(String iDataset, LocalDate iDate, List<String> iISOCodes, String iMode) {
		dataset = iDataset;
		date = iDate;
		ISOCodes = iISOCodes;
		mode = iMode;	
	}
	
	// Prevent default constructor
	private ReportForm() {}
	
	private static List<List<String>> generateReportConfirmedCases() {
		List<List<String>> report = new ArrayList<List<String>>();
		HashMap<String, Long> populationDict = DataAnalysis.getPopulationBeforeDate(dataset, date);
		HashMap<String, Integer> confirmedCasesDict = DataAnalysis.getConfirmedCasesBeforeDate(dataset, date);
		for (String ISOCode: ISOCodes) {
			Integer confirmedCases = confirmedCasesDict.get(ISOCode);
			Long population = populationDict.get(ISOCode);
			Long confirmedCasesPer1MPopulation = (long) (1.*confirmedCases*1000000/population);
			List<String> entry = new ArrayList<String>();
			entry.add(ISOCode);
			entry.add(confirmedCases.toString());
			entry.add(confirmedCasesPer1MPopulation.toString());
			report.add(entry);
		}
		return report;
	};
	
	public List<List<String>> generateReport() {
		List<List<String>> report = null;
		if (mode.equals("confirmed_cases")) {
			report = generateReportConfirmedCases();
		}
		return report;
	}
}