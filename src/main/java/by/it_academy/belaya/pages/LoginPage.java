package by.it_academy.belaya.pages;

import by.it_academy.belaya.base.Singleton;
import by.it_academy.belaya.enums.Countries;
import by.it_academy.belaya.utils.WaitUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.*;

public class LoginPage {

    private static final Logger logger = LogManager.getLogger();

    @FindBy(xpath = "(//a[@href='#'])[2]")
    private WebElement customerHelpCenterLink;

    @FindBy(xpath = "//input[@type='tel']")
    private WebElement phoneInputField;

    @FindBy(xpath = "//input[@type='email']")
    private WebElement emailInputField;

    @FindBy(xpath = "//div[@role='listbox']")
    private WebElement phoneDropDownMenu;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement signInButton;

    @FindBy(xpath = "//div[@class='cm1a_49']")
    private WebElement signInWithAppleButton;

    @FindBy(xpath = "//a[@class='b2121-a0 b2121-b2 b2121-a4']")
    private WebElement signInWithGosUslugiButton;

    @FindBy(xpath = "//button[@class='ca2n_49 ga121-a']")
    private WebElement signInByEmailButton;

    @FindBy(xpath = "//button[@class='ga121-a']")
    private WebElement iCantSignInButton;

    @FindBy(xpath = "//input[@class='d019-a d019-a5']")
    private WebElement verificationCodeField;

    @FindBy(xpath = "//p[@class='c8012-a2']")
    private WebElement incorrectInputMessage;

    @FindBy(xpath = "//button[@class='na3c_49 ga121-a undefined']")
    private WebElement whyCantISighInButton;

    public LoginPage() {
        WebDriver driver = Singleton.getDriver();
        PageFactory.initElements(driver, this);
        logger.info("Opened Login Page: {}", Singleton.getDriver().getCurrentUrl());
    }

    public WebElement getCustomerHelpCenterLink() {
        WaitUtils.waitForElementToBeVisible(customerHelpCenterLink);
        return customerHelpCenterLink;
    }

    public WebElement getPhoneInputField() {
        WaitUtils.waitForElementToBeVisible(phoneInputField);
        return phoneInputField;
    }

    public WebElement getVerificationCodeField() {
        WaitUtils.waitForElementToBeVisible(verificationCodeField);
        return verificationCodeField;
    }

    public LoginPage selectCountryFromDropDown(Countries countryName) {
        WaitUtils.waitForElementToBeVisible(phoneDropDownMenu);
        phoneDropDownMenu.click();

        List<WebElement> options = getCountryOptions();
        Optional<WebElement> countryToSelect = options.stream()
                .filter(country -> Objects.requireNonNull(country.getAttribute("title"))
                        .equalsIgnoreCase(countryName.getCountryName()))
                .findFirst();

        if (countryToSelect.isPresent()) {
            WaitUtils.waitForElementToBeClickable(countryToSelect.get());
            countryToSelect.get().click();
            logger.info("Chosen country: {}", countryName);
        } else {
            throw new NoSuchElementException("Country '" + countryName + "' wasn't found");
        }
        return this;
    }

    public LoginPage enterPhoneNumber(String number) {
        WaitUtils.waitForElementToBeVisible(phoneInputField);
        logger.info("Entering phone number: {}", number);
        phoneInputField.sendKeys(number);
        return this;
    }

    public LoginPage clickOnSignInButton() {
        WaitUtils.waitForElementToBeVisible(signInButton);
        logger.info("Clicking on the sign-in button");
        signInButton.click();
        return this;
    }

    public LoginPage clickOnSignByEmailButton() {
        WaitUtils.waitForElementToBeVisible(signInByEmailButton);
        logger.info("Clicking on the sign-in by email button");
        signInByEmailButton.click();
        return this;
    }

    public LoginPage clickOnSignByAppleButton() {
        WaitUtils.waitForElementToBeVisible(signInWithAppleButton);
        logger.info("Clicking on the sign-in by Apple button");
        signInWithAppleButton.click();
        return this;
    }

    public AppleIdPage switchToNewWindow() {
        String originalWindowHandle = Singleton.getDriver().getWindowHandle();
        Set<String> windowHandles = getAllWindows();
        for (String handle : windowHandles) {
            if (!handle.equals(originalWindowHandle)) {
                Singleton.getDriver().switchTo().window(handle);
                logger.info("Switched to the new window: {}", handle);
                break;
            }
        }
        return new AppleIdPage();
    }

    public RecoveryPage clickOnICantSignInButton() {
        WaitUtils.waitForElementToBeVisible(iCantSignInButton);
        logger.info("Clicking on I can't sign in button");
        iCantSignInButton.click();
        return new RecoveryPage();
    }

    public LoginPage enterEmail(String email) {
        WaitUtils.waitForElementToBeVisible(emailInputField);
        logger.info("Entering email: {}", email);
        emailInputField.sendKeys(email);
        return this;
    }

    public WebElement getIncorrectInputMessage() {
        WaitUtils.waitForElementToBeVisible(incorrectInputMessage);
        return incorrectInputMessage;
    }

    public WebElement getWhyCantISighInButton() {
        WaitUtils.waitForElementToBeVisible(whyCantISighInButton);
        return whyCantISighInButton;
    }

    private List<WebElement> getCountryOptions() {
        return Singleton.getDriver().findElements(By.xpath("//div[@role='option']"));
    }

    private Set<String> getAllWindows() {
        return Singleton.getDriver().getWindowHandles();
    }
}
