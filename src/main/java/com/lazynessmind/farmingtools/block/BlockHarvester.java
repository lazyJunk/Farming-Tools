package com.lazynessmind.farmingtools.block;

import com.lazynessmind.farmingtools.FarmingTools;
import com.lazynessmind.farmingtools.block.base.BlockPedestal;
import com.lazynessmind.farmingtools.block.tileentities.TileEntityHarvester;
import com.lazynessmind.farmingtools.block.tileentities.TileEntityNatureGather;
import com.lazynessmind.farmingtools.client.gui.FTGuis;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockHarvester extends BlockPedestal<TileEntityHarvester> {

    public BlockHarvester(String name) {
        super(Material.IRON, name);
        setTickRandomly(true);
        setLightLevel(7f);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        playerIn.openGui(FarmingTools.instance, FTGuis.HARVESTER, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), getTileEntity(worldIn, pos).mainSlot());
        TileEntityNatureGather.decreaseChangeToDestroy(0.3);
    }

    @Override
    public Class<TileEntityHarvester> getTileEntityClass() {
        return TileEntityHarvester.class;
    }

    @Override
    public TileEntityHarvester createTileEntity(World world, IBlockState state) {
        return new TileEntityHarvester();
    }
}
