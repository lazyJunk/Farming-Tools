package com.lazynessmind.farmingtools.block;

import com.lazynessmind.farmingtools.block.tileentities.TileEntityNatureGather;
import com.lazynessmind.farmingtools.block.tileentities.base.FTBlockTileEntity;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

public class BlockNatureGather extends FTBlockTileEntity<TileEntityNatureGather> {

    public BlockNatureGather(String name) {
        super(Material.IRON, name, true, false);
    }

    @Override
    public Class<TileEntityNatureGather> getTileEntityClass() {
        return TileEntityNatureGather.class;
    }

    @Override
    public TileEntityNatureGather createTileEntity(World world, IBlockState state) {
        return new TileEntityNatureGather();
    }
}
