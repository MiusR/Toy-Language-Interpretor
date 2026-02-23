package org.misu.finalproject.controller.exception;

public class ControllerException extends RuntimeException {
    public ControllerException(String message) {
        super(message);
    }
    public ControllerException(RuntimeException exception) {super(exception);}
}
