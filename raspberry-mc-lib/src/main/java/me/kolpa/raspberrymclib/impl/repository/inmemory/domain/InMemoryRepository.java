package me.kolpa.raspberrymclib.impl.repository.inmemory.domain;

import me.kolpa.raspberrymclib.core.repository.domain.Repository;

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
