package net.vivialconnect.model.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Contains the total cash or credits spent in the account.
 */
public class Balance {

    @JsonProperty("cash_balance")
    private BigDecimal cashBalance;

    @JsonProperty("number_credits")
    private BigDecimal numberCredits;

    @JsonProperty("subscription_balance")
    private BigDecimal subscriptionBalance;

    @JsonProperty("usage_credits")
    private int usageCredits;

    /**
     * Total of cash amount spent
     * @return cash balance spent
     */
    public BigDecimal getCashBalance() {
        return cashBalance;
    }

    /**
     *  Total of number credits spent
     * @return number credits spent
     */
    public BigDecimal getNumberCredits() {
        return numberCredits;
    }

    /**
     * Account subscription balance
     * @return subscription balance
     */
    public BigDecimal getSubscriptionBalance() {
        return subscriptionBalance;
    }

    /**
     * Credits spent
     * @return credits spent
     */
    public int getUsageCredits() {
        return usageCredits;
    }
}
