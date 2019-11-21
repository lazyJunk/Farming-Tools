package com.lazynessmind.farmingtools.block;

import com.lazynessmind.farmingtools.block.tileentities.TileEntityNatureGather;
import com.lazynessmind.farmingtools.block.tileentities.base.FTBlockTileEntity;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockNatureGather extends FTBlockTileEntity<TileEntityNatureGather> {

    public BlockNatureGather(String name) {
        super(Material.IRON, name, true, false);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntityNatureGather.resetCurrentAmount();
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
