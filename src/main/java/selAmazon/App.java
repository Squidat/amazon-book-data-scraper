package selAmazon;
import org.openqa.selenium.WebDriver;

/**
 * Ignore this class if using the project as a library, its
 * the class where we test the AmazonScraper.
 */
public class App 
{
    public static void main( String[] args )
    {
        String isbns[] = {
                "0073383821",
                "0073386162",
                "013173542X",
                "013212842X",
                "013615431X",
                "047052474X",
                "076891700X",
                "080534635X",
                "143543871X",
                "160151493X",
                "0078454786"
        };

        System.out.println( "Amazon scraper" );
        WebDriver driver = BrowserFactory.generateBrowser(Browser.CHROME);
        AmazonScraper scraper = new AmazonScraper(driver);
        for (String isbn : isbns) {
            try {
                Book book = scraper.scrape(isbn);
                System.out.println(book.toString());
            } catch (Exception ex) {
                continue;
            }
        }
        scraper.destroy();
    }
}
