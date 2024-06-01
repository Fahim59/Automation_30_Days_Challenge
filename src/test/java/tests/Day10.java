package tests;

import base.BaseClass;
import org.apache.logging.log4j.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.BasePage;
import utils.ConfigLoader;
import java.io.*;
import java.time.Duration;

public class Day10 extends BaseClass {

    private static final Logger logger = LogManager.getLogger(Day10.class);

    private BasePage basePage;

    @BeforeMethod
    public void beforeMethod() {
        basePage = new BasePage(driver);
    }
    //-------------------------------------------------------//

    @Test(description = "Download a pdf file and verify 'Get Tickets' text in that file", priority = 1)
    public void downloadPDFAndVerifyText() throws InterruptedException {
        basePage.clickDownloadBtn();

        SmallWait(5000);

        File latestFile = basePage.getLatestFileFromDir(new ConfigLoader().initializeProperty().getProperty("downloadFilePath"));

        FluentWait<File> wait = new FluentWait<>(latestFile)
                .withTimeout(Duration.ofMinutes(5)).pollingEvery(Duration.ofSeconds(5))
                .ignoring(Exception.class).withMessage("File is not downloaded yet");

        try {
            boolean fileDownload = wait.until(f -> f.exists() && f.canRead());
            if (fileDownload) {
                System.out.println("File downloaded successfully");

                System.out.println("The latest downloaded file is: " + latestFile.getName());
                System.out.println("The latest downloaded file Path: " + latestFile.getAbsolutePath());
                System.out.println("The latest downloaded file Size: " + latestFile.length() + " bytes");

                basePage.openFileInNewTab(driver, latestFile);

                boolean textExists = basePage.verifyTextInPDF(latestFile, "Get Tickets");

                Assert.assertTrue(textExists, "The text 'Get Tickets' does not exist in the downloaded PDF file.");
            }
        }
        catch (Exception e) {
            System.out.println("File is not downloaded successfully");
        }

        logger.info("Download a pdf file and verify 'Get Tickets' text in that file");
    }
}