package com.lazynessmind.farmingtools.gui;

import com.lazynessmind.farmingtools.container.ContainerPlanter;
import com.lazynessmind.farmingtools.init.tileentities.TileEntityPlanter;
import com.lazynessmind.farmingtools.util.UpgradeUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiPlanter extends GuiBase {

    private TileEntityPlanter planter;

    public GuiPlanter(InventoryPlayer inventoryPlayer, TileEntityPlanter tileEntityPlanter) {
        super(new ContainerPlanter(inventoryPlayer, tileEntityPlanter), inventoryPlayer, tileEntityPlanter, "farmingtools:planter");
        this.planter = tileEntityPlanter;
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String titleGui = "Planter";
        fontRenderer.drawString(titleGui, (this.xSize / 2 - fontRenderer.getStringWidth(titleGui) / 2) + 3, 8, 4210752);
        fontRenderer.drawString("Type: " + UpgradeUtil.getNameFromType(planter.getType()), (this.xSize / 2 - fontRenderer.getStringWidth(titleGui) / 2) + 3, 20, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 10.0f, 1.0f);
        mc.getTextureManager().bindTexture(GuiTextures.HARVESTER_TEXTURE);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
