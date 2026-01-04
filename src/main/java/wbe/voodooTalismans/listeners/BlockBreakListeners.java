package wbe.voodooTalismans.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import wbe.voodooTalismans.effects.furnaceSmeltEffect;

import java.util.Arrays;
import java.util.List;

public class BlockBreakListeners implements Listener {

    private List validBlocks = Arrays.asList(Material.FURNACE, Material.BLAST_FURNACE, Material.SMOKER);

    @EventHandler(priority = EventPriority.NORMAL)
    public void removeFurnaceFromData(BlockBreakEvent event) {
        if(event.isCancelled()) {
            return;
        }

        Block brokenBlock = event.getBlock();
        if(!validBlocks.contains(brokenBlock.getType())) {
            return;
        }

        furnaceSmeltEffect.playerFurnaces.remove(brokenBlock.getLocation());
    }
}
