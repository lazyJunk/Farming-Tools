package com.lazynessmind.farmingtools.block.tileentities.specialrenderer;

import com.lazynessmind.farmingtools.block.tileentities.TileEntityGrowthPedestal;

public class GrowthPedestalSpecialRenderer extends PedestalSpecialRenderer<TileEntityGrowthPedestal> {

    @Override
    public void render(TileEntityGrowthPedestal te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        renderItemOnTop(te, x, y, z);
        renderRangeArea(te, x, y, z, 255, 0, 0);
    }
}
