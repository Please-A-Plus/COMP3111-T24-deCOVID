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
import comp3111.tableColumns.DeathCaseTable;
import comp3111.tableColumns.VaccinationTable;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
	private final List<String> prioritizedCountries = Arrays.asList("Hong Kong", "India", "Israel", "Japan", "Macao", "Singapore", "United Kingdom", "United States", "World");

	/**
	 * UI initialization
	 * It is triggered at the start of the application.
	 * It creates the dropdown menu for the country pickers,
	 * Mount factory to table columns so that it acn be populated,
	 * and set display properties for charts.
	 *
	 */
	public void initialize() {
		String iDataset = textfieldDataset.getText();
		DataAnalysis.initCountriesDict(iDataset);
		List<String> countries = new ArrayList<String>(DataAnalysis.countriesDict.keySetBackward());
		Collections.sort(countries);
		Collections.reverse(prioritizedCountries);
		for (String prioritizedCountry: prioritizedCountries){
			countries.remove(prioritizedCountry);
			countries.add(0, prioritizedCountry);
		}

		for (MenuButton menuButton : Arrays.asList(tableA_countriesPicker, chartA_countriesPicker,
				tableB_countriesPicker, chartB_countriesPicker,
				tableC_countriesPicker, chartC_countriesPicker)) {
			for (String country : countries) {
				CheckBox checkBox = new CheckBox(country);
				CustomMenuItem customMenuItem = new CustomMenuItem(checkBox);
				customMenuItem.setHideOnClick(false);
				menuButton.getItems().add(customMenuItem);
			}
			menuButton.setOnHiding((event) -> handleMenuClose(event));
		}
		tableA_countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
		tableA_totalCasesColumn.setCellValueFactory(new PropertyValueFactory<>("totalCases"));
		tableA_totalCasesPerMillionColumn.setCellValueFactory(new PropertyValueFactory<>("totalCasesPerMillion"));

		tableB_countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
		tableB_totalDeathsColumn.setCellValueFactory(new PropertyValueFactory<>("totalDeaths"));
		tableB_totalDeathsPerMillionColumn.setCellValueFactory(new PropertyValueFactory<>("totalDeathsPerMillion"));

		tableC_countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
		tableC_fullyVaccinatedColumn.setCellValueFactory(new PropertyValueFactory<>("fullyVaccinated"));
		tableC_rateOfVaccinationColumn.setCellValueFactory(new PropertyValueFactory<>("rateOfVaccination"));

		chartA_lineChart.setCreateSymbols(false);
		chartB_lineChart.setCreateSymbols(false);
		chartC_lineChart.setCreateSymbols(false);

		chartA_yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(chartA_yAxis, null, ""));
		chartA_xAxis.setTickLabelFormatter(xAxisLabelFactory(chartA_xAxis));

		chartB_yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(chartB_yAxis, null, ""));
		chartB_xAxis.setTickLabelFormatter(xAxisLabelFactory(chartB_xAxis));

		chartC_yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(chartC_yAxis, null, "%"));
		chartC_xAxis.setTickLabelFormatter(xAxisLabelFactory(chartC_xAxis));


	}

	/**
	 * Data validation
	 * Checks the user input for tables.
	 * @param date The date inputted.
	 * @param iISOs The List of countries (ISOs or names) inputted.
	 * @return boolean Whether the input is valid.
	 *
	 */
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

	/**
	 * Data validation
	 * Checks the user input for charts.
	 * @param iStartDate The start date inputted.
	 * @param iEndDate The end date inputted.
	 * @param iISOs The List of countries (ISOs or names) inputted.
	 * @return boolean Whether the input is valid.
	 *
	 */
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
		if (iISOs.size() > 20) {
			textAreaConsole.setText("Warning: Over 20 countries selected. Please select less for better experience.");
			return false;
		}
		return true;
	}

	/**
	 * Axis Label Factory
	 * Creates formatter object that can label an axis with epoch values as "dd LLLL yyyy" format.
	 * @param xAxis The axis to be labelled with dates. Note that the axis should be populated with epoch values of dates.
	 * @return NumberAxis.DefaultFormatter A axis formatter object.
	 *
	 */
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
    private CheckBox tableA_selectAll;

    @FXML
	private Label tableA_title;

	/**
	 * Task A1
	 * To be triggered when "select all" is selected on task A1.
	 * @param event The event object of "select all" being clicked.
	 * @return Nothing.
	 *
	 */
	@FXML
	void selectAllTableA(ActionEvent event) {
		if (tableA_selectAll.isSelected()){
			tableA_countriesPicker.setDisable(true);
			for (MenuItem item: tableA_countriesPicker.getItems()) {
				CustomMenuItem checkItem = (CustomMenuItem) item;
				CheckBox checkBox = (CheckBox) checkItem.getContent();
				checkBox.setSelected(true);
			}
			tableA_countriesPicker.setText("All countries selected");
		} else {
			tableA_countriesPicker.setDisable(false);
			for (MenuItem item: tableA_countriesPicker.getItems()) {
				CustomMenuItem checkItem = (CustomMenuItem) item;
				CheckBox checkBox = (CheckBox) checkItem.getContent();
				checkBox.setSelected(false);
			}
			tableA_countriesPicker.setText("Click to select");
		}
	}

	/**
	 * Task A1
	 * Populates the Task A1 table. To be triggered when "Generate" is clicked on task A1.
	 * @param event The event object of "Generate" being clicked.
	 * @return Nothing.
	 *
	 */
	@FXML
	void submitTableA(ActionEvent event) {
		String iDataset = textfieldDataset.getText();
		LocalDate iDate = tableA_date.getValue();
		List<String> iISOCodes = new ArrayList<String>();
		for (MenuItem item : tableA_countriesPicker.getItems()) {
			CustomMenuItem checkItem = (CustomMenuItem) item;
			CheckBox checkBox = (CheckBox) checkItem.getContent();
			if (checkBox.isSelected()) {
				String ISOCode = DataAnalysis.countriesDict.getBackward(checkBox.getText());
				iISOCodes.add(ISOCode);
			}
		}

		if (!tableInputValidate(iDate, iISOCodes))
			return;

		HashMap<String, CovidRecord> casesTable = DataAnalysis.getCasesTable(iDataset, iDate, iISOCodes);
		tableA_tableView.getItems().clear();
		for (var key : iISOCodes) {
			var rec = casesTable.get(key);
			ConfirmedCaseTable entry;
			if (rec == null)
				entry = new ConfirmedCaseTable(DataAnalysis.countriesDict.getForward(key), "Data not found", "Data not found");
			else
				entry = new ConfirmedCaseTable(DataAnalysis.countriesDict.getForward(key),
						rec.confirmedCaseRecord.totalCases.toString(),
						rec.confirmedCaseRecord.totalCasesPerMillion.toString());
			tableA_tableView.getItems().add(entry);
		}

		tableA_title.setText(String.format("Number of Confirmed COVID-19 Cases as of %s", iDate.toString()));
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

	/**
	 * Task A2
	 * Populates the Task A2 chart. To be triggered when "Generate" is clicked on task A2.
	 * @param event The event object of "Generate" being clicked.
	 * @return Nothing.
	 *
	 */
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
			series.setName(location);
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
    private CheckBox tableC_selectAll;

    @FXML
	private Label tableC_title;

	/**
	 * Task C1
	 * To be triggered when "select all" is selected on task C1.
	 * @param event The event object of "select all" being clicked.
	 * @return Nothing.
	 *
	 */
	@FXML
	void selectAllTableC(ActionEvent event) {
		if (tableC_selectAll.isSelected()){
			tableC_countriesPicker.setDisable(true);
			for (MenuItem item: tableC_countriesPicker.getItems()) {
				CustomMenuItem checkItem = (CustomMenuItem) item;
				CheckBox checkBox = (CheckBox) checkItem.getContent();
				checkBox.setSelected(true);
			}
			tableC_countriesPicker.setText("All countries selected");
		} else {
			tableC_countriesPicker.setDisable(false);
			for (MenuItem item: tableC_countriesPicker.getItems()) {
				CustomMenuItem checkItem = (CustomMenuItem) item;
				CheckBox checkBox = (CheckBox) checkItem.getContent();
				checkBox.setSelected(false);
			}
			tableC_countriesPicker.setText("Click to select");
		}
	}

	/**
	 * Task C1
	 * Populates the Task C1 table. To be triggered when "Generate" is clicked on task C1.
	 * @param event The event object of "Generate" being clicked.
	 * @return Nothing.
	 *
	 */
	@FXML
	void submitTableC(ActionEvent event) {
		String iDataset = textfieldDataset.getText();
		LocalDate iDate = tableC_date.getValue();

		List<String> ilocations = new ArrayList<String>();
		for (MenuItem item: tableC_countriesPicker.getItems()) {
			CustomMenuItem checkItem = (CustomMenuItem) item;
			CheckBox checkBox = (CheckBox) checkItem.getContent();
			if (checkBox.isSelected()) {
				String location = checkBox.getText();
				ilocations.add(location);
			}
		}

		if (!tableInputValidate(iDate, ilocations))
			return;

		HashMap<String, VaccinationTable> vaccineReport = DataAnalysis.getVaccinationTable(iDataset, iDate, ilocations);
		tableC_tableView.getItems().clear();
		vaccineReport.forEach((location, row) -> {
			tableC_tableView.getItems().add(row);
		});
		tableC_title.setText(String.format("Rate of Vaccination against COVID-19 as of %s", iDate.toString()));
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

	/**
	 * Task C2
	 * Populates the Task C2 chart. To be triggered when "Generate" is clicked on task C2.
	 * @param event The event object of "Generate" being clicked.
	 * @return Nothing.
	 *
	 */
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
			series.setName(location);
			for (FloatCoordinates coordinates: line) {
				series.getData().add(new XYChart.Data<>(coordinates.getDate().toEpochDay(), coordinates.getValue()));
			}
			chartC_lineChart.getData().addAll(series);
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
    private Button tableB_submitButton;

    @FXML
    private Tab tableB_tab;

    @FXML
    private TableView<DeathCaseTable> tableB_tableView;

    @FXML
    private Label tableB_title;

    @FXML
    private TableColumn<?, ?> tableB_totalDeathsColumn;

    @FXML
    private TableColumn<?, ?> tableB_totalDeathsPerMillionColumn;

	@FXML
    private CheckBox tableB_selectAll;

	/**
	 * Task B1
	 * To be triggered when "select all" is selected on task B1.
	 * @param event The event object of "select all" being clicked.
	 * @return Nothing.
	 *
	 */
	@FXML
	void selectAllTableB(ActionEvent event) {
		if (tableB_selectAll.isSelected()){
			tableB_countriesPicker.setDisable(true);
			for (MenuItem item: tableB_countriesPicker.getItems()) {
				CustomMenuItem checkItem = (CustomMenuItem) item;
				CheckBox checkBox = (CheckBox) checkItem.getContent();
				checkBox.setSelected(true);
			}
			tableB_countriesPicker.setText("All countries selected");
		} else {
			tableB_countriesPicker.setDisable(false);
			for (MenuItem item: tableB_countriesPicker.getItems()) {
				CustomMenuItem checkItem = (CustomMenuItem) item;
				CheckBox checkBox = (CheckBox) checkItem.getContent();
				checkBox.setSelected(false);
			}
			tableB_countriesPicker.setText("Click to select");
		}
	}

	/**
	 * Task B1
	 * Populates the Task B1 table. To be triggered when "Generate" is clicked on task B1.
	 * @param event The event object of "Generate" being clicked.
	 * @return Nothing.
	 *
	 */
	@FXML
	void submitTableB(ActionEvent event) {
		String iDataset = textfieldDataset.getText();
		LocalDate iDate = tableB_date.getValue();
		List<String> iISOCodes = new ArrayList<String>();
		for (MenuItem item : tableB_countriesPicker.getItems()) {
			CustomMenuItem checkItem = (CustomMenuItem) item;
			CheckBox checkBox = (CheckBox) checkItem.getContent();
			if (checkBox.isSelected()) {
				String ISOCode = DataAnalysis.countriesDict.getBackward(checkBox.getText());
				iISOCodes.add(ISOCode);
			}
		}

		if (!tableInputValidate(iDate, iISOCodes))
			return;

		HashMap<String, CovidRecord> casesTable = DataAnalysis.getCasesTable(iDataset, iDate, iISOCodes);
		tableB_tableView.getItems().clear();
		for (var key : iISOCodes) {
			var rec = casesTable.get(key);
			DeathCaseTable entry;
			if (rec == null)
				entry = new DeathCaseTable(DataAnalysis.countriesDict.getForward(key), "Data not found", "Data not found");
			else
				entry = new DeathCaseTable(DataAnalysis.countriesDict.getForward(key),
						rec.confirmedDeathRecord.totalDeaths.toString(),
						rec.confirmedDeathRecord.totalDeathsPerMillion.toString());
			tableB_tableView.getItems().add(entry);
		}

		tableB_title.setText(String.format("Number of Confirmed COVID-19 Deaths as of %s", iDate.toString()));
		textAreaConsole.setText("COVID-19 deaths table generated successfully.");
	}

	// Task B2

	@FXML
    private MenuButton chartB_countriesPicker;

    @FXML
    private DatePicker chartB_endDate;

    @FXML
    private LineChart<Long, Float> chartB_lineChart;

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

	/**
	 * Task B2
	 * Populates the Task B2 chart. To be triggered when "Generate" is clicked on task B2.
	 * @param event The event object of "Generate" being clicked.
	 * @return Nothing.
	 *
	 */
	@FXML
	void submitChartB(ActionEvent event) {
		String iDataset = textfieldDataset.getText();
		LocalDate iStartDate = chartB_startDate.getValue();
		LocalDate iEndDate = chartB_endDate.getValue();

		List<String> ilocations = new ArrayList<String>();
		for (MenuItem item : chartB_countriesPicker.getItems()) {
			CustomMenuItem checkItem = (CustomMenuItem) item;
			CheckBox checkBox = (CheckBox) checkItem.getContent();
			if (checkBox.isSelected()) {
				String location = checkBox.getText();
				ilocations.add(location);
			}
		}

		if (!chartInputValidate(iStartDate, iEndDate, ilocations))
			return;

		chartB_xAxis.setAutoRanging(false);
		chartB_xAxis.setLowerBound(iStartDate.toEpochDay());
		chartB_xAxis.setUpperBound(iEndDate.toEpochDay());

		HashMap<String, List<FloatCoordinates>> deathChart = DataAnalysis.getDeathsChart(iDataset, iStartDate,
				iEndDate, ilocations);
		chartB_lineChart.getData().clear();
		deathChart.forEach((location, line) -> {
			XYChart.Series<Long, Float> series = new XYChart.Series<>();
			series.setName(location);
			for (FloatCoordinates coordinates : line) {
				series.getData().add(new XYChart.Data<>(coordinates.getDate().toEpochDay(), coordinates.getValue()));
			}
			chartB_lineChart.getData().add(series);
		});

		textAreaConsole.setText("COVID-19 deaths chart generated successfully.");
	}

	/**
	 * UI initialization
	 * Updates the display text to country picker as "[number] countries selected". To be triggered when the drop down menu collapses.
	 * @param event The event object a MenuButton collapsing its popup.
	 *
	 *
	 */
	public void handleMenuClose(Event event) {
		Integer selected = 0;
		var menu = (MenuButton) event.getSource();
		for(MenuItem item: menu.getItems()) {
			CustomMenuItem checkItem = (CustomMenuItem) item;
			CheckBox checkBox = (CheckBox) checkItem.getContent();
			if (checkBox.isSelected()) {
				selected += 1;
			}
		}
		menu.setText(String.format("%d countries selected", selected));
	}
}