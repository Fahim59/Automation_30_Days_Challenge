package pages;

import base.BaseClass;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import org.testng.Assert;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.*;
import java.awt.datatransfer.*;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class BasePage extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    /**
     * Day 1
    */
    private final By basicAuthOption = By.linkText("Basic Auth");
    private final By confirmMsg = By.xpath("//p[contains(text(),'Congratulations! You must have the proper credential')]");

    public void clickBasicAuth(){
        click_Element(basicAuthOption);
    }

    public String verifyMessage(){
        return get_Text(confirmMsg);
    }

    /**
     * Day 2
    */
    private final By passField = By.id("pass");
    private final By passNewField = By.id("passnew");

    public void enterPassword(String pass){
        write_JS_Executor(passField, pass);
    }

    public void enterPasswordRemovingAttribute(String pass){
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("document.getElementById('passnew').removeAttribute('disabled')");

        write_Send_Keys(passNewField, pass);
    }

    /**
     * Day 3
    */
    private final By ratingField = By.cssSelector(".star-rating");

    private final By ratingTextField = By.xpath("//input[@id='txt_rating']");

    private final By checkRatingBtn = By.xpath("//button[@id='check_rating']");

    private final By validateRatingMsg = By.xpath("//span[@id='validate_rating']");

    public String getContentOfRatingField(){
        WebElement element = wait_for_presence(ratingField);

        String script = "return window.getComputedStyle(arguments[0], '::after').getPropertyValue('content');";
        String pseudoContent = (String) js.executeScript(script, element);

        if (pseudoContent != null && pseudoContent.length() > 1) {
            pseudoContent = pseudoContent.substring(1, pseudoContent.length() - 1);
        }

        return pseudoContent;
    }

    public void enterRating(String rating){
        write_Send_Keys(ratingTextField, rating);
    }

    public void clickRatingButton(){
        click_Element(checkRatingBtn);
    }

    public String getValidationMsg(){
        return get_Text(validateRatingMsg);
    }

    /**
     * Day 4
    */
    private final By clickBtn = By.xpath("//button[@id='growbutton']");

    private final By getTriggerMsg = By.xpath("//p[@id='growbuttonstatus']");

    public void clickOnButton(){
        WebElement button = wait_for_visibility(clickBtn);
        wait.until(ExpectedConditions.attributeContains(button, "class", "grown"));

        if (button.getAttribute("class").contains("grown")) {
            click_Element(clickBtn);
        }
    }

    public String getTriggerMsg(){
        return get_Text(getTriggerMsg);
    }

    /**
     * Day 5
    */
    private final By inputField = By.xpath("(//input[@type='number'])");

    private final By successMsg = By.xpath("//small[@class='info success']");

    public int getInputFieldSize() {
        return get_Size(inputField);
    }

    public void enterCode(){
        Actions actions = new Actions(driver);

        Runnable pressKeyUpNineTimesAndTab = () -> {
            for (int i = 0; i < 9; i++) {
                actions.sendKeys(Keys.ARROW_UP).perform();
            }
            actions.sendKeys(Keys.TAB).perform();
        };

        pressKeyUpNineTimesAndTab.run();
    }

    public String getSuccessMsg(){
        return get_Text(successMsg);
    }

    /**
     * Day 6
    */
    private final By startBtn = By.xpath("//button[@id='startButton']");

    private final By stopBtn = By.xpath("//button[@id='stopButton']");

    public void clickStartBtn(){
        click_Element(startBtn);
    }

    public void clickStopBtn(int value){
        String script = "return document.querySelector('.progress-bar').style.width;";

        while (true) {
            String progressValue = (String) js.executeScript(script);

            int progress = Integer.parseInt(progressValue.replace("%", ""));

            if (progress == value) {
                click_Element(stopBtn);
                break;
            }
        }
    }

    /**
     * Day 7
    */
    private final By textField = By.xpath("//p[@id='msg']");
    private final By shareBtn = By.xpath("//span[normalize-space()='Share']");

    private final By twitterBtn = By.xpath("//span[normalize-space()='Twitter']");
    private final By twitterMsg = By.xpath("//*[text()='Menu item Twitter clicked']");

    private final By instagramBtn = By.xpath("//span[normalize-space()='Instagram']");
    private final By instagramMsg = By.xpath("//*[text()='Menu item Instagram clicked']");

    private final By dribbleBtn = By.xpath("//span[normalize-space()='Dribble']");
    private final By dribbleMsg = By.xpath("//*[text()='Menu item Dribble clicked']");

    private final By telegramBtn = By.xpath("//span[normalize-space()='Telegram']");
    private final By telegramMsg = By.xpath("//*[text()='Menu item Telegram clicked']");

    public BasePage clickTextField(){
        WebElement element = wait_for_visibility(textField);
        actions.contextClick(element).perform();

        return this;
    }
    public void clickShareBtn(){
        WebElement element = wait_for_visibility(shareBtn);
        actions.moveToElement(element).perform();
    }

    public void clickTwitterBtn(){
        click_Element(twitterBtn);
    }
    public String getTwitterMsg(){
        return get_Text(twitterMsg);
    }

    public void clickInstagramBtn(){
        click_Element(instagramBtn);
    }
    public String getInstagramMsg(){
        return get_Text(instagramMsg);
    }

    public void clickDribbleBtn(){
        click_Element(dribbleBtn);
    }
    public String getDribbleMsg(){
        return get_Text(dribbleMsg);
    }

    public void clickTelegramBtn(){
        click_Element(telegramBtn);
    }
    public String getTelegramMsg(){
        return get_Text(telegramMsg);
    }

    public void openMenu(String option, String actualMsg){
        clickTextField().clickShareBtn();

        switch (option) {
            case "twitter":
                clickTwitterBtn();
                Assert.assertEquals(actualMsg, getTwitterMsg());
                break;

            case "instagram":
                clickInstagramBtn();
                Assert.assertEquals(actualMsg, getInstagramMsg());
                break;

            case "dribble":
                clickDribbleBtn();
                Assert.assertEquals(actualMsg, getDribbleMsg());
                break;

            case "telegram":
                clickTelegramBtn();
                Assert.assertEquals(actualMsg, getTelegramMsg());
                break;

            default: throw new IllegalStateException("INVALID SOCIAL MEDIA OPTION: " +option);
        }
    }

    /**
     * Day 8
    */
    private final By checkOrderBtn = By.xpath("(//button[normalize-space()='Check Order'])");

    public void dragAndDrop(String [] expectedOrder){
        for (int i = 0; i < expectedOrder.length; i++) {
            WebElement sourceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='person-name' and contains(text(),'"+expectedOrder[i]+"')]")));
            WebElement targetElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='number' and contains(text(),'"+(i+1)+"')]")));

            actions.dragAndDrop(sourceElement, targetElement).perform();
        }
    }

    public void verifyOrder(String [] expectedOrder){
        for (int i = 0; i < expectedOrder.length; i++) {
            WebElement item = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@id='draggable-list']/li[" +(i+1)+ "]//p[@class='person-name']")));
            String actualName = item.getText();

            Assert.assertTrue(actualName.contains(expectedOrder[i]), "Error: Person at position " +(i+1)+ " is " +actualName+ " instead of " +expectedOrder[i]);
        }
    }

    public void clickCheckOrderBtn(){
        click_Element(checkOrderBtn);
    }

    /**
     * Day 9
    */
    public SearchContext shadowRoot(){
        WebElement shadowHost = driver.findElement(By.tagName("guid-generator"));
        return (SearchContext) js.executeScript("return arguments[0].shadowRoot", shadowHost);
    }

    public void clickGuidGenerator(){
        WebElement guidGenerator = shadowRoot().findElement(By.id("buttonGenerate"));
        guidGenerator.click();

        System.out.println("GUID Clicked");
    }

    public void clickCopyBtn(){
        WebElement copyBtn = shadowRoot().findElement(By.id("buttonCopy"));
        copyBtn.click();

        System.out.println("Copy Clicked");
    }

    public String getInputFieldValue(){
        WebElement inputField = shadowRoot().findElement(By.id("editField"));
        return (String) js.executeScript("return arguments[0].value;", inputField);
    }

    public String getClipBoardValue() throws IOException, UnsupportedFlavorException {
        return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
    }

    /**
     * Day 10
    */
    private final By downloadBtn = By.xpath("//a[@type='button']");

    public void clickDownloadBtn() throws InterruptedException {
        Scroll_Down();
        click_Element(downloadBtn);
    }

    public File getLatestFileFromDir(String dirPath) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
            return files[0];
        }
        return null;
    }

    public void openFileInNewTab(WebDriver driver, File file) {
        try {
            String fileUrl = "file:///" + file.getAbsolutePath().replace("\\", "/");
            System.out.println("Opening file URL: " + fileUrl);

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.open();");

            String currentHandle = driver.getWindowHandle();
            for (String handle : driver.getWindowHandles()) {
                if (!handle.equals(currentHandle)) {
                    driver.switchTo().window(handle);
                    break;
                }
            }

            driver.get(fileUrl);
        }
        catch (Exception e) {
            System.out.println("Failed to open the file in a new tab: " + e.getMessage());
        }
    }

    public boolean verifyTextInPDF(File pdfFile, String searchText) throws IOException {
        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();

            String text = pdfStripper.getText(document);
            return text.contains(searchText);
        }
    }

    /**
     * Day 11
    */
    private final By tagCount = By.cssSelector("div[class='details'] p span");

    private final By removeBtn = By.cssSelector("div[class='details'] button");

    private final By tagField = By.cssSelector("input[type='text']");

    private final By tagText = By.cssSelector("li:nth-child(1)");

    public String getTagCount(){
        return get_Text(tagCount);
    }

    public void clickRemoveBtn() {
        click_Element(removeBtn);
    }

    public void enterTags(String tag){
        WebElement element = wait_for_visibility(tagField);
        element.sendKeys(tag + Keys.ENTER);
    }

    public String getTagText(){
        return get_Text(tagText);
    }

    /**
     * Day 12
    */
    private final By links = By.tagName("a");

    public int getLinkCount() {
        return get_Size(links);
    }

    /**
     * Day 13
    */
    private final By pdfLink = By.linkText("Download the printable PDF of Selenium cheat sheet");

    public void clickPdfLink() {
        click_Element(pdfLink);

        String currentHandle = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(currentHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }
    }

    /**
     * Day 14
    */
    private final By acceptCookiesBtn = By.xpath("//*[@role='button' and contains(text(),'Accept All Cookies')]");

    private final By departureField = By.xpath("(//input[@type='text'])[1]");
    private final By destinationField = By.xpath("(//input[@type='text'])[2]");

    private final By departDateField = By.xpath("(//input[contains(@id,'date-picker')])[1]");
    private final By returnDateField = By.xpath("(//input[contains(@id,'date-picker')])[2]");

    private final By searchFlightBtn = By.xpath("(.//*[text()='Search flights'])[1]");
    private final By continueSearchFlightBtn = By.xpath("//span[normalize-space()='Continue to flight results']");

    private final By searchText = By.xpath("//h2[contains(normalize-space(),'No flights have been found for your search criteria')]");

    private final By errorText = By.xpath("(//jb-error[@id])[1]");

    public void clickCookiesBtn() {
        WebElement iframe = driver.findElement(By.xpath("//iframe[@name='trustarc_cm']"));
        driver.switchTo().frame(iframe);

        click_Element_Js(acceptCookiesBtn);

        driver.switchTo().defaultContent();
    }

    public void enterDepartureCity(String city){
        write_Send_Keys(departureField, city);

        WebElement destination = wait_for_visibility(By.xpath("//strong[normalize-space()='"+city+"']"));
        destination.click();
    }
    public void enterDestinationCity(String city){
        write_Send_Keys(destinationField, city);

        WebElement destination = wait_for_visibility(By.xpath("//strong[normalize-space()='"+city+"']"));
        destination.click();
    }

    public void enterDepartDate(String date){
        click_Element(departDateField);
        write_Send_Keys(departDateField, date + Keys.ENTER);
    }

    public void enterReturnDate(String date){
        click_Element(returnDateField);
        write_Send_Keys(returnDateField, date + Keys.ENTER);
    }

    public void clickSearchFlightBtn() {
        click_Element_Js(searchFlightBtn);
        click_Element(continueSearchFlightBtn);
    }

    public void verifySearchText() {
        WebElement element = wait_for_visibility(searchText);
        if(element.getText().contains("No flights have been found")){
            System.out.println("No Flight is available");
        }
        else{
            System.out.println("Flight available");
        }
    }

    public void verifyErrorText() {
        WebElement element = wait_for_visibility(errorText);
        if(element.getText().contains("Choose date after")){
            System.out.println("No Flight is available");
        }
        else{
            System.out.println("Flight available");
        }
    }

    /**
     * Day 15
    */
    private final By firstNameField = By.cssSelector("#RESULT_TextField-1");
    private final By lastNameField = By.cssSelector("#RESULT_TextField-2");

    private final By phoneField = By.cssSelector("#RESULT_TextField-3");
    private final By countryField = By.cssSelector("#RESULT_TextField-4");
    private final By cityField = By.cssSelector("#RESULT_TextField-5");
    private final By emailField = By.cssSelector("#RESULT_TextField-6");

    private final By genderField = By.name("RESULT_RadioButton-7");
    private final By dayField = By.name("RESULT_CheckBox-8");
    private final By timeField = By.name("RESULT_RadioButton-9");

    private final By fileUploadField = By.name("RESULT_FileUpload-10");

    private final By submitBtn = By.xpath("//input[@name='Submit']");

    public BasePage enterFirstName(String firstName){
        write_Send_Keys(firstNameField, firstName);
        return this;
    }
    public BasePage enterLastName(String lastName){
        write_Send_Keys(lastNameField, lastName);
        return this;
    }

    public BasePage enterPhone(String phone){
        write_Send_Keys(phoneField, phone);
        return this;
    }
    public BasePage enterCountry(String country){
        write_Send_Keys(countryField, country);
        return this;
    }
    public BasePage enterCity(String city){
        write_Send_Keys(cityField, city);
        return this;
    }
    public BasePage enterEmail(String email){
        write_Send_Keys(emailField, email);
        return this;
    }

    public BasePage selectGender(String gender){
        click_Radio_Element(genderField, gender);
        return this;
    }
    public BasePage selectDay(String day){
        click_Radio_Element(dayField, day);
        return this;
    }
    public BasePage selectTime(String time){
        select_Dropdown_Element(timeField, time);
        return this;
    }

    public void uploadFile(String path) throws InterruptedException {
        upload_file(fileUploadField, path);
    }

    public void clickSubmitBtn() {
        click_Element_Js(submitBtn);
    }

    public BasePage enterDetails(String firstName, String lastName, String phone, String country, String city, String email,
                                 String gender, String day, String time){
        return enterFirstName(firstName).enterLastName(lastName).enterPhone(phone).enterCountry(country).
                enterCity(city).enterEmail(email).selectGender(gender).selectDay(day).selectTime(time);
    }

    /**
     * Day 16
    */
    private final By posterField = By.xpath("//img[@class='poster']");
    private final By priceTextField = By.xpath("//p[@class='current-price']");

    public void hoverOnPoster() {
        WebElement poster = wait_for_visibility(posterField);
        actions.moveToElement(poster).perform();
    }

    public String fetchPrice(){
        return get_Text(priceTextField);
    }

    /**
     * Day 17
    */
    private final By myLocationBtn = By.xpath("//button[@data-ya-track='geolocate']");
    private final By locationName = By.xpath("//span[@class='LocationName-geo']");
    private final By noLocationMsg = By.xpath("//div[@class='Locator-noResults']");

    public void clickMyLocationBtn(){
        click_Element(myLocationBtn);
    }

    public void printLocationNames(){
        try{
            WebElement locations = wait_for_visibility(locationName);

            if(locations.isDisplayed()){
                List<WebElement> locationNames = wait_for_presence_list(locationName);

                for (WebElement location : locationNames) {
                    System.out.println("KFC " + location.getText() + "\n");
                }
            }
        }
        catch (Exception e) {
            if(get_Text(noLocationMsg).contains("Sorry")) {
                System.out.println("No locations found for the given geolocation.");
            }
        }
    }

    /**
     * Day 18
    */
    private final By buttonText = By.xpath("//p[@id='info']");
    private final By foundMeBtn = By.xpath("//a[@id='fugitive']");

    public void scrollToBtn() {
        WebElement button = wait_for_visibility(foundMeBtn);
        js.executeScript("arguments[0].scrollIntoView();", button);
    }

    public void clickFoundMeBtn() {
        click_Element(foundMeBtn);
    }

    public void scrollToText() {
        WebElement text = wait_for_visibility(buttonText);
        js.executeScript("arguments[0].scrollIntoView();", text);
    }

    public String getBtnText(){
        return get_Text(buttonText);
    }

    /**
     * Day 19
    */
    public void clickStarRating(int count){
        WebElement element = driver.findElement(By.xpath("//label[@for='star-"+count+"']"));
        element.click();
    }
    public boolean checkEmojiVisibility(int count){
        WebElement element = driver.findElement(By.cssSelector("img[src='emojis/emoji-"+count+".png']"));
        return element.isDisplayed();
    }

    public String getMessage(){
        WebElement element = driver.findElement(By.className("text"));

        String script = "return window.getComputedStyle(arguments[0], '::before').getPropertyValue('content')";
        String pseudoContent = (String) js.executeScript(script, element);
        pseudoContent = pseudoContent.replaceAll("^\"|\"$", "");

        return pseudoContent;
    }
    public String getNumber(){
        WebElement element = driver.findElement(By.className("numb"));

        String script = "return window.getComputedStyle(arguments[0], '::before').getPropertyValue('content')";
        String pseudoContent = (String) js.executeScript(script, element);
        pseudoContent = pseudoContent.replaceAll("^\"|\"$", "");

        return pseudoContent;
    }

    /**
     * Day 20
    */
    private final By inputTextField = By.xpath("//input[@placeholder='Enter text or URL']");
    private final By generateBtn = By.xpath("//button[normalize-space()='Generate QR Code']");

    private final By qrCodeField = By.xpath("//img[@alt='qr-code']");

    public void enterText(String text){
        write_Send_Keys(inputTextField, text);
    }

    public void clickQRGenerator(){
        click_Element(generateBtn);
    }

    public String getQRCode(){
        WebElement qrCode = wait_for_visibility(qrCodeField);
        String url = qrCode.getAttribute("src");
        String content = "";

        if(qrCode.isDisplayed()){
            content = decodeQRCode(url);
        }
        return content;
    }

    private static String decodeQRCode(Object qrCodeImage) {
        Result result;
        BufferedImage bufferedImage = null;

        try {
            if (qrCodeImage instanceof String && ((String) qrCodeImage).contains("http")) {
                bufferedImage = ImageIO.read(new URL((String) qrCodeImage));
            }
            else if (qrCodeImage instanceof BufferedImage) {
                bufferedImage = (BufferedImage) qrCodeImage;
            }
            else {
                throw new IllegalArgumentException("Unsupported input type for qrCodeImage");
            }

            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            result = new MultiFormatReader().decode(bitmap);

        }
        catch (IOException | NotFoundException | com.google.zxing.NotFoundException e) {
            throw new RuntimeException(e);
        }

        return result.getText();
    }

    /**
     * Day 21
    */
    private final By adField = By.xpath("//div[@class='ea-stickybox-hide']");

    private final By accountCreateLink = By.linkText("Create an Account");

    private final By firstName = By.cssSelector("#firstname");
    private final By lastName = By.cssSelector("#lastname");
    private final By emailAddress = By.cssSelector("#email_address");
    private final By passwordField = By.cssSelector("#password");
    private final By confirmPasswordField = By.cssSelector("#password-confirmation");

    private final By createAccountBtn = By.cssSelector("button[title='Create an Account'] span");

    private final By homeBtn = By.xpath("//a[@aria-label='store logo']//img");

    private final By gearMenu = By.xpath("//span[normalize-space()='Gear']");
    private final By bagsMenu = By.xpath("(//span[contains(text(),'Bags')])[1]");

    private final By allProducts = By.xpath("//a[@class='product-item-link']");

    private final By productName = By.xpath("//span[@itemprop='name']");
    private final By price = By.cssSelector("span[id='product-price-7'] span[class='price']");

    private final By cartBtn = By.xpath("//span[normalize-space()='Add to Cart']");

    private final By checkoutBtn = By.xpath("//a[normalize-space()='shopping cart']");

    private final By productNameField = By.cssSelector("td[class='col item'] div[class='product-item-details'] a");
    private final By priceField = By.cssSelector("td[class='col price'] span[class='price']");

    private final By proceedToCheckoutBtn = By.xpath("//span[normalize-space()='Proceed to Checkout']");

    private final By streetAddressField = By.xpath("//input[@name='street[0]']");
    private final By city_Field = By.xpath("//input[@name='city']");
    private final By state_Field = By.name("region_id");
    private final By zip_Field = By.xpath("//input[@name='postcode']");
    private final By phone_Field = By.xpath("//input[@name='telephone']");
    private final By shippingType = By.xpath("//input[@type='radio']");

    private final By nextBtn = By.xpath("//span[normalize-space()='Next']");
    private final By placeOrderBtn = By.xpath("//span[normalize-space()='Place Order']");

    private final By confirmMessage = By.xpath("(.//*[text()='Thank you for your purchase!'])[1]");

    public void closeAd(){
        click_Element(adField);
    }

    public void clickAccountCreateLink(){
        click_Element(accountCreateLink);
    }

    public BasePage enterFirst_Name(String fname){
        write_Send_Keys(firstName, fname);
        return this;
    }
    public BasePage enterLast_Name(String lname){
        write_Send_Keys(lastName, lname);
        return this;
    }
    public BasePage enterEmailAddress(String email){
        write_Send_Keys(emailAddress, email);
        return this;
    }
    public BasePage enterPass(String password){
        write_Send_Keys(passwordField, password);
        return this;
    }
    public BasePage enterConfirmPass(String password){
        write_Send_Keys(confirmPasswordField, password);
        return this;
    }

    public BasePage enterAccountDetails(String fname, String lname, String email, String password, String cpassword){
        return enterFirst_Name(fname).enterLast_Name(lname).enterEmailAddress(email).enterPass(password).enterConfirmPass(cpassword);
    }

    public void clickCreateAccountBtn(){
        click_Element(createAccountBtn);
    }

    public void clickHomeBtn(){
        click_Element(homeBtn);
    }

    public void clickBagsMenu(){
        hover_And_Click(gearMenu, bagsMenu);
    }

    public int getProductsCount() {
        return get_Size(allProducts);
    }

    public void clickProduct(String product){
        for(int i = 0; i < getProductsCount(); i++){
            String productName = driver.findElements(allProducts).get(i).getText();

            if(productName.equalsIgnoreCase(product)){
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='"+product+"']"))).click();
                break;
            }
        }
    }

    public String getProductName(){
        return get_Text(productName);
    }
    public String getProductPrice(){
        return get_Text(price);
    }

    public void clickCartBtn(){
        click_Element(cartBtn);

        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(checkoutBtn));
        element.click();
    }

    public String getProductNameField(){
        return get_Text(productNameField);
    }
    public String getProductPriceField(){
        return get_Text(priceField);
    }

    public void clickProceedToCheckoutBtn(){
        click_Element_Js(proceedToCheckoutBtn);
    }

    public BasePage enterStreetAddress(String address){
        write_Send_Keys(streetAddressField, address);
        return this;
    }
    public BasePage enter_City(String city){
        write_Send_Keys(city_Field, city);
        return this;
    }
    public BasePage selectState(String state){
        select_Dropdown_Element(state_Field, state);
        return this;
    }
    public BasePage enterZip(String zip){
        write_Send_Keys(zip_Field, zip);
        return this;
    }
    public BasePage enter_Phone(String phone){
        write_Send_Keys(phone_Field, phone);
        return this;
    }
    public BasePage select_ShippingType(String type){
        click_Radio_Element(shippingType, type);
        return this;
    }

    public BasePage enterShippingDetails(String address, String city, String state, String zip, String phone, String type){
        return enterStreetAddress(address).enter_City(city).selectState(state).enterZip(zip).
                enter_Phone(phone).select_ShippingType(type);
    }

    public void clickNextBtn(){
        click_Element(nextBtn);
    }

    public void clickPlaceOrderBtn(){
        click_Element_Js(placeOrderBtn);
    }

    public String getConfirmMessage(){
        return get_Text(confirmMessage);
    }

    /**
     * Day 22
    */
    private final By megaMenu = By.xpath("//span[normalize-space()='Mega Menu']");
    private final By appleMenu = By.xpath("//a[normalize-space()='Apple']");

    private final By allItems = By.xpath("(//div[@class='product-layout product-grid no-desc col-xl-4 col-lg-4 col-md-4 col-sm-6 col-6'])");

    private final By product_Name = By.xpath("(//a[@class='text-ellipsis-2'])");
    private final By product_Price = By.xpath("(//span[@class='price-new'])");

    public void clickAppleMenu(){
        hover_And_Click(megaMenu, appleMenu);
    }

    public By getAllItemsLocator() {
        return allItems;
    }
    public By getProductNameLocator() {
        return product_Name;
    }
    public By getProductPriceLocator() {
        return product_Price;
    }

    /**
     * Day 23
    */
    private final By redirectionBtn = By.xpath("//a[normalize-space()='Start Redirection chain']");

    private final By goBackBtn = By.xpath("//a[normalize-space()='Go Back']");

    private final By lastPageText = By.xpath("//p[text()='Welcome to the Last Page']");

    public void clickRedirectionBtn(){
        click_Element(redirectionBtn);
    }

    public void clickGoBackBtn(){
        click_Element(goBackBtn);
    }

    public String getLastPageText(){
        return get_Text(lastPageText);
    }

    public void verifyTextOnEachRedirection(){
        String redirectText = "";

        String[] expectedTexts = {
                "Welcome to Second Page",
                "Welcome to Third Page",
                "Welcome to Fourth Page",
                "Welcome to Fifth Page",
                "Welcome to Sixth Page"
        };

        for (String expectedText : expectedTexts) {
            WebElement pageText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='" + expectedText + "']")));
            redirectText = pageText.getText();

            System.out.println(redirectText);

            Assert.assertEquals(expectedText, redirectText);
        }
    }

    /**
     * Day 24
    */
    private final By clearAllBtn = By.xpath("//button[contains(text(),'Clear all')]");

    public void selectFilter(String filterCategory, String... filterOptions){
        try {
            WebElement category = driver.findElement(By.xpath("//*[@class='phx-field__label phx:weight-bold' and contains(text(),'"+filterCategory+"')]"));

            if (category == null) {
                throw new Exception("Invalid filter category: " +filterCategory);
            }

            if (filterOptions.length == 1 && filterOptions[0].equalsIgnoreCase("all")) {
                List<WebElement> checkboxes = category.findElements(By.xpath(".//input[@type='checkbox']"));

                for (WebElement checkbox : checkboxes) {
                    if (!checkbox.isSelected()) {
                        js.executeScript("arguments[0].click();", checkbox);
                    }
                }
            }
            else {
                for (String option : filterOptions) {
                    WebElement checkbox = category.findElement(By.xpath("//span[normalize-space()='"+option+"']"));

                    if (checkbox == null) {
                        throw new Exception("Invalid filter option: " +option+ " for category: " +filterCategory);
                    }

                    if (!checkbox.isSelected()) {
                        js.executeScript("arguments[0].click();", checkbox);
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void resetFilter() {
        click_Element_Js(clearAllBtn);
    }

    /**
     * Day 25
    */
    private final By currentYearPopulation = By.xpath("//span[@rel='current_population']");

    private final By birth_Today = By.xpath("//span[@rel='births_today']");
    private final By death_Today = By.xpath("//span[@rel='dth1s_today']");
    private final By growth_Today = By.xpath("//span[@rel='absolute_growth']");

    private final By birth_Year = By.xpath("//span[@rel='births_this_year']");
    private final By death_Year = By.xpath("//span[@rel='dth1s_this_year']");
    private final By growth_Year = By.xpath("//span[@rel='absolute_growth_year']");

    public void getPopulationData(By locator){
        List<WebElement> elements = wait_for_presence_list(locator);
        for (WebElement e : elements) {
            System.out.println(e.getText());
        }
    }

    public void getCurrentYearPopulation(){
        System.out.print("Current World Population: ");
        getPopulationData(currentYearPopulation);
    }

    public void getBirthToday(){
        System.out.print("Birth Today: ");
        getPopulationData(birth_Today);
    }
    public void getDeathToday(){
        System.out.print("Death Today: ");
        getPopulationData(death_Today);
    }
    public void getGrowthToday(){
        System.out.print("Growth Today: ");
        getPopulationData(growth_Today);
    }

    public void getBirthThisYear(){
        System.out.print("Birth This Year: ");
        getPopulationData(birth_Year);
    }
    public void getDeathThisYear(){
        System.out.print("Death This Year: ");
        getPopulationData(death_Year);
    }
    public void getGrowthThisYear(){
        System.out.print("Growth This Year: ");
        getPopulationData(growth_Year);
    }
}
