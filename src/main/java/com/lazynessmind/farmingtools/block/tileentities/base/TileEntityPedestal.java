package com.lazynessmind.farmingtools.block.tileentities.base;

import com.lazynessmind.farmingtools.interfaces.IRange;
import com.lazynessmind.farmingtools.interfaces.IRedPower;
import com.lazynessmind.farmingtools.util.TypeUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

@SuppressWarnings("unchecked cast")
public class TileEntityPedestal extends FTTileEntity implements IRange, IRedPower {

    private boolean showRange, redPower;
    private int range, yRange;
    private int type;
    private double timeBetween;

    private ItemStackHandler itemStackHandler;

    public TileEntityPedestal(boolean showRange, boolean redPower, int yRange) {
        this.showRange = showRange;
        this.redPower = redPower;
        this.range = TypeUtil.getHorizontalRangeFromType(0);
        this.timeBetween = TypeUtil.getTimeBetweenFromType(0);
        this.yRange = yRange;
        this.type = 0;

        this.itemStackHandler = new ItemStackHandler(1);
    }

    @Override
    public void writeNBT(NBTTagCompound compound) {
        super.writeNBT(compound);

        compound.setTag("Items", this.itemStackHandler.serializeNBT());
        compound.setBoolean("ShowRange", this.showRange);
        compound.setBoolean("NeedRedstone", this.redPower);
        compound.setInteger("Range", this.range);
        compound.setInteger("yRange", this.yRange);
        compound.setInteger("Type", this.type);
        compound.setDouble("TimeBetween", this.timeBetween);
    }

    @Override
    public void readNBT(NBTTagCompound compound) {
        super.readNBT(compound);

        this.itemStackHandler.deserializeNBT(compound.getCompoundTag("Items"));
        this.showRange = compound.getBoolean("ShowRange");
        this.redPower = compound.getBoolean("NeedRedstone");
        this.range = compound.getInteger("Range");
        this.yRange = compound.getInteger("yRange");
        this.type = compound.getInteger("Type");
        this.timeBetween = compound.getDouble("RandomChange");
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == EnumFacing.WEST || facing == EnumFacing.EAST || facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH) {
                return (T) this.itemStackHandler;
            }
        }
        return super.getCapability(capability, facing);
    }

    public void updateTile() {
        markDirty();
        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
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

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public double getTimeBetween() {
        return timeBetween;
    }

    public void setTimeBetween(double timeBetween) {
        this.timeBetween = timeBetween;
    }

    public ItemStackHandler getMainHandler() {
        return this.itemStackHandler;
    }

    public ItemStack mainSlot() {
        return getMainHandler().getStackInSlot(0);
    }
}
