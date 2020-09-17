package dev.shaundsmith.minecraft.rest;

import lombok.Value;

import java.util.Collections;
import java.util.List;

/**
 * API resource representing a collection of API resources.
 *
 * @param <T> the type of API resources stored in the collection
 */
@Value
public class EntriesResource<T> {

    /** The entires in the collection. */
    List<T> entries;

    /**
     * Constructs a new collection of API resources.
     *
     * <p> If {@literal null} entries of provided then the collection will be empty.
     *
     * @param entries the entries in the collection
     */
    public EntriesResource(List<T> entries) {
        this.entries = entries == null ? Collections.emptyList() : entries;
    }

    /**
     * Returns the size of the collection.
     *
     * @return the size of the collection
     */
    public int getSize() {
        return entries.size();
    }

}
