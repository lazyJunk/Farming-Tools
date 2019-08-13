package com.lazynessmind.farmingtools.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import java.awt.*;

public class RenderUtils {

    public static void renderBox(BufferBuilder bufferBuilder, double x, double y, double z, double _x, double _y, double _z, int r, int g, int b, float stroke, Tessellator tessellator) {
        GlStateManager.disableFog();
        GlStateManager.disableLighting();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.glLineWidth(stroke);
        bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(x, y, z).color((float) r, (float) g, (float) b, 0.0F).endVertex();
        bufferBuilder.pos(x, y, z).color(r, g, b, 255).endVertex();
        bufferBuilder.pos(_x, y, z).color(r, g, b, 255).endVertex();
        bufferBuilder.pos(_x, y, _z).color(r, g, b, 255).endVertex();
        bufferBuilder.pos(x, y, _z).color(r, g, b, 255).endVertex();
        bufferBuilder.pos(x, y, z).color(r, g, b, 255).endVertex();
        bufferBuilder.pos(x, _y, z).color(r, g, b, 255).endVertex();
        bufferBuilder.pos(_x, _y, z).color(r, g, b, 255).endVertex();
        bufferBuilder.pos(_x, _y, _z).color(r, g, b, 255).endVertex();
        bufferBuilder.pos(x, _y, _z).color(r, g, b, 255).endVertex();
        bufferBuilder.pos(x, _y, z).color(r, g, b, 255).endVertex();
        bufferBuilder.pos(x, _y, _z).color(r, g, b, 255).endVertex();
        bufferBuilder.pos(x, y, _z).color(r, g, b, 255).endVertex();
        bufferBuilder.pos(_x, y, _z).color(r, g, b, 255).endVertex();
        bufferBuilder.pos(_x, _y, _z).color(r, g, b, 255).endVertex();
        bufferBuilder.pos(_x, _y, z).color(r, g, b, 255).endVertex();
        bufferBuilder.pos(_x, y, z).color(r, g, b, 255).endVertex();
        bufferBuilder.pos(_x, y, z).color((float) r, (float) g, (float) b, 0.0F).endVertex();
        tessellator.draw();
        GlStateManager.glLineWidth(1.0F);
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.enableFog();
    }
}
