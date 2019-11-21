package com.lazynessmind.farmingtools.block.tileentities;

import com.lazynessmind.farmingtools.block.tileentities.base.FTTileEntity;
import com.lazynessmind.farmingtools.util.FarmUtils;
import com.lazynessmind.farmingtools.util.MathUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class TileEntityNatureGather extends FTTileEntity implements ITickable {

    private static int currentAmount;
    private int range = 10;

    //* This value is changed when the power is being used
    private static double changeToDestroyBlocks = 0;

    @Override
    public void update() {
        if (!this.world.isRemote) {
            currentAmount = this.getNaturePower();
            if (this.world.rand.nextDouble() < changeToDestroyBlocks) {
                List<BlockPos> possInRange = FarmUtils.checkInRangeFromGround(this.range, this.pos, this.range, false);
                BlockPos poss = possInRange.get(MathUtil.random(0, possInRange.size() - 1));

                if (isAllowedBlock(poss)) {
                    this.world.setBlockState(poss, Blocks.AIR.getDefaultState(), 3);
                    this.world.playSound(Minecraft.getMinecraft().player, poss, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1f, 1f);
                }
            }
        }
    }

    private int getNaturePower() {
        int count = 0;
        for (BlockPos poss : FarmUtils.checkInRangeFromGround(this.range, this.pos, this.range, false)) {
            Block blockAtPos = this.world.getBlockState(poss).getBlock();
            if (blockAtPos instanceof BlockBush || blockAtPos instanceof BlockLeaves || blockAtPos instanceof BlockLog) {
                count++;
            }
        }
        return count;
    }

    private boolean isAllowedBlock(BlockPos pos) {
        return this.world.getBlockState(pos).getBlock() instanceof BlockBush
                || this.world.getBlockState(pos).getBlock() instanceof BlockLeaves
                || this.world.getBlockState(pos).getBlock() instanceof BlockLog;
    }

    public static int getCurrentNaturePower() {
        return currentAmount;
    }

    public static void resetCurrentAmount() {
        currentAmount = 0;
    }

    public static double getChangeToDestroy() {
        return changeToDestroyBlocks;
    }

    public static void increaseChangeToDestroy(double amount) {
        changeToDestroyBlocks += amount;
    }

    public static void decreaseChangeToDestroy(double amount) {
        changeToDestroyBlocks -= amount;
    }
}
