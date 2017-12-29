package selAmazon;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 * Represents a row of the 'See all other buying options' table.
 */
public class BuyOption {
    private String price;
    private String condition;
    private String seller;

    public BuyOption(String pricePlusShipping, String condition, String seller) {
        this.price = pricePlusShipping;
        this.condition = condition;
        this.seller = seller;
    }

    /**
     * Constructor that receives the HTML element that contains the buying Option,
     * and creates an instance of the class extracting the data from this HTML element.
     * @param buyOptionContainer HTML element that contains the buying Option
     * @throws NoSuchElementException If one of the fields is not found, this exception will be thrown.
     */
    public BuyOption(WebElement buyOptionContainer) throws NoSuchElementException {
        this.price = extractPrice(buyOptionContainer);
        this.condition = extractCondition(buyOptionContainer);
        this.seller = extractSeller(buyOptionContainer);
    }

    /**
     * Extracts the price from the buyOptionContainer.
     * It doesn't include the shipping price.
     * @param buyOptionContainer HTML element that contains the buying Option
     * @return String that contains the option price
     * @throws NoSuchElementException
     */
    public String extractPrice(WebElement buyOptionContainer) throws NoSuchElementException {
        WebElement priceColumn = buyOptionContainer.findElement(By.className("olpPriceColumn"));
        WebElement priceContainer = priceColumn.findElement(By.className("olpOfferPrice"));
        return priceContainer.getText();
    }

    /**
     * Extracts the condition of the buyOptionContainer
     * @param buyOptionContainer HTML element that contains the buying Option
     * @return String that contains the condition found on the WebElement
     * @throws NoSuchElementException
     */
    public String extractCondition(WebElement buyOptionContainer) throws NoSuchElementException {
        WebElement conditionColumn = buyOptionContainer.findElement(By.className("olpConditionColumn"));
        WebElement conditionContainer = conditionColumn.findElement(By.className("olpCondition"));
        return conditionContainer.getText();
    }

    /**
     * Extracts the seller of the buyOptionContainer
     * @param buyOptionContainer HTML element that contains the buying Option
     * @return String that contains the seller found on the WebElement
     * @throws NoSuchElementException
     */
    public String extractSeller(WebElement buyOptionContainer) throws NoSuchElementException {
        WebElement sellerColumn = buyOptionContainer.findElement(By.className("olpSellerColumn"));
        WebElement sellerContainer = sellerColumn.findElement(By.className("olpSellerName"));
        return sellerContainer.getText();
    }

    /**
     * String representation of a Book object.
     * The format is: "Price: %s | Condition: %s | Seller: %s"
     */
    @Override
    public String toString() {
        if (price == null || condition == null || seller == null)
            return "No product information";

        return String.format("Price: %s | Condition: %s | Seller: %s", price, condition, seller);
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String pricePlusShipping) {
        this.price = pricePlusShipping;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
}
