package tests;

import base.BaseClass;
import org.apache.logging.log4j.*;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.BasePage;

import java.io.FileReader;

public class Day23 extends BaseClass {

    private static final Logger logger = LogManager.getLogger(Day23.class);

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

    @Test(description = "Redirection chain of pages and verify GO Back button", priority = 1, enabled = true)
    public void redirectionChainOfPages() {
        basePage.clickRedirectionBtn();

        basePage.verifyTextOnEachRedirection();

        Assert.assertEquals(details.getJSONObject("day23").getString("text"), basePage.getLastPageText());

        basePage.clickGoBackBtn();

        logger.info("Redirection chain of pages and verify GO Back button");
    }
}