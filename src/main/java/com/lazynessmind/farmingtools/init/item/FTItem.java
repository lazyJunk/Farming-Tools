package com.lazynessmind.farmingtools.init.item;

import com.lazynessmind.farmingtools.FarmingTools;
import com.lazynessmind.farmingtools.init.FarmingToolsItems;
import com.lazynessmind.farmingtools.interfaces.IHasModel;
import net.minecraft.item.Item;

public class FTItem extends Item implements IHasModel {

    public FTItem(String name) {
        setRegistryName(name);
        setCreativeTab(FarmingTools.rndItemsTab);
        setUnlocalizedName(name);

        FarmingToolsItems.ITEMS.add(this);
    }

    @Override
    public void registerModels() {
        FarmingTools.common.registerItemRenderer(this, 0, "inventory");
    }

    @Override
    public void registerModels(int metadata) {
    }
}
