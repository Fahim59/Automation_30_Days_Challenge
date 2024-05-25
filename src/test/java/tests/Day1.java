package tests;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.HasAuthentication;
import org.openqa.selenium.UsernameAndPassword;
import org.testng.Assert;
import pages.*;
import org.apache.logging.log4j.*;
import org.json.*;
import org.testng.annotations.*;

import java.io.FileReader;
import java.net.URI;
import java.util.function.Predicate;

public class Day1 extends BaseClass {

    private static final Logger logger = LogManager.getLogger(Day1.class);

    private BasePage basePage;

    FileReader data;
    JSONObject products;

    @BeforeClass
    public void beforeClass() throws Exception {
        try {
            String file = "src/main/resources/data.json";
            data = new FileReader(file);

            JSONTokener tokener = new JSONTokener(data);
            products = new JSONObject(tokener);
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

    @Test(description = "", priority = 1)
    public void got_to_home_page() {
        Predicate<URI> predicatURI = uri -> uri.getHost().contains("the-internet.herokuapp.com");
        ((HasAuthentication)driver).register(predicatURI, UsernameAndPassword.of("admin", "admin"));



        //logger.info("User go to the NopCommerce Home page");

        //Open_Website(EndPoint.DAY1.url);

        //driver.get("https://the-internet.herokuapp.com");

        driver.findElement(By.linkText("Basic Auth")).click();

        Assert.assertEquals(basePage.verifyMessage(),"Congratulations! You must have the proper credentials.");
    }
}