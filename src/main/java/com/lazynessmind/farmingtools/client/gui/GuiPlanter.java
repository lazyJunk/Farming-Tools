package com.lazynessmind.farmingtools.client.gui;

import com.lazynessmind.farmingtools.client.gui.button.ProgressBar;
import com.lazynessmind.farmingtools.client.gui.container.ContainerPedestal;
import com.lazynessmind.farmingtools.client.gui.container.slots.SlotPlanter;
import com.lazynessmind.farmingtools.block.tileentities.TileEntityPlanter;
import com.lazynessmind.farmingtools.network.FTNetworkHandler;
import com.lazynessmind.farmingtools.network.packet.MessageGetEnergy;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiPlanter extends GuiBase {

    private TileEntityPlanter planter;
    private ProgressBar energy;
    public static int currentEnergy = 0;

    public GuiPlanter(InventoryPlayer inventoryPlayer, TileEntityPlanter tileEntityPlanter) {
        super(new ContainerPedestal(inventoryPlayer, tileEntityPlanter, new SlotPlanter(tileEntityPlanter, 0, 80, 33)), inventoryPlayer, tileEntityPlanter, "farmingtools:planter");
        this.planter = tileEntityPlanter;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.energy = new ProgressBar(this.guiLeft + 37, this.guiTop + 75, planter.getEnergy().getEnergyStored(), planter.getEnergy().getMaxEnergyStored(), ProgressBar.Style.ENERGY);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        this.energy.draw(currentEnergy);
        FTNetworkHandler.sendPacketToServer(new MessageGetEnergy(x, y, z, "com.lazynessmind.farmingtools.client.gui.GuiPlanter", "currentEnergy"));

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
