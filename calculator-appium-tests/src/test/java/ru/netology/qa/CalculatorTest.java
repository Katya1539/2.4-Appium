package ru.netology.qa;

import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class ActivityTest {

    private AndroidDriver driver;

    @BeforeEach
    public void setUp() {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "android");
        desiredCapabilities.setCapability("appium:deviceName", "some name");
        desiredCapabilities.setCapability("appium:appPackage", "ru.netology.testing.uiautomator");
        desiredCapabilities.setCapability("appium:appActivity", "ru.netology.testing.uiautomator.MainActivity");
        desiredCapabilities.setCapability("appium:automationName", "uiautomator2");
        desiredCapabilities.setCapability("appium:ensureWebviewsHavePages", true);
        desiredCapabilities.setCapability("appium:nativeWebScreenshot", true);
        desiredCapabilities.setCapability("appium:newCommandTimeout", 3600);
        desiredCapabilities.setCapability("appium:connectHardwareKeyboard", true);

        driver = new AndroidDriver(this.getUrl(), desiredCapabilities);

    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    private URL getUrl() {
        try {
            return new URL("http://127.0.0.1:4723");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void successfulTextEntry() {
        Selectors selectors = new Selectors(driver);
        selectors.input.sendKeys("Netology");
        selectors.buttonChange.click();
        Assertions.assertEquals("Netology", selectors.textChanged.getText());
    }

    @Test
    public void enteringEmptyString() {
        Selectors selectors = new Selectors(driver);
        String textBefore = selectors.textChanged.getText();
        selectors.input.sendKeys("   ");
        selectors.buttonChange.click();

        Assertions.assertEquals(textBefore, selectors.textChanged.getText());

    }

    @Test
    public void newActivity() throws InterruptedException {
        Selectors selectors = new Selectors(driver);
        selectors.input.sendKeys("Netology");
        selectors.buttonActivity.click();
        Thread.sleep(2000);
        selectors.activityText.isDisplayed();
        Assertions.assertEquals("Netology", selectors.activityText.getText());

    }


}
