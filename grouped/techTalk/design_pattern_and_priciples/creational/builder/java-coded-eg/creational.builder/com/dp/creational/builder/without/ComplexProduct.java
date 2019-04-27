package com.dp.creational.builder.without;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ComplexProduct
{
    private List<Product> products = new ArrayList<>();

    public void assembleProduct(Product p)
    {
        products.add(p);
    }

    public String getParts()
    {
        String str = "Complex object is made of :";
        for (Product p : products) {
            str = str.concat(" " + p.getName());
        }
        return str;
    }

    public Iterator<Product> iterator()
    {
        return products.iterator();
    }
}
