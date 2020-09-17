package dev.shaundsmith.minecraft.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.shaundsmith.minecraft.rest.exception.ErrorHandler;
import dev.shaundsmith.minecraft.rest.exception.UnsupportedMediaTypeException;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HeaderMap;
import io.undertow.util.Headers;
import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Base HTTP handler for handling REST requests.
 *
 * @param <T> the type of the request body
 * @param <U> the type of the response body
 */
@AllArgsConstructor
public abstract class RestHttpHandler<T, U> implements HttpHandler {

    /** Error handlers supported by this Http Handler. */
    private final List<ErrorHandler> errorHandlers;

    /** Object mapper to use for serializing/deserializing JSON. */
    private final ObjectMapper objectMapper;

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        // Dispatch to a worker thread
        if (exchange.isInIoThread()) {
            exchange.dispatch(this);
            return;
        }

        try {
            validateHeaders(exchange.getRequestHeaders());
            RestResponse<U> response = handleRequest(exchange.getRequestHeaders(),
                    getRequestBodyAsJson(exchange),
                    new QueryParameters(exchange.getQueryParameters()));

            buildResponseHeaders(exchange, response);
            exchange.setStatusCode(response.getResponseCode());
            if (response.getBody() != null) {
                exchange.getResponseSender().send(objectMapper.writeValueAsString(response.getBody()));
            }
        } catch (Exception e) {
            errorHandlers.stream()
                    .filter(handler -> handler.supports(e))
                    .findFirst()
                    .ifPresent(handler -> handler.handle(exchange, e));
        }
    }

    /**
     * Handles the request with the given headers, body, and query parameters.
     *
     * @param requestHeaders the request headers provided by the client
     * @param body the request body
     * @param queryParameters the query parameters from the URL
     *
     * @return the response to return to the client
     */
    protected abstract RestResponse<U> handleRequest(HeaderMap requestHeaders, T body, QueryParameters queryParameters);

    private void validateHeaders(HeaderMap requestHeaders) throws UnsupportedMediaTypeException {
        if (requestHeaders.get(Headers.CONTENT_TYPE) != null &&
                !requestHeaders.get(Headers.CONTENT_TYPE).contains("application/json")) {
            throw new UnsupportedMediaTypeException();
        }
    }


    private T getRequestBodyAsJson(HttpServerExchange exchange) throws IOException {
        exchange.startBlocking();
        BufferedReader br = new BufferedReader(new InputStreamReader(exchange.getInputStream(), StandardCharsets.UTF_8));
        String jsonString = br.lines().collect(Collectors.joining());

        return jsonString.isEmpty() ? null : objectMapper.readValue(jsonString, new TypeReference<T>() {});
    }

    private void buildResponseHeaders(HttpServerExchange exchange, RestResponse<U> response) {
        response.getHeaders().forEach(header -> exchange.getResponseHeaders().putAll(
                header.getHeaderName(),
                new ArrayList<>(header)));
        exchange.getResponseHeaders().add(Headers.CONTENT_TYPE, "application/json");
    }

}
