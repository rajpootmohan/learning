package com.dp.structural.decorator.eg;

// Abstract base decorator
public class DataSourceDecorator implements DataSource
{

    private DataSource wrappee;

    public DataSourceDecorator(DataSource source)
    {
        this.wrappee = source;
    }

    @Override
    public void writeData(String data)
    {
        wrappee.writeData(data);
    }

    @Override
    public String readData()
    {
        return wrappee.readData();
    }

}
