package com.lazynessmind.farmingtools.power;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.IEnergyStorage;

public class Energy implements IEnergyStorage {

    private int currentEnergy;
    private int capacity;
    private int maxReceive;
    private int maxExtract;


    public Energy(int capacity, int maxTransfer) {
        this(capacity, maxTransfer, maxTransfer, 0);
    }

    private Energy(int capacity, int maxReceive, int maxExtract, int energy) {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.currentEnergy = Math.max(0, Math.min(capacity, energy));
    }

    public void save(NBTTagCompound compound){
        compound.setInteger("Capacity", this.capacity);
        compound.setInteger("MaxReceive", this.maxReceive);
        compound.setInteger("MaxExtract", this.maxExtract);
        compound.setInteger("CurrentEnergy", this.currentEnergy);
    }

    public void load(NBTTagCompound compound){
        this.capacity = compound.getInteger("Capacity");
        this.maxReceive = compound.getInteger("MaxReceive");
        this.maxExtract = compound.getInteger("MaxExtract");
        this.currentEnergy = compound.getInteger("CurrentEnergy");
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (!canReceive())
            return 0;

        int energyReceived = Math.min(capacity - currentEnergy, Math.min(this.maxReceive, maxReceive));
        if (!simulate)
            currentEnergy += energyReceived;
        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if (!canExtract())
            return 0;

        int energyExtracted = Math.min(currentEnergy, Math.min(this.maxExtract, maxExtract));
        if (!simulate)
            currentEnergy -= energyExtracted;
        return energyExtracted;
    }

    @Override
    public int getEnergyStored() {
        return currentEnergy;
    }

    @Override
    public int getMaxEnergyStored() {
        return capacity;
    }

    @Override
    public boolean canExtract() {
        return this.maxExtract > 0;
    }

    @Override
    public boolean canReceive() {
        return this.maxReceive > 0;
    }

    public boolean canExtractFromInternal(){
        return this.currentEnergy != 0;
    }

    public void removeFromBuffer(int amount){
        int newValue = this.currentEnergy - amount;
        if(newValue > 0) this.currentEnergy = newValue;
        else if(newValue < 0) this.currentEnergy = 0;
    }

    public void setCurrentEnergy(int currentEnergy) {
        this.currentEnergy = currentEnergy;
    }

    public int getCurrentEnergy() {
        return this.currentEnergy;
    }
}
