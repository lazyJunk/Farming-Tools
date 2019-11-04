package com.lazynessmind.farmingtools.init.item;

import com.lazynessmind.farmingtools.FarmingTools;
import com.lazynessmind.farmingtools.block.base.BlockPedestal;
import com.lazynessmind.farmingtools.block.tileentities.base.TileEntityPedestal;
import com.lazynessmind.farmingtools.util.Enum;
import com.lazynessmind.farmingtools.util.RarityUtil;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemTypeUpgrade extends FTItem {

    public ItemTypeUpgrade(String name) {
        super(name, RarityUtil.RARE);
        setHasSubtypes(true);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            ItemStack heldItem = player.getHeldItem(hand);
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            Block block = worldIn.getBlockState(pos).getBlock();

            if (tileEntity instanceof TileEntityPedestal && block instanceof BlockPedestal) {
                TileEntityPedestal tileEntityPedestal = (TileEntityPedestal)tileEntity;
                BlockPedestal blockPedestal = (BlockPedestal)block;

                int currentMetadata = heldItem.getMetadata();
                int tileEntityCurrentType = tileEntityPedestal.getType();

                if(tileEntityCurrentType < currentMetadata){
                    tileEntityPedestal.setType(currentMetadata);
                    worldIn.setBlockState(pos, blockPedestal.getStateFromMeta(currentMetadata));
                    worldIn.playEvent(2001, pos, Block.getStateId(block.getDefaultState()));
                    worldIn.playSound(null, pos, SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.BLOCKS, 1, 1);
                    if(!player.isCreative()) heldItem.shrink(1);
                    return EnumActionResult.SUCCESS;
                }
            }
        }
        return EnumActionResult.PASS;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int meta = stack.getMetadata();
        return getUnlocalizedName() + "_" + Enum.Type.values()[meta].getName();
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            for (int i = 0; i < Enum.Type.values().length; ++i) {
                items.add(new ItemStack(this, 1, i));
            }
        }
    }

    @Override
    public void registerModels(int metadata) {
        for (int i = 0; i < Enum.Type.values().length; i++) {
            FarmingTools.common.registerItemsRenderer(this, i, "inventory", Enum.Type.values()[i].getName());
        }
    }
}
