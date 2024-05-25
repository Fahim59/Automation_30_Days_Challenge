package tests;

import base.BaseClass;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;
import org.json.*;
import org.apache.logging.log4j.*;

import java.io.FileReader;
import java.net.URI;
import java.util.function.Predicate;

public class Day1 extends BaseClass {

    private static final Logger logger = LogManager.getLogger(Day1.class);

    private BasePage basePage;

    FileReader data;
    JSONObject day1Details;

    @BeforeClass
    public void beforeClass() throws Exception {
        try {
            String file = "src/main/resources/data.json";
            data = new FileReader(file);

            JSONTokener tokener = new JSONTokener(data);
            day1Details = new JSONObject(tokener);
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

    @Test(description = "Bypasses the basic browser authentication popup", priority = 1)
    public void bypass_browser_auth() {
        Predicate<URI> predicatURI = uri -> uri.getHost().contains(day1Details.getJSONObject("day1").getString("host"));

        ((HasAuthentication)driver).register(predicatURI, UsernameAndPassword.of(day1Details.getJSONObject("day1").getString("username"),
                day1Details.getJSONObject("day1").getString("password")));

        basePage.clickBasicAuth();

        Assert.assertEquals(basePage.verifyMessage(),day1Details.getJSONObject("day1").getString("message"));

        logger.info("Bypasses the basic browser authentication popup");
    }
}