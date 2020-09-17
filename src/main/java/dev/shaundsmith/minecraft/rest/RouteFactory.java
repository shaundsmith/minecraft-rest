package dev.shaundsmith.minecraft.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.shaundsmith.minecraft.rest.exception.ErrorHandler;
import dev.shaundsmith.minecraft.rest.exception.ErrorHandlerSupplier;
import dev.shaundsmith.minecraft.rest.mod.GetModRestHandler;
import dev.shaundsmith.minecraft.rest.mod.GetModsRestHandler;
import dev.shaundsmith.minecraft.rest.player.GetPlayerRestHandler;
import dev.shaundsmith.minecraft.rest.player.GetPlayersRestHandler;
import dev.shaundsmith.minecraft.rest.server.GetServerDetailsRestHandler;
import io.undertow.Handlers;
import io.undertow.server.HttpHandler;
import io.undertow.server.RoutingHandler;
import lombok.AllArgsConstructor;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class RouteFactory {

    private final ErrorHandlerSupplier errorHandlerSupplier;

    public HttpHandler createRoutes() {
        RoutingHandler routingHandler = Handlers.routing();

        for (RestHttpHandler<?, ?> handler : getHandlers()) {
            Route route = handler.getClass().getAnnotation(Route.class);
            routingHandler.add(route.method().name(), route.path(), handler);
        }

        return routingHandler;
    }

    private List<RestHttpHandler<?, ?>> getHandlers() {
        List<ErrorHandler> errorHandlers = errorHandlerSupplier.get();
        ObjectMapper objectMapper = new ObjectMapper();
        List<RestHttpHandler<?, ?>> handlers = new ArrayList<>();

        handlers.add(new GetServerDetailsRestHandler(() -> FMLCommonHandler.instance().getMinecraftServerInstance(),
                errorHandlers, objectMapper));
        handlers.add(new GetModsRestHandler(Loader.instance(), errorHandlers, objectMapper));
        handlers.add(new GetModRestHandler(Loader.instance(), errorHandlers, objectMapper));
        handlers.add(new GetPlayersRestHandler(() -> FMLCommonHandler.instance().getMinecraftServerInstance(),
                errorHandlers, objectMapper));
        handlers.add(new GetPlayerRestHandler(() -> FMLCommonHandler.instance().getMinecraftServerInstance(),
                errorHandlers, objectMapper));

        return handlers;
    }

}
