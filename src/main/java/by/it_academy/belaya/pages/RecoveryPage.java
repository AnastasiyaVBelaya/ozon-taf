package by.it_academy.belaya.pages;

import by.it_academy.belaya.base.Singleton;
import by.it_academy.belaya.utils.WaitUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RecoveryPage {
    private static final Logger logger = LogManager.getLogger();

    @FindBy(xpath = "//div[@class='bq016-a']")
    private WebElement titleText;

    @FindBy(xpath = "//div[@class='b3231-a b3231-a0'][1]")
    private WebElement firstReasonButton;

    @FindBy(xpath = "//div[@class='b3231-a b3231-a0'][2]")
    private WebElement secondReasonButton;

    public RecoveryPage() {
        WebDriver driver = Singleton.getDriver();
        PageFactory.initElements(driver, this);
        logger.info("Opened Recovery Page: {}",Singleton.getDriver().getCurrentUrl());
    }

    public boolean isRecoveryPageOpened() {
        WaitUtils.waitForElementToBeVisible(titleText);
        return titleText.getText().contains("Select a reason");
    }

    public WebElement getFirstReasonButton() {
        WaitUtils.waitForElementToBeVisible(firstReasonButton);
        return firstReasonButton;
    }

    public WebElement getSecondReasonButton() {
        WaitUtils.waitForElementToBeVisible(secondReasonButton);
        return secondReasonButton;
    }
}
