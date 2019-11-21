package com.lazynessmind.farmingtools.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NaturePower {

    public static int getNaturePower(World world, BlockPos pos, int range) {
        int count = 0;
        for (BlockPos poss : FarmUtils.checkInRange(10, pos, range, false)) {
            Block blockAtPos = world.getBlockState(poss).getBlock();
            if (blockAtPos instanceof BlockBush || blockAtPos instanceof BlockLeaves || blockAtPos instanceof BlockLog) {
                count++;
            }
        }
        return count;
    }
}
