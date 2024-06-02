package tests;

import base.BaseClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.*;
import pages.BasePage;
import utils.ConfigLoader;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Day13 extends BaseClass {

    private static final Logger logger = LogManager.getLogger(Day13.class);

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

    @Test(description = "Download PDF and store in local System", priority = 1)
    public void downloadAndStoreFile() {
        basePage.clickPdfLink();

        String currentUrl = driver.getCurrentUrl();

        try {
            URL url = new URL(currentUrl);
            String saveDirectory = new ConfigLoader().initializeProperty().getProperty("downloadFilePath");
            String fileName = currentUrl.substring(currentUrl.lastIndexOf("/") + 1);

            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            int responseCode = httpConn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = httpConn.getInputStream();
                BufferedInputStream reader = new BufferedInputStream(inputStream);

                FileOutputStream fos = new FileOutputStream(saveDirectory+ "\\" + fileName);

                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = reader.read(buffer, 0, 1024)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }

                fos.close();
                reader.close();
                System.out.println("File downloaded successfully");
            }
            else {
                System.out.println("No file to download. Server replied HTTP code: " +responseCode);
            }
            httpConn.disconnect();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("Download PDF and store in local System");
    }
}