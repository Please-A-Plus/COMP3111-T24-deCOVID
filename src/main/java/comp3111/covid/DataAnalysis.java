package comp3111.covid;

import org.apache.commons.csv.*;

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

	public static HashMap<String, HashMap<LocalDate, Float>> getConfirmedCasesPerMillionBetweenDate(String dataset, LocalDate startDate, LocalDate endDate) {
		var totalCasesDict = new HashMap<String, HashMap<LocalDate, Float>>();
		for (CSVRecord rec : getFileParser(dataset)) {
			CovidRecord covidRecord = parseDataset(rec);

			if (!totalCasesDict.containsKey(covidRecord.iso_code)) {
				totalCasesDict.put(covidRecord.iso_code, new HashMap<LocalDate, Float>());
			}

			// if the date is between startDate and endDate, add it to the dictionary
			if (covidRecord.date.isAfter(startDate) && covidRecord.date.isBefore(endDate)) {
				totalCasesDict.get(covidRecord.iso_code).put(covidRecord.date, covidRecord.confirmedCaseRecord.totalCasesPerMillion);
			}
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

	public static HashMap<String, VaccinationTable> getVaccinationTable(String dataset, LocalDate date, List<String> locations) {
		var table = new HashMap<String, VaccinationTable>();
		var prev = new HashMap<String, ArrayList<String>>();
		for (String location: locations) {
			var arr = new ArrayList<String>();
			arr.add("0");
			arr.add("0");
			prev.put(location, arr);
		}
		for (CSVRecord rec : getFileParser(dataset)) {

			if (locations.contains(rec.get("location"))){
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
				LocalDate recDate = LocalDate.parse(rec.get("date"), formatter);

				if (recDate.isBefore(date)){
					//set to prev value
					String populationString = prev.get(rec.get("location")).get(0);
					String vaccinationString = prev.get(rec.get("location")).get(1);
					//if record exist, use current record
					if (!(rec.get("population").equals("") || rec.get("people_fully_vaccinated").equals(""))){
						populationString = rec.get("population");
						vaccinationString = rec.get("people_fully_vaccinated");
						var arr = new ArrayList<String>();
						arr.add(populationString);
						arr.add(vaccinationString);
						prev.put(rec.get("location"), arr);
					}
					List<String> entry = new ArrayList<String>();
					Float rate = (float) Long.parseLong(vaccinationString) / Long.parseLong((populationString)) * 100;
					var row = new VaccinationTable(rec.get("location"), vaccinationString, String.format("%.2f%%",rate));
					table.put(rec.get("location"), row);
				}
			}
		}
		return table;
	}

	public static HashMap<String, List<FloatCoordinates>> getVaccinationChart(String dataset, LocalDate startDate, LocalDate endDate, List<String> locations) {
		//initialize return hashmap
		HashMap<String, List<FloatCoordinates>> table = new HashMap<String, List<FloatCoordinates>>();
		//to tackle missing values in dataset
		var prevRate = new HashMap<String, Float>();
		for (String location: locations) {
			List<FloatCoordinates> series = new ArrayList<FloatCoordinates>();
			table.put(location, series);
			prevRate.put(location, Float.valueOf(0));
		}
		//search csv
		for (CSVRecord rec : getFileParser(dataset)) {
			String recLoc = rec.get("location");

			if (locations.contains(recLoc)) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
				LocalDate recDate = LocalDate.parse(rec.get("date"), formatter);

				if (recDate.isAfter(startDate) && recDate.isBefore(endDate)){
					String populationString = rec.get("population");
					String vaccinationString = rec.get("people_fully_vaccinated");
					Float rate = prevRate.get(rec.get("location"));
					if (!(populationString.equals("") || vaccinationString.equals(""))) {
						rate = (float) Long.parseLong(vaccinationString) / Long.parseLong((populationString)) * 100;
						prevRate.put(rec.get("location"), rate);
					}
					FloatCoordinates coordinates = new FloatCoordinates(recDate, rate);
					table.get(recLoc).add(coordinates);
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