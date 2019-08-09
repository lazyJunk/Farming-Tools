package com.lazynessmind.farmingtools.gui;

import com.lazynessmind.farmingtools.container.ContainerHarvester;
import com.lazynessmind.farmingtools.init.tileentities.TileEntityHarvester;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;

import java.awt.*;

public class GuiHarvester extends GuiBase {

    private TileEntityHarvester tileEntityHarvester;

    public GuiHarvester(InventoryPlayer inventoryPlayer, TileEntityHarvester tileEntityHarvester) {
        super(new ContainerHarvester(inventoryPlayer, tileEntityHarvester), inventoryPlayer, tileEntityHarvester, "farmingtools:harvester");
        this.tileEntityHarvester = tileEntityHarvester;
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String titleGui = "Harvester";
        String hoeNeeded = "Where is the hoe?";
        fontRenderer.drawString(titleGui, (this.xSize / 2 - fontRenderer.getStringWidth(titleGui) / 2) + 3, 8, 4210752);
        if (!tileEntityHarvester.hasHoeOnSlot()) {
            fontRenderer.drawString(hoeNeeded, (this.xSize / 2 - fontRenderer.getStringWidth(hoeNeeded) / 2) + 3, 70, Color.RED.getRGB());
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 10.0f, 1.0f);
        mc.getTextureManager().bindTexture(GuiTextures.HARVESTER_TEXTURE);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
