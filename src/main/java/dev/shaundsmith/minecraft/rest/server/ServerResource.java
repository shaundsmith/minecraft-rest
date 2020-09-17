package dev.shaundsmith.minecraft.rest.server;

import lombok.Value;

/**
 * API resource representing a server configuration.
 */
@Value
class ServerResource {

    /** The 'message of the day' displayed to users of the server. */
    String motd;

    /** The name of the world. */
    String worldName;

    /** The port the server is running on. */
    int port;

    /** Whether the server is running. */
    boolean running;

    /** Whether the server is in online mode. */
    boolean onlineMode;

    /** The build limit of the server. */
    int buildLimit;

}
