package tests;

import base.BaseClass;
import com.github.javafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.BasePage;

import java.io.FileReader;

public class Day11 extends BaseClass {

    private static final Logger logger = LogManager.getLogger(Day11.class);

    Faker faker = new Faker();

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

    @Test(description = "Add and Remove Tags then assert tag's presence and count", priority = 1)
    public void addAndRemoveTags() {
        System.out.println("Tags remaining: " +basePage.getTagCount());

        basePage.clickRemoveBtn();

        for(int i = 0; i <= 10; i++){
            basePage.enterTags(faker.programmingLanguage().name());
        }

        Assert.assertEquals("0", basePage.getTagCount());
        System.out.println("Tags remaining: " +basePage.getTagCount());

        basePage.clickRemoveBtn();

        basePage.enterTags(details.getJSONObject("day11").getString("tag"));

        System.out.println("Tags Text: " +basePage.getTagText());

        logger.info("Add and Remove Tags then assert tag's presence and count");
    }
}