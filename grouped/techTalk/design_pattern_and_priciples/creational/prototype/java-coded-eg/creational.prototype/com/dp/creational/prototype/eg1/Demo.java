package com.dp.creational.prototype.eg1;

import java.util.ArrayList;
import java.util.List;

public class Demo
{
    public static void main(String[] args)
    {
        List<Shape> shapes = new ArrayList<>();
        List<Shape> shapesCopy = new ArrayList<>();

        Circle circle = new Circle();
        circle.setX(10);
        circle.setY(20);
        circle.setRadius(10);
        shapes.add(circle);

        Circle anotherCircle = (Circle) circle.clone();
        shapes.add(anotherCircle);

        Rectangle rectangle = new Rectangle();
        rectangle.setX(30);
        rectangle.setY(10);
        rectangle.setWidth(10);
        rectangle.setHeight(20);
        shapes.add(rectangle);

        cloneAndCompare(shapes, shapesCopy);
    }

    public static void cloneAndCompare(List<Shape> shapes, List<Shape> shapesCopy)
    {
        for (Shape shape : shapes) {
            shapesCopy.add(shape.clone());
        }

        for (int i = 0; i < shapes.size(); i++) {
            if (shapes.get(i) != shapesCopy.get(i)) {
                System.out.println(i + ": Shapes are different objects (yay!)");
                if (shapes.get(i).equals(shapesCopy.get(i))) {
                    System.out.println(i + ": And they are identical (yay!)");
                } else {
                    System.out.println(i + ": But they are not identical (booo!)");
                }
            } else {
                System.out.println(i + ": Shape objects are the same (booo!)");
            }
        }

    }
}
