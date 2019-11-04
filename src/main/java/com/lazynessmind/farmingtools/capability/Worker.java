package com.lazynessmind.farmingtools.capability;

import com.lazynessmind.farmingtools.interfaces.IWork;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.util.INBTSerializable;

/*
 * Disclaimer:
 *
 * This class was made by CJMinecraft;
 * All the credits to the guy that made something that i can use to be lazy as fuck :)
 *
 * class link : https://github.com/CJMinecraft01/BitOfEverything/blob/master/src/main/java/cjminecraft/bitofeverything/capabilties/Worker.java
 *
 * From: lazynessmind
 */


/**
 * The default implementation of the {@link com.lazynessmind.farmingtools.init.FarmingToolsCapabilities#CAPABILITY_WORKER}
 *
 * @author CJMinecraft
 *
 */

/*
 * Modified by lazynessmind
 *
 * - added setWork, setWorkDone, mode
 */
public class Worker implements IWork, INBTSerializable<NBTTagCompound> {

    private int cooldown;
    private int maxCooldown;
    private boolean reversed;
    private int mode;

    /**
     * What will be ran in the {@link #doWork()} and {@link #workDone()} methods
     */
    private Runnable doWork, workDone;

    /**
     * Create an instance of the {@link com.lazynessmind.farmingtools.init.FarmingToolsCapabilities#CAPABILITY_WORKER} which
     * will do work
     *
     * @param maxCooldown
     *            The maximum number of ticks until work is done
     * @param doWork
     *            What will happen every tick
     * @param workDone
     *            What will happen when the work is completed
     *
     */
    public Worker(int maxCooldown, Runnable doWork, Runnable workDone) {
        this.cooldown = 0;
        this.maxCooldown = maxCooldown;
        this.doWork = doWork;
        this.workDone = workDone;
    }

    /**
     * Create an instance of the {@link com.lazynessmind.farmingtools.init.FarmingToolsCapabilities#CAPABILITY_WORKER}
     *
     * Possibility to add runnable after creating the instance;
     *  @author lazynessmind
     *
     * @param maxCooldown
     *            The maximum number of ticks until work is done
     *
     */
    public Worker(int maxCooldown){
        this.cooldown = 0;
        this.maxCooldown = maxCooldown;
    }

    /**
     * Set doWork runnable that run every tick;
     *
     * @author lazynessmind
     *
     * @param doWork
     *            Runnable that run every tick
     */
    public void setDoWork(Runnable doWork) {
        this.doWork = doWork;
    }

    /**
     * Sets Runnable that will run when the work is done;
     *
     * @author lazynessmind
     *
     * @param workDone
     *            Runnable that will run when the work is done
     */
    public void setWorkDone(Runnable workDone) {
        this.workDone = workDone;
    }

    /**
     * Set the maximum number of ticks until work is done
     *
     * @param maxCooldown
     *            The maximum number of ticks until work is done
     * @return The updated {@link Worker}
     */
    public Worker setMaxCooldown(int maxCooldown) {
        this.maxCooldown = maxCooldown;
        return this;
    }

    /**
     * Get the cooldown
     */
    @Override
    public int getWorkDone() {
        return this.cooldown;
    }

    /**
     * Get the cooldown cap or max cooldown
     */
    @Override
    public int getMaxWork() {
        return this.maxCooldown;
    }

    public void setReversed(boolean reversed) {
        this.reversed = reversed;
    }

    public boolean isReversed() {
        return this.reversed;
    }

    /**
     * To be called every tick using {@link ITickable}
     */
    @Override
    public void doWork() {
        if (this.reversed)
            this.cooldown--;
        else
            this.cooldown++;
        this.doWork.run();
        if (this.cooldown == this.maxCooldown)
            workDone();
        if (this.maxCooldown != 0)
            this.cooldown %= this.maxCooldown; // Caps the cooldown to the max cooldown

        if (this.cooldown < 0)
            this.cooldown = 0;
    }

    /**
     * To be used internally, called when the work has been done
     */
    @Override
    public void workDone() {
        this.workDone.run();
    }

    /**
     * Write all data to an {@link NBTTagCompound}
     */
    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("cooldown", this.cooldown);
        nbt.setInteger("maxCooldown", this.maxCooldown);
        return nbt;
    }

    /**
     * Read all data from an {@link NBTTagCompound}
     */
    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        this.cooldown = nbt.getInteger("cooldown");
        this.maxCooldown = nbt.getInteger("maxCooldown");
    }

}