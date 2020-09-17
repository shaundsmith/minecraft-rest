package dev.shaundsmith.minecraft.rest.mod;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.shaundsmith.minecraft.rest.EntriesResource;
import dev.shaundsmith.minecraft.rest.exception.ErrorHandler;
import dev.shaundsmith.minecraft.rest.HttpMethod;
import dev.shaundsmith.minecraft.rest.QueryParameters;
import dev.shaundsmith.minecraft.rest.RestResponse;
import dev.shaundsmith.minecraft.rest.RestHttpHandler;
import dev.shaundsmith.minecraft.rest.Route;
import io.undertow.util.HeaderMap;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Handler for returning details about all mods available on the server.
 *
 * <pre>
 * GET: /mods/{id}/
 *     {id} - the ID of the mod
 * Returns:
 *     200 response and a collection of {@link ModResource}.
 * </pre>
 */
@Route(path = "/mods/", method = HttpMethod.GET)
public class GetModsRestHandler extends RestHttpHandler<Void, EntriesResource<ModResource>> {

    private final Loader loader;

    public GetModsRestHandler(Loader loader, List<ErrorHandler> errorHandlers, ObjectMapper objectMapper) {
        super(errorHandlers, objectMapper);
        this.loader = loader;
    }

    @Override
    protected RestResponse<EntriesResource<ModResource>> handleRequest(HeaderMap requestHeaders, Void body, QueryParameters queryParameters) {
        List<ModContainer> mods = loader.getModList();

        List<ModResource> modResources = mods.stream()
                .map(mod -> new ModResource(mod.getModId(), mod.getName(), mod.getDisplayVersion()))
                .collect(Collectors.toList());

        return new RestResponse<>(new HeaderMap(), new EntriesResource<>(modResources), 200);
    }
}
