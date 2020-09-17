package dev.shaundsmith.minecraft.rest.player;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.shaundsmith.minecraft.rest.ClientResponse;
import dev.shaundsmith.minecraft.rest.HttpClient;
import io.undertow.Undertow;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.math.BlockPos;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static dev.shaundsmith.minecraft.rest.Assertions.assertThat;
import static dev.shaundsmith.minecraft.rest.JsonSupport.jsonObject;
import static dev.shaundsmith.minecraft.rest.TestServerBuilder.aServer;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GetPlayerRestHandlerTest {

    @Mock MinecraftServer mockMinecraftServer;
    @Mock PlayerList mockPlayerList;
    Undertow server;

    GetPlayerRestHandler sut;

    @BeforeEach
    void beforeEach() {
        sut = new GetPlayerRestHandler(() -> mockMinecraftServer, Collections.emptyList(), new ObjectMapper());
        server = aServer().withHandler(sut).build();
        server.start();
    }

    @AfterEach
    void afterEach() {
        server.stop();
    }

    @Test
    void returns_the_mod_with_the_given_id() throws Exception {
        UUID playerId = UUID.randomUUID();
        List<EntityPlayerMP> players = Collections.singletonList(
                TestPlayer.of(playerId, "test-player", 100f, new BlockPos(100, 200, 300)));
        given(mockMinecraftServer.getPlayerList()).willReturn(mockPlayerList);
        given(mockPlayerList.getPlayers()).willReturn(players);

        ClientResponse response = HttpClient.get(getServerAddress() + "/players/" + playerId + "/");

        assertThat(response)
                .hasResponseCode(200)
                .hasBody(jsonObject()
                        .add("id", playerId.toString())
                        .add("name", "test-player")
                        .add("health", 100)
                        .add("position", jsonObject()
                                .add("x", 100)
                                .add("y", 200)
                                .add("z", 300)));
    }

    @Test
    void mod_with_the_given_id_must_exist() throws Exception {
        given(mockMinecraftServer.getPlayerList()).willReturn(mockPlayerList);
        given(mockPlayerList.getPlayers()).willReturn(Collections.emptyList());
        ClientResponse response = HttpClient.get(getServerAddress() + "/players/player-123/");

        assertThat(response)
                .hasResponseCode(404)
                .doesNotHaveBody();
    }

    private String getServerAddress() {
        Undertow.ListenerInfo listener = server.getListenerInfo().get(0);
        return "http:/" + listener.getAddress().toString();
    }

}