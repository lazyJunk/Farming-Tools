package com.lazynessmind.farmingtools.init.tileentities;

import com.lazynessmind.farmingtools.interfaces.IRange;
import com.lazynessmind.farmingtools.interfaces.IRedPower;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityPedestal extends FTTileEntity implements IRange, IRedPower {

    private boolean showRange, redPower;
    private int cooldown, maxCooldown;
    private int range, yRange;
    private int type;

    public TileEntityPedestal(boolean showRange, boolean redPower, int maxCooldown, int range, int yRange) {
        this.showRange = showRange;
        this.redPower = redPower;
        this.maxCooldown = maxCooldown;
        this.range = range;
        this.yRange = yRange;
        this.type = type;
    }

    @Override
    public void writeNBT(NBTTagCompound compound) {
        super.writeNBT(compound);

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

        this.showRange = compound.getBoolean("ShowRange");
        this.redPower = compound.getBoolean("NeedRedstone");
        this.cooldown = compound.getInteger("Cooldown");
        this.maxCooldown = compound.getInteger("MaxCooldown");
        this.range = compound.getInteger("Range");
        this.yRange = compound.getInteger("yRange");
        this.type = compound.getInteger("Type");
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

    public void work(Runnable workDone) {
        if (this.cooldown < maxCooldown) {
            this.cooldown++;
        } else if (this.cooldown == maxCooldown) {
            this.cooldown = 0;
            workDone.run();
        }
    }

    //GETTERSETTERS
    public int getCooldown() {
        return cooldown;
    }

    public int getMaxCooldown() {
        return maxCooldown;
    }

    public void setMaxCooldown(int maxCooldown) {
        this.maxCooldown = maxCooldown;
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
}
