package me.kolpa.raspberrymcspigot.impl.repository.memory.domain.generic;

import me.kolpa.raspberrymcspigot.core.repository.domain.generic.Repository;

import java.util.ArrayList;
import java.util.List;

public abstract class InMemoryRepository<T> implements Repository<T>
{
	private List<T> values = new ArrayList<>();
	
	@Override
	public void add(T entity)
	{
		values.add(entity);
	}

	@Override
	public List<T> getAll()
	{
		return new ArrayList<>(values);
	}

	@Override
	public void remove(T entity)
	{
		values.remove(entity);
	}
	
	public List<T> getValues()
	{
		return values;
	}
	
	public void setValues(List<T> values)
	{
		this.values = values;
	}
}
