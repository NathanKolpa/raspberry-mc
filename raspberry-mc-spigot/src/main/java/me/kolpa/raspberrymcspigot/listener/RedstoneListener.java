package me.kolpa.raspberrymcspigot.listener;

import me.kolpa.raspberrymcspigot.core.structure.BlockPosition;
import me.kolpa.raspberrymcspigot.core.usecases.RedstoneUpdateInteractor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class RedstoneListener implements Listener
{
	private final JavaPlugin plugin;
	private final RedstoneUpdateInteractor redstoneUpdateInteractor;

	public RedstoneListener(JavaPlugin plugin, RedstoneUpdateInteractor redstoneUpdateInteractor)
	{
		this.plugin = plugin;
		this.redstoneUpdateInteractor = redstoneUpdateInteractor;
	}
	
	
	//TODO: update on redstone signal change
	@EventHandler
	public void onRedstoneUpdate(BlockRedstoneEvent event)
	{
		if (event.getBlock().getType() != Material.REDSTONE_LAMP)
			return;
		
		System.out.println(1);

		Block block = event.getBlock();
		redstoneUpdateInteractor.execute(new BlockPosition(block.getX(), block.getY(), block.getZ()));
	}
}
