package com.lazynessmind.farmingtools.item.base;

import com.lazynessmind.farmingtools.FarmingTools;
import com.lazynessmind.farmingtools.init.FarmingToolsItems;
import com.lazynessmind.farmingtools.interfaces.IHasModel;
import com.lazynessmind.farmingtools.util.RarityUtil;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FTItem extends Item implements IHasModel {

    public RarityUtil rarityUtil;

    public FTItem(String name) {
        setRegistryName(name);
        setCreativeTab(FarmingTools.rndItemsTab);
        setUnlocalizedName(name);

        FarmingToolsItems.ITEMS.add(this);
    }

    public FTItem(String name, RarityUtil rarityUtil) {
        this(name);
        this.rarityUtil = rarityUtil;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return this.rarityUtil.rarity;
    }

    @Override
    public void registerModels() {
        FarmingTools.common.registerItemRenderer(this, 0, "inventory");
    }

    @Override
    public void registerModels(int metadata) {
    }
}
