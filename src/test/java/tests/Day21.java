package tests;

import base.BaseClass;
import com.github.javafaker.Faker;
import org.apache.logging.log4j.*;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.BasePage;

import java.io.FileReader;

public class Day21 extends BaseClass {

    private static final Logger logger = LogManager.getLogger(Day21.class);

    private BasePage basePage;

    Faker faker = new Faker();

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

    @Test(description = "Create an Account with valid info", priority = 1, enabled = true)
    public void createAccountWithValidInfo() throws InterruptedException {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@hotmail.com";
        String password = details.getJSONObject("day21").getString("password");

        basePage.closeAd();

        basePage.clickAccountCreateLink();

        basePage.enterAccountDetails(firstName, lastName, email, password, password);

        basePage.clickCreateAccountBtn();

        SmallWait(1000);

        basePage.clickHomeBtn();

        logger.info("Account created with valid info");
    }

    @Test(description = "Add product into cart and verify", priority = 2)
    public void submitAnOrder() throws InterruptedException {
        basePage.clickBagsMenu();

        basePage.closeAd();

        Scroll_Down(300);

        basePage.clickProduct(details.getJSONObject("day21").getString("productName"));

        String name = basePage.getProductName();
        String price = basePage.getProductPrice();

        basePage.clickCartBtn();

        Assert.assertEquals(name, basePage.getProductNameField(), "Product Name did not match");
        Assert.assertEquals(price, basePage.getProductPriceField(), "Product Price did not match");

        basePage.clickProceedToCheckoutBtn();

        SmallWait(2000);

        basePage.enterShippingDetails(
                details.getJSONObject("day21").getJSONArray("billingDetails").getString(0),
                details.getJSONObject("day21").getJSONArray("billingDetails").getString(1),
                details.getJSONObject("day21").getJSONArray("billingDetails").getString(2),
                details.getJSONObject("day21").getJSONArray("billingDetails").getString(3),
                details.getJSONObject("day21").getJSONArray("billingDetails").getString(4),
                details.getJSONObject("day21").getJSONArray("billingDetails").getString(5)
        );

        SmallWait(2000);

        basePage.clickNextBtn();

        SmallWait(2000);

        basePage.clickPlaceOrderBtn();

        System.out.println(basePage.getConfirmMessage());

        logger.info("Order successfully submitted");
    }
}