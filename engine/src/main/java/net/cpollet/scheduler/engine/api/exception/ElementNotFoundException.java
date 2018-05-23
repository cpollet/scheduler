package net.cpollet.scheduler.engine.api.exception;

public class ElementNotFoundException extends RuntimeException {
    public ElementNotFoundException(String elementId) {
        super(String.format("Element with ID [%s] not found", elementId));
    }
}
