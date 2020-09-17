package dev.shaundsmith.minecraft.rest;

import io.undertow.util.HeaderMap;
import lombok.Value;

/**
 * The HTTP REST response to send back to the client.
 *
 * @param <T> the type of body provided in the response
 */
@Value
public class RestResponse<T> {

    /** The response headers. */
    HeaderMap headers;

    /** The response body. */
    T body;

    /** The response code. */
    int responseCode;

}
