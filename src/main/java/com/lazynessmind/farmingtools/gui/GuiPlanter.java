package com.lazynessmind.farmingtools.gui;

import com.lazynessmind.farmingtools.container.ContainerPedestal;
import com.lazynessmind.farmingtools.container.slots.SlotPlanter;
import com.lazynessmind.farmingtools.init.tileentities.TileEntityPlanter;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiPlanter extends GuiBase {

    private TileEntityPlanter planter;

    public GuiPlanter(InventoryPlayer inventoryPlayer, TileEntityPlanter tileEntityPlanter) {
        super(new ContainerPedestal(inventoryPlayer, tileEntityPlanter, new SlotPlanter(tileEntityPlanter.getMainHandler(), 0, 80, 33)), inventoryPlayer, tileEntityPlanter, "farmingtools:planter");
        this.planter = tileEntityPlanter;
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 10.0f, 1.0f);
        mc.getTextureManager().bindTexture(GuiTextures.HARVESTER_TEXTURE);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
