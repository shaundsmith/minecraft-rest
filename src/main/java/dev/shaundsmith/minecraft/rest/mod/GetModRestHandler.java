package dev.shaundsmith.minecraft.rest.mod;

import com.fasterxml.jackson.databind.ObjectMapper;
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

/**
 * REST Handler for returning details about a mod with a given ID.
 *
 * <pre>
 * GET: /mods/{id}/
 *     {id} - the ID of the mod
 * Returns:
 *     200 response and a {@link ModResource} - if the mod can be found.
 *     404 response - if the mod cannot be found.
 * </pre>
 */
@Route(path = "/mods/{id}/", method = HttpMethod.GET)
public class GetModRestHandler extends RestHttpHandler<Void, ModResource> {

    private final Loader loader;

    public GetModRestHandler(Loader loader, List<ErrorHandler> errorHandlers, ObjectMapper objectMapper) {
        super(errorHandlers, objectMapper);
        this.loader = loader;
    }

    @Override
    protected RestResponse<ModResource> handleRequest(HeaderMap requestHeaders, Void body, QueryParameters queryParameters) {
        List<ModContainer> mods = loader.getModList();

        return mods.stream()
                .filter(mod -> mod.getModId().equals(queryParameters.get("id")))
                .map(mod -> new ModResource(mod.getModId(), mod.getName(), mod.getDisplayVersion()))
                .map(modResource -> new RestResponse<>(new HeaderMap(), modResource, 200))
                .findFirst()
                .orElse(new RestResponse<>(new HeaderMap(), null, 404));
    }
}