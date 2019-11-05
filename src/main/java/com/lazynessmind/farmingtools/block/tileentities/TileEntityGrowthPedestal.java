package com.lazynessmind.farmingtools.block.tileentities;

import com.lazynessmind.farmingtools.block.tileentities.base.TileEntityPedestal;
import com.lazynessmind.farmingtools.init.FarmingToolsItems;
import com.lazynessmind.farmingtools.util.FarmUtils;
import com.lazynessmind.farmingtools.util.TypeUtil;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import scala.collection.parallel.ParIterableLike;

public class TileEntityGrowthPedestal extends TileEntityPedestal implements ITickable {

    private int timer = 0;

    public TileEntityGrowthPedestal() {
        super(false, false, 1);
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
                if(world.isAreaLoaded(pos, 10)){
                    tickCrop();
                }
            }
        }
        this.markDirty();
    }

    private void tickCrop() {
        if (mainSlot().getCount() != 0 && mainSlot().getItem() == FarmingToolsItems.ADVANCED_BONE_MEAL) {
            for (BlockPos blockPos : FarmUtils.checkInRange(getRange(), pos, 1, false)) {
                if (world.getBlockState(blockPos).getBlock() instanceof BlockCrops) {
                    BlockCrops crops = (BlockCrops) world.getBlockState(blockPos).getBlock();
                    if (needRedstonePower()) {
                        if (world.isBlockPowered(pos)) {
                            if (!FarmUtils.canFarm(crops, world, blockPos)) {
                                world.setBlockState(blockPos, crops.getStateFromMeta(crops.getMaxAge()), 3);
                                if(!Minecraft.getMinecraft().player.isCreative()) getMainHandler().extractItem(0, 1, false);
                            }
                        }
                    } else {
                        if (!FarmUtils.canFarm(crops, world, blockPos)) {
                            world.setBlockState(blockPos, crops.getStateFromMeta(crops.getMaxAge()), 3);
                            if(!Minecraft.getMinecraft().player.isCreative()) getMainHandler().extractItem(0, 1, false);
                        }
                    }
                }
            }
        }
    }
}
