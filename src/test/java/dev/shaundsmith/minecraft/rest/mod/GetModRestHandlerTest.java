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

import java.util.Collections;

import static dev.shaundsmith.minecraft.rest.Assertions.assertThat;
import static dev.shaundsmith.minecraft.rest.JsonSupport.jsonObject;
import static dev.shaundsmith.minecraft.rest.TestServerBuilder.aServer;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GetModRestHandlerTest {

    @Mock Loader mockLoader;
    Undertow server;

    GetModRestHandler sut;

    @BeforeEach
    void beforeEach() {
        sut = new GetModRestHandler(mockLoader, Collections.emptyList(), new ObjectMapper());
        server = aServer().withHandler(sut).build();
        server.start();
    }

    @AfterEach
    void afterEach() {
        server.stop();
    }

    @Test
    void returns_the_mod_with_the_given_id() throws Exception {
        given(mockLoader.getModList())
                .willReturn(Collections.singletonList(new TestModContainer("test-mod", "test mod", "1.0.0")));

        ClientResponse response = HttpClient.get(getServerAddress() + "/mods/test-mod/");

        assertThat(response)
                .hasResponseCode(200)
                .hasBody(jsonObject()
                        .add("id", "test-mod")
                        .add("name", "test mod")
                        .add("version", "1.0.0"));
    }

    @Test
    void mod_with_the_given_id_must_exist() throws Exception {
        given(mockLoader.getModList())
                .willReturn(Collections.singletonList(new TestModContainer("test-mod", "test mod", "1.0.0")));

        ClientResponse response = HttpClient.get(getServerAddress() + "/mods/another-test-mod/");

        assertThat(response)
                .hasResponseCode(404)
                .doesNotHaveBody();
    }

    private String getServerAddress() {
        Undertow.ListenerInfo listener = server.getListenerInfo().get(0);
        return "http:/" + listener.getAddress().toString();
    }

}