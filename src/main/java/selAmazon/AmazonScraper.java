package selAmazon;

import org.openqa.selenium.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that manages the scraping. Requires a {@link org.openqa.selenium.WebDriver WebDriver} which will run
 * all the actions.
 * Always remember to {@link AmazonScraper#destroy() destroy} any instance of this class, if you dont, you will have to
 * manually close the browser opened.
 */
public class AmazonScraper {
    WebDriver driver;

    /**
     * Constructor of AmazonScraper
     * @param driver Instance of {@link org.openqa.selenium.WebDriver WebDriver}.
     *               If using the package as a library, use {@link selAmazon.BrowserFactory} to create the driver,
     *               else if you have the Selenium library, you can create your own WebDriver.
     */
    public AmazonScraper(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Looks for the specified ISBN in Amazon.com and returns a Book with the scraped information.
     * @param ISBN
     * @return Book object with the scraped data.
     * @throws WebDriverException If the AmazonScraper driver is NULL, an exception will be thrown.
     */
    public Book scrape(String ISBN) throws WebDriverException {
        if (this.driver == null)
            throw new WebDriverException("AmazonScraper WebDriver is null");

        driver.navigate().to("https://www.amazon.com/");
        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
        searchBox.clear();
        searchBox.sendKeys(ISBN);
        searchBox.submit();

        //Get first result
        try {
            WebElement resultLi = driver.findElement(By.id("result_0"));
            WebElement productImage = resultLi.findElement(By.cssSelector(".s-access-image"));
            productImage.click();
        } catch (NoSuchElementException ex) {
            throw new NoSuchElementException(String.format("Book with ISBN: %s not found", ISBN));
        }

        String productListPrice;
        try {
            WebElement productPriceSpan = driver.findElement(By.xpath("//span[@class='a-size-medium a-color-price header-price']"));
            productListPrice = productPriceSpan.getText();
        } catch (NoSuchElementException ex) {
            productListPrice = "Not found";
        }
        //System.out.println("Product list price: " + productListPrice);

        String salesRankParagraph;
        try {
            WebElement salesRankLi = driver.findElement(By.id("SalesRank"));
            salesRankParagraph = salesRankLi.getText();
        } catch (NoSuchElementException ex) {
            salesRankParagraph = "Not found";
        }

        //WebElement seeAllBuyingOptionsButton = driver.findElement(By.id("a-autoid-2-announce"));
        //String seeAllBuyingOptionsLink = seeAllBuyingOptionsButton.getAttribute("href");
        String seeAllBuyingOptionsLink = String.format("http://amazon.com/gp/offer-listing/%s/ref=dp_olp_all_mbc?ie=UTF8&condition=all", ISBN);
        driver.navigate().to(seeAllBuyingOptionsLink);

        ArrayList<BuyOption> buyOptions = new ArrayList<BuyOption>();
        while (true) {
            WebElement offerList = driver.findElement(By.id("olpOfferList"));
            List<WebElement> buyOptionContainers = offerList.findElements(By.className("olpOffer"));

            for (WebElement buyOptionContainer : buyOptionContainers) {
                try {
                    BuyOption option = new BuyOption(buyOptionContainer);
                    buyOptions.add(option);
                    //System.out.println(option.toString());
                } catch (NoSuchElementException ex) {
                    //System.out.println("Invalid buy option");
                }
            }

            try {
                WebElement nextPageButton = driver.findElement(By.xpath("//li[@class='a-last']/a"));
                String nextPageLink = nextPageButton.getAttribute("href");
                driver.navigate().to(nextPageLink);
            } catch (NoSuchElementException ex) {
                //System.out.println("No more pages");
                break;
            }
        }

        return new Book(ISBN, productListPrice, salesRankParagraph, buyOptions);
    }


    /**
     * Closes the AmazonScraper's driver, closing out the browser's process.
     */
    public void destroy() {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }
}
