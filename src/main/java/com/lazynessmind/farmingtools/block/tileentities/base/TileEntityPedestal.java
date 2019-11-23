package com.lazynessmind.farmingtools.block.tileentities.base;

import com.lazynessmind.farmingtools.interfaces.IRange;
import com.lazynessmind.farmingtools.interfaces.IRedPower;
import com.lazynessmind.farmingtools.power.Energy;
import com.lazynessmind.farmingtools.util.TypeUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;

@SuppressWarnings("unchecked cast")
public abstract class TileEntityPedestal extends TileSidedInventoryBase implements IRange, IRedPower {

    private boolean showRange, redPower;
    private int type;
    private double workTime;
    private int costPerWork;

    private int[] SLOTS_SIDES = new int[]{0};

    private IItemHandler handlerEast = new SidedInvWrapper(this, EnumFacing.EAST);
    private IItemHandler handlerWest = new SidedInvWrapper(this, EnumFacing.WEST);
    private IItemHandler handlerNorth = new SidedInvWrapper(this, EnumFacing.NORTH);
    private IItemHandler handlerSouth = new SidedInvWrapper(this, EnumFacing.SOUTH);

    private Energy energy;

    public TileEntityPedestal(boolean showRange, boolean redPower) {
        super(1);
        this.showRange = showRange;
        this.redPower = redPower;
        this.workTime = TypeUtil.getWorkTime(0);
        this.type = 0;
        this.costPerWork = TypeUtil.energyExtractFromType(0);

        this.energy = new Energy(10000, 10000);
    }

    public Energy getEnergy() {
        return this.energy;
    }

    @Override
    public void writeNBT(NBTTagCompound compound) {
        super.writeNBT(compound);

        compound.setTag("Items", this.getInv().serializeNBT());
        compound.setBoolean("ShowRange", this.showRange);
        compound.setBoolean("NeedRedstone", this.redPower);
        compound.setInteger("Type", this.type);
        compound.setDouble("TimeBetween", this.workTime);
        this.getEnergy().save(compound);
    }

    @Override
    public void readNBT(NBTTagCompound compound) {
        super.readNBT(compound);

        this.getInv().deserializeNBT(compound.getCompoundTag("Items"));
        this.showRange = compound.getBoolean("ShowRange");
        this.redPower = compound.getBoolean("NeedRedstone");
        this.type = compound.getInteger("Type");
        this.workTime = compound.getDouble("RandomChange");
        this.getEnergy().load(compound);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return true;
        } else if(capability == CapabilityEnergy.ENERGY){
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            if (facing == EnumFacing.EAST)
                return (T) handlerEast;
            else if (facing == EnumFacing.WEST)
                return (T) handlerWest;
            else if (facing == EnumFacing.NORTH)
                return (T) handlerNorth;
            else if (facing == EnumFacing.SOUTH)
                return (T) handlerSouth;
        if (facing != null && capability == CapabilityEnergy.ENERGY)
                return (T) energy;
        return super.getCapability(capability, facing);
    }

    public void updateValues(){
        this.setType(this.getBlockMetadata());
        this.outWorkTime(TypeUtil.getWorkTime(this.getType()));
        this.setCostPerWork(TypeUtil.energyExtractFromType(this.getType()));
    }

    public boolean canWork(){
        return this.energy.canExtractFromInternal();
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

    public void setType(int type) {
        this.type = type;
        markDirty();
    }

    public int getType() {
        return this.type;
    }

    public double getWorkTime() {
        return this.workTime;
    }

    public void outWorkTime(double timeBetween) {
        this.workTime = timeBetween;
    }

    public int getCostPerWork() {
        return this.costPerWork;
    }

    public void setCostPerWork(int costPerWork) {
        this.costPerWork = costPerWork;
    }

    @Override
    protected int[] getSlotForFace(EnumFacing facing) {
        if (facing == EnumFacing.EAST || facing == EnumFacing.WEST || facing == EnumFacing.NORTH) {
            return this.SLOTS_SIDES;
        } else {
            return new int[0];
        }
    }
}
