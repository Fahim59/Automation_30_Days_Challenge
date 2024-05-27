package tests;

import base.BaseClass;
import org.apache.logging.log4j.*;
import org.json.*;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.BasePage;

import java.io.FileReader;

public class Day5 extends BaseClass {

    private static final Logger logger = LogManager.getLogger(Day5.class);

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

    @Test(description = "Enter valid code by pressing the only key button", priority = 1)
    public void enterValidCode() {
        for(int i = 0; i < basePage.getInputFieldSize(); i++){
            basePage.enterCode();
        }

        Assert.assertEquals(basePage.getSuccessMsg(), details.getJSONObject("day5").getString("message"));

        logger.info("Enter valid code by keyboard keys by pressing the only key button and asserting 'success' message");
    }
}