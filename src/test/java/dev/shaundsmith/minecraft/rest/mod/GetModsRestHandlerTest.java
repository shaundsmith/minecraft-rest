package dev.shaundsmith.minecraft.rest.mod;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.shaundsmith.minecraft.rest.ClientResponse;
import dev.shaundsmith.minecraft.rest.HttpClient;
import io.undertow.Undertow;
import net.minecraftforge.fml.common.Loader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;

import static dev.shaundsmith.minecraft.rest.Assertions.assertThat;
import static dev.shaundsmith.minecraft.rest.JsonSupport.jsonArray;
import static dev.shaundsmith.minecraft.rest.JsonSupport.jsonObject;
import static dev.shaundsmith.minecraft.rest.TestServerBuilder.aServer;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GetModsRestHandlerTest {

    @Mock Loader mockLoader;
    Undertow server;

    GetModsRestHandler sut;

    @BeforeEach
    void beforeEach() {
        sut = new GetModsRestHandler(mockLoader, Collections.emptyList(), new ObjectMapper());
        server = aServer().withHandler(sut).build();
        server.start();
    }

    @AfterEach
    void afterEach() {
        server.stop();
    }

    @Test
    void returns_the_mods() throws Exception {
        given(mockLoader.getModList())
                .willReturn(Arrays.asList(
                        new TestModContainer("test-mod-1", "test mod 1", "1.0.0"),
                        new TestModContainer("test-mod-2", "test mod 2", "0.0.4.alpha")));

        ClientResponse response = HttpClient.get(getServerAddress() + "/mods/");

        assertThat(response)
                .hasResponseCode(200)
                .hasBody(jsonObject()
                        .add("entries", jsonArray()
                                .add(jsonObject()
                                        .add("id", "test-mod-1")
                                        .add("name", "test mod 1")
                                        .add("version", "1.0.0"))
                                .add(jsonObject()
                                        .add("id", "test-mod-2")
                                        .add("name", "test mod 2")
                                        .add("version", "0.0.4.alpha")))
                        .add("size", 2));
    }

    private String getServerAddress() {
        Undertow.ListenerInfo listener = server.getListenerInfo().get(0);
        return "http:/" + listener.getAddress().toString();
    }

}