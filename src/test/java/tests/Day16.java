package tests;

import base.BaseClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.BasePage;

public class Day16 extends BaseClass {

    private static final Logger logger = LogManager.getLogger(Day16.class);

    private BasePage basePage;

    @BeforeMethod
    public void beforeMethod() {
        basePage = new BasePage(driver);
    }
    //-------------------------------------------------------//

    @Test(description = "Hover on the movie poster and fetch price of it", priority = 1)
    public void hoverPosterAndFetchPrice() {
        basePage.hoverOnPoster();

        Assert.assertEquals(24.96, Double.valueOf(basePage.fetchPrice().replace("$", "")));

        logger.info("Hover on the movie poster and fetch price of it");
    }
}