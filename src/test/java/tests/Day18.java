package tests;

import base.BaseClass;
import org.apache.logging.log4j.*;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.BasePage;

import java.io.FileReader;

public class Day18 extends BaseClass {

    private static final Logger logger = LogManager.getLogger(Day18.class);

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

    @Test(description = "Scroll and verify visibility of button and click on it", priority = 1)
    public void verifyButtonVisibility() throws InterruptedException {

        basePage.scrollToBtn();

        SmallWait(1000);

        basePage.clickFoundMeBtn();

        SmallWait(1000);

        basePage.scrollToText();

        Assert.assertEquals(details.getJSONObject("day18").getString("message"), basePage.getBtnText());

        logger.info("Scroll and verify visibility of button and click on it");
    }
}