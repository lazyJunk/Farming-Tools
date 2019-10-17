package com.lazynessmind.farmingtools.init.tileentities;

import com.lazynessmind.farmingtools.config.FarmingToolsConfigs;
import com.lazynessmind.farmingtools.init.FarmingToolsItems;
import com.lazynessmind.farmingtools.util.FarmUtils;
import com.lazynessmind.farmingtools.util.UpgradeUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityGrowthPedestal extends TileEntityPedestal implements ITickable {

    private int growthSpeed;

    public TileEntityGrowthPedestal() {
        super(false, false, 0, UpgradeUtil.getRangeFromType(0), 3, 2, FarmingToolsItems.ADVANCED_BONE_MEAL);

        this.growthSpeed = FarmingToolsConfigs.growthPedestalSpeedVar;
    }

    @Override
    public void writeNBT(NBTTagCompound compound) {
        super.writeNBT(compound);

        compound.setInteger("growth_speed", this.growthSpeed);
    }

    @Override
    public void readNBT(NBTTagCompound compound) {
        super.readNBT(compound);

        growthSpeed = compound.getInteger("growth_speed");
    }

    @Override
    public void update() {
        this.growthSpeed = FarmingToolsConfigs.growthPedestalSpeedVar;
        setType(getBlockMetadata());
        setRange(UpgradeUtil.getRangeFromType(getBlockMetadata()));
        tickCrop();
        this.markDirty();
    }

    private void tickCrop() {
        for (BlockPos blockPos : FarmUtils.checkInRange(getRange(), pos, getVerticalRange(), false)) {
            if (!world.isRemote) {
                IBlockState cropState = world.getBlockState(blockPos);
                Block crop = cropState.getBlock();
                if (crop instanceof BlockCrops) {
                    if (crop.getTickRandomly()) {
                        crop.updateTick(world, blockPos, cropState, world.rand);
                    }
                }
            }
        }
    }
}
