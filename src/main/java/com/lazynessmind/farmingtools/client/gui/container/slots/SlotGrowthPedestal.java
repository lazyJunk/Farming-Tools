package com.lazynessmind.farmingtools.client.gui.container.slots;

import com.lazynessmind.farmingtools.item.ItemAdvancedBoneMeal;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SlotGrowthPedestal extends SlotItemHandler {

    public SlotGrowthPedestal(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return stack.getItem() instanceof ItemAdvancedBoneMeal;
    }


}
