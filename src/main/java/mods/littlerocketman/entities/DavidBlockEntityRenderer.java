package mods.littlerocketman.entities;

import mods.littlerocketman.blocks.DavidBlock;
import mods.littlerocketman.registry.LRMBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.SignBlock;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.BlockPos;

public class DavidBlockEntityRenderer extends BlockEntityRenderer<ChompskiBlockEntity> {
    private MinecraftClient client = MinecraftClient.getInstance();
    private final BakedModel model = client.getBlockRenderManager().getModel(LRMBlocks.DAVID.getDefaultState());

    public DavidBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(ChompskiBlockEntity david, float tickDelta, MatrixStack matrices, VertexConsumerProvider vcon, int light, int overlay) {
        try {
            matrices.push();
            render2(david, tickDelta, matrices, vcon, light, overlay);
        } catch (Exception e){
            // ;)
        } finally {
            matrices.pop();
        }
    }

    public void render2(ChompskiBlockEntity david, float tickDelta, MatrixStack matrices, VertexConsumerProvider vcon, int light, int overlay){
        BlockState state = david.getCachedState();
        BlockPos pos = david.getPos();
        //BlockPos centerPos = new BlockPos(pos.getX()+0.5,pos.getY(),pos.getZ()+0.5);
        float g = 0.6666667F;
        float h;
        matrices.translate(0.5D, 0.0D, 0.5D);
        h = -((float)(state.get(DavidBlock.ROTATION) * 360) / 16.0F);
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(h));
        matrices.push();
        matrices.translate(-0.5D,0,-0.5D);
        client.getBlockRenderManager().getModelRenderer().render(david.getWorld(),model,state,pos,matrices,vcon.getBuffer(RenderLayer.getCutout()),false,david.getWorld().getRandom(),42,overlay);
        matrices.pop();
        matrices.scale(0.5f,0.5f,0.5f);
        matrices.translate(0,0.25,0);
        renderLeftItem(david,matrices,vcon,light,overlay);
        renderRightItem(david,matrices,vcon,light,overlay);
        renderHeadItem(david,matrices,vcon,light,overlay);
    }

    public void renderLeftItem(ChompskiBlockEntity david, MatrixStack matrices, VertexConsumerProvider vcon, int light, int overlay){
        matrices.push();
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-90));
        matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(-115));
        matrices.translate(-0.4f, 0.25f, -0.45f);
        client.getItemRenderer().renderItem(david.getStack(0), ModelTransformation.Mode.FIXED, light, overlay, matrices, vcon);
        matrices.pop();
    }

    public void renderRightItem(ChompskiBlockEntity david, MatrixStack matrices, VertexConsumerProvider vcon, int light, int overlay){
        matrices.push();
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-90));
        matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(-115));
        matrices.translate(-0.4f, 0.25f, 0.45f);
        client.getItemRenderer().renderItem(david.getStack(1), ModelTransformation.Mode.FIXED, light, overlay, matrices, vcon);
        matrices.pop();
    }
    public void renderHeadItem(ChompskiBlockEntity david, MatrixStack matrices, VertexConsumerProvider vcon, int light, int overlay){
        matrices.push();
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180));
        matrices.translate(0f, 1.7f, 0.1f);
        matrices.scale(0.75f,0.75f,0.75f);
        client.getItemRenderer().renderItem(david.getStack(2), ModelTransformation.Mode.FIXED, light, overlay, matrices, vcon);
        matrices.pop();
    }
}
