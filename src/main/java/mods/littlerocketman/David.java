package mods.littlerocketman;

import mods.littlerocketman.registry.LRMBlocks;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class David implements ModInitializer {

    @Override
    public void onInitialize() {
        LRMBlocks.init();
    }

    public static Identifier getId(String name){
        return new Identifier("little_rocket_man", name);
    }

    public static ItemGroup ChompskiGroup = FabricItemGroupBuilder.build(
            getId("core_group"),
            () -> new ItemStack(LRMBlocks.DAVID));
}
