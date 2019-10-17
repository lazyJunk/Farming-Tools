package com.lazynessmind.farmingtools.init.blocks;

import com.lazynessmind.farmingtools.FarmingTools;
import com.lazynessmind.farmingtools.gui.FTGuis;
import com.lazynessmind.farmingtools.init.tileentities.TileEntityHarvester;
import com.lazynessmind.farmingtools.util.ParticleCreator;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockHarvester extends BlockPedestal<TileEntityHarvester> {

    public BlockHarvester(Material material, String name) {
        super(material, name);
        setTickRandomly(true);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        playerIn.openGui(FarmingTools.instance, FTGuis.HARVESTER, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), getTileEntity(worldIn, pos).getMainHandler().getStackInSlot(0));
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
