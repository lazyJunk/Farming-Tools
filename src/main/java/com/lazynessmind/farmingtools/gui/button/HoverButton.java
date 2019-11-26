package com.lazynessmind.farmingtools.gui.button;

import com.lazynessmind.farmingtools.gui.GuiTextures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class HoverButton extends GuiButton {

    private GuiTextures.Icon icon;

    public HoverButton(int buttonId, int x, int y, GuiTextures.Icon btnIcon) {
        super(buttonId, x, y, "");
        this.icon = btnIcon;
        this.enabled = false;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            mc.getTextureManager().bindTexture(GuiTextures.BUTTON_TEXTURES);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(this.x + 12, this.y + 2, icon.getX(), icon.getY(), icon.getW(), icon.getH());
        }
    }
}
