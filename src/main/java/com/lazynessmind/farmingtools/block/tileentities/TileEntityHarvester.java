package com.lazynessmind.farmingtools.block.tileentities;

import com.lazynessmind.farmingtools.block.tileentities.base.TileEntityPedestal;
import com.lazynessmind.farmingtools.util.FarmUtils;
import com.lazynessmind.farmingtools.util.TypeUtil;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityHarvester extends TileEntityPedestal implements ITickable {

    private int timer;

    public TileEntityHarvester() {
        super(false, false, 3);
    }

    @Override
    public void update() {
        setType(getBlockMetadata());
        setRange(TypeUtil.getHorizontalRangeFromType(getType()));
        setTimeBetween(TypeUtil.getTimeBetweenFromType(getType()));
        if (!world.isRemote) {
            if (timer < getTimeBetween()) {
                timer++;
            } else if (timer >= getTimeBetween()) {
                timer = 0;
                harvestBlock(pos);
            }
        }
        this.markDirty();
    }

    private void harvestBlock(BlockPos pos) {
        if (!mainSlot().isEmpty()) {
            for (BlockPos poss : FarmUtils.checkInRange(getRange(), pos, 3, false)) {
                if (world.getBlockState(poss).getBlock() instanceof BlockCrops) {
                    BlockCrops crops = (BlockCrops) world.getBlockState(poss).getBlock();
                    if (needRedstonePower()) {
                        if (world.isBlockPowered(pos)) {
                            if (FarmUtils.canFarm(crops, world, poss) && hasHoeOnSlot()) {
                                FarmUtils.farmAndDrop(crops, world, poss, world.getBlockState(poss), true);
                                if (mainSlot().isItemStackDamageable()) {
                                    mainSlot().damageItem(1, Minecraft.getMinecraft().player);
                                }
                            }
                        }
                    } else {
                        if (FarmUtils.canFarm(crops, world, poss) && hasHoeOnSlot()) {
                            FarmUtils.farmAndDrop(crops, world, poss, world.getBlockState(poss), true);
                            if (mainSlot().isItemStackDamageable()) {
                                mainSlot().damageItem(1, Minecraft.getMinecraft().player);
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean hasHoeOnSlot() {
        return !mainSlot().isEmpty();
    }
}
