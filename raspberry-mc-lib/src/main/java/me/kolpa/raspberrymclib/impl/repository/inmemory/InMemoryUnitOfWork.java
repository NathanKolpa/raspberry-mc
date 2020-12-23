package me.kolpa.raspberrymclib.impl.repository.inmemory;

import me.kolpa.raspberrymclib.core.repository.UnitOfWork;
import me.kolpa.raspberrymclib.core.repository.domain.OutputPinRepository;
import me.kolpa.raspberrymclib.core.repository.domain.TemperatureSensorInputPinRepository;
import me.kolpa.raspberrymclib.impl.repository.inmemory.domain.InMemoryOutputPinRepository;
import me.kolpa.raspberrymclib.impl.repository.inmemory.domain.InMemoryTemperatureSensorInputPinRepository;

public class InMemoryUnitOfWork implements UnitOfWork
{
	private final InMemoryOutputPinRepository gpioRepository;
	private final InMemoryTemperatureSensorInputPinRepository inMemoryTemperatureSensorInputPinRepository;

	public InMemoryUnitOfWork(InMemoryOutputPinRepository gpioRepository, InMemoryTemperatureSensorInputPinRepository inMemoryTemperatureSensorInputPinRepository)
	{
		this.gpioRepository = gpioRepository;
		this.inMemoryTemperatureSensorInputPinRepository = inMemoryTemperatureSensorInputPinRepository;
	}

	@Override
	public OutputPinRepository outputPins()
	{
		return gpioRepository;
	}

	@Override
	public TemperatureSensorInputPinRepository temperatureSensors()
	{
		return inMemoryTemperatureSensorInputPinRepository;
	}

	@Override
	public void save()
	{
		
	}
}
