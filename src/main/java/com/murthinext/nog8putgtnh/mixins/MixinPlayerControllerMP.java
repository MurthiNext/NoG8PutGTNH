package com.murthinext.nog8putgtnh.mixins;

import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.murthinext.nog8putgtnh.OffhandPlacement;

@Mixin(PlayerControllerMP.class)
public abstract class MixinPlayerControllerMP {

    @Inject(method = "onPlayerRightClick", at = @At("HEAD"), cancellable = true)
    private void nog8put$blockOffhandPlacement(EntityPlayer player, World world, ItemStack stack, int x, int y, int z,
        int side, Vec3 hitVec, CallbackInfoReturnable<Boolean> cir) {
        if (OffhandPlacement.shouldBlockPlacement(player, stack)) {
            cir.setReturnValue(false);
        }
    }
}
