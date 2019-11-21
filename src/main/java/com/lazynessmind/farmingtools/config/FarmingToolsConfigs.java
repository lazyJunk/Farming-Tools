package com.lazynessmind.farmingtools.config;

import com.lazynessmind.farmingtools.FarmingToolsConst;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FarmingToolsConfigs {

    private static Configuration config = null;

    public static final String CATEGORY_NAME_BLOCKS = "blocks";
    public static final String CATEGORY_NAME_ITEMS = "items";
    public static final String CATEGORY_NAME_FEATURE = "feature";

    //Blocks/TileEntity
    public static int fertilizedSoilSpeedVar;
    public static int growthPedestalSpeedVar;

    //Items
    public static int advancedBoneMealRange;

    //Features
    public static boolean showUpdateMessage;

    public static void preInit() {
        File configFile = new File(Loader.instance().getConfigDir(), "FarmingToolsConfigs.cfg");
        config = new Configuration(configFile);
        syncFromFiles();
    }

    public static Configuration getConfig() {
        return config;
    }


    public static void clientPreInit() {
        MinecraftForge.EVENT_BUS.register(new ConfigEventHandler());
    }

    public static void syncFromFiles() {
        syncConfig(true, true);
    }

    public static void syncFromGui() {
        syncConfig(false, true);
    }

    public static void syncFromFields() {
        syncConfig(false, false);
    }

    private static void syncConfig(boolean loadFromConfigFile, boolean readFieldsFromConfig) {
        if (loadFromConfigFile)
            config.load();

        setItemsConfigs(readFieldsFromConfig);
        setBlocksConfigs(readFieldsFromConfig);
        setFeatureConfigs(readFieldsFromConfig);

        if (config.hasChanged())
            config.save();
    }

    private static void setBlocksConfigs(boolean readFieldsFromConfig) {
        Property fertilizedSoilSpeed = config.get(CATEGORY_NAME_BLOCKS, "fertilized_soil_speed", 7);
        fertilizedSoilSpeed.setLanguageKey("gui.config.blocks.fertilizedSoil.name");
        fertilizedSoilSpeed.setComment(I18n.format("gui.config.blocks.fertilizedSoil.comment"));
        fertilizedSoilSpeed.setMinValue(1);

        Property growthPedestalSpeed = config.get(CATEGORY_NAME_BLOCKS, "growth_speed_speed", 7);
        growthPedestalSpeed.setLanguageKey("gui.config.blocks.growthPedestal.name");
        growthPedestalSpeed.setComment(I18n.format("gui.config.blocks.growthPedestal.comment"));
        growthPedestalSpeed.setMinValue(1);

        List<String> propertyOrderBlocks = new ArrayList<String>();
        propertyOrderBlocks.add(fertilizedSoilSpeed.getName());
        propertyOrderBlocks.add(growthPedestalSpeed.getName());
        config.setCategoryPropertyOrder(CATEGORY_NAME_BLOCKS, propertyOrderBlocks);

        if (readFieldsFromConfig) {
            fertilizedSoilSpeedVar = fertilizedSoilSpeed.getInt();
            growthPedestalSpeedVar = growthPedestalSpeed.getInt();
        }

        fertilizedSoilSpeed.set(fertilizedSoilSpeedVar);
        growthPedestalSpeed.set(growthPedestalSpeedVar);
    }

    public static void setItemsConfigs(boolean readFieldsFromConfig) {
        Property propertyAdvancedBoneMealRange = config.get(CATEGORY_NAME_ITEMS, "advanced_bone_meal_range", 5);
        propertyAdvancedBoneMealRange.setLanguageKey("gui.config.items.advancedBoneMeal.name");
        propertyAdvancedBoneMealRange.setComment(I18n.format("gui.config.items.advancedBoneMeal.comment"));
        propertyAdvancedBoneMealRange.setMinValue(1);
        propertyAdvancedBoneMealRange.setMaxValue(100);


        List<String> propertyOrderItems = new ArrayList<String>();
        propertyOrderItems.add(propertyAdvancedBoneMealRange.getName());
        config.setCategoryPropertyOrder(CATEGORY_NAME_ITEMS, propertyOrderItems);

        if (readFieldsFromConfig) {
            advancedBoneMealRange = propertyAdvancedBoneMealRange.getInt();
        }

        propertyAdvancedBoneMealRange.set(advancedBoneMealRange);
    }

    private static void setFeatureConfigs(boolean readFieldsFromConfig) {
        Property showUpdateMessageProp = config.get(CATEGORY_NAME_FEATURE, "show_update_message", true);
        showUpdateMessageProp.setLanguageKey("gui.config.features.showUpdateMessage.name");
        showUpdateMessageProp.setComment(I18n.format("gui.config.features.showUpdateMessage.comment"));

        List<String> propertyOrderFeatures = new ArrayList<String>();
        propertyOrderFeatures.add(showUpdateMessageProp.getName());
        config.setCategoryPropertyOrder(CATEGORY_NAME_ITEMS, propertyOrderFeatures);

        if (readFieldsFromConfig) {
            showUpdateMessage = showUpdateMessageProp.getBoolean();
        }

        showUpdateMessageProp.set(showUpdateMessage);
    }

    public static class ConfigEventHandler {

        @SubscribeEvent(priority = EventPriority.LOWEST)
        public void onEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(FarmingToolsConst.MODID)) {
                syncFromGui();
            }
        }

    }

}