package dev.shaundsmith.minecraft.rest.exception;

import io.undertow.server.HttpServerExchange;

/**
 * Error handler for generic/unknown errors.
 */
class UnknownErrorHandler implements ErrorHandler {

    @Override
    public boolean supports(Exception e) {
        return true;
    }

    @Override
    public void handle(HttpServerExchange exchange, Exception e) {
        exchange.setStatusCode(500);
        exchange.getResponseSender().send(e.getMessage());
    }
}
