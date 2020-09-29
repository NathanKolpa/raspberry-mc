package me.kolpa.raspberrymclib.impl.repository.inmemory;

import me.kolpa.raspberrymclib.core.repository.UnitOfWork;
import me.kolpa.raspberrymclib.core.repository.UnitOfWorkFactory;
import me.kolpa.raspberrymclib.impl.repository.inmemory.domain.InMemoryGpioRepository;

public class InMemoryUnitOfWorkFactory implements UnitOfWorkFactory
{
	private final InMemoryGpioRepository gpioRepository = new InMemoryGpioRepository();
	
	public InMemoryGpioRepository getGpioRepository()
	{
		return gpioRepository;
	}
	
	@Override
	public UnitOfWork create()
	{
		return new InMemoryUnitOfWork(gpioRepository);
	}
}
