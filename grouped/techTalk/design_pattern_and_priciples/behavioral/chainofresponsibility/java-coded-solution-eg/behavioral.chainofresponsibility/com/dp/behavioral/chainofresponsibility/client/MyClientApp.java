package com.dp.behavioral.chainofresponsibility.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.dp.behavioral.chainofresponsibility.middleware.Middleware;
import com.dp.behavioral.chainofresponsibility.middleware.RoleCheckMiddleware;
import com.dp.behavioral.chainofresponsibility.middleware.ThrottlingMiddleware;
import com.dp.behavioral.chainofresponsibility.middleware.UserExistsMiddleware;
import com.dp.behavioral.chainofresponsibility.server.Server;

public class MyClientApp
{
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private static Server server;

    private static void init()
    {
        server = new Server();
        server.register("admin@example.com", "admin_pass");
        server.register("user@example.com", "user_pass");

        // All checks are linked. Client can build various chains using the
        // same components.
        Middleware middleware = new ThrottlingMiddleware(2);
        middleware.linkWith(new UserExistsMiddleware()).linkWith(new RoleCheckMiddleware());

        // Server gets a chain from client code.
        server.setMiddleware(middleware);
    }

    public static void main(String[] args) throws IOException
    {
        init();

        boolean success;
        do {
            System.out.print("Enter email: ");
            String email = reader.readLine();
            System.out.print("Input password: ");
            String password = reader.readLine();
            success = server.logIn(email, password);
        } while (!success);
    }
}
