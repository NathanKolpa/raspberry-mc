package me.kolpa.raspberrymcspigot.repository;

import me.kolpa.raspberrymcspigot.world.SavedBlock;

import java.util.List;

public interface GpioBlockRepository
{
	void add(SavedBlock block);
	void remove(SavedBlock block);
	List<SavedBlock> getAll();
	
	void save();
	void load();
}
