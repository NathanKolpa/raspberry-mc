package me.kolpa.raspberrymclib.impl.repository.inmemory;

import me.kolpa.raspberrymclib.core.repository.UnitOfWork;
import me.kolpa.raspberrymclib.core.repository.domain.GpioPinRepository;
import me.kolpa.raspberrymclib.impl.repository.inmemory.domain.InMemoryGpioRepository;

public class InMemoryUnitOfWork implements UnitOfWork
{
	private InMemoryGpioRepository gpioRepository;

	public InMemoryUnitOfWork(InMemoryGpioRepository gpioRepository)
	{
		this.gpioRepository = gpioRepository;
	}

	@Override
	public GpioPinRepository gpioPins()
	{
		return gpioRepository;
	}

	@Override
	public void close()
	{

	}
}
