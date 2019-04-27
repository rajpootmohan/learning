package com.dp.behavioral.visitor.visitor;

import com.dp.behavioral.visitor.shapes.Circle;
import com.dp.behavioral.visitor.shapes.CompoundShape;
import com.dp.behavioral.visitor.shapes.Dot;
import com.dp.behavioral.visitor.shapes.Rectangle;

public interface Visitor
{
    public String visitDot(Dot dot);

    public String visitCircle(Circle circle);

    public String visitRectangle(Rectangle rectangle);

    public String visitCompoundGraphic(CompoundShape cg);
}
