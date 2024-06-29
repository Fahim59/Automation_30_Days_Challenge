package tests;

import base.BaseClass;
import org.apache.logging.log4j.*;
import org.testng.annotations.*;
import pages.BasePage;

public class Day28 extends BaseClass {

    private static final Logger logger = LogManager.getLogger(Day28.class);

    private BasePage basePage;

    @BeforeMethod
    public void beforeMethod() {
        basePage = new BasePage(driver);
    }
    //-------------------------------------------------------//

    @Test(description = "Fetch the count of holidays and weekends till the given month and validate it", priority = 1)
    public void clickHighestAndLowestProductButton() {
        String givenMonth = "Dec 2024";

        basePage.clickDatePicker();

        basePage.fetchHolidaysAndWeekends(givenMonth);

        logger.info("Fetch the count of holidays and weekends till the given month and validate it");
    }
}