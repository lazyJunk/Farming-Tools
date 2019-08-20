package com.lazynessmind.farmingtools.client.specialrenderer;

import com.lazynessmind.farmingtools.init.tileentities.TileEntityPlanter;
import com.lazynessmind.farmingtools.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

public class PlanterSpecialRenderer extends TileEntitySpecialRenderer<TileEntityPlanter> {

    private EntityItem entityItem = new EntityItem(Minecraft.getMinecraft().world, 0, 0, 0, ItemStack.EMPTY);

    private float yaw = 0f;


    @Override
    public void render(TileEntityPlanter te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if (yaw < 360f) {
            yaw += 1f;
        } else if (yaw >= 360f) {
            yaw = 0f;
        }

        entityItem.setItem(te.getHandler().getStackInSlot(0));

        if(te.canShowRangeArea()) {
            this.setLightmapDisabled(true);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            RenderUtils.renderBox(bufferbuilder, (x - te.getRange()), y, (z - te.getRange()), (x + te.getRange() + 1), y+1, (z + te.getRange() + 1), 255, 255, 255,2f, tessellator);
            this.setLightmapDisabled(false);
        }

        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(x, y, z);
            GlStateManager.scale(0.5f, 0.5f, 0.5f);
            GlStateManager.translate(1f, 1.1f, 1f);
            GlStateManager.rotate(yaw, 0f, 0.1f, 0f);
            Minecraft.getMinecraft().getRenderManager().renderEntity(entityItem, 0, 0, 0, yaw, 0f, false);
        }
        GlStateManager.popMatrix();
    }
}