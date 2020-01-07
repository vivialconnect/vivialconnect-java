package net.vivialconnect.model.number;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TaggedNumberCollection {

    @JsonProperty("count")
    private int count;

    @JsonProperty("items")
    private List<Number> numbers;

    @JsonProperty("next")
    private int nextPage;

    @JsonProperty("pages")
    private int pages;

    @JsonProperty("previous")
    private int previousPage;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Number> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Number> numbers) {
        this.numbers = numbers;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPreviousPage() {
        return previousPage;
    }

    public void setPreviousPage(int previousPage) {
        this.previousPage = previousPage;
    }
}
