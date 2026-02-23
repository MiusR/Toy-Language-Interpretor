package org.misu.finalproject.controller.commands;


import org.misu.finalproject.controller.commands.exception.CommandException;

public abstract class Command {
    private final String key, description;

    protected Command(String key, String description) {
        this.key = key;
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }

    public abstract void execute() throws CommandException;
}
