package com.lazynessmind.farmingtools.init.blocks;

import com.lazynessmind.farmingtools.init.tileentities.FTBlockTileEntity;
import com.lazynessmind.farmingtools.init.tileentities.TileEntityFertilizedSoil;
import com.lazynessmind.farmingtools.util.ParticleCreator;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockFertilizedSoil extends FTBlockTileEntity<TileEntityFertilizedSoil> {

    //Copied base AABB from minecraft farmland
    private static final AxisAlignedBB COPIED_FARMLAND_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);

    public BlockFertilizedSoil(Material materialIn, String name) {
        super(materialIn, name, true);
        setTickRandomly(true);
        setHardness(0.5f);
        setHarvestLevel("wood", 0);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TextFormatting.GREEN + "Only work during day time.");
        tooltip.add(TextFormatting.GREEN + "Water isn't a problem.");
    }

    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        super.randomDisplayTick(stateIn, worldIn, pos, rand);
        if(worldIn.getWorldInfo().getWorldTime() < 12500){
            ParticleCreator.spawnParticle(EnumParticleTypes.WATER_SPLASH, worldIn, pos, 10, rand);
        }
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        return true;
    }

    @Override
    public Class<TileEntityFertilizedSoil> getTileEntityClass() {
        return TileEntityFertilizedSoil.class;
    }

    @Override
    public TileEntityFertilizedSoil createTileEntity(World world, IBlockState state) {
        return new TileEntityFertilizedSoil();
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return COPIED_FARMLAND_AABB;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
}
