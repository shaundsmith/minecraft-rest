package dev.shaundsmith.minecraft.rest.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.shaundsmith.minecraft.rest.ClientResponse;
import dev.shaundsmith.minecraft.rest.HttpClient;
import io.undertow.Undertow;
import net.minecraft.server.MinecraftServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static dev.shaundsmith.minecraft.rest.Assertions.assertThat;
import static dev.shaundsmith.minecraft.rest.JsonSupport.jsonObject;
import static dev.shaundsmith.minecraft.rest.TestServerBuilder.aServer;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GetServerDetailsRestHandlerTest {

    @Mock MinecraftServer mockMinecraftServer;
    Undertow server;

    GetServerDetailsRestHandler sut;

    @BeforeEach
    void beforeEach() {
        sut = new GetServerDetailsRestHandler(() -> mockMinecraftServer, Collections.emptyList(), new ObjectMapper());
        server = aServer().withHandler(sut).build();
        server.start();
    }

    @AfterEach
    void afterEach() {
        server.stop();
    }

    @Test
    void returns_the_server_details() throws Exception {
        given(mockMinecraftServer.getMOTD()).willReturn("The motd");
        given(mockMinecraftServer.getFolderName()).willReturn("the-folder");
        given(mockMinecraftServer.getServerPort()).willReturn(12345);
        given(mockMinecraftServer.isServerRunning()).willReturn(false);
        given(mockMinecraftServer.isServerInOnlineMode()).willReturn(true);
        given(mockMinecraftServer.getBuildLimit()).willReturn(255);

        ClientResponse response = HttpClient.get(getServerAddress() + "/server/");

        assertThat(response)
                .hasResponseCode(200)
                .hasBody(jsonObject()
                        .add("motd", "The motd")
                        .add("worldName", "the-folder")
                        .add("port", 12345)
                        .add("running", false)
                        .add("onlineMode", true)
                        .add("buildLimit", 255));
    }

    private String getServerAddress() {
        Undertow.ListenerInfo listener = server.getListenerInfo().get(0);
        return "http:/" + listener.getAddress().toString();
    }

}
