package comp3111.covid;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TableForm {
	private static String dataset;
	private static LocalDate date;
	private static List<String> ISOCodes;
	private static String mode;

	// Report form constructor
	public TableForm(String iDataset, LocalDate iDate, List<String> iISOCodes, String iMode) {
		dataset = iDataset;
		date = iDate;
		ISOCodes = iISOCodes;
		mode = iMode;	// either confirmed_cases
	}
	
	// Prevent default constructor
	private TableForm() {}
	
	public List<HashMap<String, String>> generateReportConfirmedCases() {
		List<HashMap<String, String>> report = new ArrayList<HashMap<String, String>>();
//		HashMap<String, Long> populationDict = DataAnalysis.getPopulationBeforeDate(dataset, date);
		HashMap<String, Float> confirmedCasesPerMillionDict = DataAnalysis.getConfirmedCasesPerMillionBeforeDate(dataset, date);
		HashMap<String, Long> confirmedCasesDict = DataAnalysis.getConfirmedCasesBeforeDate(dataset, date);
		for (String ISOCode: ISOCodes) {
			Long confirmedCases = confirmedCasesDict.get(ISOCode);
			Float confirmedCasesPerMillion= confirmedCasesPerMillionDict.get(ISOCode);
			HashMap<String, String> entry = new HashMap<String, String>();
			entry.put("iso_code", ISOCode);
			entry.put("total_cases", confirmedCases.toString());
			entry.put("total_cases_per_million", confirmedCasesPerMillion.toString());
			report.add(entry);
		}
		return report;
	};

	public List<List<String>> generateReportDeathCases(){
		List<List<String>> report = new ArrayList<List<String>>();
		HashMap<String, Long>  confirmedDeathDict = DataAnalysis.getTotalDeath(dataset, date);
		HashMap<String, Float> confirmedDeathPerMillionDict = DataAnalysis.getTotalDeathPerMillion(dataset, date);

		for(String ISOCode: ISOCodes){
			Long confirmedDeath = confirmedDeathDict.get(ISOCode);
			Float confirmedDeathPerMillion = confirmedDeathPerMillionDict.get(ISOCode);
			List<String> entry = new ArrayList<String>();
			entry.add(ISOCode);
			entry.add(confirmedDeath.toString());
			entry.add(confirmedDeathPerMillion.toString());
			report.add(entry);
		}
		return report;
	}
	
	public List<HashMap<String, String>> generateReport() {
		List<HashMap<String, String>> report = null;
		if (mode.equals("confirmed_cases")) {
			report = generateReportConfirmedCases();
		}
		else if(mode.equals("death_cases")){
			report = generateReportDeathCases();
		}
		return report;
	}
}