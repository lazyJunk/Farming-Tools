package com.lazynessmind.farmingtools.handler;

import com.lazynessmind.farmingtools.init.FarmingToolsItems;
import com.lazynessmind.farmingtools.init.item.ItemAdvancedBoneMeal;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.init.Bootstrap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FTRegistryHandler {

    public static void registry() {
        dispenserBehaviorRegistration();
    }

    private static void dispenserBehaviorRegistration() {
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(FarmingToolsItems.ADVANCED_BONE_MEAL, new Bootstrap.BehaviorDispenseOptional() {
            protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                this.successful = true;
                World world = source.getWorld();
                BlockPos blockpos = source.getBlockPos().offset((EnumFacing) source.getBlockState().getValue(BlockDispenser.FACING));
                ItemAdvancedBoneMeal.doBoneMealEffectOnGround(FarmingToolsItems.ADVANCED_BONE_MEAL.getDefaultInstance(), world, blockpos);
                return stack;
            }
        });
    }
}
