package net.vivialconnect.model.number;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Wrapper class for map a collection of numbers tagged returned by the API.
 * <p>
 * This class also contains properties and methods for handle pagination of these numbers.
 */
public class TaggedNumberCollection {

    /**
     * Count of tagged numbers in the account
     */
    @JsonProperty("count")
    private int count;

    /**
     * Collection of numbers tagged
     */
    @JsonProperty("items")
    private List<Number> numbers;

    /**
     * Next page value, if there are a next page
     */
    @JsonProperty("next")
    private int nextPage;

    /**
     * Total pages value
     */
    @JsonProperty("pages")
    private int pages;

    /**
     * Previous page value
     */
    @JsonProperty("previous")
    private int previousPage;

    public TaggedNumberCollection() {
    }

    /**
     * Convenient constructor for create and set a collection of tagged numbers
     *
     * @param numbers collection of numbers
     */
    public TaggedNumberCollection(List<Number> numbers) {
        this.numbers = numbers;
    }

    /**
     * Convenience constructor for create and set:
     * - Count of tagged numbers.
     * - Collection of tagged numbers
     * - Next Page value
     * - Previous page value
     *
     * @param count        count of tagged numbers
     * @param numbers      collection of tagged numbers
     * @param nextPage     next page value
     * @param pages        pages value
     * @param previousPage previous pages value
     */
    public TaggedNumberCollection(int count, List<Number> numbers, int nextPage, int pages, int previousPage) {
        this.count = count;
        this.numbers = numbers;
        this.nextPage = nextPage;
        this.pages = pages;
        this.previousPage = previousPage;
    }

    /**
     * Count of tagged numbers returned by the API
     *
     * @return
     */
    public int getCount() {
        return count;
    }

    /**
     * Set count value
     * <strong>Note:</strong> Setting this value does not have any effect in the API.
     *
     * @param count count value
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Collection of tagged numbers returned by the API.
     *
     * @return collection of numbers value
     */
    public List<Number> getNumbers() {
        return numbers;
    }

    /**
     * Set a collection of number
     * <strong>Note:</strong> Setting this value does not have any effect in the API.
     *
     * @param numbers
     */
    public void setNumbers(List<Number> numbers) {
        this.numbers = numbers;
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
     * Set the next page value
     * <strong>Note:</strong> Setting this value does not have any effect in the API.
     *
     * @param nextPage next page value
     */
    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    /**
     * Number of pages able to paginate
     *
     * @return pages value
     */
    public int getPages() {
        return pages;
    }

    /**
     * Set pages value.
     * <strong>Note:</strong> Setting this value does not have any effect in the API.
     *
     * @param pages
     */
    public void setPages(int pages) {
        this.pages = pages;
    }

    /**
     * Previous page value, if there any
     *
     * @return previous page value
     */
    public int getPreviousPage() {
        return previousPage;
    }

    /**
     * Set the previous page value
     * <strong>Note:</strong> Setting this value does not have any effect in the API.
     *
     * @param previousPage previous page value
     */
    public void setPreviousPage(int previousPage) {
        this.previousPage = previousPage;
    }
}
