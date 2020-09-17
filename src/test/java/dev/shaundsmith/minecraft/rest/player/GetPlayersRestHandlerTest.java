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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static dev.shaundsmith.minecraft.rest.Assertions.assertThat;
import static dev.shaundsmith.minecraft.rest.JsonSupport.jsonArray;
import static dev.shaundsmith.minecraft.rest.JsonSupport.jsonObject;
import static dev.shaundsmith.minecraft.rest.TestServerBuilder.aServer;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GetPlayersRestHandlerTest {

    @Mock MinecraftServer mockMinecraftServer;
    @Mock PlayerList mockPlayerList;
    Undertow server;

    GetPlayersRestHandler sut;

    @BeforeEach
    void beforeEach() {
        sut = new GetPlayersRestHandler(() -> mockMinecraftServer, Collections.emptyList(), new ObjectMapper());
        server = aServer().withHandler(sut).build();
        server.start();
    }

    @AfterEach
    void afterEach() {
        server.stop();
    }

    @Test
    void returns_the_players() throws Exception {
        UUID player1Id = UUID.randomUUID();
        UUID player2Id = UUID.randomUUID();
        List<EntityPlayerMP> players = Arrays.asList(
                TestPlayer.of(player1Id, "test-player-1", 100f, new BlockPos(100, 200, 300)),
                TestPlayer.of(player2Id, "test-player-2", 26.3f, new BlockPos(-500, 11000, 30)));
        given(mockMinecraftServer.getPlayerList()).willReturn(mockPlayerList);
        given(mockPlayerList.getPlayers()).willReturn(players);

        ClientResponse response = HttpClient.get(getServerAddress() + "/players/");

        assertThat(response)
                .hasResponseCode(200)
                .hasBody(jsonObject().add("entries", jsonArray()
                        .add(jsonObject()
                                .add("id", player1Id.toString())
                                .add("name", "test-player-1")
                                .add("health", 100)
                                .add("position", jsonObject()
                                        .add("x", 100)
                                        .add("y", 200)
                                        .add("z", 300)))
                        .add(jsonObject()
                                .add("id", player2Id.toString())
                                .add("name", "test-player-2")
                                .add("health", 26.3)
                                .add("position", jsonObject()
                                        .add("x", -500)
                                        .add("y", 11000)
                                        .add("z", 30))))
                        .add("size", 2));
    }

    private String getServerAddress() {
        Undertow.ListenerInfo listener = server.getListenerInfo().get(0);
        return "http:/" + listener.getAddress().toString();
    }

}