package com.lazynessmind.farmingtools.init;

import com.lazynessmind.farmingtools.interfaces.IWork;
import com.lazynessmind.farmingtools.capability.Worker;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class FarmingToolsCapabilities {

    @CapabilityInject(IWork.class)
    public static Capability<IWork> CAPABILITY_WORKER = null;

    public static void register(){
        CapabilityManager.INSTANCE.register(IWork.class, new CapabilityWorker(), Worker.class);
    }

    public static class CapabilityWorker implements Capability.IStorage<IWork> {

        @Override
        public NBTBase writeNBT(Capability<IWork> capability, IWork instance, EnumFacing side) {
            return null;
        }

        @Override
        public void readNBT(Capability<IWork> capability, IWork instance, EnumFacing side, NBTBase nbt) {
        }

    }
}
