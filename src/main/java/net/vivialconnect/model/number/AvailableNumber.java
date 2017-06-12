package net.vivialconnect.model.number;

import net.vivialconnect.model.error.VivialConnectException;

public interface AvailableNumber extends INumber {
    
    /**
     * Buys this number and adds it to your account.
     * <p>
     * Take a look at the "See also" section to see which optional properties
     * can be set prior to purchasing this number.
     * <p>
     * For more details on how to purchase a number, please refer to the
     * VivialConnect <a href="https://www.vivialconnect.net/docs/quickstart/buynumber.html">"How to Purchase a Number"</a>
     * API guide.
     * 
     * @see #setName(java.lang.String)
     * @see #setStatusTextUrl(java.lang.String)
     * @see #setConnectorId(int)
     * @see #setIncomingTextUrl(java.lang.String)
     * @see #setIncomingTextMethod(java.lang.String)
     * @see #setIncomingTextFallbackUrl(java.lang.String)
     * @see #setIncomingTextFallbackMethod(java.lang.String)
     * 
     * @return the newly-purchased number, associated to your account
     * @throws VivialConnectException if there is an API-level error, such as the number not being found
     */
    AssociatedNumber buy() throws VivialConnectException;
}