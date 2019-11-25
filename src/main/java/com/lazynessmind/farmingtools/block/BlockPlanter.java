package com.lazynessmind.farmingtools.block;

import com.lazynessmind.farmingtools.FarmingTools;
import com.lazynessmind.farmingtools.block.base.BlockPedestal;
import com.lazynessmind.farmingtools.block.tileentities.TileEntityPlanter;
import com.lazynessmind.farmingtools.block.tileentities.base.TileEntityPedestal;
import com.lazynessmind.farmingtools.client.gui.FTGuis;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPlanter extends BlockPedestal<TileEntityPlanter> {

    public BlockPlanter(String name) {
        super(Material.IRON, name);
        setTickRandomly(true);
        setLightLevel(7f);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        playerIn.openGui(FarmingTools.instance, FTGuis.PLANTER, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        InventoryHelper.dropInventoryItems(worldIn, pos, getTileEntity(worldIn, pos));
    }

    @Override
    public Class<TileEntityPlanter> getTileEntityClass() {
        return TileEntityPlanter.class;
    }

    @Override
    public TileEntityPlanter createTileEntity(World world, IBlockState state) {
        return new TileEntityPlanter();
    }
}
