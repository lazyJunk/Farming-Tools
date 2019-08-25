package com.lazynessmind.farmingtools.gui;

import com.lazynessmind.farmingtools.FarmingToolsConst;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiTextures {

    public static final ResourceLocation BUTTON_TEXTURES = new ResourceLocation(FarmingToolsConst.MODID + ":textures/gui/buttons.png");
    public static final ResourceLocation HARVESTER_TEXTURE = new ResourceLocation(FarmingToolsConst.MODID + ":textures/gui/harvester.png");

    @SideOnly(Side.CLIENT)
    public enum Icon {
        BB(0, 32, 12, 12),
        REDSTONE(12, 32, 12, 12),
        INFO(24, 32, 12, 12);

        private final int x;
        private final int y;
        private final int w;
        private final int h;

        Icon(int xIn, int yIn, int w, int h) {
            this.x = xIn;
            this.y = yIn;
            this.w = w;
            this.h = h;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public int getH() {
            return h;
        }

        public int getW() {
            return w;
        }
    }
}
