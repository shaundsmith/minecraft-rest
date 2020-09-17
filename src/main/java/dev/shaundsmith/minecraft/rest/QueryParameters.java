package dev.shaundsmith.minecraft.rest;

import lombok.AllArgsConstructor;

import java.util.Deque;
import java.util.Map;

/**
 * Represents any query parameters provided in the URL.
 */
@AllArgsConstructor
public class QueryParameters {

    /** The query parameters. */
    private final Map<String, Deque<String>> parameters;

    /**
     * Returns the query parameter with the given key.
     *
     * @param key the query parameter key
     *
     * @return the query parameter value
     */
    public String get(String key) {
        return parameters.containsKey(key) ? parameters.get(key).getFirst() : null;
    }

}
