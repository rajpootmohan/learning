package com.dp.behavioral.state.states;

import com.dp.behavioral.state.ui.Player;

public abstract class State
{
    public Player player;

    /**
     * Context passes itself through the state constructor. This may help a state to fetch some useful context data if
     * needed.
     */
    public State(Player player)
    {
        this.player = player;
    }

    public abstract String onLock();

    public abstract String onPlay();

    public abstract String onNext();

    public abstract String onPrevious();
}
