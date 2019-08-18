package com.lazynessmind.farmingtools.init.blocks;

import com.lazynessmind.farmingtools.FarmingTools;
import com.lazynessmind.farmingtools.gui.FTGuis;
import com.lazynessmind.farmingtools.init.tileentities.FTBlockTileEntity;
import com.lazynessmind.farmingtools.init.tileentities.TileEntityHarvester;
import com.lazynessmind.farmingtools.util.ParticleCreator;
import com.lazynessmind.farmingtools.util.SpawnUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockHarvester extends FTBlockTileEntity<TileEntityHarvester> {

    private static final AxisAlignedBB BASE_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.5625D, 0.9375D);

    public BlockHarvester(Material material, String name) {
        super(material, name, true);
        setTickRandomly(true);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TextFormatting.GREEN + "I need a hoe.");
        if(advanced.isAdvanced()){
            tooltip.add(" ");
            tooltip.add(TextFormatting.GREEN + "Range: " + TextFormatting.WHITE + " 1x1x1");
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntityHarvester tileEntityHarvester = getTileEntity(worldIn, pos);
        SpawnUtils.spawnItemAt(worldIn, pos, tileEntityHarvester.getHandler().getStackInSlot(0));
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        playerIn.openGui(FarmingTools.instance, FTGuis.HARVESTER, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BASE_AABB;
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
    public Class<TileEntityHarvester> getTileEntityClass() {
        return TileEntityHarvester.class;
    }

    @Override
    public TileEntityHarvester createTileEntity(World world, IBlockState state) {
        return new TileEntityHarvester();
    }
}
