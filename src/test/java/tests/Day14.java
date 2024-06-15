package tests;

import base.BaseClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;
import pages.BasePage;

public class Day14 extends BaseClass {

    private static final Logger logger = LogManager.getLogger(Day14.class);

    private BasePage basePage;

    @BeforeMethod
    public void beforeMethod() {
        basePage = new BasePage(driver);
    }
    //-------------------------------------------------------//

    @Test(description = "Verify Flight Result Search", priority = 1)
    public void verifyFlightResultSearch() {
        basePage.clickCookiesBtn();

        basePage.enterDepartureCity("Mumbai");
        basePage.enterDestinationCity("London-Heathrow, UK (LHR)");

        basePage.enterDepartDate("06/10/2024");
        basePage.enterReturnDate("06/13/2024");

        basePage.clickSearchFlightBtn();

        basePage.verifySearchText();

        logger.info("Verify Flight Result Search");
    }

    @Test(description = "Verify Failed Flight Search", priority = 2)
    public void verifyFailedFlightSearch() {
        basePage.enterDepartureCity("Mumbai");
        basePage.enterDestinationCity("London-Heathrow, UK (LHR)");

        basePage.enterDepartDate("01/01/2024");
        basePage.enterReturnDate("01/01/2024");

        basePage.clickSearchFlightBtn();

        basePage.verifyErrorText();

        logger.info("Verify Failed Flight Search");
    }
}