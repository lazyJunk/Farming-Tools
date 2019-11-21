package com.lazynessmind.farmingtools.block.base;

import com.lazynessmind.farmingtools.FarmingTools;
import com.lazynessmind.farmingtools.block.tileentities.TileEntityNatureGather;
import com.lazynessmind.farmingtools.block.tileentities.base.FTBlockTileEntity;
import com.lazynessmind.farmingtools.block.tileentities.base.TileEntityPedestal;
import com.lazynessmind.farmingtools.init.FarmingToolsItems;
import com.lazynessmind.farmingtools.item.ItemBlockPedestal;
import com.lazynessmind.farmingtools.util.Enum;
import com.lazynessmind.farmingtools.util.ParticleCreator;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockPedestal<T extends TileEntityPedestal> extends FTBlockTileEntity<T> {

    public static final PropertyInteger META = PropertyInteger.create("meta", 0, 3);
    private static final AxisAlignedBB BASE_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.6875D, 0.9375D);

    public BlockPedestal(Material material, String name) {
        super(material, name, true, true);
        this.setDefaultState(this.blockState.getBaseState().withProperty(this.getMetaProperty(), Integer.valueOf(0)));
        setTickRandomly(true);
        setHardness(1.0f);
        setHarvestLevel("wood", 0);
        FarmingToolsItems.ITEMS.add(new ItemBlockPedestal(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (int i = 0; i < Enum.Type.values().length; i++) {
            items.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(META, meta);
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

    public PropertyInteger getMetaProperty() {
        return META;
    }

    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(this.getMetaProperty(), meta);
    }

    public int getMetaFromState(IBlockState state) {
        return state.getValue(this.getMetaProperty());
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new PropertyInteger[]{META});
    }

    @Override
    public int damageDropped(IBlockState state) {
        return (int) (getMetaFromState(state));
    }

    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (worldIn.getWorldInfo().getWorldTime() < 12500) {
            if (worldIn.rand.nextBoolean())
                ParticleCreator.spawnParticle(EnumParticleTypes.PORTAL, worldIn, pos, 7, rand);
        } else if (getTileEntity(worldIn, pos).needRedstonePower()) {
            if (worldIn.rand.nextBoolean()) {
                int i = ((Integer) stateIn.getValue(META)).intValue();
                double d0 = (double) pos.getX() + 0.5D + ((double) rand.nextFloat() - 0.5D) * 0.2D;
                double d1 = (double) ((float) pos.getY() + 0.9735D);
                double d2 = (double) pos.getZ() + 0.5D + ((double) rand.nextFloat() - 0.5D) * 0.2D;
                float f = (float) i / 15.0F;
                float f1 = f * 0.6F + 0.4F;
                float f2 = Math.max(0.0F, f * f * 0.7F - 0.5F);
                float f3 = Math.max(0.0F, f * f * 0.6F - 0.7F);
                worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d0, d1, d2, (double) f1, (double) f2, (double) f3);
            }
        }
    }

    @Override
    public void registerModels(int metadata) {
        for (int i = 0; i < Enum.Type.values().length; i++) {
            FarmingTools.common.registerItemsRenderer(Item.getItemFromBlock(this), i, "inventory", Enum.Type.values()[i].getName());
        }
    }

    @Override
    public Class<T> getTileEntityClass() {
        return null;
    }

    @Override
    public T createTileEntity(World world, IBlockState state) {
        return null;
    }
}
