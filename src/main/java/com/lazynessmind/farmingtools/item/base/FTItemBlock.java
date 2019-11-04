package com.lazynessmind.farmingtools.item.base;

import com.lazynessmind.farmingtools.util.RarityUtil;
import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class FTItemBlock extends ItemBlock {

    public RarityUtil rarityUtil;

    public FTItemBlock(Block block, boolean hasSubtype, RarityUtil rarityUtil){
        super(block);
        setHasSubtypes(hasSubtype);
        this.rarityUtil = rarityUtil;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return this.rarityUtil.rarity;
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }
}
