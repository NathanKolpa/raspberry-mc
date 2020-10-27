package me.kolpa.raspberryapi.impl.repository.inmemory;

import me.kolpa.raspberryapi.core.repository.UnitOfWork;
import me.kolpa.raspberryapi.core.repository.UnitOfWorkFactory;
import me.kolpa.raspberryapi.impl.repository.inmemory.domain.InMemoryOutputPinRepository;

public class InMemoryUnitOfWorkFactory implements UnitOfWorkFactory
{
	private final InMemoryOutputPinRepository gpioRepository = new InMemoryOutputPinRepository();
	
	public InMemoryOutputPinRepository getOutputPinRepository()
	{
		return gpioRepository;
	}
	
	@Override
	public UnitOfWork create()
	{
		return new InMemoryUnitOfWork(gpioRepository);
	}
}
