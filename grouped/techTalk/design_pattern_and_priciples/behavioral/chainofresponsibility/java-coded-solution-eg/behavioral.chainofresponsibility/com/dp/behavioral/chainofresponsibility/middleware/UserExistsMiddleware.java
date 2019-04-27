package com.dp.behavioral.chainofresponsibility.middleware;

import com.dp.behavioral.chainofresponsibility.server.Server;

// Check user's credentials
public class UserExistsMiddleware extends Middleware
{
    public boolean check(String email, String password)
    {
        if (!Server.hasEmail(email)) {
            return false;
        }
        return checkNext(email, password);
    }
}
