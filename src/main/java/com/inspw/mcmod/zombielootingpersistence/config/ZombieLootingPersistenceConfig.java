package com.inspw.mcmod.zombielootingpersistence.config;

import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ZombieLootingPersistenceConfig {
    public static final ZombieLootingPersistenceConfig DEFAULT = new ZombieLootingPersistenceConfig(
            Registry.ITEM.stream()
                    .filter(Item::isDamageable)
                    .map(item -> Registry.ITEM.getId(item).toString())
                    .sorted()
                    .collect(Collectors.toCollection(LinkedHashSet::new))
    );

    private Set<String> excludedItemIds = new LinkedHashSet<>();

    public ZombieLootingPersistenceConfig() {
    }

    public ZombieLootingPersistenceConfig(Set<String> excludedItemIds) {
        this.excludedItemIds = excludedItemIds;
    }

    public Set<String> getExcludedItemIds() {
        return excludedItemIds;
    }

    public void setExcludedItemIds(Set<String> excludedItemIds) {
        this.excludedItemIds = excludedItemIds;
    }
}
