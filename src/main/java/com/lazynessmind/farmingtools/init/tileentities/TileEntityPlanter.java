package com.lazynessmind.farmingtools.init.tileentities;

import com.lazynessmind.farmingtools.util.FarmUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityPlanter extends TileEntity implements ITickable {

    private int seedCount = 0;
    private IBlockState crop = null;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("seed_count", seedCount);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        seedCount = compound.getInteger("seed_count");
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
        plantCrop();
    }

    private void plantCrop() {
        if (!world.isRemote) {
            if (FarmUtils.canPlantCrop(getPos(), world)) {
                if (getSeedCount() > 0) {
                    if (crop != null) {
                        world.setBlockState(pos.down(), crop);
                        decreaseSeedCount(1);
                    }
                }
            }
        }
    }

    public IBlockState getCrop() {
        return crop;
    }

    public void setCrop(IBlockState crop) {
        this.crop = crop;
    }

    public void decreaseSeedCount(int amount) {
        this.seedCount -= amount;
    }

    public void increaseSeedCount(int amount) {
        this.seedCount += amount;
    }

    public int getSeedCount() {
        return seedCount;
    }

    public void setSeedCount(int seedCount) {
        this.seedCount = seedCount;
    }
}
