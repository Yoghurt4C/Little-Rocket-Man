package mods.littlerocketman.client;

import mods.littlerocketman.entities.DavidBlockEntityRenderer;
import mods.littlerocketman.registry.LRMBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

import static mods.littlerocketman.registry.LRMBlocks.*;

public class ChompskiClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                DAVID
        );

        BlockEntityRendererRegistry.INSTANCE.register(DAVID_BLOCKENTITY, DavidBlockEntityRenderer::new);
    }
}
