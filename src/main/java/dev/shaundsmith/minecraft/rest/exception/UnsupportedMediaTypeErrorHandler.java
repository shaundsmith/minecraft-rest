package dev.shaundsmith.minecraft.rest.exception;

import io.undertow.server.HttpServerExchange;

/**
 * Error handler for {@link UnsupportedMediaTypeException}.
 *
 * <p> Returns a 415 (Unsupported Media Type) response code.
 */
class UnsupportedMediaTypeErrorHandler implements ErrorHandler {

    @Override
    public boolean supports(Exception e) {
        return e instanceof UnsupportedMediaTypeException;
    }

    @Override
    public void handle(HttpServerExchange exchange, Exception e) {
        exchange.setStatusCode(415);
    }
}
