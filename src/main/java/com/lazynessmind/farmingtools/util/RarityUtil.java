package com.lazynessmind.farmingtools.util;

import net.minecraft.util.text.TextFormatting;

/*
* lazynessmind
*
* -basically a copy of net.minecraft.util.text.TextFormatting;
* */

public enum RarityUtil {

    COMMON(TextFormatting.WHITE, "common"),
    UNCOMMON(TextFormatting.GREEN, "uncommon"),
    RARE(TextFormatting.BLUE, "rare"),
    EPIC(TextFormatting.DARK_PURPLE, "epic"),
    LEGENDARY(TextFormatting.GOLD, "legendary");

    public TextFormatting textFormatting;
    public String id;

    RarityUtil(TextFormatting textFormatting, String id){
        this.textFormatting = textFormatting;
        this.id = id;
    }
}
