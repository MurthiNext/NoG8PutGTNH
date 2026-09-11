package com.murthinext.nog8putgtnh.mixins;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.inventory.Container;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.murthinext.nog8putgtnh.client.OffhandSlotButton;

@Mixin(GuiContainer.class)
public abstract class MixinGuiContainer {

    @Shadow
    public Container inventorySlots;

    @Shadow
    protected int guiLeft;

    @Shadow
    protected int guiTop;

    @Inject(method = "drawScreen", at = @At("TAIL"))
    private void nog8put$drawOffhandPlacementButton(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        if ((Object) this instanceof GuiInventory) {
            OffhandSlotButton.draw(this.inventorySlots, this.guiLeft, this.guiTop);
        }
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void nog8put$clickOffhandPlacementButton(int mouseX, int mouseY, int mouseButton, CallbackInfo ci) {
        if ((Object) this instanceof GuiInventory && OffhandSlotButton
            .handleClick(this.inventorySlots, this.guiLeft, this.guiTop, mouseX, mouseY, mouseButton)) {
            ci.cancel();
        }
    }
}
