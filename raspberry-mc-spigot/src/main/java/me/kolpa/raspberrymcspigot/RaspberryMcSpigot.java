package me.kolpa.raspberrymcspigot;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class RaspberryMcSpigot extends JavaPlugin
{

	@Override
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(new SignListener(),this);
	}

	@Override
	public void onDisable()
	{
		// Plugin shutdown logic
	}
}
