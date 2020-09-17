package dev.shaundsmith.minecraft.rest.mod;

import lombok.Value;

/**
 * API resource representing a Minecraft mod.
 */
@Value
class ModResource {

    /** The ID of the mod. */
    String id;

    /** The display name of the mod. */
    String name;

    /** The version of the mod. */
    String version;

}
