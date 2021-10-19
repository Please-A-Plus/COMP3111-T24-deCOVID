package comp3111.covid;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
import javafx.scene.control.DatePicker;

/**
 * Building on the sample skeleton for 'ui.fxml' Controller Class generated by SceneBuilder 
 */
public class Controller {

    @FXML
    private Button buttonConfirmedCases;

    @FXML
    private Button buttonConfirmedDeaths;

    @FXML
    private Button buttonGetReport1;

    @FXML
    private Button buttonRateOfVaccination;

    @FXML
    private TableColumn<?, ?> countryColumn;

    @FXML
    private MenuButton countryPickerReport1;

    @FXML
    private DatePicker dateReport1;

    @FXML
    private TableView<ConfirmedCasesRecord> reportTable1;

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
    private TableColumn<?, ?> totalCasesColumn;

    @FXML
    private TableColumn<?, ?> totalCasesPer1MPopulationColumn;
  

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
    void processReport1(ActionEvent event) {
    	String iDataset = textfieldDataset.getText();
    	LocalDate iDate = dateReport1.getValue();
    	List<String> iISOCodes = new ArrayList<String>();
    	HashMap<String, String> invertedCountriesDict = new HashMap<String, String>();
    	for (String ISOCode: DataAnalysis.countriesDict.keySet()) {
    		invertedCountriesDict.put(DataAnalysis.countriesDict.get(ISOCode), ISOCode);
    	}
    	for (MenuItem item: countryPickerReport1.getItems()) {
    		CheckMenuItem checkItem = (CheckMenuItem) item;
    		if (checkItem.isSelected()) {
    			String ISOCode = invertedCountriesDict.get(checkItem.getText());
    			iISOCodes.add(ISOCode);
    		}
    	}
    	ReportForm casesReportForm = new ReportForm(iDataset, iDate, iISOCodes, "confirmed_cases");
    	List<List<String>> casesReport = casesReportForm.generateReport();
    	System.out.println(casesReport);
    	for (List<String> rec: casesReport) {
    		ConfirmedCasesRecord record = new ConfirmedCasesRecord(DataAnalysis.countriesDict.get(rec.get(0)), rec.get(1), rec.get(2));
    		reportTable1.getItems().add(record);
    	}
    }   
    
    @FXML
    void onClickTabReport1(Event event) {
    	if (DataAnalysis.countriesDict != null) return;
    	String iDataset = textfieldDataset.getText();
    	DataAnalysis.initCountriesDict(iDataset);
    	countryPickerReport1.getItems().removeAll();
    	List<String> countries = new ArrayList<String>(DataAnalysis.countriesDict.values());
    	for (String country: countries) {
    		countryPickerReport1.getItems().add(new CheckMenuItem(country));
    	}
    	countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
    	totalCasesColumn.setCellValueFactory(new PropertyValueFactory<>("totalCases"));
    	totalCasesPer1MPopulationColumn.setCellValueFactory(new PropertyValueFactory<>("totalCasesPer1MPopulation"));
    }
    
    @FXML
    void handleCountryPicker(ActionEvent event) {
    	System.out.println("kontoll");
    	event.consume();
    }
}

