package com.ogga029.afkraidfarm.client;

import com.ogga029.afkraidfarm.mixins.BossBarHudAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.util.Hand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;

public class AfkraidfarmClient implements ClientModInitializer {

    private static final Logger log = LoggerFactory.getLogger(AfkraidfarmClient.class);
    public static boolean active = true;
    private boolean should_drink = false;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null && client.world != null && active) {
//                log.info("checkAnDrinkBadOmen()");
                checkAndDrinkBadOmen();
            }
        });
    }

    private void checkAndDrinkBadOmen(){
        MinecraftClient client = MinecraftClient.getInstance();
        BossBarHud hud = MinecraftClient.getInstance().inGameHud.getBossBarHud();
        Map<UUID, ClientBossBar> bossBars = ((BossBarHudAccessor) (Object) hud).getBossBars();



        if (bossBars.isEmpty()) {
            if (should_drink){
                var bottle_found = false;
                log.info("Drinking");
                for (int slot = 0; slot < 9; slot++) {
                    var stack = client.player.getInventory().getStack(slot);
                    if (stack.getItem().toString().toLowerCase().contains("ominous_bottle")) {
                        bottle_found = true;
                        client.player.getInventory().selectedSlot = slot;
                        client.options.useKey.setPressed(true);
                        new Thread(() -> {
                            try {
                                Thread.sleep(1650);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            client.execute(() -> client.options.useKey.setPressed(false));
                        }).start();

                        break;
                    }
                }
                if (!bottle_found){

                }
                should_drink = false;
            }
        }
        else
            should_drink = true;

    }
}
