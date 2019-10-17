package com.lazynessmind.farmingtools.interfaces;

import net.minecraft.tileentity.TileEntity;

/**
 * Disclaimer:
 *
 * This class was made by CJMinecraft;
 * All the credits to the guy that made something that i can use to be lazy as fuck :)
 *
 * class link : https://github.com/CJMinecraft01/BitOfEverything/blob/master/src/main/java/cjminecraft/bitofeverything/capabilties/IWork.java
 */

/**
 * An interface which represents a {@link TileEntity} which will "do work". The
 * interface representation of {@link com.lazynessmind.farmingtools.init.FarmingToolsCapabilities#CAPABILITY_WORKER}
 *
 * @author CJMinecraft
 *
 */
public interface IWork {

    /**
     * Return the current work that has been done
     *
     * @return the current work that has been done
     */
    int getWorkDone();

    /**
     * Return the maximum amount of work the {@link TileEntity} can do
     *
     * @return the maximum value for the "cooldown"
     */
    int getMaxWork();

    /**
     * Called when every tick by the {@link TileEntity} Typically will update
     * the "cooldown" value
     */
    void doWork();

    /**
     * Called when the work has been completed i.e. when the cooldown has reset
     */
    void workDone();

}