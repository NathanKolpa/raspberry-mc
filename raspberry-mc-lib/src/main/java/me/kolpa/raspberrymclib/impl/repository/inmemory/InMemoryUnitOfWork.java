package me.kolpa.raspberrymclib.impl.repository.inmemory;

import me.kolpa.raspberrymclib.core.repository.UnitOfWork;
import me.kolpa.raspberrymclib.core.repository.domain.OutputPinRepository;
import me.kolpa.raspberrymclib.impl.repository.inmemory.domain.InMemoryOutputPinRepository;

public class InMemoryUnitOfWork implements UnitOfWork
{
	private final InMemoryOutputPinRepository gpioRepository;

	public InMemoryUnitOfWork(InMemoryOutputPinRepository gpioRepository)
	{
		this.gpioRepository = gpioRepository;
	}

	@Override
	public OutputPinRepository outputPins()
	{
		return gpioRepository;
	}

	@Override
	public void save()
	{
		
	}
}
