package me.kolpa.raspberrymcspigot.listener;

import me.kolpa.raspberrymcspigot.core.structure.BlockPosition;
import me.kolpa.raspberrymcspigot.core.usecases.RemoveBlockInteractor;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class DestroyListener implements Listener
{
	private final RemoveBlockInteractor removeBlockInteractor;

	public DestroyListener(RemoveBlockInteractor removeBlockInteractor)
	{
		this.removeBlockInteractor = removeBlockInteractor;
	}

	@EventHandler
	private void onBlockBreak(BlockBreakEvent event)
	{
		if (event.getBlock().getType() != Material.REDSTONE_LAMP && event.getBlock()
				.getType() != Material.GOLD_BLOCK && event.getBlock()
				.getType() != Material.REDSTONE_WIRE && !Tag.SIGNS.isTagged(event.getBlock().getType()))
			return;

		Block block = event.getBlock();
		removeBlockInteractor.execute(new BlockPosition(block.getX(), block.getY(), block.getZ()));
	}
}
