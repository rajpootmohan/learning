package com.dp.behavioral.visitor.shapes;

import com.dp.behavioral.visitor.visitor.Visitor;

public interface Shape
{
    public void move(int x, int y);

    public void draw();

    public String accept(Visitor visitor);
}
