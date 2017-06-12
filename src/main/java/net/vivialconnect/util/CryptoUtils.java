package net.vivialconnect.util;

import java.util.Locale;

public class CryptoUtils{

    public static String toHex(byte[] content){
        StringBuilder hexBuilder = new StringBuilder(content.length * 2);
        for (int i = 0; i < content.length; i++){
            String hex = Integer.toHexString(content[i]);
            if (hex.length() == 1){
                hexBuilder.append("0");
            }else if (hex.length() == 8){
                hex = hex.substring(6);
            }

            hexBuilder.append(hex);
        }

        return hexBuilder.toString().toLowerCase(Locale.getDefault());
    }
}