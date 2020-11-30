package dev.ditsche.mailo.exception;

/**
 * @author Tobias Dittmann
 */
public class TemplateNotFoundException extends RuntimeException {
    public TemplateNotFoundException(String m) {
        super(m);
    }
}
