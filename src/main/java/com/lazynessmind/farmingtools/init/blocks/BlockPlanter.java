package com.lazynessmind.farmingtools.init.blocks;

import com.lazynessmind.farmingtools.FarmingTools;
import com.lazynessmind.farmingtools.gui.FTGuis;
import com.lazynessmind.farmingtools.init.FarmingToolsItems;
import com.lazynessmind.farmingtools.init.tileentities.FTBlockTileEntity;
import com.lazynessmind.farmingtools.init.tileentities.TileEntityPlanter;
import com.lazynessmind.farmingtools.util.Enum;
import com.lazynessmind.farmingtools.util.ParticleCreator;
import com.lazynessmind.farmingtools.util.SpawnUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
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

public class BlockPlanter extends FTBlockTileEntity<TileEntityPlanter> {

    public static final PropertyInteger META = PropertyInteger.create("meta", 0, 3);
    private static final AxisAlignedBB BASE_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.5625D, 0.9375D);

    public BlockPlanter(Material material, String name) {
        super(material, name, false);
        this.setDefaultState(this.blockState.getBaseState().withProperty(this.getMetaProperty(), Integer.valueOf(0)));
        setTickRandomly(true);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        worldIn.setBlockState(pos, state, 3);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        playerIn.openGui(FarmingTools.instance, FTGuis.PLANTER, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(META, meta);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntityPlanter tileEntityPlanter = getTileEntity(worldIn, pos);
        SpawnUtils.spawnItemAt(worldIn, pos, tileEntityPlanter.getHandler().getStackInSlot(0));
        SpawnUtils.spawnItemAt(worldIn, pos, new ItemStack(FarmingToolsItems.PLANTER, 1, getMetaFromState(state)));
        super.breakBlock(worldIn, pos, state);
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

    public PropertyInteger getMetaProperty() {
        return META;
    }

    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(this.getMetaProperty(), Integer.valueOf(meta));
    }

    public int getMetaFromState(IBlockState state) {
        return ((Integer) state.getValue(this.getMetaProperty())).intValue();
    }

//    @Override
//    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
//        for(int i = 0; i < Enum.Planter.values().length; i++){
//            items.add(new ItemStack(this, 1, i));
//        }
//    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{META});
    }


    @Override
    public Class<TileEntityPlanter> getTileEntityClass() {
        return TileEntityPlanter.class;
    }

    @Override
    public TileEntityPlanter createTileEntity(World world, IBlockState state) {
        return new TileEntityPlanter();
    }

    @Override
    public void registerModels(int metadata) {
        for (int i = 0; i < Enum.Planter.values().length; i++) {
            FarmingTools.common.registerItemsRenderer(Item.getItemFromBlock(this), i, "inventory", Enum.Planter.values()[i].getName());
        }
    }
}
