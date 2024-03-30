package net.thenextlvl.protect.listener;

import lombok.RequiredArgsConstructor;
import net.thenextlvl.protect.ProtectPlugin;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEvent;

@RequiredArgsConstructor
public class WorldListener implements Listener {
    private final ProtectPlugin plugin;

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        event.setCancelled(!plugin.protectionService().canBreak(
                event.getPlayer(), event.getBlock().getLocation()
        ));
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        event.setCancelled(!plugin.protectionService().canBuild(
                event.getPlayer(), event.getBlock().getLocation()
        ));
    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        var block = event.getClickedBlock();
        if (block == null) return;
        if (block.getType().equals(Material.FARMLAND) && event.getAction().equals(Action.PHYSICAL))
            event.setCancelled(!plugin.protectionService().canTrample(
                    event.getPlayer(), block.getLocation()
            ));
        else if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        else if (event.getPlayer().isSneaking() && event.hasItem() && event.getMaterial().isBlock()) return;
        else if (!block.getType().isInteractable()) return;
        event.setCancelled(!plugin.protectionService().canInteract(
                event.getPlayer(), block.getLocation()
        ));
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onWorldEvent(BlockPhysicsEvent event) {
        var area = plugin.areaProvider().getArea(event.getBlock());
        event.setCancelled(!area.getFlag(plugin.flags.physics));
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockFromTo(BlockFromToEvent event) {
        var area = plugin.areaProvider().getArea(event.getToBlock());
        event.setCancelled(!area.getFlag(plugin.flags.physics));
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockGrow(BlockGrowEvent event) {
        var area = plugin.areaProvider().getArea(event.getBlock());
        event.setCancelled(!area.getFlag(plugin.flags.blockGrowth));
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onWorldEvent(BlockExplodeEvent event) {
        var area = plugin.areaProvider().getArea(event.getBlock());
        event.setCancelled(!area.getFlag(plugin.flags.explosions));
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onWorldEvent(EntityExplodeEvent event) {
        var area = plugin.areaProvider().getArea(event.getLocation());
        event.blockList().removeIf(block -> !area.getFlag(plugin.flags.explosions));
        event.setCancelled(!area.getFlag(plugin.flags.explosions));
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onWorldEvent(BlockRedstoneEvent event) {
        var area = plugin.areaProvider().getArea(event.getBlock());
        if (!area.getFlag(plugin.flags.redstone)) event.setNewCurrent(0);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onWorldEvent(PlayerBucketFillEvent event) {
        event.setCancelled(!plugin.protectionService().canBreak(
                event.getPlayer(), event.getBlock().getLocation()
        ));
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onWorldEvent(PlayerBucketEmptyEvent event) {
        event.setCancelled(!plugin.protectionService().canBuild(
                event.getPlayer(), event.getBlock().getLocation()
        ));
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onWorldEvent(BlockSpreadEvent event) {
        var area = plugin.areaProvider().getArea(event.getBlock());
        event.setCancelled(!area.getFlag(plugin.flags.blockSpread));
    }
}
