package comp3111.covid;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


import comp3111.covidEntity.CovidRecord;
import comp3111.tableColumns.ConfirmedCaseTable;
import comp3111.tableColumns.VaccinationTable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.text.Text;


/**
 * Building on the sample skeleton for 'ui.fxml' Controller Class generated by
 * SceneBuilder
 */
public class Controller {
	// initial setup
	/**
	 * Task Zero
	 * To be triggered by the "Confirmed Cases" button on the Task Zero Tab
	 * 
	 */
	@FXML
	void doConfirmedCases(ActionEvent event) {
		String iDataset = textfieldDataset.getText();
		String iISO = textfieldISO.getText();
		String oReport = DataAnalysis.getConfirmedCases(iDataset, iISO);
		textAreaConsole.setText(oReport);
	}

	/**
	 * Task Zero
	 * To be triggered by the "Confirmed Deaths" button on the Task Zero Tab
	 * 
	 */
	@FXML
	void doConfirmedDeaths(ActionEvent event) {
		String iDataset = textfieldDataset.getText();
		String iISO = textfieldISO.getText();
		String oReport = DataAnalysis.getConfirmedDeaths(iDataset, iISO);
		textAreaConsole.setText(oReport);
	}

	/**
	 * Task Zero
	 * To be triggered by the "Rate of Vaccination" button on the Task Zero Tab
	 * 
	 */
	@FXML
	void doRateOfVaccination(ActionEvent event) {
		String iDataset = textfieldDataset.getText();
		String iISO = textfieldISO.getText();
		String oReport = DataAnalysis.getRateOfVaccination(iDataset, iISO);
		textAreaConsole.setText(oReport);
	}

	@FXML
	private Button buttonConfirmedCases;

	@FXML
	private Button buttonConfirmedDeaths;

	@FXML
	private Button buttonRateOfVaccination;

	@FXML
	private Tab tabApp1;

	@FXML
	private Tab tabApp2;

	@FXML
	private Tab tabApp3;

	@FXML
	private Tab tabReport1;

	@FXML
	private Tab tabReport2;

	@FXML
	private Tab tabReport3;

	@FXML
	private Tab tabTaskZero;

	@FXML
	private TextArea textAreaConsole;

	@FXML
	private TextField textfieldDataset;

	@FXML
	private TextField textfieldISO;
	
	// initialize the controller class
	public void initialize() {
		String iDataset = textfieldDataset.getText();
		DataAnalysis.initCountriesDict(iDataset);
		List<String> countries = new ArrayList<String>(DataAnalysis.countriesDict.values());
		Collections.sort(countries);

		for (MenuButton menuButton : Arrays.asList(tableA_countriesPicker, chartA_countriesPicker,
				tableB_countriesPicker, chartB_countriesPicker,
				tableC_countriesPicker, tableC_countriesPicker)) {
			for (String country : countries) {
				CheckBox checkBox = new CheckBox(country);
				CustomMenuItem customMenuItem = new CustomMenuItem(checkBox);
				customMenuItem.setHideOnClick(false);
				menuButton.getItems().add(customMenuItem);
			}
		}
		tableA_countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
		tableA_totalCasesColumn.setCellValueFactory(new PropertyValueFactory<>("totalCases"));
		tableA_totalCasesPerMillionColumn.setCellValueFactory(new PropertyValueFactory<>("totalCasesPerMillion"));

		tableB_countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
		tableB_totalDeathsColumn.setCellValueFactory(new PropertyValueFactory<>("totalCases"));
		tableB_totalDeathsPerMillionColumn.setCellValueFactory(new PropertyValueFactory<>("totalCasesPer1MPopulation"));

		tableC_countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
		tableC_fullyVaccinatedColumn.setCellValueFactory(new PropertyValueFactory<>("fullyVaccinated"));
		tableC_rateOfVaccinationColumn.setCellValueFactory(new PropertyValueFactory<>("rateOfVaccination"));

		chartA_lineChart.setTitle("Cumulative Confirmed COVID-19 Cases (per 1M)");
		chartA_yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(chartA_yAxis, null, ""));
		chartA_xAxis.setTickLabelFormatter(xAxisLabelFactory(chartA_xAxis));

		chartB_yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(chartB_yAxis, null, "Million"));
		chartB_xAxis.setTickLabelFormatter(xAxisLabelFactory(chartB_xAxis));

		chartC_yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(chartC_yAxis, null, "%"));
		chartC_xAxis.setTickLabelFormatter(xAxisLabelFactory(chartC_xAxis));


	}

	// Table input validation
	public boolean tableInputValidate(LocalDate date, List<String> iISOs) {
		if (date == null) {
			textAreaConsole.setText("Input Error: Please input start date.");
			return false;
		}
		if (iISOs.isEmpty()) {
			textAreaConsole.setText("Input Error: Please select countries.");
			return false;
		}
		return true;
	}

	// Chart input validation
	public boolean chartInputValidate(LocalDate iStartDate, LocalDate iEndDate, List<String> iISOs) {
		if (iStartDate == null) {
			textAreaConsole.setText("Input Error: Please input start date.");
			return false;
		}
		if (iEndDate == null) {
			textAreaConsole.setText("Input Error: Please input end date.");
			return false;
		}
		if (iStartDate.isAfter(iEndDate)) {
			textAreaConsole.setText("Input Error: Please set start date before end date.");
			return false;
		}
		if (iISOs.isEmpty()) {
			textAreaConsole.setText("Input Error: Please select countries.");
			return false;
		}
		return true;
	}

	// display epoch values in x axis as date
	public NumberAxis.DefaultFormatter xAxisLabelFactory(NumberAxis xAxis) {
		return new NumberAxis.DefaultFormatter(xAxis) {
			@Override
			public String toString(final Number object) {
				Long longValue = object.longValue();
				LocalDate date = LocalDate.ofEpochDay(longValue);
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
				return date.format(formatter);
			}
		};
	}

	// Table A - Confirmed cases table

	@FXML
	private MenuButton tableA_countriesPicker;

	@FXML
	private DatePicker tableA_date;

	@FXML
	private Button tableA_submitButton;

	@FXML
	private TableView<ConfirmedCaseTable> tableA_tableView;

	@FXML
	private TableColumn<?, ?> tableA_countryColumn;

	@FXML
	private TableColumn<?, ?> tableA_totalCasesColumn;

	@FXML
	private TableColumn<?, ?> tableA_totalCasesPerMillionColumn;

	@FXML
	void submitTableA(ActionEvent event) {
		String iDataset = textfieldDataset.getText();
		LocalDate iDate = tableA_date.getValue();
		List<String> iISOCodes = new ArrayList<String>();
		HashMap<String, String> invertedCountriesDict = new HashMap<String, String>();
		for (String ISOCode : DataAnalysis.countriesDict.keySet()) {
			invertedCountriesDict.put(DataAnalysis.countriesDict.get(ISOCode), ISOCode);
		}
		for (MenuItem item : tableA_countriesPicker.getItems()) {
			CustomMenuItem checkItem = (CustomMenuItem) item;
			CheckBox checkBox = (CheckBox) checkItem.getContent();
			if (checkBox.isSelected()) {
				String ISOCode = invertedCountriesDict.get(checkBox.getText());
				iISOCodes.add(ISOCode);
			}
		}

		if (!tableInputValidate(iDate, iISOCodes))
			return;

		HashMap<String, CovidRecord> casesTable = DataAnalysis.getCasesTable(iDataset, iDate, iISOCodes);
		tableA_tableView.getItems().clear();
		for (var key : casesTable.keySet()) {
			var rec = casesTable.get(key);
			ConfirmedCaseTable entry;
			if (rec == null)
				entry = new ConfirmedCaseTable(DataAnalysis.countriesDict.get(key), "Data not found", "Data not found");
			else
				entry = new ConfirmedCaseTable(DataAnalysis.countriesDict.get(key),
						rec.confirmedCaseRecord.totalCases.toString(),
						rec.confirmedCaseRecord.totalCasesPerMillion.toString());
			tableA_tableView.getItems().add(entry);
		}

		textAreaConsole.setText("COVID-19 cases table generated successfully.");
	}

	// ChartA - Confirmed cases chart
	@FXML
	private MenuButton chartA_countriesPicker;

	@FXML
	private DatePicker chartA_endDate;

	@FXML
	private DatePicker chartA_startDate;

	@FXML
	private Button chartA_submitButton;

	@FXML
	private LineChart<Long, Float> chartA_lineChart;

	@FXML
	private NumberAxis chartA_xAxis;

	@FXML
	private NumberAxis chartA_yAxis;

	@FXML
	void submitChartA(ActionEvent event) {
		String iDataset = textfieldDataset.getText();
		LocalDate iStartDate = chartA_startDate.getValue();
		LocalDate iEndDate = chartA_endDate.getValue();

		List<String> ilocations = new ArrayList<String>();
		for (MenuItem item : chartA_countriesPicker.getItems()) {
			CustomMenuItem checkItem = (CustomMenuItem) item;
			CheckBox checkBox = (CheckBox) checkItem.getContent();
			if (checkBox.isSelected()) {
				String location = checkBox.getText();
				ilocations.add(location);
				System.out.println(location);
			}
		}

		if (!chartInputValidate(iStartDate, iEndDate, ilocations))
			return;

		chartA_xAxis.setAutoRanging(false);
		chartA_xAxis.setLowerBound(iStartDate.toEpochDay());
		chartA_xAxis.setUpperBound(iEndDate.toEpochDay());

		HashMap<String, List<FloatCoordinates>> casesChart = DataAnalysis.getCasesChart(iDataset, iStartDate, iEndDate,
				ilocations);
		chartA_lineChart.getData().clear();
		casesChart.forEach((location, line) -> {
			XYChart.Series<Long, Float> series = new XYChart.Series<>();
			for (FloatCoordinates coordinates : line) {
				series.getData().add(new XYChart.Data<>(coordinates.getDate().toEpochDay(), coordinates.getValue()));
			}
			chartA_lineChart.getData().add(series);
		});

		textAreaConsole.setText("COVID-19 cases chart generated successfully.");
	}

	// Table C - Vaccination record table
	@FXML
	private MenuButton tableC_countriesPicker;

	@FXML
	private TableColumn<?, ?> tableC_countryColumn;

	@FXML
	private DatePicker tableC_date;

	@FXML
	private TableView<VaccinationTable> tableC_tableView;

	@FXML
	private TableColumn<VaccinationTable, String> tableC_fullyVaccinatedColumn;

	@FXML
	private TableColumn<VaccinationTable, String> tableC_rateOfVaccinationColumn;

	@FXML
	void submitTableC(ActionEvent event) {
		// sample input: date = 14/1/2021, location = Asia
		String iDataset = textfieldDataset.getText();
		LocalDate iDate = tableC_date.getValue();
		List<String> iISOCodes = new ArrayList<String>();
		HashMap<String, String> invertedCountriesDict = new HashMap<String, String>();
		for (String ISOCode : DataAnalysis.countriesDict.keySet()) {
			invertedCountriesDict.put(DataAnalysis.countriesDict.get(ISOCode), ISOCode);
		}
		for (MenuItem item : tableC_countriesPicker.getItems()) {
			CustomMenuItem checkItem = (CustomMenuItem) item;
			CheckBox checkBox = (CheckBox) checkItem.getContent();
			if (checkBox.isSelected()) {
				String ISOCode = invertedCountriesDict.get(checkBox.getText());
				iISOCodes.add(ISOCode);
			}
		}

		if (!tableInputValidate(iDate, iISOCodes))
			return;

		List<List<String>> vaccineReport = DataAnalysis.getVaccinationTable(iDataset, iDate, iISOCodes);
		tableC_tableView.getItems().clear();
		for (List<String> rec : vaccineReport) {
			VaccinationTable record = new VaccinationTable(DataAnalysis.countriesDict.get(rec.get(0)), rec.get(1),
					rec.get(2));
			tableC_tableView.getItems().add(record);
		}
		
		textAreaConsole.setText("COVID-19 vaccination rate table generated successfully.");
	}

	// ChartC - Vaccination chart

	@FXML
	private MenuButton chartC_countriesPicker;

	@FXML
	private DatePicker chartC_endDate;

	@FXML
	private DatePicker chartC_startDate;

	@FXML
	private Button chartC_submitButton;

	@FXML
	private LineChart<Long, Float> chartC_lineChart;

	@FXML
	private NumberAxis chartC_xAxis;

	@FXML
	private NumberAxis chartC_yAxis;

	@FXML
	void submitChartC(ActionEvent event) {
		String iDataset = textfieldDataset.getText();
		LocalDate iStartDate = chartC_startDate.getValue();
		LocalDate iEndDate = chartC_endDate.getValue();

		List<String> ilocations = new ArrayList<String>();
		for (MenuItem item : chartC_countriesPicker.getItems()) {
			CustomMenuItem checkItem = (CustomMenuItem) item;
			CheckBox checkBox = (CheckBox) checkItem.getContent();
			if (checkBox.isSelected()) {
				String location = checkBox.getText();
				ilocations.add(location);
			}
		}

		if (!chartInputValidate(iStartDate, iEndDate, ilocations))
			return;

		chartC_xAxis.setAutoRanging(false);
		chartC_xAxis.setLowerBound(iStartDate.toEpochDay());
		chartC_xAxis.setUpperBound(iEndDate.toEpochDay());

		HashMap<String, List<FloatCoordinates>> vaccineChart = DataAnalysis.getVaccinationChart(iDataset, iStartDate,
				iEndDate, ilocations);
		chartC_lineChart.getData().clear();
		vaccineChart.forEach((location, line) -> {
			XYChart.Series<Long, Float> series = new XYChart.Series<>();
			for (FloatCoordinates coordinates : line) {
				series.getData().add(new XYChart.Data<>(coordinates.getDate().toEpochDay(), coordinates.getValue()));
			}
			chartC_lineChart.getData().add(series);
		});

		textAreaConsole.setText("COVID-19 vaccination rate chart generated successfully.");
	}

	// Table B - Deaths table

    @FXML
    private MenuButton tableB_countriesPicker;

    @FXML
    private TableColumn<?, ?> tableB_countryColumn;

    @FXML
    private DatePicker tableB_date;

    @FXML
    private CheckBox tableB_selectAll;

    @FXML
    private Button tableB_submitButton;

    @FXML
    private Tab tableB_tab;

    @FXML
    private TableView<?> tableB_tableView;

    @FXML
    private Label tableB_title;

    @FXML
    private TableColumn<?, ?> tableB_totalDeathsColumn;

    @FXML
    private TableColumn<?, ?> tableB_totalDeathsPerMillionColumn;
	
	@FXML
	void submitTableB(ActionEvent event) {
		// TODO Refactor this
	}

	// @FXML
	// void submitTableB(ActionEvent event) {
	// 	// Get date and display as title
	// 	LocalDate deathDateParsed = dateDeathTable.getValue();
	// 	if (deathDateParsed == null) {
	// 		return;
	// 	}

	// 	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
	// 	String formattedDate = deathDateParsed.format(formatter);
	// 	deathTitle.setText("Confirmed COVID-19 Deaths as of " + formattedDate);

	// 	List<String> inverseISOCodes = new ArrayList<String>();
	// 	HashMap<String, String> inverseCountriesDict = new HashMap<String, String>();

	// 	for (String ISOCode : DataAnalysis.countriesDict.keySet()) {
	// 		inverseCountriesDict.put(DataAnalysis.countriesDict.get(ISOCode), ISOCode);
	// 	}

	// 	for (MenuItem item : deathCountryPicker.getItems()) {
	// 		CustomMenuItem checkCountry = (CustomMenuItem) item;
	// 		CheckBox checkBox = (CheckBox) checkCountry.getContent();
	// 		if (checkBox.isSelected()) {
	// 			String ISOCode = inverseCountriesDict.get(checkBox.getText());
	// 			inverseISOCodes.add(ISOCode);
	// 		}
	// 	}

	// 	if (inverseISOCodes.isEmpty())
	// 		return;

	// 	// TableForm deathReportForm = new TableForm(textfieldDataset.getText(),
	// 	// deathDateParsed, inverseISOCodes, "death_cases");
	// 	// List<List<String>> deathReport = deathReportForm.generateReport();
	// 	// reportTableB1.getItems().clear();
	// 	// for(List<String> rec: deathReport){
	// 	// ConfirmedCaseTable record = new
	// 	// ConfirmedCaseTable(DataAnalysis.countriesDict.get(rec.get(0)), rec.get(1),
	// 	// rec.get(2));
	// 	// reportTableB1.getItems().add(record);
	// 	// }

	// }

	// Task B2

	@FXML
    private MenuButton chartB_countriesPicker;

    @FXML
    private DatePicker chartB_endDate;

    @FXML
    private LineChart<?, ?> chartB_lineChart;

    @FXML
    private CheckBox chartB_selectAll;

    @FXML
    private DatePicker chartB_startDate;

    @FXML
    private Button chartB_submitButton;

    @FXML
    private Tab chartB_tab;

    @FXML
    private Label chartB_title;

    @FXML
    private NumberAxis chartB_xAxis;

    @FXML
    private NumberAxis chartB_yAxis;
	
	@FXML
	void submitChartB(ActionEvent event) {
		// TODO Refactor this
	}

	// @FXML
	// void sumbitChartB(ActionEvent event) {
	// 	String iDataset = textfieldDataset.getText();
	// 	LocalDate iStartDate = startDeathDate.getValue();
	// 	LocalDate iEndDate = finishDeathDate.getValue();

	// 	List<String> ilocations = new ArrayList<String>();
	// 	for (MenuItem item : deathGraphCountryPicker.getItems()) {
	// 		CustomMenuItem checkItem = (CustomMenuItem) item;
	// 		CheckBox checkBox = (CheckBox) checkItem.getContent();
	// 		if (checkBox.isSelected()) {
	// 			String location = checkBox.getText();
	// 			ilocations.add(location);
	// 		}
	// 	}

	// 	if (!chartInputValidate(iStartDate, iEndDate, ilocations))
	// 		return;

	// 	chartB_xAxis.setAutoRanging(false);
	// 	chartB_xAxis.setLowerBound(iStartDate.toEpochDay());
	// 	chartB_xAxis.setUpperBound(iEndDate.toEpochDay());

	// 	HashMap<String, List<FloatCoordinates>> deathChart = DataAnalysis.getTotalDeathPerMillionPeriod(iDataset,
	// 			iStartDate, iEndDate, ilocations);
	// 	for (String key : deathChart.keySet()) {
	// 		System.out.println(key);
	// 	}
	// 	chartB_lineChart.getData().clear();
	// 	deathChart.forEach((location, line) -> {
	// 		XYChart.Series<Long, Float> series = new XYChart.Series<>();
	// 		for (FloatCoordinates coordinates : line) {
	// 			series.getData().add(new XYChart.Data<>(coordinates.getDate().toEpochDay(), coordinates.getValue()));
	// 		}
	// 		chartB_lineChart.getData().add(series);
	// 	});
	// }
}