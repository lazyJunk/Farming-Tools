package com.lazynessmind.farmingtools.block.tileentities.specialrenderer;

import com.lazynessmind.farmingtools.block.tileentities.base.TileEntityPedestal;
import com.lazynessmind.farmingtools.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

public class PedestalSpecialRenderer<T extends TileEntityPedestal> extends TileEntitySpecialRenderer<T> {

    private EntityItem entityItem = new EntityItem(Minecraft.getMinecraft().world, 0, 0, 0, ItemStack.EMPTY);
    private float yaw = 0f;

    public void renderItemOnTop(T te, double x, double y, double z) {
        if (yaw < 360f) {
            yaw += 1f;
        } else if (yaw >= 360f) {
            yaw = 0f;
        }

        entityItem.setItem(te.getStackInSlot(0));

        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(x, y, z);
            GlStateManager.scale(0.5f, 0.5f, 0.5f);
            GlStateManager.translate(1f, 1.5f, 1f);
            GlStateManager.rotate(yaw, 0f, 0.1f, 0f);
            Minecraft.getMinecraft().getRenderManager().renderEntity(entityItem, 0, 0, 0, yaw, 0f, false);
        }
        GlStateManager.popMatrix();
    }

    public void renderRangeArea(T te, double x, double y, double z) {
        if (te.canShowRangeArea()) {
            this.setLightmapDisabled(true);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            RenderUtils.renderBox(bufferbuilder, (x - 1), y, (z - 1), (x + 1), (y + 1), (z + 1), 255, 255, 255, 2f, tessellator);
            this.setLightmapDisabled(false);
        }
    }

    public void renderRangeArea(T te, double x, double y, double z, int r, int g, int b) {
        if (te.canShowRangeArea()) {
            this.setLightmapDisabled(true);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            RenderUtils.renderBox(bufferbuilder, (x - 1), y, (z - 1), (x + 2), (y + 1), (z + 2), r, g, b, 2f, tessellator);
            this.setLightmapDisabled(false);
        }
    }
}