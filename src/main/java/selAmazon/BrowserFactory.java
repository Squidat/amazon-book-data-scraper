package selAmazon;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Class in charge of creating the {@link org.openqa.selenium.WebDriver WebDrivers}.
 * Useful if you are using the package as a library.
 * If you have the Selenium library, you can create your own WebDrivers.
 */
public class BrowserFactory {

    /**
     * Creates an instance of a {@link org.openqa.selenium.WebDriver WebDriver}
     * depending on the {@link selAmazon.Browser Browser} received.
     *
     * Currently it only supports Chrome, but it can be updated to support different browsers.
     *
     * @param browser Option from the enum {@link selAmazon.Browser Browser}
     * @return {@link org.openqa.selenium.WebDriver WebDriver}
     */
    static public WebDriver generateBrowser(Browser browser) {
        WebDriver driver = null;
        switch (browser) {
            case CHROME:
                try {
                    driver = new ChromeDriver();
                } catch (IllegalStateException ex) {
                    System.out.println("Chromedriver not found, download and move to: " +
                            System.getProperty("user.dir"));
                    throw ex;
                }
                break;

        }

        return driver;
    }
}
