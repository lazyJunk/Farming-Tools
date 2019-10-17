package com.lazynessmind.farmingtools.init.tileentities;

import com.lazynessmind.farmingtools.util.FarmUtils;
import com.lazynessmind.farmingtools.util.UpgradeUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.IPlantable;

public class TileEntityPlanter extends TileEntityPedestal implements ITickable {

    private IBlockState crop;

    public TileEntityPlanter() {
        super(false, false, UpgradeUtil.getMaxCooldownFromType(0), UpgradeUtil.getRangeFromType(0), 1, 2, Items.BEETROOT_SEEDS);

        getWorker().setDoWork(this::updateCooldownCap);
        getWorker().setWorkDone(() -> {
            Item itemSlot = getMainHandler().getStackInSlot(0).getItem();
            if (!getMainHandler().getStackInSlot(0).isEmpty()) {
                if (itemSlot instanceof ItemSeeds || itemSlot instanceof ItemSeedFood) {
                    crop = ((IPlantable) getMainHandler().getStackInSlot(0).getItem()).getPlant(world, pos);
                }
            }
            if(getFuel() > 0){
                plantCrop(crop);
            }
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
            updateTile();
            this.markDirty();
        }
    }

    private void plantCrop(IBlockState crop) {
        for (BlockPos pos : FarmUtils.checkInXZRange(getRange(), getPos(), false)) {
            if (FarmUtils.canPlantCrop(pos, world)) {
                if (crop != null && !getMainHandler().getStackInSlot(0).isEmpty()) {
                    if (needRedstonePower()) {
                        if (world.isBlockPowered(pos)) {
                            world.setBlockState(pos, crop);
                            getMainHandler().extractItem(0, 1, false);
                            removeFromFuel(1);
                        }
                    } else {
                        world.setBlockState(pos, crop);
                        getMainHandler().extractItem(0, 1, false);
                        removeFromFuel(1);
                    }
                }
            }
        }
    }
}
