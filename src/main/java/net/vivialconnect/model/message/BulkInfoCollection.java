
package net.vivialconnect.model.message;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BulkInfoCollection {

    @JsonProperty("bulks")
    private List<BulkInfo> bulkList;

    @JsonProperty("count")
    private int count;
    
    @JsonProperty("next")
    private int nextPage;
    
    @JsonProperty("pages")
    private int pages;
    
    @JsonProperty("previous")
    private int previousPage;

    public List<BulkInfo> getBulkList() {
        return bulkList;
    }

    public int getCount() {
        return count;
    }

    public int getNextPage() {
        return nextPage;
    }

    public int getPages() {
        return pages;
    }

    public int getPreviousPage() {
        return previousPage;
    }
    
}