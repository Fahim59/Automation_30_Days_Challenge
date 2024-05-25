package tests;

import base.BaseClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.HasAuthentication;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UsernameAndPassword;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.BasePage;

import java.io.FileReader;
import java.net.URI;
import java.util.function.Predicate;

public class Day2 extends BaseClass {

    private static final Logger logger = LogManager.getLogger(Day2.class);

    private BasePage basePage;

    FileReader data;
    JSONObject day2Details;

    @BeforeClass
    public void beforeClass() throws Exception {
        try {
            String file = "src/main/resources/data.json";
            data = new FileReader(file);

            JSONTokener tokener = new JSONTokener(data);
            day2Details = new JSONObject(tokener);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            if (data != null) {
                data.close();
            }
        }
    }

    @BeforeMethod
    public void beforeMethod() {
        basePage = new BasePage(driver);
    }
    //-------------------------------------------------------//

    @Test(description = "Input a value in a disabled field", priority = 1)
    public void input_onDisableField() {
        basePage.enterPassword(day2Details.getJSONObject("day2").getString("password"));

        basePage.enterPasswordRemovingAttribute(day2Details.getJSONObject("day2").getString("password"));

        logger.info("Input a value in a disabled field");
    }
}