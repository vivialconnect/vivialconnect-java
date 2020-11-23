package net.vivialconnect.model.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TransactionResponse {

    @JsonProperty
    private int count;

    @JsonProperty
    private List<Transaction> transactions;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
