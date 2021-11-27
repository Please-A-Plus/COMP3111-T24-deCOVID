package comp3111.covid;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

public class ChartForm {
	private static String dataset;
	private static LocalDate startDate;
	private static LocalDate endDate;
	private static List<String> ISOCodes;
	private static String mode;

	// Report form constructor
	public ChartForm(String iDataset, LocalDate iStartDate, LocalDate iEndDate, List<String> iISOCodes, String iMode) {
		dataset = iDataset;
        startDate = iStartDate;
        endDate = iEndDate;
        ISOCodes = iISOCodes;
		mode = iMode;	// either confirmed_cases
	}
	
	// Prevent default constructor
	private ChartForm() {}
	
	public HashMap<String, HashMap<String, Float>> generateChartConfirmedCases() {
		HashMap<String, HashMap<LocalDate, Float>> confirmedCasesPerMillionDict = DataAnalysis.getConfirmedCasesPerMillionBetweenDate(dataset, startDate, endDate);
		HashMap<String, HashMap<String, Float>> chartEntries = new HashMap<String, HashMap<String, Float>>();
		for (String ISOCode: ISOCodes) {
			chartEntries.put(ISOCode, new HashMap<String, Float>());
			for (var entry: confirmedCasesPerMillionDict.get(ISOCode).entrySet()) {
				chartEntries.get(ISOCode).put(entry.getKey().toString(), entry.getValue());
			}
		}
		return chartEntries;
	};
	
	public HashMap<String, HashMap<String, Float>> generateChart() {
		HashMap<String, HashMap<String, Float>> report = null;
		if (mode.equals("confirmed_cases")) {
			report = generateChartConfirmedCases();
		}
		return report;
	}
}
