package comp3111.covid;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.xpath.SourceTree;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;

/**
 * Building on the sample skeleton for 'ui.fxml' Controller Class generated by SceneBuilder 
 */
public class Controller {
    @FXML
    private MenuButton chartA_countriesPicker;

    @FXML
    private DatePicker chartA_endDate;

    @FXML
    private DatePicker chartA_startDate;

    @FXML
    private Button chartA_getButton;

    @FXML
    private LineChart<String, Float> chartA_lineChart;

    @FXML
    private Button buttonConfirmedCases;

    @FXML
    private Button buttonConfirmedDeaths;

    @FXML
    private Button tableA_submitButton;

    @FXML
    private Button buttonRateOfVaccination;

    @FXML
    private MenuButton tableA_countriesPicker;

    @FXML
    private TableColumn<?, ?> countryColumn;

	@FXML
	private TableColumn<?, ?> tableC_countryColumn;

    @FXML
    private MenuButton countryPickerReport1;

	@FXML
	private MenuButton tableC_countriesPicker;

    @FXML
    private DatePicker tableA_date;

	@FXML
	private DatePicker tableC_date;

    @FXML
    private TableView<ConfirmedCaseTable> tableA_tableView;

	@FXML
	private TableView<VaccinationAndRateRecord> tableC_tableView;

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
    
    @FXML
    private TableColumn<?, ?> tableA_countryColumn;

    @FXML
    private TableColumn<?, ?> tableA_totalCasesColumn;

    @FXML
    private TableColumn<?, ?> tableA_totalCasesPerMillionColumn;

    @FXML
    private TableColumn<?, ?> totalCasesPer1MPopulationColumn;

	@FXML
	private TableColumn<VaccinationAndRateRecord, String> tableC_fullyVaccinatedColumn;

	@FXML
	private TableColumn<VaccinationAndRateRecord, String> tableC_rateOfVaccinationColumn;
  

    /**
     *  Task Zero
     *  To be triggered by the "Confirmed Cases" button on the Task Zero Tab 
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
     *  Task Zero
     *  To be triggered by the "Confirmed Deaths" button on the Task Zero Tab
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
     *  Task Zero
     *  To be triggered by the "Rate of Vaccination" button on the Task Zero Tab
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
    void submitTableA(ActionEvent event) {
    	String iDataset = textfieldDataset.getText();
    	LocalDate iDate = tableA_date.getValue();
    	if (iDate == null) {
    		textAreaConsole.setText("Input Error: Empty Date");
    		return;
    	}
    	List<String> iISOCodes = new ArrayList<String>();
    	HashMap<String, String> invertedCountriesDict = new HashMap<String, String>();
    	for (String ISOCode: DataAnalysis.countriesDict.keySet()) {
    		invertedCountriesDict.put(DataAnalysis.countriesDict.get(ISOCode), ISOCode);
    	}
    	for (MenuItem item: tableA_countriesPicker.getItems()) {
    		CustomMenuItem checkItem = (CustomMenuItem) item;
    		CheckBox checkBox = (CheckBox) checkItem.getContent();
    		if (checkBox.isSelected()) {
    			String ISOCode = invertedCountriesDict.get(checkBox.getText());
    			iISOCodes.add(ISOCode);
    		}
    	}
    	if (iISOCodes.isEmpty()) {
    		textAreaConsole.setText("Input Error: No Countries is Selected");
    		return;
    	}
    	TableForm casesReportForm = new TableForm(iDataset, iDate, iISOCodes, "confirmed_cases");
    	List<List<String>> casesReport = casesReportForm.generateReport();
    	tableA_tableView.getItems().clear();
    	for (List<String> rec: casesReport) {
    		ConfirmedCaseTable record = new ConfirmedCaseTable(DataAnalysis.countriesDict.get(rec.get(0)), rec.get(1), rec.get(2));
    		tableA_tableView.getItems().add(record);
    	}
    }   
	@FXML
	void submitTableC(ActionEvent event) {
    	//sample input: date = 14/1/2021, location = Asia
		String iDataset = textfieldDataset.getText();
		LocalDate iDate = tableC_date.getValue();
		if (iDate == null) {
			textAreaConsole.setText("Input Error: Empty date");
			return;
		}

		List<String> iISOCodes = new ArrayList<String>();
		HashMap<String, String> invertedCountriesDict = new HashMap<String, String>();
		for (String ISOCode: DataAnalysis.countriesDict.keySet()) {
			invertedCountriesDict.put(DataAnalysis.countriesDict.get(ISOCode), ISOCode);
		}
		for (MenuItem item: tableC_countriesPicker.getItems()) {
			CustomMenuItem checkItem = (CustomMenuItem) item;
			CheckBox checkBox = (CheckBox) checkItem.getContent();
			if (checkBox.isSelected()) {
				String ISOCode = invertedCountriesDict.get(checkBox.getText());
				iISOCodes.add(ISOCode);
			}
		}
		if (iISOCodes.isEmpty()) {
			textAreaConsole.setText("Input Error: No countries are selected");
			return;
		}

		List<List<String>> vaccineReport = DataAnalysis.getVaccinationTable(iDataset, iDate, iISOCodes);
		tableC_tableView.getItems().clear();
		for (List<String> rec: vaccineReport) {
			VaccinationAndRateRecord record = new VaccinationAndRateRecord(DataAnalysis.countriesDict.get(rec.get(0)), rec.get(1), rec.get(2));
			tableC_tableView.getItems().add(record);
		}
	}

    @FXML
    void submitChartA(ActionEvent event) {
    	String iDataset = textfieldDataset.getText();
    	LocalDate iStartDate = chartA_startDate.getValue();
    	LocalDate iEndDate = chartA_endDate.getValue();
    	if (iStartDate == null || iEndDate == null) {
    		textAreaConsole.setText("Input Error: Empty Date");
    		return;
    	}

    	List<String> iISOCodes = new ArrayList<String>();
    	HashMap<String, String> invertedCountriesDict = new HashMap<String, String>();
    	for (String ISOCode: DataAnalysis.countriesDict.keySet()) {
    		invertedCountriesDict.put(DataAnalysis.countriesDict.get(ISOCode), ISOCode);
    	}
    	for (MenuItem item: chartA_countriesPicker.getItems()) {
    		CustomMenuItem checkItem = (CustomMenuItem) item;
    		CheckBox checkBox = (CheckBox) checkItem.getContent();
    		if (checkBox.isSelected()) {
    			String ISOCode = invertedCountriesDict.get(checkBox.getText());
    			iISOCodes.add(ISOCode);
    		}
    	}
        if (iISOCodes.isEmpty()) {
    		textAreaConsole.setText("Input Error: No Countries is Selected");
    		return;
    	}

    	ChartForm chartForm = new ChartForm(iDataset, iStartDate, iEndDate, iISOCodes, "confirmed_cases");
    	HashMap<String, HashMap<LocalDate, Float>> casesChart = chartForm.generateChart();

        for (var entry : casesChart.entrySet()) {
            String country = entry.getKey();
            HashMap<LocalDate, Float> cases = entry.getValue();
            XYChart.Series<String, Float> series = new XYChart.Series<>();
            series.setName(country);
            for (var entry2 : cases.entrySet()) {
                LocalDate date = entry2.getKey();
                Float value = entry2.getValue();
                series.getData().add(new XYChart.Data<>(date.toString(), value));
            }
            chartA_lineChart.getData().add(series);
        }
    }   

    public void initialize() {
    	String iDataset = textfieldDataset.getText();
        DataAnalysis.initCountriesDict(iDataset);
        List<String> countries = new ArrayList<String>(DataAnalysis.countriesDict.values());
    	Collections.sort(countries);
        
        for (MenuButton menuButton: Arrays.asList(tableA_countriesPicker, chartA_countriesPicker, 

                                    tableC_countriesPicker)) {
            for (String country: countries) {
                CheckBox checkBox = new CheckBox(country);
                CustomMenuItem customMenuItem = new CustomMenuItem(checkBox);
                customMenuItem.setHideOnClick(false);
                menuButton.getItems().add(customMenuItem);
            }
    	}
        tableA_countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
    	tableA_totalCasesColumn.setCellValueFactory(new PropertyValueFactory<>("totalCases"));
    	tableA_totalCasesPerMillionColumn.setCellValueFactory(new PropertyValueFactory<>("totalCasesPer1MPopulation"));

        tableC_countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
    	tableC_fullyVaccinatedColumn.setCellValueFactory(new PropertyValueFactory<>("fullyVaccinated"));
    	tableC_rateOfVaccinationColumn.setCellValueFactory(new PropertyValueFactory<>("rateOfVaccination"));

        chartA_lineChart.setTitle("Cumulative Confirmed COVID-19 Cases (per 1M)");

    }
}

