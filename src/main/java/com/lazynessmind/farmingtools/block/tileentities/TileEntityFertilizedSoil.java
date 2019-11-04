package com.lazynessmind.farmingtools.block.tileentities;

import com.lazynessmind.farmingtools.config.FarmingToolsConfigs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityFertilizedSoil extends TileEntity implements ITickable {

    private int growSpeed = FarmingToolsConfigs.fertilizedSoilSpeedVar / 2;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("grow_speed", this.growSpeed);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        this.growSpeed = compound.getInteger("grow_speed");
        super.readFromNBT(compound);
    }

    @Override
    public void update() {
        //update when changed since the value is defined when first placed on world
        this.growSpeed = FarmingToolsConfigs.fertilizedSoilSpeedVar / 2;
        tickCrop(this.pos.up());
    }

    // Update the crop depending on the currentSpeed;
    private void tickCrop(BlockPos pos) {
        IBlockState cropState = this.world.getBlockState(pos);
        Block crop = cropState.getBlock();
        if (!world.isRemote) {
            if (crop instanceof BlockCrops) {
                if (crop.getTickRandomly()) {
                    if (this.world.getBlockState(pos) == cropState) {
                        for (int i = 0; i < this.growSpeed; i++) {
                            boolean rndBool = getWorld().rand.nextBoolean();
                            if (rndBool) {
                                crop.updateTick(this.world, pos, cropState, this.world.rand);
                            }
                        }
                    }
                }
            }
        }
    }
}
