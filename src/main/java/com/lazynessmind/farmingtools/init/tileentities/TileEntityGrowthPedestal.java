package com.lazynessmind.farmingtools.init.tileentities;

import com.lazynessmind.farmingtools.config.FarmingToolsConfigs;
import com.lazynessmind.farmingtools.util.FarmUtils;
import com.lazynessmind.farmingtools.util.UpgradeUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityGrowthPedestal extends TileEntityPedestal implements ITickable {

    private int growthSpeed = FarmingToolsConfigs.growthPedestalSpeedVar;

    public TileEntityGrowthPedestal() {
        super(false, false, UpgradeUtil.getMaxCooldownFromType(0), UpgradeUtil.getRangeFromType(0), 3, 0);

        getWorker().setDoWork(()->{
            growthSpeed = FarmingToolsConfigs.growthPedestalSpeedVar;
        });

        getWorker().setWorkDone(()->{
            for (BlockPos pos : FarmUtils.checkInRange(getRange(), this.getPos(), getVerticalRange(), false)) {
                tickCrop(pos);
            }
        });
    }

    @Override
    public void writeNBT(NBTTagCompound compound) {
        super.writeNBT(compound);

        compound.setInteger("growth_speed", growthSpeed);
    }

    @Override
    public void readNBT(NBTTagCompound compound) {
        super.readNBT(compound);

        growthSpeed = compound.getInteger("growth_speed");
    }

    @Override
    public void update() {
        setType(getBlockMetadata());
        setRange(UpgradeUtil.getRangeFromType(getBlockMetadata()));
        getWorker().doWork();
        this.markDirty();
    }

    // Update the crop depending on the currentSpeed;
    private void tickCrop(BlockPos pos) {
        IBlockState cropState = this.world.getBlockState(pos);
        Block crop = cropState.getBlock();
        if (!world.isRemote) {
            if (crop instanceof BlockCrops) {
                if (crop.getTickRandomly()) {
                    if (this.world.getBlockState(pos) == cropState) {
                        for (int i = 0; i < this.growthSpeed; i++) {
                            crop.updateTick(this.world, pos, cropState, this.world.rand);
                        }
                    }
                }
            }
        }
    }
}
