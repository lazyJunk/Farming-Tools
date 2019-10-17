package com.lazynessmind.farmingtools.util;

import net.minecraft.item.EnumRarity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.EnumHelper;

/*
* lazynessmind
*
* -basically a copy of net.minecraft.util.text.TextFormatting;
* */

public enum RarityUtil {

    COMMON(EnumHelper.addRarity("common", TextFormatting.WHITE, "CommonRarity")),
    UNCOMMON(EnumHelper.addRarity("uncommon", TextFormatting.GREEN, "UncommonRarity")),
    RARE(EnumHelper.addRarity("rare", TextFormatting.BLUE, "RareRarity")),
    EPIC(EnumHelper.addRarity("epic", TextFormatting.DARK_PURPLE, "PurpleRarity")),
    LEGENDARY(EnumHelper.addRarity("legendary", TextFormatting.GOLD, "LegendaryRarity"));

    public EnumRarity rarity;

    RarityUtil(EnumRarity rarity){
        this.rarity = rarity;
    }
}
