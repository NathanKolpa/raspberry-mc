package me.kolpa.raspberrymcspigot.listener;

import me.kolpa.raspberrymcspigot.core.structure.sign.SignData;
import me.kolpa.raspberrymcspigot.core.usecases.AddOutputPinStructureInteractor;
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
	private AddOutputPinStructureInteractor addOutputPinStructureInteractor;
	private final JavaPlugin plugin;

	public SignListener(JavaPlugin plugin, AddOutputPinStructureInteractor addOutputPinStructureInteractor)
	{
		this.addOutputPinStructureInteractor = addOutputPinStructureInteractor;
		this.plugin = plugin;
	}


	@EventHandler
	private void onSignChange(SignChangeEvent e)
	{
		Block attachedBlock = getAttachedBlock(e);

		if (attachedBlock == null)
			return;

		if (attachedBlock.getType() != Material.REDSTONE_LAMP)
			return;

		SignData data = SignData.parse(e.getLines());

		if(data == null)
			return;

		if(data.getHeader().equalsIgnoreCase("output"))
		{
			e.setCancelled(true);
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> 
			{
				addOutputPinStructureInteractor.execute(pin -> new SpigotOutputPinStructure(pin, e.getPlayer().getWorld().getUID().toString(), attachedBlock, e.getBlock()), data);
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
