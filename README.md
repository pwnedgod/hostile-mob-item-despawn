# Zombie Looting Persistence

Prevents zombie persistence (no despawn) after picking up items, especially after a mob died.
If they do despawn, the items they have picked up will be dropped on the ground.

This mod only prevents a zombie from being persisted at item pickup.
In other words, it does not remove the *no despawn flag* if they already picked up an item before this mod is installed.

## Configuration

Item exceptions can be set on `config/zombie-looting-persistence.json`.
Defaults to a damageable items.

Zombies that loot excluded items will *not despawn*.
