package com.lazynessmind.farmingtools.client.gui;

import com.lazynessmind.farmingtools.client.gui.container.ContainerPedestal;
import com.lazynessmind.farmingtools.client.gui.container.slots.SlotGrowthPedestal;
import com.lazynessmind.farmingtools.block.tileentities.TileEntityGrowthPedestal;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiGrowthPedestal extends GuiBase {


    public GuiGrowthPedestal(InventoryPlayer inventoryPlayer, TileEntityGrowthPedestal tileEntityGrowthPedestal) {
        super(new ContainerPedestal(inventoryPlayer, tileEntityGrowthPedestal, new SlotGrowthPedestal(tileEntityGrowthPedestal.getMainHandler(), 0, 80, 33)), inventoryPlayer, tileEntityGrowthPedestal, "farmingtools:growth_pedestal");
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String titleGui = "Growth Pedestal";
        fontRenderer.drawString(titleGui, (this.xSize / 2 - fontRenderer.getStringWidth(titleGui) / 2) + 3, 8, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 10.0f, 1.0f);
        mc.getTextureManager().bindTexture(GuiTextures.HARVESTER_TEXTURE);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
