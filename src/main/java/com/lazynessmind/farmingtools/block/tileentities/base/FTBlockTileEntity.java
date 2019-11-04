package com.lazynessmind.farmingtools.block.tileentities.base;

import com.lazynessmind.farmingtools.block.base.FTBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class FTBlockTileEntity<TE extends TileEntity> extends FTBlock {

    public FTBlockTileEntity(Material material, String name, boolean creative, boolean hasItemBlock) {
        super(material, name, creative, hasItemBlock);
    }

    public abstract Class<TE> getTileEntityClass();

    public TE getTileEntity(IBlockAccess world, BlockPos pos) {
        return (TE) world.getTileEntity(pos);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public abstract TE createTileEntity(World world, IBlockState state);

    public void scheduleUpdate(World world, BlockPos pos){
        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
    }
}
