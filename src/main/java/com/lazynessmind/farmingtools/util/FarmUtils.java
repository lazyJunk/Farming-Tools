package com.lazynessmind.farmingtools.util;

import net.minecraft.block.BlockCrops;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.RandomUtils;

public class FarmUtils {

    public static void farmWithHoe(BlockCrops crop, World world, BlockPos pos, ItemStack stack) {
            int rndVal = RandomUtils.nextInt(3, 5);
            for (int i = 0; i < rndVal; i++) {
                SpawnUtils.spawnItemAt(world, pos, stack);
            }
            world.setBlockState(pos, crop.getStateFromMeta(0));
    }

    public static boolean canFarmWithHoe(BlockCrops crop, World world, BlockPos pos){
        return world.getBlockState(pos) == crop.getStateFromMeta(crop.getMaxAge());
    }
}
