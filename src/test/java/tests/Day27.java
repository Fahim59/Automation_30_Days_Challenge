package tests;

import base.BaseClass;
import org.apache.logging.log4j.*;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.*;
import pages.BasePage;

import java.io.FileReader;

public class Day27 extends BaseClass {

    private static final Logger logger = LogManager.getLogger(Day27.class);

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

    @Test(description = "Click on the Add to Cart button of the highest and lowest price product", priority = 1)
    public void clickHighestAndLowestProductButton() throws InterruptedException {
        basePage.login(details.getJSONObject("day27").getString("uid"),
                details.getJSONObject("day27").getString("pass"));

        Scroll_Down(150);

        basePage.clickHighestPriceProduct();

        basePage.clickLowestPriceProduct();

        logger.info("Click on the Add to Cart button of the highest and lowest price product");
    }
}