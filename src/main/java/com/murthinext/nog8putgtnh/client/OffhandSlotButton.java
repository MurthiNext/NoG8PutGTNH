package com.murthinext.nog8putgtnh.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.murthinext.nog8putgtnh.OffhandPlacement;

import xonin.backhand.api.core.BackhandSlot;

public final class OffhandSlotButton {

    public static final int SIZE = 3;

    private static final int COLOR_ALLOWED = 0xFF00FF00;
    private static final int COLOR_FORBIDDEN = 0xFFFF0000;

    private OffhandSlotButton() {}

    public static void draw(Container container, int guiLeft, int guiTop) {
        int[] pos = getPosition(container, guiLeft, guiTop);
        if (pos == null) {
            return;
        }
        boolean lighting = GL11.glIsEnabled(GL11.GL_LIGHTING);
        boolean depth = GL11.glIsEnabled(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        Gui.drawRect(pos[0], pos[1], pos[0] + SIZE, pos[1] + SIZE, getColor());
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        if (depth) {
            GL11.glEnable(GL11.GL_DEPTH_TEST);
        }
        if (lighting) {
            GL11.glEnable(GL11.GL_LIGHTING);
        }
    }

    /** @return 是否消耗了本次点击 */
    public static boolean handleClick(Container container, int guiLeft, int guiTop, int mouseX, int mouseY,
        int mouseButton) {
        if (mouseButton != 0) {
            return false;
        }
        int[] pos = getPosition(container, guiLeft, guiTop);
        if (pos == null) {
            return false;
        }
        if (mouseX < pos[0] || mouseX >= pos[0] + SIZE || mouseY < pos[1] || mouseY >= pos[1] + SIZE) {
            return false;
        }
        OffhandPlacement.toggle();
        Minecraft.getMinecraft()
            .getSoundHandler()
            .playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
        return true;
    }

    private static int getColor() {
        return OffhandPlacement.isAllowed() ? COLOR_ALLOWED : COLOR_FORBIDDEN;
    }

    /** @return 按钮左上角的 GUI 坐标，找不到副手槽时返回 null */
    private static int[] getPosition(Container container, int guiLeft, int guiTop) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (player == null) {
            return null;
        }
        for (Object slotObject : container.inventorySlots) {
            if (slotObject instanceof BackhandSlot slot && slot.inventory == player.inventory) {
                return new int[] { guiLeft + slot.xDisplayPosition + 17, guiTop + slot.yDisplayPosition - 1 };
            }
        }
        return null;
    }
}
