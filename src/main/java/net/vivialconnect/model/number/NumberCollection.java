package net.vivialconnect.model.number;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Wrapper class that contains a collection of phone numbers.
 *
 * This class is used for map the results of:
 *
 * <ul>
 *     <li>API Available numbers call</li>
 *     <li>API Associated numbers call</li>
 * </ul>
 *
 */
public class NumberCollection {

    /**
     * Collection of phone number entities
     * @see Number
     * @see AssociatedNumber
     * @see AvailableNumber
     */
    @JsonProperty("phone_numbers")
    private List<Number> numbers;

    /**
     * @return a collection of available numbers
     */
    public List<AvailableNumber> getAvailableNumbers() {
        List<AvailableNumber> availableNumbers = new ArrayList<AvailableNumber>(numbers.size());
        for (Number number : numbers){
            availableNumbers.add((AvailableNumber) number);
        }

        return availableNumbers;
    }

    /**
     * @return a collection of associated numbers in the account
     */
    public List<AssociatedNumber> getAssociatedNumbers() {
        List<AssociatedNumber> associatedNumbers = new ArrayList<AssociatedNumber>(numbers.size());
        for (Number number : numbers){
            associatedNumbers.add((AssociatedNumber) number);
        }

        return associatedNumbers;
    }

    /**
     * Set a collection of numbers
     * @param numbers collection of numbers value
     */
    public void setNumbers(List<Number> numbers) {
        this.numbers = numbers;
    }
}