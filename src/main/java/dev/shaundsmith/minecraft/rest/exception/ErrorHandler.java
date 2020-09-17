package dev.shaundsmith.minecraft.rest.exception;

import io.undertow.server.HttpServerExchange;

public interface ErrorHandler {

    /**
     * Returns true if the error handler supports the given exception.
     *
     * @param e the exception to test
     *
     * @return {@literal true} if the error handler supports the exception, {@literal false} otherwise.
     */
    boolean supports(Exception e);

    /**
     * Handles the given exception.
     *
     * @param exchange the HTTP exchange for the current HTTP request
     * @param e the exception to test
     */
    void handle(HttpServerExchange exchange, Exception e);

}
