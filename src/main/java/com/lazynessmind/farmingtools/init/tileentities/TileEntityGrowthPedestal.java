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

public class TileEntityGrowthPedestal extends TileEntity implements ITickable {

    private int growthSpeed = FarmingToolsConfigs.growthPedestalSpeedVar;
    private int range = 4;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("growth_speed", growthSpeed);
        compound.setInteger("range", range);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        growthSpeed = compound.getInteger("growth_speed");
        range = compound.getInteger("range");
        super.readFromNBT(compound);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        int metadata = getBlockMetadata();
        return new SPacketUpdateTileEntity(this.pos, metadata, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return nbt;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        this.readFromNBT(tag);
    }

    @Override
    public NBTTagCompound getTileData() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return nbt;
    }

    @Override
    public void update() {
        this.growthSpeed = FarmingToolsConfigs.growthPedestalSpeedVar;
        for (BlockPos pos : FarmUtils.checkInRange(range, this.getPos(), 3)) {
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
