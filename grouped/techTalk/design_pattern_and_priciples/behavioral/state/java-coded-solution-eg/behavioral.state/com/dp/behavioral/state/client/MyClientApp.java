package com.dp.behavioral.state.client;

import com.dp.behavioral.state.ui.Player;
import com.dp.behavioral.state.ui.UI;

public class MyClientApp
{
    public static void main(String[] args)
    {
        Player player = new Player();
        UI ui = new UI(player);
        ui.init();
    }
}
