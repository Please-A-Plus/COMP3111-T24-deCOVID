package unitTester;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import org.apache.xerces.impl.xpath.XPath.NodeTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import comp3111.covid.MyApplication;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MyApplicationTester extends ApplicationTest {
  LocalDate startDateTest, endDateTest;

  @Override
  public void start (Stage stage) throws Exception {
    Parent mainNode = FXMLLoader.load(MyApplication.class.getResource("/ui.fxml"));
    stage.setScene(new Scene(mainNode));
    stage.show();
  }

  @Before
  public void setUp () throws Exception {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
    startDateTest= LocalDate.parse("11/21/2020", formatter);
    endDateTest= LocalDate.parse("5/20/2021", formatter);
  }

  @Test
  public void testCasesTabEmptyInput () {
    clickOn("#tabCasesOuter");
    clickOn("#tableA_submitButton");
  }

  @Test
  public void testDeathTabEmptyInput () {
    clickOn("#tabDeathsOuter");
    clickOn("#tableB_submitButton");
  }

  @Test
  public void testVaccinationTabEmptyInput () {
    clickOn("#tabVaccinationOuter");
    clickOn("#tableC_submitButton");
  }

  @Test
  public void testCasesChartEmptyInput () {
    clickOn("#tabCasesOuter");
    clickOn("#chartA_tab");
    clickOn("#chartA_submitButton");
  }

  @Test
  public void testDeathChartEmptyInput () {
    clickOn("#tabDeathsOuter");
    clickOn("#chartB_tab");
    clickOn("#chartB_submitButton");
  }

  @Test
  public void testVaccinationChartEmptyInput () {
    clickOn("#tabVaccinationOuter");
    clickOn("#chartC_tab");
    clickOn("#chartC_submitButton");
  }

  @Test
  public void testCasesTabWithInput () {
    clickOn("#tabCasesOuter");
    clickOn("#tableA_date");
    write("21/11/2020");
    press(KeyCode.ENTER).release(KeyCode.ENTER);
    clickOn("#tableA_countriesPicker");
    clickOn(new MouseButton[]{});
    clickOn("#tableA_submitButton");
  }

  @Test
  public void testDeathsTabWithInput () {
    clickOn("#tabDeathsOuter");
    clickOn("#tableB_date");
    write("21/11/2020");
    press(KeyCode.ENTER).release(KeyCode.ENTER);
    clickOn("#tableB_countriesPicker");
    clickOn(new MouseButton[]{});
    clickOn("#tableB_submitButton");
  }

  @Test
  public void testVaccinationTabWithInput () {
    clickOn("#tabVaccinationOuter");
    clickOn("#tableC_date");
    write("21/11/2020");
    press(KeyCode.ENTER).release(KeyCode.ENTER);
    clickOn("#tableC_countriesPicker");
    clickOn(new MouseButton[]{});
    clickOn("#tableC_submitButton");
  }

  @Test
  public void testCasesChartWithInput () {
    clickOn("#tabCasesOuter");
    clickOn("#chartA_tab");
    clickOn("#chartA_endDate");
    write("21/08/2021");
    press(KeyCode.ENTER).release(KeyCode.ENTER);
    clickOn("#chartA_startDate");
    write("10/06/2020");
    press(KeyCode.ENTER).release(KeyCode.ENTER);
    clickOn("#tableA_countriesPicker");
    clickOn(new MouseButton[]{});
    clickOn("#chartA_submitButton");
  }

  @Test
  public void testDeathsChartWithInput () {
    clickOn("#tabDeathsOuter");
    clickOn("#chartB_tab");
    clickOn("#chartB_endDate");
    write("21/08/2021");
    press(KeyCode.ENTER).release(KeyCode.ENTER);
    clickOn("#chartB_startDate");
    write("10/06/2020");
    press(KeyCode.ENTER).release(KeyCode.ENTER);
    clickOn("#tableB_countriesPicker");
    clickOn(new MouseButton[]{});
    clickOn("#chartB_submitButton");
  }

  @Test
  public void testVaccinationChartWithInput () {
    clickOn("#tabVaccinationOuter");
    clickOn("#chartC_tab");
    clickOn("#chartC_endDate");
    write("21/08/2021");
    press(KeyCode.ENTER).release(KeyCode.ENTER);
    clickOn("#chartC_startDate");
    write("10/06/2020");
    press(KeyCode.ENTER).release(KeyCode.ENTER);
    clickOn("#tableC_countriesPicker");
    clickOn(new MouseButton[]{});
    clickOn("#chartC_submitButton");
  }
}