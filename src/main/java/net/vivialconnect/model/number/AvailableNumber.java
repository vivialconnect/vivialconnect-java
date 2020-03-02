package net.vivialconnect.model.number;

import net.vivialconnect.model.error.BadRequestException;
import net.vivialconnect.model.error.ServerErrorException;
import net.vivialconnect.model.error.ApiRequestException;
import net.vivialconnect.model.error.ForbiddenAccessException;
import net.vivialconnect.model.error.UnauthorizedAccessException;

/**
 *  Interface that defines the common characteristics for numbers that can be purchased
 */
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
     * @see #setIncomingTextMethod(net.vivialconnect.model.enums.CallbackMethod)
     * @see #setIncomingTextFallbackUrl(java.lang.String)
     * @see #setIncomingTextFallbackMethod(net.vivialconnect.model.enums.CallbackMethod)
     * 
     * @return the newly-purchased number, associated to your account
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params and/or payload  are not valid
     * @throws UnauthorizedAccessException if any of the auth properties account ID, API Key and/or API secret are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     */
    AssociatedNumber buy() throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException;
}