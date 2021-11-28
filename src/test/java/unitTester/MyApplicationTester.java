package unitTester;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import comp3111.covid.MyApplication;

import static org.junit.Assert.*;

public class MyApplicationTester extends ApplicationTest {

  @Override
  public void start (Stage stage) throws Exception {
    Parent mainNode = FXMLLoader.load(MyApplication.class.getResource("/ui.fxml"));
    stage.setScene(new Scene(mainNode));
    stage.show();
  }

  @Before
  public void setUp () throws Exception {
  }

  @Test
  public void testVaccinationTabEmptyInput () {
    clickOn("#tabVaccinationOuter");
    clickOn("#tableC_submitButton");
  }

  @Test
  public void testDeathChartWithInput () {
    clickOn("#tabDeathsOuter");
    clickOn("#chartB_tab");
    clickOn("#chartB_tab");
    clickOn("#chartB_submitButton");
    // DatePicker datePicker = TestFxLibraryCommon.lookup(identifier);
  }
}