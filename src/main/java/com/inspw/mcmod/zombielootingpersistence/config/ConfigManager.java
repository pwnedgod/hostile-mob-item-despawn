package com.inspw.mcmod.zombielootingpersistence.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String CONFIG_FILE_PATH = "config/zombie-looting-persistence.json";

    private static ConfigManager instance;

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private ZombieLootingPersistenceConfig config;

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }

        return instance;
    }

    @Nullable
    public ZombieLootingPersistenceConfig getConfig() {
        return config;
    }

    public void init() {
        boolean shouldSave = false;

        try (FileReader fileReader = new FileReader(CONFIG_FILE_PATH)) {
            config = gson.fromJson(fileReader, ZombieLootingPersistenceConfig.class);
            shouldSave = true;
        } catch (FileNotFoundException e) {
            config = ZombieLootingPersistenceConfig.DEFAULT;
            shouldSave = true;
        } catch (JsonParseException | IOException e) {
            LOGGER.warn("Zombie Looting Persistence: Could not load configuration! Using default configuration.", e);
            config = ZombieLootingPersistenceConfig.DEFAULT;
        }

        if (shouldSave) {
            save();
        }
    }

    public void save() {
        try (FileWriter fileWriter = new FileWriter(CONFIG_FILE_PATH)) {
            gson.toJson(config, fileWriter);
        } catch (IOException e) {
            LOGGER.warn("Could not save configuration.", e);
        }
    }
}
