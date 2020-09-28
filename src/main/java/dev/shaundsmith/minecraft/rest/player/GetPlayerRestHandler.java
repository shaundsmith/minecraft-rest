package dev.shaundsmith.minecraft.rest.player;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.shaundsmith.minecraft.rest.HttpMethod;
import dev.shaundsmith.minecraft.rest.Position;
import dev.shaundsmith.minecraft.rest.QueryParameters;
import dev.shaundsmith.minecraft.rest.RestHttpHandler;
import dev.shaundsmith.minecraft.rest.RestResponse;
import dev.shaundsmith.minecraft.rest.Route;
import dev.shaundsmith.minecraft.rest.exception.ErrorHandler;
import io.undertow.util.HeaderMap;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * REST Handler for returning details about a player with a given ID.
 *
 * <pre>
 * GET: /players/{id}/
 *     {id} - the UUID of the player
 * Returns:
 *     200 response and a {@link PlayerResource} - if the player can be found.
 *     404 response - if the player cannot be found.
 * </pre>
 */
@Route(path = "/players/{id}/", method = HttpMethod.GET)
public class GetPlayerRestHandler extends RestHttpHandler<Void, PlayerResource> {

    private final Supplier<MinecraftServer> serverSupplier;

    public GetPlayerRestHandler(Supplier<MinecraftServer> serverSupplier, List<ErrorHandler> errorHandlers, ObjectMapper objectMapper) {
        super(errorHandlers, objectMapper);
        this.serverSupplier = serverSupplier;
    }

    @Override
    protected RestResponse<PlayerResource> handleRequest(HeaderMap requestHeaders, Void body, QueryParameters queryParameters) {
        MinecraftServer server = serverSupplier.get();

        return server.getPlayerList()
                .getPlayers()
                .stream()
                .filter(player -> player.getUniqueID().equals(UUID.fromString(queryParameters.get("id"))))
                .map(player -> {
                    BlockPos position = player.getPosition();
                    return new PlayerResource(
                            player.getUniqueID(),
                            player.getName(),
                            player.getHealth(),
                            new Position(
                                    position.getX(),
                                    position.getY(),
                                    position.getZ()));
                })
                .map(playerResource -> new RestResponse<>(new HeaderMap(), playerResource, 200))
                .findFirst()
                .orElse(new RestResponse<>(new HeaderMap(), null, 404));
    }
}
