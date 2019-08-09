package com.lazynessmind.farmingtools;

import com.lazynessmind.farmingtools.config.FarmingToolsConfigs;
import com.lazynessmind.farmingtools.handler.FTRegistryHandler;
import com.lazynessmind.farmingtools.network.FTNetworkHandler;
import com.lazynessmind.farmingtools.proxy.CommonProxy;
import com.lazynessmind.farmingtools.tabs.FarmingToolsBlocksTab;
import com.lazynessmind.farmingtools.tabs.FarmingToolsItemsTab;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = FarmingToolsConst.MODID, name = FarmingToolsConst.NAME, version = FarmingToolsConst.VERSION, guiFactory = FarmingToolsConst.GUI_FACTORY, updateJSON = FarmingToolsConst.UPDATE_JSON)
public class FarmingTools {

    @Mod.Instance
    public static FarmingTools instance;
    @SidedProxy(clientSide = FarmingToolsConst.CLIENT_PROXY, serverSide = FarmingToolsConst.COMMON_PROXY)
    public static CommonProxy common;
    public static FarmingToolsItemsTab rndItemsTab = new FarmingToolsItemsTab();
    public static FarmingToolsBlocksTab rndBlocksTab = new FarmingToolsBlocksTab();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        FarmingToolsConfigs.preInit();
        FTNetworkHandler.init();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        FarmingToolsConfigs.preInit();
        FarmingToolsConfigs.clientPreInit();
        FTRegistryHandler.registry();
    }


    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}
