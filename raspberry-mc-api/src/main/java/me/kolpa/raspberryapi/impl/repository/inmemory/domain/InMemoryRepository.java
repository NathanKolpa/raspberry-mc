package me.kolpa.raspberryapi.impl.repository.inmemory.domain;


import me.kolpa.raspberryapi.core.repository.domain.Repository;

import java.util.ArrayList;
import java.util.List;

public abstract class InMemoryRepository<T> implements Repository<T>
{
	private final List<T> values = new ArrayList<>();
	
	@Override
	public List<T> getAll()
	{
		return new ArrayList<>(values);
	}
	
	public List<T> getValues()
	{
		return values;
	}
}
