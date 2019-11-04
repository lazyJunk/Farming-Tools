package com.lazynessmind.farmingtools.enchantment;

import com.lazynessmind.farmingtools.init.FarmingToolsEnchants;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;

public class EnchantHoeHarvest3x3 extends Enchantment {

    public EnchantHoeHarvest3x3() {
        super(Rarity.RARE, FarmingToolsEnchants.HOE, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        setRegistryName("3x3harvest");
        setName("3x3harvest");

        FarmingToolsEnchants.ENCHANTS.add(this);
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 20 * enchantmentLevel;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return getMinEnchantability(enchantmentLevel) + 10;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof ItemHoe;
    }
}
