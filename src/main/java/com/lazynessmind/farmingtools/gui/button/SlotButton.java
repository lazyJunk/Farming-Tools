package com.lazynessmind.farmingtools.gui.button;

import com.lazynessmind.farmingtools.gui.GuiTextures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SlotButton extends GuiButton {

    public GuiTextures.Icon icon;

    public SlotButton(int buttonId, int x, int y, GuiTextures.Icon icon) {
        super(buttonId, x, y, 18, 18,"");
        enabled = false;
        this.icon = icon;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        super.drawButton(mc, mouseX, mouseY, partialTicks);
        if (this.visible) {
            mc.getTextureManager().bindTexture(GuiTextures.BUTTON_TEXTURES);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            Base base = Base.BASIC;

            this.drawTexturedModalRect(this.x, this.y, base.getX(), base.getY(), this.width, this.height);
            this.drawTexturedModalRect(this.x + 2, this.y + 4, icon.getX(), icon.getY(), icon.getW(), icon.getH());
        }
    }

    @SideOnly(Side.CLIENT)
    private enum Base {
        BASIC(52, 0);

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
