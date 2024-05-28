package tests;

import base.BaseClass;
import org.apache.logging.log4j.*;
import org.json.*;
import org.testng.annotations.*;
import pages.BasePage;

import java.io.FileReader;

public class Day8 extends BaseClass {

    private static final Logger logger = LogManager.getLogger(Day8.class);

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

    @Test(description = "Drag and Drop Items into their corresponding spots", priority = 1)
    public void dragAndDropItems() {
        JSONArray jsonArray = details.getJSONObject("day8").getJSONArray("expectedOrder");
        String[] expectedOrder = new String[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            expectedOrder[i] = jsonArray.getString(i);
        }

        basePage.dragAndDrop(expectedOrder);
        basePage.clickCheckOrderBtn();

        basePage.verifyOrder(expectedOrder);

        logger.info("Drag and Drop Items into their corresponding spots");
    }
}