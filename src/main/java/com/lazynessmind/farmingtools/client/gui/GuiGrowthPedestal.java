package com.lazynessmind.farmingtools.client.gui;

import com.lazynessmind.farmingtools.client.gui.button.ProgressBar;
import com.lazynessmind.farmingtools.client.gui.container.ContainerPedestal;
import com.lazynessmind.farmingtools.client.gui.container.slots.SlotGrowthPedestal;
import com.lazynessmind.farmingtools.block.tileentities.TileEntityGrowthPedestal;
import com.lazynessmind.farmingtools.network.FTNetworkHandler;
import com.lazynessmind.farmingtools.network.packet.MessageGetEnergy;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiGrowthPedestal extends GuiBase {

    private TileEntityGrowthPedestal growthPedestal;
    private ProgressBar energy;
    public static int currentEnergy = 0;

    public GuiGrowthPedestal(InventoryPlayer inventoryPlayer, TileEntityGrowthPedestal tileEntityGrowthPedestal) {
        super(new ContainerPedestal(inventoryPlayer, tileEntityGrowthPedestal, new SlotGrowthPedestal(tileEntityGrowthPedestal, 0, 80, 33)), inventoryPlayer, tileEntityGrowthPedestal, "farmingtools:growth_pedestal");
        this.growthPedestal = tileEntityGrowthPedestal;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.energy = new ProgressBar(this.guiLeft + 37, this.guiTop + 75, growthPedestal.getEnergy().getEnergyStored(), growthPedestal.getEnergy().getMaxEnergyStored(), ProgressBar.Style.ENERGY);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.energy.draw(currentEnergy);
        FTNetworkHandler.sendPacketToServer(new MessageGetEnergy(x, y, z, "com.lazynessmind.farmingtools.client.gui.GuiGrowthPedestal", "currentEnergy"));
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
