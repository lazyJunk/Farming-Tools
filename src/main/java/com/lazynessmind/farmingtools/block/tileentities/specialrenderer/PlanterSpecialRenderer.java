package com.lazynessmind.farmingtools.block.tileentities.specialrenderer;

import com.lazynessmind.farmingtools.block.tileentities.TileEntityPlanter;

public class PlanterSpecialRenderer extends PedestalSpecialRenderer<TileEntityPlanter> {

    @Override
    public void render(TileEntityPlanter te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        renderItemOnTop(te, x, y, z);
        renderRangeArea(te, x, y, z, 0, 0, 255);
    }
}
