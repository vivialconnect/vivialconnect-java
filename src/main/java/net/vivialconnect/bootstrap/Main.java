package net.vivialconnect.bootstrap;

import net.vivialconnect.client.VivialConnectClient;
import net.vivialconnect.model.error.VivialConnectException;

import net.vivialconnect.model.number.Number;

public class Main {
    
    private static final int NUMBER_ID = 131;

    static final int ACCOUNT_ID = 10130;

    static final String API_KEY = "MTKUAQS6SS0STMWT8PBLD530VWA0AYNURN8";
    static final String API_SECRET = "N8TBkht8QHoDFw50HKNA1mf339cVyOgsQ9K89Gk8rWbVAwmr";

    static final String FROM_NUMBER = "+13022136859";
    static final String TO_NUMBER = "+18099667830";
	
    public static void main(String[] args) throws VivialConnectException {
        VivialConnectClient.init(ACCOUNT_ID, API_KEY, API_SECRET);
        
        Number.getNumberById(131);
    }
}