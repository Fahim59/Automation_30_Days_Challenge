package tests;

import base.BaseClass;
import org.apache.logging.log4j.*;
import org.json.*;
import org.testng.*;
import org.testng.annotations.*;
import pages.BasePage;

import java.io.FileReader;

public class Day4 extends BaseClass {

    private static final Logger logger = LogManager.getLogger(Day4.class);

    private BasePage basePage;

    FileReader data;
    JSONObject details;

    @BeforeClass
    public void beforeClass() throws Exception {
        try {
            String file = "src/main/resources/data.json";
            data = new FileReader(file);

            JSONTokener tokener = new JSONTokener(data);
            details = new JSONObject(tokener);
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

    @Test(description = "Click on a growing button and see trigger message", priority = 1)
    public void clickGrowingButton() {

        basePage.clickOnButton();

        Assert.assertEquals(basePage.getTriggerMsg(), details.getJSONObject("day4").getString("message"));

        logger.info("Click on the growing button and once clicked see 'Event Triggered' message.");
    }
}