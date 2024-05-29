package tests;

import base.BaseClass;
import org.apache.logging.log4j.*;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.BasePage;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class Day9 extends BaseClass {

    private static final Logger logger = LogManager.getLogger(Day9.class);

    private BasePage basePage;

    @BeforeMethod
    public void beforeMethod() {
        basePage = new BasePage(driver);
    }
    //-------------------------------------------------------//

    @Test(description = "Compare value from clipboard and input box", priority = 1)
    public void compareValue() throws IOException, UnsupportedFlavorException {
        basePage.clickGuidGenerator();
        basePage.clickCopyBtn();

        Assert.assertEquals(basePage.getInputFieldValue(), basePage.getClipBoardValue(),"Value Mismatched");

        logger.info("Using assertion compare the value from the clipboard and the value of the input field");
    }
}