package dev.shaundsmith.minecraft.rest;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.RoutingHandler;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Test helper for building Undertow servers.
 */
public class TestServerBuilder {

    private final RoutingHandler routingHandler = Handlers.routing();

    public static TestServerBuilder aServer() {
        return new TestServerBuilder();
    }

    public TestServerBuilder withHandler(RestHttpHandler<?, ?> handler) {
        Route route = handler.getClass().getAnnotation(Route.class);
        routingHandler.add(route.method().name(), route.path(), handler);

        return this;
    }

    public Undertow build() {
        return Undertow.builder()
                .addHttpListener(getRandomPort(), "localhost")
                .setHandler(routingHandler)
                .build();
    }

    private int getRandomPort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        } catch (IOException e) {
            throw new RuntimeException("Failed to create test server", e);
        }
    }

}
