package com.lazynessmind.farmingtools.gui;

import com.lazynessmind.farmingtools.gui.button.TwoStateButton;
import com.lazynessmind.farmingtools.block.tileentities.base.TileEntityPedestal;
import com.lazynessmind.farmingtools.interfaces.IRange;
import com.lazynessmind.farmingtools.interfaces.IRedPower;
import com.lazynessmind.farmingtools.network.FTNetworkHandler;
import com.lazynessmind.farmingtools.network.packet.MessageRedstonePower;
import com.lazynessmind.farmingtools.network.packet.MessageShowArea;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.client.config.HoverChecker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiBase extends GuiContainer {

    private TileEntityPedestal tileEntity;
    public int x;
    public int y;
    public int z;
    private int guiRight, guiBottom;
    private String tileEntityId;
    private HoverChecker areaChecker, redstoneChecker;
    private TwoStateButton showEffectAreaButton, activeRedstone;

    public GuiBase(Container container, InventoryPlayer inventoryPlayer, TileEntityPedestal tileEntity, String tileEntityId) {
        super(container);
        this.tileEntity = tileEntity;
        this.x = tileEntity.getPos().getX();
        this.y = tileEntity.getPos().getY();
        this.z = tileEntity.getPos().getZ();
        this.tileEntityId = tileEntityId;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.xSize = 196;
        this.guiRight = (this.width/2) + (this.xSize/2);
        this.guiBottom = (this.height/2) + (this.ySize/2);

        if (tileEntity instanceof IRange) {
            showEffectAreaButton = addButton(new TwoStateButton(901, this.guiLeft + 5, this.guiTop + 5, GuiTextures.Icon.BB));
            showEffectAreaButton.isActive = ((IRange) tileEntity).canShowRangeArea();
        }
        if (tileEntity instanceof IRedPower) {
            activeRedstone = addButton(new TwoStateButton(902, this.guiLeft + 5, this.guiTop + 26, GuiTextures.Icon.REDSTONE));
            activeRedstone.isActive = ((IRedPower) tileEntity).needRedstonePower();
        }


        areaChecker = new HoverChecker(showEffectAreaButton, 200);
        redstoneChecker = new HoverChecker(activeRedstone, 200);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        if (areaChecker.checkHover(mouseX, mouseY)) {
            List<String> strings = new ArrayList<>();
            strings.add("Show/Don't show range area!");
            GuiUtils.drawHoveringText(strings, mouseX, mouseY, width, height, 200, fontRenderer);
        }

        if (redstoneChecker.checkHover(mouseX, mouseY)) {
            List<String> strings = new ArrayList<>();
            strings.add("On/Off Redstone signal.");
            strings.add("If On just works with redstone.");
            GuiUtils.drawHoveringText(strings, mouseX, mouseY, width, height, 200, fontRenderer);
        }
        
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if (button instanceof TwoStateButton) {
            TwoStateButton btn = (TwoStateButton) button;
            if (btn.id == 901) {
                if (btn.isActive) {
                    MessageShowArea packet = new MessageShowArea(tileEntityId, false, x, y, z);
                    FTNetworkHandler.sendPacketToServer(packet);
                    showEffectAreaButton.isActive = packet.state;
                } else {
                    MessageShowArea packet = new MessageShowArea(tileEntityId, true, x, y, z);
                    FTNetworkHandler.sendPacketToServer(packet);
                    showEffectAreaButton.isActive = packet.state;
                }
            } else if (btn.id == 902) {
                if (btn.isActive) {
                    MessageRedstonePower packet = new MessageRedstonePower(tileEntityId, false, x, y, z);
                    FTNetworkHandler.sendPacketToServer(packet);
                    activeRedstone.isActive = packet.state;
                } else {
                    MessageRedstonePower packet = new MessageRedstonePower(tileEntityId, true, x, y, z);
                    FTNetworkHandler.sendPacketToServer(packet);
                    activeRedstone.isActive = packet.state;
                }
            }
        }
    }
}
