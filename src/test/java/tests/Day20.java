package tests;

import base.BaseClass;
import org.apache.logging.log4j.*;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.BasePage;

import java.io.FileReader;

public class Day20 extends BaseClass {

    private static final Logger logger = LogManager.getLogger(Day20.class);

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

    @Test(description = "Fetch and Verify text from QR code", priority = 1)
    public void fetchAndVerifyQRCodeText() {
        basePage.enterText(details.getJSONObject("day20").getString("message"));

        basePage.clickQRGenerator();

        Assert.assertEquals(details.getJSONObject("day20").getString("message"), basePage.getQRCode());

        logger.info("Fetch and Verify text from QR code");
    }
}