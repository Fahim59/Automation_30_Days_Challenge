package tests;

import base.BaseClass;
import org.apache.logging.log4j.*;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;
import pages.BasePage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Product {
    String name;
    double price;

    Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

public class Day22 extends BaseClass {

    private static final Logger logger = LogManager.getLogger(Day22.class);

    private BasePage basePage;

    @BeforeMethod
    public void beforeMethod() {
        basePage = new BasePage(driver);
    }
    //-------------------------------------------------------//

    @Test(description = "Sort the Product price from low to high", priority = 1)
    public void sortProductPrice() {
        List<Product> products = new ArrayList<>();

        basePage.clickAppleMenu();

        List<WebElement> count = driver.findElements(basePage.getAllItemsLocator());
        int countOfElements = count.size();

        System.out.println("The number of Products listed on the page is : " +countOfElements);

        List<WebElement> productNames = driver.findElements(basePage.getProductNameLocator());
        List<WebElement> productPrices = driver.findElements(basePage.getProductPriceLocator());

        for(int i = 0; i < countOfElements; i++){
            String productName = productNames.get(i).getText();
            String priceText = productPrices.get(i).getText();
            double productPrice = Double.parseDouble(priceText.replace("$", "").replace(",", ""));

            //System.out.println("Product Name: " +productName+ ", Product Price: " +productPrice);

            products.add(new Product(productName, productPrice));
        }

        Collections.sort(products, Comparator.comparingDouble(product -> product.price));

        System.out.println("\n" + "Sorted Products with its price from low to high -" + "\n");

        for (Product product : products) {
            System.out.println("Product Name: " +product.name+ ", Product Price: $" +product.price);
        }

        logger.info("Sort the Product price from low to high");
    }
}