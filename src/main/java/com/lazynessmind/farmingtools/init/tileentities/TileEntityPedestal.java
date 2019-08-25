package com.lazynessmind.farmingtools.init.tileentities;

import com.lazynessmind.farmingtools.init.FarmingToolsCapabilities;
import com.lazynessmind.farmingtools.init.capabilities.Worker;
import com.lazynessmind.farmingtools.interfaces.IRange;
import com.lazynessmind.farmingtools.interfaces.IRedPower;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

@SuppressWarnings("unchecked cast")
public class TileEntityPedestal extends FTTileEntity implements IRange, IRedPower {

    private boolean showRange, redPower;
    private int cooldown, maxCooldown;
    private int range, yRange;
    private int type;

    private ItemStackHandler handler;
    private Worker worker;

    public TileEntityPedestal(boolean showRange, boolean redPower, int maxCooldown, int range, int yRange, int numSlots) {
        this.showRange = showRange;
        this.redPower = redPower;
        this.maxCooldown = maxCooldown;
        this.range = range;
        this.yRange = yRange;
        this.type = 0;

        this.handler = new ItemStackHandler(numSlots);
        this.worker = new Worker(maxCooldown);
    }

    @Override
    public void writeNBT(NBTTagCompound compound) {
        super.writeNBT(compound);

        compound.setTag("Items", this.handler.serializeNBT());
        compound.setTag("Worker", this.worker.serializeNBT());
        compound.setBoolean("ShowRange", this.showRange);
        compound.setBoolean("NeedRedstone", this.redPower);
        compound.setInteger("Cooldown", this.cooldown);
        compound.setInteger("MaxCooldown", this.maxCooldown);
        compound.setInteger("Range", this.range);
        compound.setInteger("yRange", this.yRange);
        compound.setInteger("Type", this.type);
    }

    @Override
    public void readNBT(NBTTagCompound compound) {
        super.readNBT(compound);

        this.handler.deserializeNBT(compound.getCompoundTag("Items"));
        this.worker.deserializeNBT(compound.getCompoundTag("Worker"));
        this.showRange = compound.getBoolean("ShowRange");
        this.redPower = compound.getBoolean("NeedRedstone");
        this.cooldown = compound.getInteger("Cooldown");
        this.maxCooldown = compound.getInteger("MaxCooldown");
        this.range = compound.getInteger("Range");
        this.yRange = compound.getInteger("yRange");
        this.type = compound.getInteger("Type");
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == FarmingToolsCapabilities.CAPABILITY_WORKER;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return (T) this.handler;
        if (capability == FarmingToolsCapabilities.CAPABILITY_WORKER) return (T) this.worker;
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean canShowRangeArea() {
        return this.showRange;
    }

    @Override
    public void showRangeArea(boolean state) {
        this.showRange = state;
        markDirty();
    }

    @Override
    public boolean needRedstonePower() {
        return this.redPower;
    }

    @Override
    public void setNeedRedstonePower(boolean state) {
        this.redPower = state;
        markDirty();
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
        markDirty();
    }

    public int getVerticalRange() {
        return yRange;
    }

    public void setVerticalRange(int yRange) {
        this.yRange = yRange;
        markDirty();
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public ItemStackHandler getHandler() {
        return handler;
    }

    public Worker getWorker() {
        return worker;
    }


}
