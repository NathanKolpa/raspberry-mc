package me.kolpa.raspberrymcspigot.listener;

import me.kolpa.raspberrymcspigot.core.structure.sign.SignData;
import me.kolpa.raspberrymcspigot.core.usecases.AddInputPinStructureInteractor;
import me.kolpa.raspberrymcspigot.core.usecases.AddOutputPinStructureInteractor;
import me.kolpa.raspberrymcspigot.impl.structure.SpigotInputPinStructure;
import me.kolpa.raspberrymcspigot.impl.structure.SpigotOutputPinStructure;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SignListener implements Listener
{
	private final AddOutputPinStructureInteractor addOutputPinStructureInteractor;
	private final AddInputPinStructureInteractor addInputPinStructureInteractor;
	private final JavaPlugin plugin;

	public SignListener(JavaPlugin plugin, AddOutputPinStructureInteractor addOutputPinStructureInteractor, AddInputPinStructureInteractor addInputPinStructureInteractor)
	{
		this.addOutputPinStructureInteractor = addOutputPinStructureInteractor;
		this.plugin = plugin;
		this.addInputPinStructureInteractor = addInputPinStructureInteractor;
	}


	@EventHandler
	private void onSignChange(SignChangeEvent e)
	{
		Block attachedBlock = getAttachedBlock(e);

		if (attachedBlock == null)
			return;

		if (attachedBlock.getType() != Material.REDSTONE_LAMP && attachedBlock.getType() != Material.GOLD_BLOCK)
			return;

		SignData data = SignData.parse(e.getLines());

		if(data == null)
			return;

		if(data.getHeader().equalsIgnoreCase("output"))
		{
			if(attachedBlock.getType() != Material.REDSTONE_LAMP)
				return;
			
			e.setCancelled(true);
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> 
			{
				addOutputPinStructureInteractor.execute(pin -> new SpigotOutputPinStructure(pin, e.getPlayer().getWorld().getUID().toString(), attachedBlock, e.getBlock()), data);
			});
		}
		else if(data.getHeader().equalsIgnoreCase("input"))
		{
			Block redstone = attachedBlock.getRelative(BlockFace.UP);
			
			if(redstone == null || (redstone.getType() != Material.REDSTONE_WIRE || attachedBlock.getType() != Material.GOLD_BLOCK))
				return;
			
			e.setCancelled(true);
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () ->
			{
				addInputPinStructureInteractor.execute(pin -> new SpigotInputPinStructure(pin, e.getPlayer().getWorld().getUID().toString(), attachedBlock, e.getBlock(), redstone), data);
			});
		}

	}

	private Block getAttachedBlock(SignChangeEvent e)
	{
		Block sign = e.getBlock();
		BlockData blockData = sign.getBlockData();

		if (!(blockData instanceof Directional))
			return null;

		BlockFace dir = ((Directional) blockData).getFacing();
		return sign.getRelative(dir.getOppositeFace());
	}
}
