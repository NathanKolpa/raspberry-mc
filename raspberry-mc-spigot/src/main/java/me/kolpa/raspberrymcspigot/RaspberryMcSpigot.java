package me.kolpa.raspberrymcspigot;

import me.kolpa.raspberrymcspigot.core.usecases.AddOutputPinStructureInteractor;
import me.kolpa.raspberrymcspigot.core.usecases.RedstoneUpdateInteractor;
import me.kolpa.raspberrymcspigot.core.usecases.RemoveBlockInteractor;
import me.kolpa.raspberrymcspigot.impl.repository.file.FileUnitOfWorkFactory;
import me.kolpa.raspberrymcspigot.impl.repository.file.json.JsonSerializer;
import me.kolpa.raspberrymcspigot.listener.DestroyListener;
import me.kolpa.raspberrymcspigot.listener.RedstoneListener;
import me.kolpa.raspberrymcspigot.listener.SignListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class RaspberryMcSpigot extends JavaPlugin
{

	@Override
	public void onEnable()
	{
		FileUnitOfWorkFactory fileUnitOfWorkFactory = FileUnitOfWorkFactory.readFile(new JsonSerializer());
		
		//TODO: dingen toevoegen is crack
		AddOutputPinStructureInteractor addOutputPinStructureInteractor = new AddOutputPinStructureInteractor(
				fileUnitOfWorkFactory);
		RemoveBlockInteractor removeBlockInteractor = new RemoveBlockInteractor(fileUnitOfWorkFactory);
		RedstoneUpdateInteractor redstoneUpdateInteractor = new RedstoneUpdateInteractor(fileUnitOfWorkFactory);
		
		getServer().getPluginManager().registerEvents(new SignListener(this, addOutputPinStructureInteractor), this);
		getServer().getPluginManager().registerEvents(new DestroyListener(removeBlockInteractor), this);
		getServer().getPluginManager().registerEvents(new RedstoneListener(this, redstoneUpdateInteractor), this);
	}

	@Override
	public void onDisable()
	{
	}
}
