package com.dp.structural.decorator.eg;

import java.util.Base64;

// Encryption decorator
public class EncryptionDecorator extends DataSourceDecorator
{

    public EncryptionDecorator(DataSource source)
    {
        super(source);
    }

    @Override
    public void writeData(String data)
    {
        super.writeData(encode(data));
    }

    @Override
    public String readData()
    {
        return decode(super.readData());
    }

    public String readDecodedData()
    {
        return decode(super.readData());
    }

    public String decode(String data)
    {
        byte[] result = Base64.getDecoder().decode(data);
        for (int i = 0; i < result.length; i++) {
            result[i] -= (byte) 1;
        }
        return new String(result);
    }

    public String encode(String data)
    {
        byte[] result = data.getBytes();
        for (int i = 0; i < result.length; i++) {
            result[i] += (byte) 1;
        }
        return Base64.getEncoder().encodeToString(result);
    }

}
