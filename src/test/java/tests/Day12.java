package tests;

import base.BaseClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.BasePage;
import java.util.HashMap;
import java.util.Map;

class PageInfo {
    String title;
    int numLinks;

    PageInfo(String title, int numLinks) {
        this.title = title;
        this.numLinks = numLinks;
    }
}

public class Day12 extends BaseClass {

    private static final Logger logger = LogManager.getLogger(Day12.class);
    static HashMap<String, PageInfo> pageInfoMap;

    private BasePage basePage;

    @BeforeClass
    public void beforeClass() throws Exception {
        pageInfoMap = new HashMap<>();
    }

    @BeforeMethod
    public void beforeMethod() {
        basePage = new BasePage(driver);
    }
    //-------------------------------------------------------//

    @Test(description = "Verify link count calculation and identification of page with maximum links", priority = 1)
    public void identifyPageWithMaxLinks() {
        String[] urls = {
                "https://www.lambdatest.com/blog/selenium-best-practices-for-web-testing/",
                "https://www.ministryoftesting.com/articles/websites-to-practice-testing",
                "https://naveenautomationlabs.com/opencart/",
                "https://demo.guru99.com/"
        };

        for (String url : urls) {
            Open_Website(url);

            String currentUrl = driver.getCurrentUrl();
            String pageTitle = driver.getTitle();

            pageInfoMap.put(currentUrl, new PageInfo(pageTitle, basePage.getLinkCount()));
        }

        for (Map.Entry<String, PageInfo> entry : pageInfoMap.entrySet()) {
            System.out.println("URL: " + entry.getKey());
            System.out.println("Title: " + entry.getValue().title);
            System.out.println("Number of Links: " + entry.getValue().numLinks);
            System.out.println();
        }

        Map.Entry<String, PageInfo> maxEntry = null;
        for (Map.Entry<String, PageInfo> entry : pageInfoMap.entrySet()) {
            if (maxEntry == null || entry.getValue().numLinks > maxEntry.getValue().numLinks) {
                maxEntry = entry;
            }
        }

        if (maxEntry != null) {
            System.out.println("Page with Maximum Links: " + maxEntry.getValue().title + " - " + maxEntry.getValue().numLinks + " links");
        }

        Assert.assertNotNull(maxEntry, "Max entry should not be null");
        Assert.assertTrue(maxEntry.getValue().numLinks > 0, "Max number of links should be greater than 0");

        logger.info("Verify link count calculation and identification of page with maximum links");
    }
}