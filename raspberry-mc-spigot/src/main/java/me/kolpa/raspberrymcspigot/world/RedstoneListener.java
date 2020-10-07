package me.kolpa.raspberrymcspigot.world;

import me.kolpa.raspberrymcspigot.RaspberryController;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

import java.util.List;

public class RedstoneListener implements Listener
{
	private final RaspberryController controller;

	public RedstoneListener(RaspberryController controller)
	{
		this.controller = controller;
	}

	@EventHandler
	public void onBlockRedstone(BlockRedstoneEvent event)
	{
		if(event.getBlock().getType() != Material.REDSTONE_LAMP)
			return;

		List<SavedBlock> blocks = controller.getBlocks();
		
		for(SavedBlock block : blocks)
		{
			if(!block.isSame(event.getBlock()))
				continue;
			
			controller.updateBlock(block);
			
			break;
		}
	}
	
	
}
