package com.lazynessmind.farmingtools.tabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FarmingToolsBlocksTab extends CreativeTabs {

    public FarmingToolsBlocksTab() {
        super("ftBlocksTab");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(Item.getItemFromBlock(Blocks.DIRT));
    }
}
