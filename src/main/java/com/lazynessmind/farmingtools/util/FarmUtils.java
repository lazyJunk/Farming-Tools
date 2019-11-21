package com.lazynessmind.farmingtools.util;

import com.lazynessmind.farmingtools.init.FarmingToolsBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class FarmUtils {

    public static boolean canFarm(BlockCrops crop, World world, BlockPos pos) {
        return world.getBlockState(pos) == crop.getStateFromMeta(crop.getMaxAge());
    }

    public static boolean canPlantCrop(BlockPos pos, World world) {
        return world.getBlockState(pos).getBlock() == Blocks.AIR && world.getBlockState(pos.down()).getBlock() == Blocks.FARMLAND
                || world.getBlockState(pos).getBlock() == Blocks.AIR && world.getBlockState(pos.down()).getBlock() == FarmingToolsBlocks.FERTILIZED_SOIL;
    }

    public static void farmAndDrop(BlockCrops crops, World world, BlockPos pos, IBlockState access, boolean drop) {
        if (drop) {
            crops.dropBlockAsItem(world, pos, world.getBlockState(pos), 1);
            world.setBlockToAir(pos);
        } else {
            world.setBlockToAir(pos);
        }
    }

    public static List<BlockPos> checkInRange(int range, BlockPos currentPos, int yRange, boolean checkItself) {
        List<BlockPos> positions = new ArrayList<>();
        if (checkItself) positions.add(currentPos);
        for (int x = -range; x < range + 1; x++) {
            for (int y = -yRange; y < yRange + 1; y++) {
                for (int z = -range; z < range + 1; z++) {
                    positions.add(new BlockPos(currentPos.getX() + x, currentPos.getY() + y, currentPos.getZ() + z));
                }
            }
        }
        return positions;
    }

    public static List<BlockPos> checkInXZRange(int range, BlockPos currentPos, boolean checkItself) {
        List<BlockPos> positions = new ArrayList<>();
        if (checkItself) positions.add(currentPos);
        for (int x = -range; x < range + 1; x++) {
            for (int z = -range; z < range + 1; z++) {
                positions.add(new BlockPos(currentPos.getX() + x, currentPos.getY(), currentPos.getZ() + z));
            }
        }
        return positions;
    }

    public static boolean checkBlockInPos(Block checkBlock, World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() == checkBlock;
    }

    public static List<BlockPos> checkInRangeFromGround(int range, BlockPos currentPos, int yRange, boolean checkItself) {
        List<BlockPos> positions = new ArrayList<>();
        if (checkItself) positions.add(currentPos);
        for (int x = -range; x < range + 1; x++) {
            for (int y = 0; y < yRange + 1; y++) {
                for (int z = -range; z < range + 1; z++) {
                    positions.add(new BlockPos(currentPos.getX() + x, currentPos.getY() + y, currentPos.getZ() + z));
                }
            }
        }
        return positions;
    }
}
