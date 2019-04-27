package com.dp.creational.singleton.solution;

public final class Singleton
{
    private String val;

    private static volatile Singleton instance;

    private Singleton(String val)
    {
        this.val = val;
    }

    public static Singleton getInstance(String val)
    {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton(val);
                }
            }
        }
        return instance;
    }

    public String getVal()
    {
        return val;
    }
}
