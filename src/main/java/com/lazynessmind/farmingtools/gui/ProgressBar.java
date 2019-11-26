package com.lazynessmind.farmingtools.gui;

import com.lazynessmind.farmingtools.FarmingToolsConst;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ProgressBar extends Gui {

    private static final ResourceLocation BAR_STYLES = new ResourceLocation(FarmingToolsConst.MODID, "textures/gui/bar_styles.png");

    private int x, y, w, h;
    private int maxProgress;
    private int currentProgress;
    private float percentage = 0;

    private Style style;

    public ProgressBar(int xIn, int yIn, int currentProgress, int maxProgress, Style style) {
        this.x = xIn;
        this.y = yIn;
        this.w = style.getW();
        this.h = style.getH();
        this.currentProgress = currentProgress;
        this.maxProgress = maxProgress;
        this.percentage = this.currentProgress / (float) maxProgress;

        this.style = style;
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
        this.percentage = this.currentProgress / (float) maxProgress;
    }

    public void draw(int currentProgress) {
        setCurrentProgress(currentProgress);
        Minecraft.getMinecraft().getTextureManager().bindTexture(BAR_STYLES);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        this.drawTexturedModalRect(this.x, this.y, this.style.getBackgroundX(), this.style.getBackgroundY(), this.w, this.h);
        this.drawTexturedModalRect(this.x, this.y, this.style.getForegroundX(), this.style.getForegroundY(), (int) (this.percentage * this.w), this.h);
    }

    @SideOnly(Side.CLIENT)
    public enum Style {
        ARROW(0, 10, 22, 10, 22, 16),
        XP(0, 0, 0, 5, 102, 5),
        ENERGY(102, 0, 102, 5, 102, 5),
        SMALL_VERTICAL_XP(44, 10, 50, 10, 6, 14);

        private final int xBack;
        private final int yBack;
        private final int xFore;
        private final int yFore;
        private final int w;
        private final int h;

        Style(int xStartBack, int yStartBack, int xStartFore, int yStartFore, int wIn, int hIn) {
            this.xBack = xStartBack;
            this.yBack = yStartBack;
            this.xFore = xStartFore;
            this.yFore = yStartFore;
            this.w = wIn;
            this.h = hIn;
        }

        public int getBackgroundX() {
            return this.xBack;
        }

        public int getBackgroundY() {
            return this.yBack;
        }

        public int getForegroundX() {
            return this.xFore;
        }

        public int getForegroundY() {
            return this.yFore;
        }

        public int getW() {
            return this.w;
        }

        public int getH() {
            return this.h;
        }
    }
}
