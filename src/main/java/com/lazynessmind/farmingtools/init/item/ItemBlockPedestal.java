package com.lazynessmind.farmingtools.init.item;

import com.lazynessmind.farmingtools.util.RarityUtil;
import com.lazynessmind.farmingtools.util.UpgradeUtil;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBlockPedestal extends FTItemBlock{

    public ItemBlockPedestal(Block block) {
        super(block, true, RarityUtil.UNCOMMON);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add("");
        tooltip.add(TextFormatting.WHITE + "Type: " + TextFormatting.GREEN + UpgradeUtil.getNameFromType(stack.getMetadata()));
        tooltip.add(TextFormatting.WHITE + "Base cooldown: " + TextFormatting.GREEN + UpgradeUtil.getMaxCooldownFromType(stack.getMetadata()));
        tooltip.add(TextFormatting.WHITE + "Base range: " + TextFormatting.GREEN + UpgradeUtil.getRangeFromType(stack.getMetadata()));
    }
}
