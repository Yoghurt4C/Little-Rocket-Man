package mods.littlerocketman.entities;

import mods.littlerocketman.blocks.DavidBlock;
import mods.littlerocketman.registry.LRMBlocks;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;

public class ChompskiBlockEntity extends BlockEntity implements Inventory, BlockEntityClientSerializable {
    private DefaultedList<ItemStack> items = DefaultedList.ofSize(3, ItemStack.EMPTY);

    public ChompskiBlockEntity() {
        super(LRMBlocks.DAVID_BLOCKENTITY);
    }

    public ActionResult onUse(PlayerEntity player, Hand hand, BlockHitResult hit) {
        Direction facing = this.getCachedState().get(DavidBlock.FACING);
        Direction side = hit.getSide();
        if (!side.equals(facing.rotateYClockwise()) && !side.equals(facing.rotateYCounterclockwise()) && !side.equals(Direction.UP)) {
            return ActionResult.FAIL;
        }

        int slot;
        if (side.equals(facing.rotateYCounterclockwise())) slot = 0;
        else if (side.equals(facing.rotateYClockwise())) slot = 1;
        else slot = 2;
        ItemStack stack = player.getStackInHand(hand);
        ItemStack davidStack = getInvStack(slot);

        if (!davidStack.isEmpty()) {
            ItemScatterer.spawn(world, pos, DefaultedList.copyOf(ItemStack.EMPTY, davidStack));
            setInvStack(slot, ItemStack.EMPTY);
        } else if (!stack.isEmpty()) {
            setInvStack(slot, new ItemStack(stack.getItem(), 1));
            stack.decrement(1);
        }
        if (!world.isClient()) {
            this.markDirty();
            this.sync();
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        betterFromTag(tag,items);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        Inventories.toTag(tag,this.items);
        return tag;
    }

    @Override
    public void fromClientTag(CompoundTag tag){this.fromTag(tag);}

    @Override
    public CompoundTag toClientTag(CompoundTag tag){return this.toTag(tag);}

    public static void betterFromTag(CompoundTag tag, DefaultedList<ItemStack> stacks) {
        ListTag listTag = tag.getList("Items", 10);

        if (!listTag.isEmpty()) {
            for (int i = 0; i < listTag.size(); ++i) {
                CompoundTag compoundTag = listTag.getCompound(i);
                int j = compoundTag.getByte("Slot") & 255;
                if (j >= 0 && j < stacks.size()) {
                    stacks.set(j, ItemStack.fromTag(compoundTag));
                }
            }
        } else { stacks.clear(); }
    }

    @Override
    public int getInvSize() {
        return 3;
    }

    @Override
    public boolean isInvEmpty() {
        for (int i = 0; i < getInvSize(); i++) {
            ItemStack stack = getInvStack(i);
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public boolean isInvFull(){
        for (int i = 0; i < getInvSize(); i++) {
            ItemStack stack = getInvStack(i);
            if (stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getInvStack(int slot) {
        return items.get(slot);
    }

    @Override
    public ItemStack takeInvStack(int slot, int count) {
        ItemStack result = Inventories.splitStack(items, slot, count);
        if (!result.isEmpty()) {
            markDirty();
        }
        return result;
    }

    @Override
    public ItemStack removeInvStack(int slot) {
        return Inventories.removeStack(items, slot);
    }

    @Override
    public void setInvStack(int slot, ItemStack stack) {
        items.set(slot, stack);
        if (stack.getCount() > getInvMaxStackAmount()) {
            stack.setCount(getInvMaxStackAmount());
        }
    }

    @Override
    public boolean canPlayerUseInv(PlayerEntity player) { return true; }

    @Override
    public void clear() {items.clear();}

    @Override
    public int getInvMaxStackAmount() { return 1; }
}
