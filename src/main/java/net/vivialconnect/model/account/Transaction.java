package net.vivialconnect.model.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.JsonNode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Purchasing a number, sending a SMS or MMS, etc. are registered as transaction. This allows to get detailed insights about
 * how an account credits or cash are being spent.
 */
@JsonRootName("transactions")
public class Transaction {

    @JsonProperty
    private int id;

    @JsonProperty("transaction_type")
    private String transactionType;

    @JsonProperty("account_id")
    private int accountId;

    @JsonProperty("cash_amount")
    private BigDecimal cashAmount;

    @JsonProperty("credit_amount")
    private BigDecimal creditAmount;

    @JsonProperty("post_time")
    private Date postTime;

    @JsonProperty("unit_count")
    private int unitCount;

    private Balance balances;

    private JsonNode data;

    /**
     * Transaction ID
     * @return Transaction ID value
     */
    public int getId() {
        return id;
    }

    /**
     * Transaction Type, e.g.: SMS Out, SMS IN, etc.
     * @return transaction type value
     */
    public String getTransactionType() {
        return transactionType;
    }

    /**
     * Customer account ID
     * @return account ID value
     */
    public int getAccountId() {
        return accountId;
    }

    /**
     * Amount spent equivalent to ten thousand cents.
     * @return cash amount spent
     */
    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    /**
     * Credits spent for this transaction.
     * @return credit quantity
     */
    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    /**
     * Date when the transaction was issued.
     * @return transaction issued date
     */
    public Date getPostTime() {
        return postTime;
    }

    /**
     * Number of times of this transaction.
     * @return transaction count
     */
    public int getUnitCount() {
        return unitCount;
    }

    /**
     *  Total cash or credits spent in the account.
     * @return Balance object
     */
    public Balance getBalances() {
        return balances;
    }

    /**
     * JsonNode object with detailed data about the transaction. This differ for each transaction type.
     * See JsonNode documentation for more:
     * https://fasterxml.github.io/jackson-databind/javadoc/2.8/com/fasterxml/jackson/databind/JsonNode.html
     * @return JsonNode object
     */
    public JsonNode getData() {
        return data;
    }
}
