package tests;

import base.BaseClass;
import com.github.javafaker.Faker;
import org.apache.logging.log4j.*;
import org.json.JSONObject;
import org.testng.annotations.*;
import pages.BasePage;
import utils.ConfigLoader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Day15 extends BaseClass {

    private static final Logger logger = LogManager.getLogger(Day15.class);
    private static final String API_URL = "https://randomuser.me/api/";

    Faker faker = new Faker();

    private BasePage basePage;

    JSONObject apiData;

    @BeforeClass
    public void beforeClass() throws Exception {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        apiData = new JSONObject(result.toString());
    }

    @BeforeMethod
    public void beforeMethod() {
        basePage = new BasePage(driver);
    }
    //-------------------------------------------------------//

    @Test(description = "Fill up form using API data", priority = 1)
    public void fillUpForm() throws InterruptedException {
        JSONObject results = apiData.getJSONArray("results").getJSONObject(0);

        String firstName = results.getJSONObject("name").getString("first");
        String lastName = results.getJSONObject("name").getString("last");

        String phoneNumber = faker.phoneNumber().phoneNumber();
        String country = faker.address().country();
        String city = faker.address().city();
        String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@hotmail.com";
        String gender = "Radio-0";
        String day = "CheckBox-1";
        String time = "Afternoon";
        String path = new ConfigLoader().initializeProperty().getProperty("testResultFile");

        basePage.enterDetails(firstName,lastName,phoneNumber,country,city,email, gender, day, time);

        basePage.uploadFile(path);

        //basePage.clickSubmitBtn();

        logger.info("Fill up form using API data");
    }
}