package com.lazynessmind.farmingtools.init.tileentities;

import com.lazynessmind.farmingtools.util.FarmUtils;
import com.lazynessmind.farmingtools.util.UpgradeUtil;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityHarvester extends TileEntityPedestal implements ITickable {

    public TileEntityHarvester() {
        super(true, true, UpgradeUtil.getMaxCooldownFromType(0), UpgradeUtil.getRangeFromType(0), 1, 1);

        getWorker().setDoWork(this::updateCooldownCap);
        getWorker().setWorkDone(() -> {
            harvestBlock(pos);
        });
    }

    private void updateCooldownCap() {
        int cap = getWorker().getMaxWork();
        cap -= Math.pow(UpgradeUtil.getMaxCooldownFromType(getType()), 2) % cap;
        getWorker().setMaxCooldown(cap);
    }

    @Override
    public void update() {
        setType(getBlockMetadata());
        setRange(UpgradeUtil.getRangeFromType(getBlockMetadata()));
        getWorker().doWork();
        this.markDirty();
    }

    private void harvestBlock(BlockPos pos) {
        if (!world.isRemote) {
            for (BlockPos poss : FarmUtils.checkInRange(getRange(), pos, getVerticalRange(), false)) {
                if (world.getBlockState(poss).getBlock() instanceof BlockCrops) {
                    BlockCrops crops = (BlockCrops) world.getBlockState(poss).getBlock();
                    if (needRedstonePower()) {
                        if (world.isBlockPowered(pos)) {
                            if (FarmUtils.canFarm(crops, world, poss) && hasHoeOnSlot()) {
                                FarmUtils.farmAndDrop(crops, world, poss, world.getBlockState(poss), true);
                                if (getHandler().getStackInSlot(0).isItemStackDamageable()) {
                                    getHandler().getStackInSlot(0).damageItem(1, Minecraft.getMinecraft().player);
                                }
                            }
                        }
                    } else {
                        if (FarmUtils.canFarm(crops, world, poss) && hasHoeOnSlot()) {
                            FarmUtils.farmAndDrop(crops, world, poss, world.getBlockState(poss), true);
                            if (getHandler().getStackInSlot(0).isItemStackDamageable()) {
                                getHandler().getStackInSlot(0).damageItem(1, Minecraft.getMinecraft().player);
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean hasHoeOnSlot() {
        return !getHandler().getStackInSlot(0).isEmpty();
    }
}
