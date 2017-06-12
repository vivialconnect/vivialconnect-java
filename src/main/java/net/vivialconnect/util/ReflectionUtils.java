package net.vivialconnect.util;

public class ReflectionUtils{

    public static String className(Class<?> clazz){
        return clazz.getSimpleName().replace("$", "");
    }
}