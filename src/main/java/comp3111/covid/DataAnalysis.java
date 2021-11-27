package comp3111.covid;

import org.apache.commons.csv.*;

import comp3111.covidEntity.ConfirmedCaseRecord;
import comp3111.covidEntity.ConfirmedDeathRecord;
import comp3111.covidEntity.CovidRecord;
import comp3111.covidEntity.VaccinationRecord;
import edu.duke.*;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

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

			//Death Data Parsing
			Long totalDeaths = parseLongWithDefault(rec.get("total_deaths"));
			Long newDeaths = parseLongWithDefault(rec.get("new_deaths"));
			Float totalDeathsPerMillion = parseFloatWithDefault(rec.get("total_deaths_per_million"));
			Float newDeathsPerMillion = parseFloatWithDefault(rec.get("new_deaths_per_million"));
			Long vaccinated = parseLongWithDefault(rec.get("people_fully_vaccinated"));
			
			//TODO: parse vaccination data
			
			ConfirmedCaseRecord confirmedCaseRecord = new ConfirmedCaseRecord(totalCases, newCases, totalCasesPerMillion, newCasesPerMillion);
			ConfirmedDeathRecord confirmedDeathRecord = new ConfirmedDeathRecord(totalDeaths, newDeaths, totalDeathsPerMillion, newDeathsPerMillion);
			VaccinationRecord vaccinationRecord = new VaccinationRecord(vaccinated);
			
			CovidRecord covidRecord = new CovidRecord(iso_code, location, recDate, population, confirmedCaseRecord, confirmedDeathRecord, vaccinationRecord);
			
			return covidRecord;
	 }

	public static HashMap<String, CovidRecord> getCasesTable(String dataset, LocalDate date, List<String> ISOCodes) {
		HashMap<String, CovidRecord> table = new HashMap<String, CovidRecord>();
		for (var ISOCode: ISOCodes) {
			table.put(ISOCode, null);
		}
		for (CSVRecord rec : getFileParser(dataset)) {
			CovidRecord covidRecord = parseDataset(rec);
			if (ISOCodes.contains(covidRecord.iso_code)){
				if (covidRecord.date.isBefore(date)){
					table.put(covidRecord.iso_code, covidRecord);
				}
			}
		}
		return table;
	}

	public static HashMap<String, Long> getTotalDeath(String dataset, LocalDate date) {
		HashMap<String, Long> totalDeathCasesMap = new HashMap<String, Long>();
		for (CSVRecord rec : getFileParser(dataset)) {
			CovidRecord covidRecord = parseDataset(rec);
			
			if (!totalDeathCasesMap.containsKey(covidRecord.iso_code)) {
				totalDeathCasesMap.put(covidRecord.iso_code, Long.valueOf(0));
			}
			totalDeathCasesMap.put(covidRecord.iso_code, covidRecord.confirmedDeathRecord.getTotalDeaths());	
		}
		return totalDeathCasesMap;
	}

	public static HashMap<String, Float> getTotalDeathPerMillion(String dataset, LocalDate date) {
		HashMap<String, Float> totalDeathCasesMap = new HashMap<String, Float>();
		for (CSVRecord rec : getFileParser(dataset)) {
			CovidRecord covidRecord = parseDataset(rec);
			
			if (!totalDeathCasesMap.containsKey(covidRecord.iso_code)) {
				totalDeathCasesMap.put(covidRecord.iso_code, Float.valueOf(0));
			}
			totalDeathCasesMap.put(covidRecord.iso_code, covidRecord.confirmedDeathRecord.getTotalDeathsPerMillion());	
		}
		return totalDeathCasesMap;
	}

	public static HashMap<String, List<FloatCoordinates>> getTotalDeathPerMillionPeriod(String dataset, LocalDate startDate, LocalDate endDate, List<String> locations) {
		//initialize return hashmap
		HashMap<String, List<FloatCoordinates>> table = new HashMap<String, List<FloatCoordinates>>();
		for (String location: locations) {
			List<FloatCoordinates> series = new ArrayList<FloatCoordinates>();
			table.put(location, series);
		}
		//search csv
		for (CSVRecord rec : getFileParser(dataset)) {
			String recLoc = rec.get("location");

			if (locations.contains(recLoc)) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
				LocalDate recDate = LocalDate.parse(rec.get("date"), formatter);

				if (recDate.isAfter(startDate)){
					if (recDate.isBefore(endDate)){
						Float deathPerMillion;
						String deathPerMillionString = rec.get("total_deaths_per_million");
						// System.out.println(deathPerMillionString);
						deathPerMillion = Float.parseFloat(deathPerMillionString);
						FloatCoordinates coordinates = new FloatCoordinates(recDate, deathPerMillion);
						table.get(recLoc).add(coordinates);
					}
				}
			}
		}
		return table;
	}

	public static HashMap<String, Long> getNewDeath(String dataset, LocalDate date) {
		HashMap<String, Long> newDeathCasesMap = new HashMap<String, Long>();
		for (CSVRecord rec : getFileParser(dataset)) {
			CovidRecord covidRecord = parseDataset(rec);
			
			if (!newDeathCasesMap.containsKey(covidRecord.iso_code)) {
				newDeathCasesMap.put(covidRecord.iso_code, Long.valueOf(0));
			}
			newDeathCasesMap.put(covidRecord.iso_code, covidRecord.confirmedDeathRecord.getNewDeaths());	
		}
		return newDeathCasesMap;
	}

	public static List<List<String>> getVaccinationTable(String dataset, LocalDate date, List<String> ISOCodes) {
		List<List<String>> table = new ArrayList<List<String>>();
		for (CSVRecord rec : getFileParser(dataset)) {

			if (ISOCodes.contains(rec.get("iso_code"))){
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
				LocalDate recDate = LocalDate.parse(rec.get("date"), formatter);

				if (recDate.equals(date)){
					String populationString = rec.get("population");
					String vaccinationString = rec.get("people_fully_vaccinated");
					if (populationString.equals("") || vaccinationString.equals("")) continue;

					List<String> entry = new ArrayList<String>();
					Float rate = (float) Long.parseLong(vaccinationString) / Long.parseLong((populationString)) * 100;
					entry.add(rec.get("iso_code"));
					entry.add(vaccinationString);
					entry.add(String.format("%.2f%%",rate));
					table.add(entry);
					ISOCodes.remove((rec.get("iso_code")));
				}
			}
		}
		for (String iso : ISOCodes){
			List<String> entry = new ArrayList<String>();
			entry.add(iso);
			entry.add("Data not found");
			entry.add("Data not found");
			table.add(entry);
		}
		return table;
	}

	public static HashMap<String, List<FloatCoordinates>> getCasesChart(String dataset, LocalDate startDate, LocalDate endDate, List<String> locations) {
		//initialize return hashmap
		HashMap<String, List<FloatCoordinates>> table = new HashMap<String, List<FloatCoordinates>>();
		for (String location: locations) {
			List<FloatCoordinates> series = new ArrayList<FloatCoordinates>();
			table.put(location, series);
		}
		//search csv
		for (CSVRecord rec : getFileParser(dataset)) {
			CovidRecord covidRecord = parseDataset(rec);

			if (locations.contains(covidRecord.location)) {
				LocalDate recDate = covidRecord.date;
				if (recDate.isAfter(startDate) && recDate.isBefore(endDate)) {
					Float rate = covidRecord.confirmedCaseRecord.totalCasesPerMillion;
					FloatCoordinates coord = new FloatCoordinates(recDate, rate);
					table.get(covidRecord.location).add(coord);
				}
			}
		}
		return table;
	}

	public static HashMap<String, List<FloatCoordinates>> getVaccinationChart(String dataset, LocalDate startDate, LocalDate endDate, List<String> locations) {
		//initialize return hashmap
		HashMap<String, List<FloatCoordinates>> table = new HashMap<String, List<FloatCoordinates>>();
		for (String location: locations) {
			List<FloatCoordinates> series = new ArrayList<FloatCoordinates>();
			table.put(location, series);
		}
		//search csv
		for (CSVRecord rec : getFileParser(dataset)) {
			String recLoc = rec.get("location");

			if (locations.contains(recLoc)) {
				Float prevRate = Float.valueOf(0);
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
				LocalDate recDate = LocalDate.parse(rec.get("date"), formatter);

				if (recDate.isAfter(startDate)){
					if (recDate.isBefore(endDate)){
						String populationString = rec.get("population");
						String vaccinationString = rec.get("people_fully_vaccinated");
						Float rate = prevRate;
						if (!(populationString.equals("") || vaccinationString.equals(""))) {
							rate = (float) Long.parseLong(vaccinationString) / Long.parseLong((populationString)) * 100;
							assert rate >= prevRate;
							assert rate <= 1;
							prevRate = rate;
						}
						FloatCoordinates coordinates = new FloatCoordinates(recDate, rate);
						table.get(recLoc).add(coordinates);
					}
				}
			}
		}
		return table;
	}

	public static Long parseLongWithDefault(String str) {
	    if (str == null || str == "") {
	        return Long.valueOf(0);
	    }
	    return Long.parseLong(str);
	}
	 
	public static Float parseFloatWithDefault(String str) {
	    if (str == null || str == "") {
	        return Float.valueOf(0);
	    }
	    return Float.parseFloat(str);
	}

}