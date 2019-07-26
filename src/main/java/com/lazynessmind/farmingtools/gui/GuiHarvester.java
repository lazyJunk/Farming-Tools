package com.lazynessmind.farmingtools.gui;

import com.lazynessmind.farmingtools.FarmingToolsConst;
import com.lazynessmind.farmingtools.container.ContainerHarvester;
import com.lazynessmind.farmingtools.init.tileentities.TileEntityHarvester;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class GuiHarvester extends GuiContainer {

    private static final ResourceLocation TEXTURE = new ResourceLocation(FarmingToolsConst.MODID + ":textures/gui/harvester.png");
    private InventoryPlayer inventoryPlayer;
    private TileEntityHarvester tileEntityHarvester;

    public GuiHarvester(InventoryPlayer inventoryPlayer, TileEntityHarvester tileEntityHarvester) {
        super(new ContainerHarvester(inventoryPlayer, tileEntityHarvester));
        this.inventoryPlayer = inventoryPlayer;
        this.tileEntityHarvester = tileEntityHarvester;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String titleGui = "Harvester";
        fontRenderer.drawString(titleGui, (this.xSize /2 - fontRenderer.getStringWidth(titleGui) /2) +3, 8, Color.RED.getRGB());
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 10.0f, 1.0f);
        mc.getTextureManager().bindTexture(TEXTURE);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
