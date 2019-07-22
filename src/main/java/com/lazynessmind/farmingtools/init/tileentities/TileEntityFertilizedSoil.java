package com.lazynessmind.farmingtools.init.tileentities;

import com.lazynessmind.farmingtools.config.FarmingToolsConfigs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityFertilizedSoil extends TileEntity implements ITickable {

    private int growSpeed = FarmingToolsConfigs.fertilizedSoilSpeedVar;

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
        //update when changed since the value is defined when first placed on world
        this.growSpeed = FarmingToolsConfigs.fertilizedSoilSpeedVar;
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

    public int getGrowSpeed() {
        return growSpeed;
    }
}
