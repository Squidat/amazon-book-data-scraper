package selAmazon;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains all the information related to an Amazon Book.
 */
public class Book {
    private String ISBN;
    private String listPrice;
    private String salesRank;
    private ArrayList<BuyOption> buyOptions;

    /**
     * Constructor of Book.
     * @param ISBN                {@link Book#ISBN}
     * @param listPrice           {@link Book#listPrice}
     * @param salesRankParagraph  Complete paragraph that contains all the ranks of the product
     *                            in several categories.
     * @param buyOptions          {@link Book#buyOptions}
     */
    public Book(String ISBN, String listPrice, String salesRankParagraph, ArrayList<BuyOption> buyOptions) {
        this.ISBN = ISBN;
        this.listPrice = listPrice;
        this.salesRank = extractProductRank(salesRankParagraph);
        this.buyOptions = buyOptions;
    }

    /**
     * Extracts the Top 100 Books rank from the salesRankParagraph
     * @param salesRankParagraph Complete paragraph that contains all the ranks of the product
     *                           in several categories.
     * @return String with the Book rank in the Top 100 Books rank.
     */
    public static String extractProductRank(String salesRankParagraph) {
        String top100BooksPattern = "#[0-9,]+ in Books \\(See Top 100 in Books\\)";
        String bookRankPattern = "#[0-9,]+";

        Pattern top100 = Pattern.compile(top100BooksPattern);
        Pattern bookRank = Pattern.compile(bookRankPattern);

        Matcher top100matcher = top100.matcher(salesRankParagraph);
        if (top100matcher.find()) {
            String top100line = top100matcher.group(0);
            Matcher bookRankMatcher = bookRank.matcher(top100line);
            if (bookRankMatcher.find()) {
                //System.out.println(bookRankMatcher.group(0));
                return bookRankMatcher.group(0);
            } else {
                //TODO: Cambiar por excepcion
                return "NO MATCH";
            }
        } else {
            //TODO: Cambiar por una exception
            return "NO MATCH";
        }
    }

    /**
     * ISBN of the book
     */
    public String getISBN() {
        return ISBN;
    }

    /**
     * @param ISBN {@link Book#getISBN()}
     */
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    /**
     * Price on the main page of the product.
     * Can be "Not found" if there was no clear price on its page.
     */
    public String getListPrice() {
        return listPrice;
    }

    /**
     * @param listPrice {@link Book#getListPrice()}
     */
    public void setListPrice(String listPrice) {
        this.listPrice = listPrice;
    }

    /**
     * Sales rank of the book.
     * Can be "Not found" if the rank was not found on the description of the book.
     */
    public String getSalesRank() {
        return salesRank;
    }

    /**
     * @param salesRank {@link Book#getSalesRank()}
     */
    public void setSalesRank(String salesRank) {
        this.salesRank = salesRank;
    }

    /**
     * List of {@link selAmazon.BuyOption BuyOption} of the book.
     */
    public ArrayList<BuyOption> getBuyOptions() {
        return buyOptions;
    }

    /**
     * @param buyOptions {@link Book#getBuyOptions()}
     */
    public void setBuyOptions(ArrayList<BuyOption> buyOptions) {
        this.buyOptions = buyOptions;
    }

    /**
     * String representation of a Book object.
     * The format is: 'ISBN: %s | List Price: %s | Sales Rank: %s | BuyOptions: %d'
     */
    @Override
    public String toString() {
        return String.format("ISBN: %s | List Price: %s | Sales Rank: %s | BuyOptions: %d",
                ISBN, listPrice, salesRank, buyOptions.size());
    }
}
