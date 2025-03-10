package by.it_academy.belaya.pages;

import by.it_academy.belaya.base.Singleton;
import by.it_academy.belaya.utils.WaitUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AppleIdPage {
    private static final Logger logger = LogManager.getLogger();

    @FindBy(xpath = "//div[@class='ac-localnav-title']")
    private WebElement titleText;

    @FindBy(xpath = "//input[@id='account_name_text_field']")
    private WebElement inputEmailOrPhoneField;

    @FindBy(xpath = "//input[@id='password_text_field']")
    private WebElement inputPasswordField;

    public AppleIdPage() {
        WebDriver driver = Singleton.getDriver();
        PageFactory.initElements(driver, this);
        logger.info("Opened AppleId Page: {}",Singleton.getDriver().getCurrentUrl());
    }

    public boolean isAppleIdPageOpened() {
        WaitUtils.waitForElementToBeVisible(titleText);
        return titleText.getText().contains("Apple Account");
    }

    public WebElement getInputEmailOrPhoneField() {
        WaitUtils.waitForElementToBeVisible(inputEmailOrPhoneField);
        return inputEmailOrPhoneField;
    }

    public WebElement getInputPasswordField() {
        WaitUtils.waitForElementToBeVisible(inputPasswordField);
        return inputPasswordField;
    }

    public AppleIdPage enterEmailOrPhone(String emailOrPhone) {
        WaitUtils.waitForElementToBeVisible(inputEmailOrPhoneField);
        inputEmailOrPhoneField.sendKeys(emailOrPhone);
        inputEmailOrPhoneField.sendKeys(Keys.ENTER);
        logger.info("Entered email/phone: {}", emailOrPhone);
        return this;
    }

    public AppleIdPage enterPassword(String password) {
        WaitUtils.waitForElementToBeVisible(inputPasswordField);
        inputPasswordField.sendKeys(password);
        inputPasswordField.sendKeys(Keys.ENTER);
        logger.info("Entered password.");
        return this;
    }
}
