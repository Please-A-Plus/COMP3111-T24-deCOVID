package comp3111.covid;

import org.apache.commons.csv.*;
import edu.duke.*;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 
 * Data Explorer on COVID-19
 * @author <a href=mailto:namkiu@ust.hk>Namkiu Chan</a>
 * @version	1.1
 * 
 */
public class DataAnalysis {
	public static HashMap<String, String> countriesDict = null;
	
	public static CSVParser getFileParser(String dataset) {
	     FileResource fr = new FileResource("dataset/" + dataset);
	     return fr.getCSVParser(true);
		}
	
	public static void initCountriesDict(String dataset) {
		if (dataset == null) {
			dataset = "COVID_Dataset_v1.0.csv";
		}
		countriesDict = new HashMap<String, String>();
		for (CSVRecord rec : getFileParser(dataset)) {
			String loc = rec.get("location");
			String ISO = rec.get("iso_code");
			if (!countriesDict.containsKey(ISO)) {
				countriesDict.put(ISO, loc);
			}
		}
	}
	
	public static String getConfirmedCases(String dataset, String iso_code) {
		String oReport = "";	
		long confirmedCases = 0;
		long numRecord = 0;
		long totalNumRecord = 0;
		
		for (CSVRecord rec : getFileParser(dataset)) {
			
			if (rec.get("iso_code").equals(iso_code)) {
				String s = rec.get("new_cases");
				if (!s.equals("")) {
					confirmedCases += Long.parseLong(s);
					numRecord++;
				}
			}		
			totalNumRecord++;
		}
		
		oReport = String.format("Dataset (%s): %,d Records\n\n", dataset, totalNumRecord);
		oReport += String.format("[Summary (%s)]\n", iso_code);
		oReport += String.format("Number of Confirmed Cases: %,d\n", confirmedCases);
		oReport += String.format("Number of Days Reported: %,d\n", numRecord);
		
		return oReport;
	}
	
	public static String getConfirmedCasesReport(String dataset, LocalDate date, List<String> countries) {
		String oReport = "";	
		long confirmedCases = 0;
		long numRecord = 0;
		long totalNumRecord = 0;
		for (CSVRecord rec : getFileParser(dataset)) {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
			LocalDate recDate = LocalDate.parse(rec.get("date"), formatter);
			if (recDate.isBefore(date) && countries.contains(rec.get("location"))) {
				String s = rec.get("new_cases");
				if (!s.equals("")) {
					confirmedCases += Long.parseLong(s);
					numRecord++;
				}				
			}		
			totalNumRecord++;
		}
		
		oReport = String.format("Dataset (%s): %,d Records\n\n", dataset, totalNumRecord);
		oReport += String.format("[Summary (%s)]\n", date, countries);
		oReport += String.format("Number of Confirmed Cases: %,d\n", confirmedCases);
		oReport += String.format("Number of Days Reported: %,d\n", numRecord);
		
		return oReport;
	}
	
	public static String getConfirmedDeaths(String dataset, String iso_code) {
			String oReport = "";	
			long confirmedDeaths = 0;
			long numRecord = 0;
			long totalNumRecord = 0;
			
			for (CSVRecord rec : getFileParser(dataset)) {
				
				if (rec.get("iso_code").equals(iso_code)) {
					String s = rec.get("new_deaths");
					if (!s.equals("")) {
						confirmedDeaths += Long.parseLong(s);
						numRecord++;
					}
				}		
				totalNumRecord++;
			}
			
			oReport = String.format("Dataset (%s): %,d Records\n\n", dataset, totalNumRecord);
			oReport += String.format("[Summary (%s)]\n", iso_code);
			oReport += String.format("Number of Deaths: %,d\n", confirmedDeaths);
			oReport += String.format("Number of Days Reported: %,d\n", numRecord);
			
			return oReport;
	 }
	 
	 public static String getRateOfVaccination(String dataset, String iso_code) {
			String oReport = "";	
			long fullyVaccinated = 0;
			long numRecord = 0;
			long totalNumRecord = 0;
			long population = 0;
			float rate = 0.0f;
						
			for (CSVRecord rec : getFileParser(dataset)) {
				
				if (rec.get("iso_code").equals(iso_code)) {
					
					String s1 = rec.get("people_fully_vaccinated");
					String s2 = rec.get("population");		
					if (!s1.equals("") && !s2.equals("")) {
						fullyVaccinated = Long.parseLong(s1);
						population = Long.parseLong(s2);						
						numRecord++;
					}
				}		
				totalNumRecord++;
			}
			
			if (population !=0)
				rate = (float) fullyVaccinated / population * 100;
			
			oReport = String.format("Dataset (%s): %,d Records\n\n", dataset, totalNumRecord);
			oReport += String.format("[Summary (%s)]\n", iso_code);
			oReport += String.format("Number of People Fully Vaccinated: %,d\n", fullyVaccinated);
			oReport += String.format("Population: %,d\n", population);
			oReport += String.format("Rate of Vaccination: %.2f%%\n", rate);
			oReport += String.format("Number of Days Reported: %,d\n", numRecord);
			
			return oReport;
	 }
	 
	 public static CovidRecord parseDataset(CSVRecord rec) {			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
			
			String iso_code = rec.get("iso_code");
			String location = rec.get("location");
			LocalDate recDate = LocalDate.parse(rec.get("date"), formatter);
			Long population = parseLongWithDefault(rec.get("population"));
			
			Long totalCases = parseLongWithDefault(rec.get("total_cases"));
			Long newCases = parseLongWithDefault(rec.get("new_cases"));
			Float totalCasesPerMillion = parseFloatWithDefault(rec.get("total_cases_per_million"));
			Float newCasesPerMillion = parseFloatWithDefault(rec.get("new_cases_per_million"));
			
			//TODO
			
			ConfirmedCaseRecord confirmedCaseRecord = new ConfirmedCaseRecord(totalCases, newCases, totalCasesPerMillion, newCasesPerMillion);
			ConfirmedDeathRecord confirmedDeathRecord = null;
			VaccinationRecord vaccinationRecord = null;
			
			CovidRecord covidRecord = new CovidRecord(iso_code, location, recDate, population, confirmedCaseRecord, confirmedDeathRecord, vaccinationRecord);
			
			return covidRecord;
	 }
	 
	public static HashMap<String, Long> getConfirmedCasesBeforeDate(String dataset, LocalDate date) {
		HashMap<String, Long> totalCasesDict = new HashMap<String, Long>();
		for (CSVRecord rec : getFileParser(dataset)) {
			CovidRecord covidRecord = parseDataset(rec);
			
			if (!totalCasesDict.containsKey(covidRecord.iso_code)) {
				totalCasesDict.put(covidRecord.iso_code, Long.valueOf(0));
			}
			totalCasesDict.put(covidRecord.iso_code, covidRecord.confirmedCaseRecord.totalCases);	
		}
		return totalCasesDict;
	}

	public static HashMap<String, Float> getConfirmedCasesPerMillionBeforeDate(String dataset, LocalDate date) {
		HashMap<String, Float> totalCasesDict = new HashMap<String, Float>();
		for (CSVRecord rec : getFileParser(dataset)) {
			CovidRecord covidRecord = parseDataset(rec);
			
			if (!totalCasesDict.containsKey(covidRecord.iso_code)) {
				totalCasesDict.put(covidRecord.iso_code, Float.valueOf(0));
			}
			totalCasesDict.put(covidRecord.iso_code, covidRecord.confirmedCaseRecord.totalCasesPerMillion);	
		}
		return totalCasesDict;
	}

	public static HashMap<String, Long> getPopulationBeforeDate(String dataset, LocalDate date) {
		HashMap<String, Long> populationDict = new HashMap<String, Long>();
		for (CSVRecord rec : getFileParser(dataset)) {
			String iso_code = rec.get("iso_code");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
			LocalDate recDate = LocalDate.parse(rec.get("date"), formatter);
			String populationString = rec.get("population");
			if (recDate.isAfter(date) || populationString.equals("")) continue;
			Long population = Long.parseLong(populationString);
			populationDict.put(iso_code, population);	
		}
		return populationDict;
	}
	 
	private static Long parseLongWithDefault(String str) {
	    if (str == null || str == "") {
	        return Long.valueOf(0);
	    }
	    return Long.parseLong(str);
	}
	 
	private static Float parseFloatWithDefault(String str) {
	    if (str == null || str == "") {
	        return Float.valueOf(0);
	    }
	    return Float.parseFloat(str);
	}

}