package me.kolpa.raspberrymcspigot;

import me.kolpa.raspberrymcspigot.impl.FileGpioBlockRepository;
import me.kolpa.raspberrymcspigot.impl.HttpRaspberry;
import me.kolpa.raspberrymcspigot.world.DestroyListener;
import me.kolpa.raspberrymcspigot.world.RedstoneListener;
import me.kolpa.raspberrymcspigot.world.SignListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class RaspberryMcSpigot extends JavaPlugin
{
	
	private RaspberryController controller = new RaspberryController(new HttpRaspberry(this), new FileGpioBlockRepository());

	@Override
	public void onEnable()
	{
		controller.reload();
		
		getServer().getPluginManager().registerEvents(new SignListener(controller),this);
		getServer().getPluginManager().registerEvents(new RedstoneListener(controller),this);
		getServer().getPluginManager().registerEvents(new DestroyListener(controller),this);
	}

	@Override
	public void onDisable()
	{
		// Plugin shutdown logic
	}
}
