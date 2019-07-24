package com.lazynessmind.farmingtools.tabs;

import com.lazynessmind.farmingtools.init.FarmingToolsItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class FarmingToolsItemsTab extends CreativeTabs {

    public FarmingToolsItemsTab() {
        super("ftItemsTab");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(FarmingToolsItems.ADVANCED_BONE_MEAL);
    }
}
