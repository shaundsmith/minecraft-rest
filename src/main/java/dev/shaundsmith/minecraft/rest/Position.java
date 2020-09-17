package dev.shaundsmith.minecraft.rest;

import lombok.Value;

/**
 * Structure representing the position of an entity.
 */
@Value
public class Position {

    /** X-axis position. */
    double x;

    /** Y-axis position. */
    double y;

    /** Z-axis position. */
    double z;

}
