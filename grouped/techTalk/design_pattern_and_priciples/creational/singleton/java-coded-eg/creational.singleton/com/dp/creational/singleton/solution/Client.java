package com.dp.creational.singleton.solution;

public class Client
{
    public static void main(String[] args)
    {
        System.out.println("If you see the same value, then singleton was reused (yay!)" + "\n"
            + "If you see different values, then 2 singletons were created (booo!!)" + "\n\n" + "RESULT:" + "\n");

        Thread foo = new Thread(new ThreadFoo());
        Thread bar = new Thread(new ThreadBar());
        foo.start();
        bar.start();
    }

    static class ThreadFoo implements Runnable
    {
        private Singleton singleton;

        @Override
        public void run()
        {
            singleton = Singleton.getInstance("Foo");
            System.out.println(singleton.getVal());
        }

    }

    static class ThreadBar implements Runnable
    {
        private Singleton singleton;

        @Override
        public void run()
        {
            singleton = Singleton.getInstance("Bar");
            System.out.println(singleton.getVal());
        }

    }

}
