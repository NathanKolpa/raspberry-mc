package me.kolpa.raspberrymcspigot.repository;

import me.kolpa.raspberrymcspigot.world.GpioBlock;
import me.kolpa.raspberrymcspigot.world.SavedBlock;

import java.util.List;

public interface GpioBlockRepository
{
	void add(GpioBlock block);
	void remove(GpioBlock block);
	List<GpioBlock> getAll();
	
	void save();
	void load();
}
