package com.inspw.mcmod.zombielootingpersistence.mixin;

import com.inspw.mcmod.zombielootingpersistence.config.ConfigManager;
import com.inspw.mcmod.zombielootingpersistence.config.ZombieLootingPersistenceConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ZombieEntity.class)
public class ZombieEntityMixin extends HostileEntity {
    protected ZombieEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void equipLootStack(EquipmentSlot slot, ItemStack stack) {
        equipStack(slot, stack);
        updateDropChances(slot);

        // Give persistence if the item is in the excluded list.
        Identifier itemId = Registry.ITEM.getId(stack.getItem());
        ZombieLootingPersistenceConfig config = ConfigManager.getInstance().getConfig();
        if (config != null && config.getExcludedItemIds().contains(itemId.toString())) {
            setPersistent();
        }
    }

    @Override
    public void remove(RemovalReason reason) {
        // On discard/despawn, we drop the held/worn item if it has been obtained from the ground.
        // Should not affect items that are given at spawn.
        if (reason == RemovalReason.DISCARDED) {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                boolean lootedItem = getDropChance(slot) > 1.0F;
                if (lootedItem) {
                    dropStack(getEquippedStack(slot));
                    equipStack(slot, ItemStack.EMPTY);
                }
            }
        }

        super.remove(reason);
    }
}
