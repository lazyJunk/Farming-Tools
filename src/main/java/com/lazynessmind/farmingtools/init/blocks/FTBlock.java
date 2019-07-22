package com.lazynessmind.farmingtools.init.blocks;

import com.lazynessmind.farmingtools.FarmingTools;
import com.lazynessmind.farmingtools.init.FarmingToolsBlocks;
import com.lazynessmind.farmingtools.init.FarmingToolsItems;
import com.lazynessmind.farmingtools.interfaces.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class FTBlock extends Block implements IHasModel {

    public FTBlock(Material materialIn, String name) {
        super(materialIn);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(FarmingTools.rndBlocksTab);

        FarmingToolsBlocks.BLOCKS.add(this);
        FarmingToolsItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public void registerModels() {
        FarmingTools.common.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    @Override
    public void registerModels(int metadata) {

    }
}
