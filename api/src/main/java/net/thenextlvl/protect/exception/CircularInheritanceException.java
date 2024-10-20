package net.thenextlvl.protect.exception;

public class CircularInheritanceException extends IllegalStateException {
    public CircularInheritanceException(Throwable cause) {
        super(cause);
    }

    public CircularInheritanceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CircularInheritanceException(String s) {
        super(s);
    }

    public CircularInheritanceException() {
    }
}