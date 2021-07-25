package com.inspw.mcmod.zombielootingpersistence;

import com.inspw.mcmod.zombielootingpersistence.config.ConfigManager;
import net.fabricmc.api.ModInitializer;

public class ZombieLootingPersistence implements ModInitializer {
    @Override
    public void onInitialize() {
        ConfigManager.getInstance().init();
    }
}
