package com.lazynessmind.farmingtools.config;

import com.lazynessmind.farmingtools.FarmingToolsConst;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.GuiConfigEntries.CategoryEntry;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FTConfigGuiFactory implements IModGuiFactory {

    @Override
    public void initialize(Minecraft minecraftInstance) {
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

    public static class RndConfigGui extends GuiConfig {

        public RndConfigGui(GuiScreen parentScreen) {
            super(parentScreen, getConfigElements(), FarmingToolsConst.MODID, false, false, I18n.format("gui.config.main_title"));
        }

        private static List<IConfigElement> getConfigElements() {
            List<IConfigElement> list = new ArrayList<IConfigElement>();
            list.add(new DummyCategoryElement(I18n.format("gui.config.category.items"), "gui.config.category.items", CategoryEntryItems.class));
            list.add(new DummyCategoryElement(I18n.format("gui.config.category.blocks"), "gui.config.category.blocks", CategoryEntryBlocks.class));
            return list;
        }

        public static class CategoryEntryItems extends CategoryEntry {

            public CategoryEntryItems(GuiConfig owningScreen, GuiConfigEntries owningEntryList,
                                      IConfigElement configElement) {
                super(owningScreen, owningEntryList, configElement);
            }

            @Override
            protected GuiScreen buildChildScreen() {
                Configuration config = FarmingToolsConfigs.getConfig();
                ConfigElement categoryItems = new ConfigElement(config.getCategory(FarmingToolsConfigs.CATEGORY_NAME_ITEMS));
                List<IConfigElement> propertiesOnScreen = categoryItems.getChildElements();
                String windowTitle = I18n.format("gui.config.category.items");
                return new GuiConfig(owningScreen, propertiesOnScreen, owningScreen.modID, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart, this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart, windowTitle);
            }

        }

        public static class CategoryEntryBlocks extends CategoryEntry {

            public CategoryEntryBlocks(GuiConfig owningScreen, GuiConfigEntries owningEntryList,
                                       IConfigElement configElement) {
                super(owningScreen, owningEntryList, configElement);
            }

            @Override
            protected GuiScreen buildChildScreen() {
                Configuration config = FarmingToolsConfigs.getConfig();
                ConfigElement categoryBlocks = new ConfigElement(config.getCategory(FarmingToolsConfigs.CATEGORY_NAME_BLOCKS));
                List<IConfigElement> propertiesOnScreen = categoryBlocks.getChildElements();
                String windowTitle = I18n.format("gui.config.category.blocks");
                return new GuiConfig(owningScreen, propertiesOnScreen, owningScreen.modID, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart, this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart, windowTitle);
            }

        }

    }

    @Override
    public boolean hasConfigGui() {
        return true;
    }

    @Override
    public GuiScreen createConfigGui(GuiScreen parentScreen) {
        return new RndConfigGui(parentScreen);
    }

}