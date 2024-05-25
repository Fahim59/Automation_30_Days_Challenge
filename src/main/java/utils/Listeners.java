package utils;

import base.BaseClass;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Listeners implements ITestListener {
    BaseClass baseClass = new BaseClass();

    private Workbook workbook;
    private Sheet sheet;
    private List<TestInfo> testInfos;
    private int rowIndex;

    private class TestInfo {
        private String methodName;
        private String result;

        public TestInfo(String methodName, String result) {
            this.methodName = methodName;
            this.result = result;
        }

        public String getMethodName() {
            return methodName;
        }

        public String getResult() {
            return result;
        }
    }

    private void createHeader() {
        rowIndex = 0;
        Row headerRow = sheet.createRow(rowIndex++);
        headerRow.createCell(0).setCellValue("Method Name");
        headerRow.createCell(1).setCellValue("Test Status");
        headerRow.createCell(2).setCellValue("Execution Data");
    }

    private void autoSizeColumn() {
        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    public void onTestFailure(ITestResult result) {
        if(result.getThrowable() != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            result.getThrowable().printStackTrace(pw);

            System.out.println(sw);
        }
        // ---------------- Screenshot ---------------- //

        //File file = baseClass.getDriver().getScreenshotAs(OutputType.FILE);
        File file = ((TakesScreenshot)baseClass.driver).getScreenshotAs(OutputType.FILE);

        byte[] encoded = null;
        try {
            encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }

        String imagePath = "Screenshots" + File.separator + baseClass.dateTime() + File.separator
                + result.getTestClass().getRealClass().getSimpleName() + File.separator + result.getName() + ".png";

        String completeImagePath = System.getProperty("user.dir") + File.separator + imagePath;

        try {
            FileUtils.copyFile(file, new File(completeImagePath));
            Reporter.log("This is the "+result.getName()+"'s failed screenshot");

            byte[] screenshotBytes = FileUtils.readFileToByteArray(new File(completeImagePath));
            String screenshotBase64 = Base64.encodeBase64String(screenshotBytes);

            Reporter.log("<a href='" + completeImagePath + "'> <img src='data:image/png;base64," + screenshotBase64 + "' height='250' width='250'/> </a>");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        Extent_Report.getTest().fail("Test Failed",
                MediaEntityBuilder.createScreenCaptureFromBase64String(new String(encoded, StandardCharsets.US_ASCII)).build());

        String methodName = result.getMethod().getMethodName();
        testInfos.add(new TestInfo(methodName, "Failed"));
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        workbook = new XSSFWorkbook();
        testInfos = new ArrayList<>();
    }

    @Override
    public void onTestStart(ITestResult result) {
        Extent_Report.startTest(result.getName(), result.getMethod().getDescription()).assignAuthor("Mustafizur Rahman");
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        sheet = workbook.createSheet("Test Results");
        createHeader();

        try (FileOutputStream outputStream = new FileOutputStream(new ConfigLoader().initializeProperty().getProperty("testResultFile"))) {
            for (int i = 0; i < testInfos.size(); i++) {
                Row row = sheet.createRow(i + 1);

                Cell methodNameCell = row.createCell(0);
                Cell resultCell = row.createCell(1);
                Cell dateCell = row.createCell(2);

                methodNameCell.setCellValue(testInfos.get(i).getMethodName());
                resultCell.setCellValue(testInfos.get(i).getResult());
                dateCell.setCellValue(currentDate);

                CellStyle cellStyle = workbook.createCellStyle();

                if (testInfos.get(i).getResult().equalsIgnoreCase("Passed")) {
                    cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
                }
                else if (testInfos.get(i).getResult().equalsIgnoreCase("Failed")) {
                    cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
                }

                resultCell.setCellStyle(cellStyle);
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            }
            autoSizeColumn();
            workbook.write(outputStream);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        Extent_Report.getReporter().flush();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        testInfos.add(new TestInfo(methodName, "Passed"));

        Extent_Report.getTest().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        Extent_Report.getTest().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) { }
}
