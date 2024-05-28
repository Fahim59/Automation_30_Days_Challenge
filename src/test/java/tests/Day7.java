package tests;

import base.BaseClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.BasePage;

import java.io.FileReader;

public class Day7 extends BaseClass {

    private static final Logger logger = LogManager.getLogger(Day7.class);

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

    @Test(description = "Right-clicks, clicks all social media links from 'Share menu', and verifies confirmation message", priority = 1)
    public void clickAllSocialMediaLink() throws InterruptedException {

        basePage.openMenu("twitter", details.getJSONObject("day7").getString("twitter"));

        SmallWait(1000);

        basePage.openMenu("instagram", details.getJSONObject("day7").getString("instagram"));

        SmallWait(1000);

        basePage.openMenu("dribble", details.getJSONObject("day7").getString("dribble"));

        SmallWait(1000);

        basePage.openMenu("telegram", details.getJSONObject("day7").getString("telegram"));

        logger.info("Right-clicks to open a context menu, navigates to the 'Share menu', clicks all social media links in the submenu, and verifies the confirmation message for each link.");
    }
}