package com.lazynessmind.farmingtools.gui;

import com.lazynessmind.farmingtools.container.ContainerGrowthPedestal;
import com.lazynessmind.farmingtools.init.tileentities.TileEntityGrowthPedestal;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiGrowthPedestal extends GuiBase {

    private TileEntityGrowthPedestal te;

    public GuiGrowthPedestal(InventoryPlayer inventoryPlayer, TileEntityGrowthPedestal te) {
        super(new ContainerGrowthPedestal(inventoryPlayer, te), inventoryPlayer, te, "farmingtools:growth_pedestal");
        this.te = te;
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
