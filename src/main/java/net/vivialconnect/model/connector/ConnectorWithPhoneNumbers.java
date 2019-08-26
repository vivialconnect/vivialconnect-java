package net.vivialconnect.model.connector;

import net.vivialconnect.model.error.VivialConnectException;

import java.util.Date;
import java.util.List;

public interface ConnectorWithPhoneNumbers{

    Date getDateModified();

	int getPhoneNumbersCount();

	int nextPage() throws VivialConnectException;

	int previousPage() throws VivialConnectException;

	int getNextPage();

	int getPreviousPage();

	int getPages();

	List<PhoneNumber> getPhoneNumbers();

}