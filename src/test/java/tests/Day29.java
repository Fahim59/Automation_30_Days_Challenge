package tests;

import base.BaseClass;
import org.apache.logging.log4j.*;
import org.testng.annotations.*;
import pages.BasePage;

public class Day29 extends BaseClass {

    private static final Logger logger = LogManager.getLogger(Day29.class);

    private BasePage basePage;

    @BeforeMethod
    public void beforeMethod() {
        basePage = new BasePage(driver);
    }
    //-------------------------------------------------------//

    @Test(description = "Drag capitals to respective countries", priority = 1)
    public void dragCapitalToRespectiveCountry() {
        basePage.dragCapitalToRespectiveCountry();
        basePage.moveCapitalToLeftSection();

        logger.info("Drag capitals to respective countries");
    }

    @Test(description = "Drag capitals to wrong countries", priority = 2)
    public void dragCapitalToWrongCountry() {
        basePage.dragCapitalToWrongCountry();
        basePage.moveCapitalToLeftSection();

        logger.info("Drag capitals to wrong countries");
    }

    @Test(description = "Drag capitals to respective and wrong countries", priority = 3)
    public void dragCapitalToRespective_WrongCountry() {
        basePage.dragCapitalToRespective_WrongCountry();
        basePage.moveCapitalToLeftSection();

        logger.info("Drag capitals to respective and wrong countries");
    }
}