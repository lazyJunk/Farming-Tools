package com.lazynessmind.farmingtools.block.tileentities;

import com.lazynessmind.farmingtools.block.tileentities.base.TileEntityPedestal;
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
        super(false, false, UpgradeUtil.getMaxCooldownFromType(0), UpgradeUtil.getRangeFromType(0), 1, Items.BEETROOT_SEEDS);
    }


    @Override
    public void update() {
        if (!world.isRemote) {
            setType(getBlockMetadata());
            setRange(UpgradeUtil.getRangeFromType(getBlockMetadata()));
            Item itemSlot = mainSlot().getItem();
            if (!mainSlot().isEmpty()) {
                if (itemSlot instanceof ItemSeeds || itemSlot instanceof ItemSeedFood) {
                    crop = ((IPlantable) mainSlot().getItem()).getPlant(world, pos);
                }
            }
            plantCrop(crop);
            updateTile();
            this.markDirty();
        }
    }

    private void plantCrop(IBlockState crop) {
        for (BlockPos pos : FarmUtils.checkInXZRange(getRange(), getPos(), false)) {
            if (FarmUtils.canPlantCrop(pos, world)) {
                if (crop != null && !mainSlot().isEmpty() && mainSlot().getItem() instanceof ItemSeedFood || mainSlot().getItem() instanceof ItemSeeds) {
                    if (needRedstonePower()) {
                        if (world.isBlockPowered(pos)) {
                            world.setBlockState(pos, crop);
                            getMainHandler().extractItem(0, 1, false);
                        }
                    } else {
                        world.setBlockState(pos, crop);
                        getMainHandler().extractItem(0, 1, false);
                    }
                }
            }
        }
    }
}
