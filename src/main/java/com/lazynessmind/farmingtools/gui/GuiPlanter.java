package com.lazynessmind.farmingtools.gui;

import com.lazynessmind.farmingtools.FarmingToolsConst;
import com.lazynessmind.farmingtools.container.ContainerHarvester;
import com.lazynessmind.farmingtools.container.ContainerPlanter;
import com.lazynessmind.farmingtools.init.tileentities.TileEntityHarvester;
import com.lazynessmind.farmingtools.init.tileentities.TileEntityPlanter;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class GuiPlanter extends GuiContainer {

    private static final ResourceLocation TEXTURE = new ResourceLocation(FarmingToolsConst.MODID + ":textures/gui/harvester.png");
    private InventoryPlayer inventoryPlayer;
    private TileEntityPlanter tileEntityPlanter;

    public GuiPlanter(InventoryPlayer inventoryPlayer, TileEntityPlanter tileEntityPlanter) {
        super(new ContainerPlanter(inventoryPlayer, tileEntityPlanter));
        this.inventoryPlayer = inventoryPlayer;
        this.tileEntityPlanter = tileEntityPlanter;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String titleGui = "Planter";
        fontRenderer.drawString(titleGui, (this.xSize /2 - fontRenderer.getStringWidth(titleGui) /2) +3, 8, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 10.0f, 1.0f);
        mc.getTextureManager().bindTexture(TEXTURE);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
