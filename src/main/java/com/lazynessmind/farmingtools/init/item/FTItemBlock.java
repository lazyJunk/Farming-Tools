package com.lazynessmind.farmingtools.init.item;

import com.lazynessmind.farmingtools.FarmingTools;
import com.lazynessmind.farmingtools.init.FarmingToolsItems;
import com.lazynessmind.farmingtools.util.RarityUtil;
import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class FTItemBlock extends ItemBlock {

    private RarityUtil rarityUtil;

    public FTItemBlock(Block block, String name) {
        super(block);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(FarmingTools.rndBlocksTab);
        FarmingToolsItems.ITEMS.add(this);
    }

    public FTItemBlock(Block block, String name, RarityUtil rarityUtil) {
        this(block, name);
        this.rarityUtil = rarityUtil;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return rarityUtil.rarity;
    }
}
