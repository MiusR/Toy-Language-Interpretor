package org.misu.finalproject.controller.commands;


import org.misu.finalproject.controller.commands.exception.CommandException;

public class ExitCommand extends Command{
    public ExitCommand() {
        super("exit", "Exit from the program.");
    }

    @Override
    public void execute() throws CommandException {
        System.exit(0);
    }
}
