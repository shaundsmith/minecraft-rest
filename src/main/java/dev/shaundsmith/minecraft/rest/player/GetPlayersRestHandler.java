package dev.shaundsmith.minecraft.rest.player;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.shaundsmith.minecraft.rest.EntriesResource;
import dev.shaundsmith.minecraft.rest.HttpMethod;
import dev.shaundsmith.minecraft.rest.Position;
import dev.shaundsmith.minecraft.rest.QueryParameters;
import dev.shaundsmith.minecraft.rest.RestResponse;
import dev.shaundsmith.minecraft.rest.RestHttpHandler;
import dev.shaundsmith.minecraft.rest.Route;
import dev.shaundsmith.minecraft.rest.exception.ErrorHandler;
import io.undertow.util.HeaderMap;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * REST Handler for returning details all players on the server.
 *
 * <pre>
 * GET: /players/{id}/
 *     {id} - the UUID of the player
 * Returns:
 *     200 response and a collection of {@link PlayerResource}.
 * </pre>
 */
@Route(path = "/players/", method = HttpMethod.GET)
public class GetPlayersRestHandler extends RestHttpHandler<Void, EntriesResource<PlayerResource>> {

    private final Supplier<MinecraftServer> serverSupplier;

    public GetPlayersRestHandler(Supplier<MinecraftServer> serverSupplier, List<ErrorHandler> errorHandlers, ObjectMapper objectMapper) {
        super(errorHandlers, objectMapper);
        this.serverSupplier = serverSupplier;

    }

    @Override
    protected RestResponse<EntriesResource<PlayerResource>> handleRequest(HeaderMap requestHeaders, Void body, QueryParameters queryParameters) {
        MinecraftServer server = serverSupplier.get();

        List<PlayerResource> players = server.getPlayerList()
                .getPlayers()
                .stream()
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
                .collect(Collectors.toList());

        return new RestResponse<>(new HeaderMap(), new EntriesResource<>(players), 200);
    }
}
