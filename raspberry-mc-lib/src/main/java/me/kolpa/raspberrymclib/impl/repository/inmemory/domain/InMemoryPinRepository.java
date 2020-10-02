package me.kolpa.raspberrymclib.impl.repository.inmemory.domain;

import me.kolpa.raspberrymclib.core.model.Pin;

public abstract class InMemoryPinRepository<T extends Pin> extends InMemoryRepository<T>
{
	private int autoIncrement = 1;
	
	@Override
	public T getById(int id)
	{
		return getValues().stream().filter(x -> x.getPinId() == id).findFirst().orElse(null);
	}

	@Override
	public void add(T v)
	{
		getValues().add(v);
		v.setPinId(autoIncrement);
		autoIncrement++;
	}
}
