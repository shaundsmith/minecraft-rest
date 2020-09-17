package dev.shaundsmith.minecraft.rest.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.shaundsmith.minecraft.rest.HttpMethod;
import dev.shaundsmith.minecraft.rest.QueryParameters;
import dev.shaundsmith.minecraft.rest.RestResponse;
import dev.shaundsmith.minecraft.rest.RestHttpHandler;
import dev.shaundsmith.minecraft.rest.Route;
import dev.shaundsmith.minecraft.rest.exception.ErrorHandler;
import io.undertow.util.HeaderMap;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.List;
import java.util.function.Supplier;

/**
 * REST Handler for returning details about a server's configuration.
 *
 * <pre>
 * GET: /server/
 * Returns:
 *     200 response and a {@link ServerResource}.
 * </pre>
 */
@Route(path = "/server/", method = HttpMethod.GET)
public class GetServerDetailsRestHandler extends RestHttpHandler<Void, ServerResource> {

    private final Supplier<MinecraftServer> serverSupplier;

    public GetServerDetailsRestHandler(Supplier<MinecraftServer> serverSupplier, List<ErrorHandler> errorHandlers, ObjectMapper objectMapper) {
        super(errorHandlers, objectMapper);
        this.serverSupplier = serverSupplier;
    }

    @Override
    protected RestResponse<ServerResource> handleRequest(HeaderMap requestHeaders, Void body, QueryParameters queryParameters) {
        MinecraftServer server = serverSupplier.get();

        ServerResource resource = new ServerResource(
                server.getMOTD(),
                server.getFolderName(),
                server.getServerPort(),
                server.isServerRunning(),
                server.isServerInOnlineMode(),
                server.getBuildLimit());

        return new RestResponse<>(new HeaderMap(), resource, 200);
    }
}
