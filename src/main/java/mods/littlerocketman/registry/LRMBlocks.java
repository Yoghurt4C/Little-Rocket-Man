package mods.littlerocketman.registry;

import mods.littlerocketman.David;
import mods.littlerocketman.blocks.DavidBlock;
import mods.littlerocketman.entities.ChompskiBlockEntity;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class LRMBlocks {
    public static final Block DAVID = new DavidBlock(FabricBlockSettings.copy(Blocks.TERRACOTTA).nonOpaque().build());
    public static final BlockEntityType<ChompskiBlockEntity> DAVID_BLOCKENTITY = BlockEntityType.Builder.create(ChompskiBlockEntity::new, DAVID).build(null);

    public static void init(){
        register("david",DAVID);
        register("david",DAVID_BLOCKENTITY);

    }

    public static BlockItem register(String name, Block block, Item.Settings settings) {
        Identifier id = David.getId(name);
        Registry.register(Registry.BLOCK, id, block);
        BlockItem item = new BlockItem(block, settings);
        item.appendBlocks(Item.BLOCK_ITEMS, item);
        Registry.register(Registry.ITEM, id, item);
        return item;
    }

    public static BlockItem register(String name, Block block) {
        return register(name, block, new Item.Settings().group(David.ChompskiGroup));
    }

    private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> build) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, David.getId(name), build);
    }
}
