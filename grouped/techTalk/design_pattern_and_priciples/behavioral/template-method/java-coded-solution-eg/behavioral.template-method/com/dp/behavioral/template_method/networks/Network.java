package com.dp.behavioral.template_method.networks;

/**
 * Base class of social network.
 */
public abstract class Network
{
    protected String userName;

    protected String password;

    public Network()
    {
    }

    /**
     * Publish the data to whatever network.
     */
    public boolean post(String message)
    {
        // Authenticate before posting. Every network uses a different
        // authentication method.
        if (logIn(this.userName, this.password)) {
            // Send the post data.
            boolean result = sendData(message.getBytes());
            logOut();
            return result;
        }
        return false;
    }

    abstract boolean logIn(String userName, String password);

    abstract boolean sendData(byte[] data);

    abstract void logOut();
}
