package net.vivialconnect.util;

import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

public class ProjectProperties {

    private static Properties p = null;

    public static void getProperties() throws IOException {

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        p = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = classLoader.getResourceAsStream("app.properties");
            p.load(inputStream);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            inputStream.close();
        }
    }

    public static String getProperty(String name) throws IOException {
        if (p == null) {
            getProperties();
        }
        return p.getProperty(name);
    }
}