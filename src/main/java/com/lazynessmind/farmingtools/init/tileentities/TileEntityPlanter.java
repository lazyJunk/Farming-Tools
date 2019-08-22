package com.lazynessmind.farmingtools.init.tileentities;

import com.lazynessmind.farmingtools.util.FarmUtils;
import com.lazynessmind.farmingtools.util.UpgradeUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.IPlantable;

public class TileEntityPlanter extends TileEntityPedestal implements ITickable {

    private IBlockState crop;

    public TileEntityPlanter() {
        super(false, false, UpgradeUtil.getMaxCooldownFromType(0), UpgradeUtil.getMaxCooldownFromType(0), 0, 1);

        getWorker().setDoWork(this::updateCooldownCap);
        getWorker().setWorkDone(() -> {
            Item itemSlot = getHandler().getStackInSlot(0).getItem();
            if (!getHandler().getStackInSlot(0).isEmpty()) {
                if (itemSlot instanceof ItemSeeds || itemSlot instanceof ItemSeedFood) {
                    crop = ((IPlantable) getHandler().getStackInSlot(0).getItem()).getPlant(world, pos);
                }
            }
            plantCrop(crop);
        });
    }

    private void updateCooldownCap() {
        int cap = getWorker().getMaxWork();
        cap -= Math.pow(UpgradeUtil.getMaxCooldownFromType(getType()), 2) % cap;
        getWorker().setMaxCooldown(cap);
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            setType(getBlockMetadata());
            setRange(UpgradeUtil.getRangeFromType(getBlockMetadata()));
            getWorker().doWork();
            this.markDirty();
        }
    }

    private void plantCrop(IBlockState crop) {
        for (BlockPos pos : FarmUtils.checkInXZRange(getRange(), getPos(), false)) {
            if (FarmUtils.canPlantCrop(pos, world)) {
                if (crop != null && !getHandler().getStackInSlot(0).isEmpty()) {
                    if (needRedstonePower()) {
                        if (world.isBlockPowered(pos)) {
                            world.setBlockState(pos, crop);
                            getHandler().extractItem(0, 1, false);
                        }
                    } else {
                        world.setBlockState(pos, crop);
                        getHandler().extractItem(0, 1, false);
                    }
                }
            }
        }
    }
}
