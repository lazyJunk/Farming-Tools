package com.lazynessmind.farmingtools.client.specialrenderer;

import com.lazynessmind.farmingtools.block.tileentities.TileEntityHarvester;

public class HarvesterSpecialRenderer extends PedestalSpecialRenderer<TileEntityHarvester> {

    @Override
    public void render(TileEntityHarvester te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        renderItemOnTop(te, x, y, z);
        renderRangeArea(te, x, y, z);
    }
}
