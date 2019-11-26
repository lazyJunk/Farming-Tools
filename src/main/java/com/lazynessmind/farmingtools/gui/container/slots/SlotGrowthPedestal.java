package com.lazynessmind.farmingtools.gui.container.slots;

import com.lazynessmind.farmingtools.item.ItemAdvancedBoneMeal;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class SlotGrowthPedestal extends Slot {

    public SlotGrowthPedestal(IInventory itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return stack.getItem() instanceof ItemAdvancedBoneMeal;
    }


}
