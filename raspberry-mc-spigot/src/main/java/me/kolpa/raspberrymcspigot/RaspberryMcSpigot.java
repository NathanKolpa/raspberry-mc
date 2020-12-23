package me.kolpa.raspberrymcspigot;

import me.kolpa.raspberrymcspigot.core.usecases.*;
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
			raspberry.connect();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return;
		}

		//TODO: dingen toevoegen is crack
		AddOutputPinStructureInteractor addOutputPinStructureInteractor = new AddOutputPinStructureInteractor(
				fileUnitOfWorkFactory,
				raspberry);

		AddInputPinStructureInteractor addInputPinStructureInteractor = new AddInputPinStructureInteractor(
				fileUnitOfWorkFactory,
				raspberry);

		RemoveBlockInteractor removeBlockInteractor = new RemoveBlockInteractor(fileUnitOfWorkFactory);
		RedstoneUpdateInteractor redstoneUpdateInteractor = new RedstoneUpdateInteractor(fileUnitOfWorkFactory,
				raspberry);
		InputUpdateInteractor inputUpdateInteractor = new InputUpdateInteractor(fileUnitOfWorkFactory);

		getServer().getPluginManager()
				.registerEvents(
						new SignListener(this, addOutputPinStructureInteractor, addInputPinStructureInteractor),
						this);
		getServer().getPluginManager().registerEvents(new DestroyListener(removeBlockInteractor), this);

		//TODO: test performance impact 
		Bukkit.getScheduler().runTaskTimer(this, redstoneUpdateInteractor::updateAll, 0, 1);

		raspberry.setUpdateHandler(inputPin ->
		{
			Bukkit.getScheduler().scheduleSyncDelayedTask(this, () ->
			{
				inputUpdateInteractor.execute(inputPin);
			});
		});
	}

	@Override
	public void onDisable()
	{
		raspberry.shutdown();
	}
}
