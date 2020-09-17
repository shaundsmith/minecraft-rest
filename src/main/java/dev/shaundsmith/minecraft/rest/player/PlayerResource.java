package dev.shaundsmith.minecraft.rest.player;

import dev.shaundsmith.minecraft.rest.Position;
import lombok.Value;

import java.util.UUID;

/**
 * API resource representing a player.
 */
@Value
class PlayerResource {

    /** The unique identifier of the player. */
    UUID id;

    /** The player's name/ */
    String name;

    /** The player's current health. */
    float health;

    /** The position of the player. */
    Position position;

}
