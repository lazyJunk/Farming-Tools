package com.lazynessmind.farmingtools.init.tileentities;

import com.lazynessmind.farmingtools.config.FarmingToolsConfigs;
import com.lazynessmind.farmingtools.util.FarmUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityGrowthPedestal extends FTTileEntity implements ITickable {

    private int growthSpeed = FarmingToolsConfigs.growthPedestalSpeedVar;
    private int range = 4;

    @Override
    public NBTTagCompound writeNBT(NBTTagCompound compound) {
        compound.setInteger("growth_speed", growthSpeed);
        compound.setInteger("range", range);
        return compound;
    }

    @Override
    public void readNBT(NBTTagCompound compound) {
        growthSpeed = compound.getInteger("growth_speed");
        range = compound.getInteger("range");
    }

    @Override
    public void update() {
        this.growthSpeed = FarmingToolsConfigs.growthPedestalSpeedVar;
        for (BlockPos pos : FarmUtils.checkInRange(range, this.getPos(), 3, false)) {
            tickCrop(pos);
        }
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
                            crop.updateTick(this.world, pos, cropState, this.world.rand);
                            crop.updateTick(this.world, pos, cropState, this.world.rand);
                            crop.updateTick(this.world, pos, cropState, this.world.rand);
                        }
                    }
                }
            }
        }
    }
}
