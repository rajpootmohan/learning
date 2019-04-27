package com.dp.behavioral.momento.solution;

public class Originator
{
    // Hiding internal state
    private String state;

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    // Saving internal state
    public Memento createMemento()
    {
        return new Memento(state);
    }

    // Restoring internal state
    public void restore(Memento memento)
    {
        state = memento.getState();
    }

    // Implementing Memento as inner class.
    // All members are private and accessible only by originator.
    public class Memento
    {
        private String state;

        private Memento(String state)
        {
            this.state = state;
        }

        private String getState()
        {
            return state;
        }

        private void setState(String state)
        {
            this.state = state;
        }
    }
}
