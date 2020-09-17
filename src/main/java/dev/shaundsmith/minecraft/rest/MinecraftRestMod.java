package dev.shaundsmith.minecraft.rest;

import dev.shaundsmith.minecraft.rest.exception.ErrorHandlerSupplier;
import io.undertow.Undertow;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

@Mod(modid = "minecraft-rest", name = "Minecraft REST", version = "1.0.0", serverSideOnly = true, acceptableRemoteVersions = "*")
public class MinecraftRestMod {

    private final RouteFactory routeFactory = new RouteFactory(new ErrorHandlerSupplier());
    private Undertow server;

    @EventHandler
    public void createServer(FMLPreInitializationEvent event) {
        server = Undertow.builder()
                .addHttpListener(8080, "localhost")
                .setHandler(routeFactory.createRoutes())
                .build();
    }

    @EventHandler
    public void startServer(FMLPostInitializationEvent event) {
        server.start();
    }

    @EventHandler
    public void stopServer(FMLServerStoppingEvent event) {
        server.stop();
    }

}
