package comp3111.covid;

import org.apache.commons.csv.*;

import comp3111.covidEntity.ConfirmedCaseRecord;
import comp3111.covidEntity.ConfirmedDeathRecord;
import comp3111.covidEntity.CovidRecord;
import comp3111.covidEntity.VaccinationRecord;
import comp3111.tableColumns.VaccinationTable;
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
	public static TwoWaysHashMap<String, String> countriesDict = null;
	
	/**
	 * 
	 * @param dataset COVID-19 CSV dataset location
	 * @return a CSVParser instance 
	 */
	public static CSVParser getFileParser(String dataset) {
		FileResource fr = new FileResource("dataset/" + dataset);
		return fr.getCSVParser(true);
	}

	/**
	 * 
	 * @param dataset COVID-19 CSV dataset location
	 */
	public static void initCountriesDict(String dataset) {
		if (dataset == null) {
			dataset = "COVID_Dataset_v1.0.csv";
		}
		countriesDict = new TwoWaysHashMap<String, String>();
		for (CSVRecord rec : getFileParser(dataset)) {
			String loc = rec.get("location");
			String ISO = rec.get("iso_code");
			if (!countriesDict.containsKeyForward(ISO)) {
				countriesDict.put(ISO, loc);
			}
		}
	}
	
	/**
	 * 
	 * @param dataset COVID-19 CSV dataset location
	 * @param iso_code ISO code of the country
	 * @return Confirmed cases report of the country
	 */
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

	/**
	 * 
	 * @param dataset COVID-19 CSV dataset location
	 * @param iso_code ISO code of the country
	 * @return Confirmed deaths report of the country
	 */
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

	/**
	 * 
	 * @param dataset COVID-19 CSV dataset location
	 * @param iso_code ISO code of the country
	 * @return Vaccination rate report of the country
	 */
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

	/**
	 * 
	 * @param rec a CSVRecord instance to be parsed
	 * @return a parsed CovidRecord instance 
	 */
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
		Float backupVaccinationRate = parseFloatWithDefault(rec.get("total_vaccinations_per_hundred"));

		//TODO: parse vaccination data

		ConfirmedCaseRecord confirmedCaseRecord = new ConfirmedCaseRecord(totalCases, newCases, totalCasesPerMillion, newCasesPerMillion);
		ConfirmedDeathRecord confirmedDeathRecord = new ConfirmedDeathRecord(totalDeaths, newDeaths, totalDeathsPerMillion, newDeathsPerMillion);
		VaccinationRecord vaccinationRecord = new VaccinationRecord(vaccinated, backupVaccinationRate);

		CovidRecord covidRecord = new CovidRecord(iso_code, location, recDate, population, confirmedCaseRecord, confirmedDeathRecord, vaccinationRecord);

		return covidRecord;
	}

	/**
	 * 
	 * @param dataset COVID-19 CSV dataset location
	 * @param date date of interest
	 * @param ISOCodes list of ISO codes of the countries
	 * 
	 * @return HashMap of ISO codes and the most relevant COVID-19 cases and deaths records of the countries
	 */
	public static HashMap<String, CovidRecord> getCasesTable(String dataset, LocalDate date, List<String> ISOCodes) {
		HashMap<String, CovidRecord> table = new HashMap<String, CovidRecord>();
		for (var ISOCode: ISOCodes) {
			table.put(ISOCode, null);
		}
		for (CSVRecord rec : getFileParser(dataset)) {
			CovidRecord covidRecord = parseDataset(rec);
			if (ISOCodes.contains(covidRecord.iso_code)){
				if (covidRecord.date.isBefore(date) || covidRecord.date.isEqual(date)){
					table.put(covidRecord.iso_code, covidRecord);
				}
			}
		}
		return table;
	}


	/**
	 * 
	 * @param dataset COVID-19 CSV dataset location
	 * @param date date of interest
	 * @param locations list of ISO codes of the countries
	 * 
	 * @return HashMap of ISO codes and the most relevant COVID-19 vaccination rate of the countries
	 */
	public static HashMap<String, VaccinationTable> getVaccinationTable(String dataset, LocalDate date, List<String> locations) {
		var table = new HashMap<String, VaccinationTable>();
		for (CSVRecord rec : getFileParser(dataset)) {
			CovidRecord covidRecord = parseDataset(rec);

			String recLoc = covidRecord.location;
			Long vaccination = covidRecord.vaccinationRecord.fullyVaccinated;
			Long population = covidRecord.population;

			if (locations.contains(recLoc) && covidRecord.date.isBefore(date) || covidRecord.date.equals(date)) {
				//is not a missing value
				if (!table.containsKey(recLoc) || vaccination > Long.parseLong(table.get(recLoc).fullyVaccinated)) {
					Float rate = vaccination == 0 ? Float.valueOf(0) : (float) vaccination / population * 100;
					//cuz poor northern cyprus had NULL population
					if (recLoc.equals("Northern Cyprus")) {
						rate = covidRecord.vaccinationRecord.backupRate;
					}
					var row = new VaccinationTable(covidRecord.location, vaccination.toString(), String.format("%.2f%%", rate));
					table.put(covidRecord.location, row);
				}
			}
		}
		return table;
	}

	/**
	 * 
	 * @param dataset COVID-19 CSV dataset location
	 * @param startDate starting date of interest
	 * @param endDate ending date of interest
	 * @param locations list of ISO codes of the countries
	 * 
	 * @return HashMap of ISO codes and the chart coodinates of the COVID-19 cases of the countries
	 */
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
				if ((recDate.isAfter(startDate) || recDate.equals(startDate)) && (recDate.isBefore(endDate) || recDate.equals(endDate))) {
					Float rate = covidRecord.confirmedCaseRecord.totalCasesPerMillion;
					FloatCoordinates coord = new FloatCoordinates(recDate, rate);
					table.get(covidRecord.location).add(coord);
				}
			}
		}
		return table;
	}

	/**
	 * 
	 * @param dataset COVID-19 CSV dataset location
	 * @param startDate starting date of interest
	 * @param endDate ending date of interest
	 * @param locations list of ISO codes of the countries
	 * 
	 * @return HashMap of ISO codes and the chart coodinates of the COVID-19 deaths of the countries
	 */
	public static HashMap<String, List<FloatCoordinates>> getDeathsChart(String dataset, LocalDate startDate, LocalDate endDate, List<String> locations) {
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
				if ((recDate.isAfter(startDate) || recDate.equals(startDate)) && (recDate.isBefore(endDate) || recDate.equals(endDate))) {
					Float deaths = covidRecord.confirmedDeathRecord.totalDeathsPerMillion;
					FloatCoordinates coord = new FloatCoordinates(recDate, deaths);
					table.get(covidRecord.location).add(coord);
				}
			}
		}
		return table;
	}

	/**
	 * 
	 * @param dataset COVID-19 CSV dataset location
	 * @param startDate starting date of interest
	 * @param endDate ending date of interest
	 * @param locations list of ISO codes of the countries
	 * 
	 * @return HashMap of ISO codes and the chart coodinates of the COVID-19 vaccination rate of the countries
	 */

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
			CovidRecord covidRecord = parseDataset(rec);
			LocalDate recDate = covidRecord.date;
			Long population = covidRecord.population;
			Long vaccination = covidRecord.vaccinationRecord.fullyVaccinated;
			Float rate = prevRate.get(rec.get("location"));

			if (locations.contains(covidRecord.location)) {
				if ((recDate.isAfter(startDate) || recDate.equals(startDate)) && (recDate.isBefore(endDate) || recDate.equals(endDate))){
					if (!(population == 0 || vaccination == 0)) {
						rate = (float) vaccination / population * 100;
						prevRate.put(covidRecord.location, rate);
					}
					FloatCoordinates coordinates = new FloatCoordinates(recDate, rate);
					table.get(covidRecord.location).add(coordinates);
				}
			}
		}
		return table;
	}

	/**
	 * 
	 * @param str string input to be parsed
	 * @return Long representaion of the string if the string is a number, else 0
	 */
	public static Long parseLongWithDefault(String str) {
	    if (str == null || str.equals("")) {
	        return Long.valueOf(0);
	    }
			return Long.parseLong(str);
	}

	/**
	 * 
	 * @param str string input to be parsed
	 * @return Float representaion of the string if the string is a number, else 0.0
	 */
	public static Float parseFloatWithDefault(String str) {
	    if (str == null || str.equals("")) {
	        return Float.valueOf(0);
	    } else {
	    return Float.parseFloat(str);
	    }
	}

}