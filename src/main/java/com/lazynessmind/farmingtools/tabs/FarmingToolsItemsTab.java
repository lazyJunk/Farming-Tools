package com.lazynessmind.farmingtools.tabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class FarmingToolsItemsTab extends CreativeTabs {

    public FarmingToolsItemsTab() {
        super("ftItemsTab");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(Items.GOLDEN_CARROT);
    }
}
