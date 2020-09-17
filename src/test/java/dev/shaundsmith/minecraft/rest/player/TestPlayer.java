package dev.shaundsmith.minecraft.rest.player;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;

import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class TestPlayer {

    static EntityPlayerMP of(UUID id, String name, float health, BlockPos blockPos) {
        EntityPlayerMP player = mock(EntityPlayerMP.class);
        given(player.getUniqueID()).willReturn(id);
        given(player.getName()).willReturn(name);
        given(player.getHealth()).willReturn(health);
        given(player.getPosition()).willReturn(blockPos);

        return player;
    }
}
