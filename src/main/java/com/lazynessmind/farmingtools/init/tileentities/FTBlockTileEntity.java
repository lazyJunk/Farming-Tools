package com.lazynessmind.farmingtools.init.tileentities;

import com.lazynessmind.farmingtools.init.blocks.FTBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class FTBlockTileEntity<TE extends TileEntity> extends FTBlock {

    public FTBlockTileEntity(Material material, String name, boolean creative) {
        super(material, name, creative);
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
