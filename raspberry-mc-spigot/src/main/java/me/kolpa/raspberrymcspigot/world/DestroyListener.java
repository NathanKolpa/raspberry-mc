package me.kolpa.raspberrymcspigot.world;

import me.kolpa.raspberrymcspigot.RaspberryController;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;

public class DestroyListener implements Listener
{
	private final RaspberryController controller;

	public DestroyListener(RaspberryController controller)
	{
		this.controller = controller;
	}

	@EventHandler
	private void onBlockBreak(BlockBreakEvent event)
	{
		if(event.getBlock().getType() != Material.REDSTONE_LAMP && !Tag.SIGNS.isTagged(event.getBlock().getType()))
			return;

		List<SavedBlock> blocks = controller.getBlocks();
		SavedBlock blockToRemove = null;
		
		for(SavedBlock block : blocks)
		{
			if(block.isSame(event.getBlock()))
			{
				blockToRemove = block;
				break;
			}
			else if(block.isSignSame(event.getBlock()))
			{
				blockToRemove = block;
				break;
			}
		}

		if(blockToRemove != null)
		controller.removeBlock(blockToRemove);

	}
}
