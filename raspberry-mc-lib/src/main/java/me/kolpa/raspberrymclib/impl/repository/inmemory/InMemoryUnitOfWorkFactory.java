package me.kolpa.raspberrymclib.impl.repository.inmemory;

import me.kolpa.raspberrymclib.core.repository.UnitOfWork;
import me.kolpa.raspberrymclib.core.repository.UnitOfWorkFactory;
import me.kolpa.raspberrymclib.impl.repository.inmemory.domain.InMemoryOutputPinRepository;

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
