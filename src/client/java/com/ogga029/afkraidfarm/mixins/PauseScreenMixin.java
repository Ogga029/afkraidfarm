package com.ogga029.afkraidfarm.mixins;

import com.ogga029.afkraidfarm.client.AfkraidfarmClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public abstract class PauseScreenMixin extends Screen {

    protected PauseScreenMixin() {
        super(Text.empty());
    }

    @Inject(method = "initWidgets", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.textRenderer;

        CheckboxWidget checkbox = CheckboxWidget.builder(
                        Text.literal("Afk Raid Farm Drinking"),
                        textRenderer
                )
                .pos(10, 10)
                .callback((checkbox1, checked) -> {
                    AfkraidfarmClient.active = checked;
                })
                .checked(AfkraidfarmClient.active)
                .build();

        this.addDrawableChild(checkbox);
    }
}
