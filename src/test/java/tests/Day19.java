package tests;

import base.BaseClass;
import org.apache.logging.log4j.*;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.BasePage;

import java.io.FileReader;

public class Day19 extends BaseClass {

    private static final Logger logger = LogManager.getLogger(Day19.class);

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

    @Test(description = "Set each available rate value and asset by image, text and number", priority = 1)
    public void setAvailableRateValue() throws InterruptedException {
        for(int i = 1; i <= 5; i++){
            basePage.clickStarRating(i);
            SmallWait(1000);

            Assert.assertTrue(basePage.checkEmojiVisibility(i), "Emoji "+i+" did was not visible");
            Assert.assertEquals(i+ details.getJSONObject("day19").getString("number"), basePage.getNumber());

            if(i == 1){
                Assert.assertEquals(details.getJSONObject("day19").getJSONArray("message").getString(0), basePage.getMessage());
            }
            else if(i == 2){
                Assert.assertEquals(details.getJSONObject("day19").getJSONArray("message").getString(1), basePage.getMessage());
            }
            else if(i == 3){
                Assert.assertEquals(details.getJSONObject("day19").getJSONArray("message").getString(2), basePage.getMessage());
            }
            else if(i == 4){
                Assert.assertEquals(details.getJSONObject("day19").getJSONArray("message").getString(3), basePage.getMessage());
            }
            else{
                Assert.assertEquals(details.getJSONObject("day19").getJSONArray("message").getString(4), basePage.getMessage());
            }
        }

        logger.info("Set each available rate value and asset by image, text and number");
    }
}