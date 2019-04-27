package com.dp.structural.composite.client;

import java.awt.Color;

import com.dp.structural.composite.editor.ImageEditor;
import com.dp.structural.composite.shapes.Circle;
import com.dp.structural.composite.shapes.CompoundShape;
import com.dp.structural.composite.shapes.Dot;
import com.dp.structural.composite.shapes.Rectangle;

public class Client
{

    public static void main(String[] args)
    {
        ImageEditor editor = new ImageEditor();

        editor.loadShapes(new Circle(10, 10, 10, Color.BLUE),

            new CompoundShape(new Circle(110, 110, 50, Color.RED), new Dot(160, 160, Color.RED)),

            new CompoundShape(new Rectangle(250, 250, 100, 100, Color.GREEN), new Dot(240, 240, Color.GREEN),
                new Dot(240, 360, Color.GREEN), new Dot(360, 360, Color.GREEN), new Dot(360, 240, Color.GREEN)));
    }
}
