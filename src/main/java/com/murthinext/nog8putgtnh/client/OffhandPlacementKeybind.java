package com.murthinext.nog8putgtnh.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;

import org.lwjgl.input.Keyboard;

import com.murthinext.nog8putgtnh.OffhandPlacement;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;

/** 快捷键切换副手放置拦截状态，默认右 Alt。 */
public final class OffhandPlacementKeybind {

    private static final String LANG_ON = "nog8putgtnh.offhand_placement.on";
    private static final String LANG_OFF = "nog8putgtnh.offhand_placement.off";

    private static final KeyBinding TOGGLE_KEY = new KeyBinding(
        "key.nog8putgtnh.toggle_offhand_placement",
        Keyboard.KEY_RMENU,
        "key.categories.nog8putgtnh");

    private OffhandPlacementKeybind() {}

    public static void register() {
        ClientRegistry.registerKeyBinding(TOGGLE_KEY);
        FMLCommonHandler.instance()
            .bus()
            .register(new Handler());
    }

    /* 复用按钮的切换逻辑。 */
    private static void toggle() {
        OffhandPlacement.toggle();
        boolean allowed = OffhandPlacement.isAllowed();
        EnumChatFormatting color = allowed ? EnumChatFormatting.GREEN : EnumChatFormatting.RED;
        String text = I18n.format(allowed ? LANG_OFF : LANG_ON);
        Minecraft.getMinecraft().ingameGUI.func_110326_a(color + text, true);
    }

    public static final class Handler {

        @SubscribeEvent
        public void onKeyInput(InputEvent.KeyInputEvent event) {
            if (Minecraft.getMinecraft().thePlayer == null) {
                return;
            }
            while (TOGGLE_KEY.isPressed()) {
                toggle();
            }
        }
    }
}
