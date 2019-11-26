package com.lazynessmind.farmingtools.block.base;

import com.lazynessmind.farmingtools.FarmingTools;
import com.lazynessmind.farmingtools.block.tileentities.base.BlockTileEntityBase;
import com.lazynessmind.farmingtools.block.tileentities.base.TileEntityPedestal;
import com.lazynessmind.farmingtools.FarmingToolsItems;
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

public class BlockPedestal<T extends TileEntityPedestal> extends BlockTileEntityBase<T> {

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
        if (getTileEntity(worldIn, pos).canWork()) {
            if (worldIn.rand.nextBoolean()) {
                ParticleCreator.spawnParticle(EnumParticleTypes.PORTAL, worldIn, pos, 7, rand);
            }
        } else if (getTileEntity(worldIn, pos).needRedstonePower()) {
            if (worldIn.rand.nextBoolean()) {
                ParticleCreator.spawnRedstoneParticle(pos, worldIn);
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
