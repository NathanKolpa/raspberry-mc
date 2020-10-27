package me.kolpa.raspberryapi.impl.repository.inmemory;

import me.kolpa.raspberryapi.core.repository.UnitOfWork;
import me.kolpa.raspberryapi.core.repository.domain.OutputPinRepository;
import me.kolpa.raspberryapi.impl.repository.inmemory.domain.InMemoryOutputPinRepository;

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
	public void close()
	{

	}
}
