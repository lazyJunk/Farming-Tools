package com.lazynessmind.farmingtools.gui.button;

import com.lazynessmind.farmingtools.gui.GuiTextures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TwoStateButton extends GuiButton {

    public boolean isActive;
    public GuiTextures.Icon icon;

    public TwoStateButton(int buttonId, int x, int y, GuiTextures.Icon icon) {
        super(buttonId, x, y, 26, 16, "");
        this.isActive = true;
        this.icon = icon;
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return this.visible && mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            mc.getTextureManager().bindTexture(GuiTextures.BUTTON_TEXTURES);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            boolean hover = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            Base base;

            if (this.isActive) {
                if (hover) {
                    base = Base.ACTIVE_HOVER;
                } else {
                    base = Base.ACTIVE;
                }
            } else {
                if (hover) {
                    base = Base.NOT_ACTIVE_HOVER;
                } else {
                    base = Base.NOT_ACTIVE;
                }
            }

            this.drawTexturedModalRect(this.x, this.y, base.getX(), base.getY(), this.width, this.height);
            this.drawTexturedModalRect(this.x + 12, this.y + 2, icon.getX(), icon.getY(), icon.getW(), icon.getH());
        }
    }

    @SideOnly(Side.CLIENT)
    private enum Base {
        ACTIVE(26, 0),
        ACTIVE_HOVER(26, 16),
        NOT_ACTIVE(0, 0),
        NOT_ACTIVE_HOVER(0, 16);

        private final int x;
        private final int y;

        Base(int xIn, int yIn) {
            this.x = xIn;
            this.y = yIn;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }
    }
}
