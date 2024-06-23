package tests;

import base.BaseClass;
import org.apache.logging.log4j.*;
import org.testng.annotations.*;
import pages.BasePage;

public class Day25 extends BaseClass {

    private static final Logger logger = LogManager.getLogger(Day25.class);

    int second = 1;

    private BasePage basePage;

    @BeforeMethod
    public void beforeMethod() {
        basePage = new BasePage(driver);
    }
    //-------------------------------------------------------//

    @Test(description = "Count current world population data", priority = 1)
    public void countWorldPopulation() throws InterruptedException {
        Scroll_Down(300);

        while(true){
            basePage.getCurrentYearPopulation();

            System.out.println("----- Current World Population, Today's Data -----");
            basePage.getBirthToday();
            basePage.getDeathToday();
            basePage.getGrowthToday();

            System.out.println("----- Current World Population, This Year's Data -----");
            basePage.getBirthThisYear();
            basePage.getDeathThisYear();
            basePage.getGrowthThisYear();

            System.out.println("=======================================================");
            SmallWait(500);
            second++;

            if (second > 20) { break; }
        }

        logger.info("Count current world population data");
    }
}