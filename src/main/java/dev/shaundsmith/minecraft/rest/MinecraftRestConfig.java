package dev.shaundsmith.minecraft.rest;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraftforge.common.config.Config;

@Config(modid = "minecraft-rest")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MinecraftRestConfig {

    @Config.Name("port")
    @Config.RangeInt(min = 1, max = 65535)
    @Config.Comment({
            "The port to run the REST API on. (Default 8080).",
            "Warning, setting this to a value <1024 may cause issues depending on your operating system."
    })
    public static int serverPort = 8080;

}
