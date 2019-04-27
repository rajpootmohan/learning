package com.dp.creational.prototype.eg1;

import java.util.HashMap;
import java.util.Map;

// Prototype registry or factory
public class BundledShapeCache
{
    private Map<String, Shape> cache = new HashMap<>();

    public BundledShapeCache()
    {
        Circle circle = new Circle();
        circle.setX(10);
        circle.setY(20);
        circle.setRadius(10);
        circle.setColor("Green");

        Rectangle rectangle = new Rectangle();
        rectangle.setX(30);
        rectangle.setY(10);
        rectangle.setWidth(10);
        rectangle.setHeight(20);
        circle.setColor("Blue");

        cache.put("Big green circle", circle);
        cache.put("Medium blue rectangle", rectangle);
    }

    public Shape put(String key, Shape shape)
    {
        cache.put(key, shape);
        return shape;
    }

    public Shape get(String key)
    {
        return cache.get(key).clone();
    }
}
