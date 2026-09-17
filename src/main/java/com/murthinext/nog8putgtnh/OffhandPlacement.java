package com.murthinext.nog8putgtnh;

import java.io.File;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemRedstone;
import net.minecraft.item.ItemReed;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

import xonin.backhand.api.core.BackhandUtils;

/** 副手放置方块的全局开关与配置持久化。 */
public final class OffhandPlacement {

    private static final String KEY = "allowOffhandBlockPlacement";

    private static boolean allowOffhandPlacement = true;
    private static Configuration configuration;

    private OffhandPlacement() {}

    public static void load(File configFile) {
        configuration = new Configuration(configFile);
        configuration.load();
        allowOffhandPlacement = configuration
            .getBoolean(KEY, Configuration.CATEGORY_GENERAL, false, "是否允许副手手持方块时将其放置到世界中。");
        if (configuration.hasChanged()) {
            configuration.save();
        }
    }

    public static boolean isAllowed() {
        return allowOffhandPlacement;
    }

    public static void toggle() {
        allowOffhandPlacement = !allowOffhandPlacement;
        if (configuration != null) {
            configuration.get(Configuration.CATEGORY_GENERAL, KEY, allowOffhandPlacement)
                .set(allowOffhandPlacement);
            configuration.save();
        }
    }

    /**
     * 判断本次右键是否应当被拦截。仅在“禁止放置 + 物品属于方块/放置类物品 + 当前由副手持有”时拦截。
     * 取出、交换、攻击、破坏方块、使用工具/食物等操作都不会经过该判断的拦截分支。
     */
    public static boolean shouldBlockPlacement(EntityPlayer player, ItemStack stack) {
        return !allowOffhandPlacement && isBlockPlacement(stack) && BackhandUtils.isUsingOffhand(player);
    }

    /** 方块及原版中“使用后会在世界生成方块”的特殊物品。 */
    public static boolean isBlockPlacement(ItemStack stack) {
        if (stack == null) {
            return false;
        }
        Item item = stack.getItem();
        return item instanceof ItemBlock || item instanceof ItemDoor
            || item instanceof ItemSeeds
            || item instanceof ItemReed
            || item instanceof ItemRedstone
            || item instanceof ItemSkull;
    }
}
