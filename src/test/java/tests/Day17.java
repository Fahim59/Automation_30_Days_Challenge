package tests;

import base.BaseClass;
import constants.EndPoint;
import org.apache.logging.log4j.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import pages.BasePage;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Day17 extends BaseClass {

    private static final Logger logger = LogManager.getLogger(Day17.class);

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
    public static void setCoordinates(double latitude, double longitude, int accuracy) {
        Map<String, Object> coordinates = new HashMap<>();

        coordinates.put("latitude", latitude);
        coordinates.put("longitude", longitude);
        coordinates.put("accuracy", accuracy);

        ((ChromeDriver) driver).executeCdpCommand("Emulation.setGeolocationOverride", coordinates);
    }

    @Test(description = "Verify KFC locations based on Geo location", priority = 1)
    public void verifyLocations() throws InterruptedException {
        JSONArray jsonArray = details.getJSONObject("day17").getJSONArray("coordinates1");

        double latitude = jsonArray.getDouble(0);
        double longitude = jsonArray.getDouble(1);
        int accuracy = jsonArray.getInt(2);

        setCoordinates(latitude, longitude, accuracy);

        Open_Website(EndPoint.DAY17.url);

        basePage.clickMyLocationBtn();

        SmallWait(3000);

        basePage.printLocationNames();

        logger.info("Verify KFC locations based on Geo location");
    }
}