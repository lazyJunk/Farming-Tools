package com.lazynessmind.farmingtools.gui.container.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class SlotPlanter extends Slot {

    public SlotPlanter(IInventory itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return stack.getItem() instanceof ItemSeeds || stack.getItem() instanceof ItemSeedFood;
    }
}
