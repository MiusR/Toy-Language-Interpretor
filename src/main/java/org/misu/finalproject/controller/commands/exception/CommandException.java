package org.misu.finalproject.controller.commands.exception;

public class CommandException extends Exception {
    public CommandException(String s) {
        super(s);
    }
    public CommandException(Exception e) { super(e); }
}
