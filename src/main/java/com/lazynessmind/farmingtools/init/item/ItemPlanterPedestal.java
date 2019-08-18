package com.lazynessmind.farmingtools.init.item;

import com.lazynessmind.farmingtools.FarmingTools;
import com.lazynessmind.farmingtools.init.FarmingToolsBlocks;
import com.lazynessmind.farmingtools.init.blocks.BlockPlanter;
import com.lazynessmind.farmingtools.util.Enum;
import com.lazynessmind.farmingtools.util.RarityUtil;
import com.lazynessmind.farmingtools.util.UpgradeUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemPlanterPedestal extends FTItem {

    public ItemPlanterPedestal(String name) {
        super(name, RarityUtil.RARE);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        worldIn.setBlockState(pos.up(), FarmingToolsBlocks.PLANTER.getDefaultState().withProperty(BlockPlanter.META, player.getHeldItem(hand).getMetadata()));
        return EnumActionResult.SUCCESS;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(TextFormatting.GREEN + "Work, work, work... MORE SEEDS");
        if (flagIn.isAdvanced()) {
            tooltip.add(" ");
            tooltip.add(TextFormatting.GREEN + "Range: " + TextFormatting.WHITE + "1x0x1");
            tooltip.add(TextFormatting.GREEN + "Type: " + TextFormatting.WHITE + "" + UpgradeUtil.getNameFromType(stack.getMetadata()));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int meta = stack.getMetadata();
        return getUnlocalizedName() + "_" + Enum.Planter.values()[meta].getName();
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if(this.isInCreativeTab(tab)){
            for(int i = 0; i < Enum.Planter.values().length; ++i){
                items.add(new ItemStack(this, 1, i));
            }
        }
    }

    @Override
    public void registerModels(int metadata) {
        for (int i = 0; i < Enum.Planter.values().length; i++) {
            FarmingTools.common.registerItemsRenderer(this, i, "inventory", Enum.Planter.values()[i].getName());
        }
    }
}
