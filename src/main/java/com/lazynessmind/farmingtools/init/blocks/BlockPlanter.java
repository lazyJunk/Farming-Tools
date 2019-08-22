package com.lazynessmind.farmingtools.init.blocks;

import com.lazynessmind.farmingtools.FarmingTools;
import com.lazynessmind.farmingtools.gui.FTGuis;
import com.lazynessmind.farmingtools.init.FarmingToolsItems;
import com.lazynessmind.farmingtools.init.item.ItemTypeUpgrade;
import com.lazynessmind.farmingtools.init.tileentities.TileEntityPedestal;
import com.lazynessmind.farmingtools.init.tileentities.TileEntityPlanter;
import com.lazynessmind.farmingtools.util.ParticleCreator;
import com.lazynessmind.farmingtools.util.SpawnUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockPlanter extends BlockPedestal<TileEntityPlanter> {

    public BlockPlanter(Material material, String name) {
        super(material, name);
        this.setDefaultState(this.blockState.getBaseState().withProperty(this.getMetaProperty(), Integer.valueOf(0)));
        setTickRandomly(true);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        playerIn.openGui(FarmingTools.instance, FTGuis.PLANTER, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntityPlanter tileEntityPlanter = getTileEntity(worldIn, pos);
        InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), tileEntityPlanter.getHandler().getStackInSlot(0));
    }

    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (worldIn.getWorldInfo().getWorldTime() < 12500) {
            ParticleCreator.spawnParticle(EnumParticleTypes.PORTAL, worldIn, pos, 10, rand);
        }
        if (getTileEntity(worldIn, pos).needRedstonePower()) {
            ParticleCreator.spawnParticle(EnumParticleTypes.SPELL, worldIn, pos, 5, rand);
        }
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
