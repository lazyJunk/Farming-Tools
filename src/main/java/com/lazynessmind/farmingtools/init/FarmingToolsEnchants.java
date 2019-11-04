package com.lazynessmind.farmingtools.init;

import com.lazynessmind.farmingtools.enchantment.EnchantHoeHarvest3x3;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemHoe;
import net.minecraftforge.common.util.EnumHelper;

import java.util.ArrayList;
import java.util.List;

public class FarmingToolsEnchants {

    public static final EnumEnchantmentType HOE = EnumHelper.addEnchantmentType("hoe", (item)->(item instanceof ItemHoe));

    public static final List<Enchantment> ENCHANTS = new ArrayList<>();

    public static final Enchantment HOEHARVEST = new EnchantHoeHarvest3x3();
}
