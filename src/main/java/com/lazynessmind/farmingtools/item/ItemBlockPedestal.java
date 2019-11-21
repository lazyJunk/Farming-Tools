package com.lazynessmind.farmingtools.item;

import com.lazynessmind.farmingtools.item.base.FTItemBlock;
import com.lazynessmind.farmingtools.util.RarityUtil;
import com.lazynessmind.farmingtools.util.TypeUtil;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBlockPedestal extends FTItemBlock {

    public ItemBlockPedestal(Block block) {
        super(block, true, RarityUtil.UNCOMMON);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add("");
        tooltip.add(TextFormatting.WHITE + "Type: " + TextFormatting.GREEN + TypeUtil.getNameFromType(stack.getMetadata()));
        tooltip.add(TextFormatting.WHITE + "Base cooldown: " + TextFormatting.GREEN + TypeUtil.getTimeBetweenFromType(stack.getMetadata()));
        tooltip.add(TextFormatting.WHITE + "Base range: " + TextFormatting.GREEN + 1);
    }
}
