package me.kolpa.raspberrymcspigot;

import me.kolpa.raspberrymcspigot.core.usecases.AddOutputPinStructureInteractor;
import me.kolpa.raspberrymcspigot.core.usecases.RedstoneUpdateInteractor;
import me.kolpa.raspberrymcspigot.core.usecases.RemoveBlockInteractor;
import me.kolpa.raspberrymcspigot.impl.raspberry.RemoteRaspberry;
import me.kolpa.raspberrymcspigot.impl.repository.file.FileUnitOfWorkFactory;
import me.kolpa.raspberrymcspigot.impl.repository.file.json.JsonSerializer;
import me.kolpa.raspberrymcspigot.listener.DestroyListener;
import me.kolpa.raspberrymcspigot.listener.SignListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class RaspberryMcSpigot extends JavaPlugin
{
	RemoteRaspberry raspberry = new RemoteRaspberry();

	@Override
	public void onEnable()
	{
		FileUnitOfWorkFactory fileUnitOfWorkFactory = FileUnitOfWorkFactory.readFile(new JsonSerializer());

		try
		{
			raspberry.fetchState();
//			raspberry.connect();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return;
		}

		//TODO: dingen toevoegen is crack
		AddOutputPinStructureInteractor addOutputPinStructureInteractor = new AddOutputPinStructureInteractor(
				fileUnitOfWorkFactory);
		RemoveBlockInteractor removeBlockInteractor = new RemoveBlockInteractor(fileUnitOfWorkFactory);
		RedstoneUpdateInteractor redstoneUpdateInteractor = new RedstoneUpdateInteractor(fileUnitOfWorkFactory,
				raspberry);

		getServer().getPluginManager().registerEvents(new SignListener(this, addOutputPinStructureInteractor), this);
		getServer().getPluginManager().registerEvents(new DestroyListener(removeBlockInteractor), this);

		//TODO: test performance impact 
		Bukkit.getScheduler().runTaskTimer(this, redstoneUpdateInteractor::updateAll, 0, 1);
	}

	@Override
	public void onDisable()
	{
		raspberry.shutdown();
	}
}
