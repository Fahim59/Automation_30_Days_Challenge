package tests;

import base.BaseClass;
import org.apache.logging.log4j.*;
import org.testng.annotations.*;
import pages.BasePage;

public class Day24 extends BaseClass {

    private static final Logger logger = LogManager.getLogger(Day24.class);

    private BasePage basePage;

    @BeforeMethod
    public void beforeMethod() {
        basePage = new BasePage(driver);
    }
    //-------------------------------------------------------//

    @Test(description = "Single Selection - Deals (New)", priority = 1)
    public void single_Selection_Deals() throws InterruptedException {

        Scroll_Down();
        SmallWait(2000);

        basePage.selectFilter("Deals", "New");

        SmallWait(1500);
        basePage.resetFilter();

        logger.info("Single Selection - Deals (New)");
    }

    @Test(description = "Multiple Selection - Brands (Apple, Samsung, TCL)", priority = 2)
    public void multiple_Selection_Brands() throws InterruptedException {
        basePage.selectFilter("Brands", "Apple", "Samsung", "TCL");

        SmallWait(1500);
        basePage.resetFilter();

        logger.info("Multiple Selection - Brands (Apple, Samsung, TCL)");
    }

    @Test(description = "Single Selection - Operating System (Android)", priority = 3)
    public void single_Selection_OS() throws InterruptedException {
        basePage.selectFilter("Operating system", "Android");

        SmallWait(1500);
        basePage.resetFilter();

        logger.info("Single Selection - Operating System (Android)");
    }

    @Test(description = "Multiple Selection - Operating System (Android, iPadOS)", priority = 4)
    public void multiple_Selection_OS() throws InterruptedException {
        basePage.selectFilter("Operating system", "Android", "iPadOS");

        SmallWait(1500);
        basePage.resetFilter();

        logger.info("Multiple Selection - Operating System (Android, iPadOS)");
    }

    @Test(description = "Select All Filters", priority = 5)
    public void all_Selection() throws InterruptedException {
        basePage.selectFilter("Deals", "New", "Special offer");
        basePage.selectFilter("Brands", "Apple", "Samsung", "TCL");
        basePage.selectFilter("Operating system", "Android", "iPadOS");

        SmallWait(1500);
        basePage.resetFilter();

        logger.info("Select All Filters");
    }

    @Test(description = "All option under a Category", priority = 6)
    public void all_SelectionUnder_A_Category() throws InterruptedException {
        basePage.selectFilter("Brands", "all");

        SmallWait(1500);
        basePage.resetFilter();

        logger.info("All option under a Category");
    }

    @Test(description = "Invalid Filter Category", priority = 7)
    public void invalid_Category() {
        basePage.selectFilter("Invalid Category", "New");

        logger.info("Invalid Filter Category");
    }

    @Test(description = "Invalid Filter Options", priority = 8)
    public void invalid_Option() {
        basePage.selectFilter("Deals", "Invalid Option");

        logger.info("Invalid Filter Options");
    }
}