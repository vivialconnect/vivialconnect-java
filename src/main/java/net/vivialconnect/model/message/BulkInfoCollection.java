
package net.vivialconnect.model.message;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Wrapper class for map a collection of bulk send jobs returned by the API.
 * <p>
 * This class also contains properties for paginate these elements.
 * <p>
 * For more info, visit <a>https://dashboard.vivialconnect.net/docs/api/messages.html#get--api-v1.0-accounts-(int-account_id)-messages-bulk.json</a>
 */
public class BulkInfoCollection {

    /**
     * Collection of bulk sends
     */
    @JsonProperty("bulks")
    private List<BulkInfo> bulkList;

    /**
     * Quantity of bulk send in the collection
     */
    @JsonProperty("count")
    private int count;

    /**
     * Next page, if there are next pages.
     */
    @JsonProperty("next")
    private int nextPage;

    /**
     * Quantity of pages for pagination
     */
    @JsonProperty("pages")
    private int pages;

    /**
     * Previous page, if there are previous pages.
     */
    @JsonProperty("previous")
    private int previousPage;

    /**
     * Collection of Bulk send objects.
     *
     * @return collection of bulk send
     */
    public List<BulkInfo> getBulkList() {
        return bulkList;
    }

    /**
     * Count of bulk send elements
     *
     * @return count of bulk sends
     */
    public int getCount() {
        return count;
    }

    /**
     * Next page
     *
     * @return next page value
     */
    public int getNextPage() {
        return nextPage;
    }

    /**
     * Number of pages able to paginate forward
     *
     * @return number of pages
     */
    public int getPages() {
        return pages;
    }

    /**
     * Previous page
     *
     * @return previous page value
     */
    public int getPreviousPage() {
        return previousPage;
    }

}