package com.dp.creational.builder.eg1;

public interface Builder
{
    void buildPartA();

    void buildPartB();

    // can include this if all builders are going to return same complex product
    // ComplexProduct getResult();
}
