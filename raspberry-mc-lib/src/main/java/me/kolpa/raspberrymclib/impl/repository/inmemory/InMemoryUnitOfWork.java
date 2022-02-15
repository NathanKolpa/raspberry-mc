package me.kolpa.raspberrymclib.impl.repository.inmemory;

import me.kolpa.raspberrymclib.core.repository.UnitOfWork;
import me.kolpa.raspberrymclib.core.repository.domain.OutputPinRepository;
import me.kolpa.raspberrymclib.core.repository.domain.SensorInputPinRepository;
import me.kolpa.raspberrymclib.impl.repository.inmemory.domain.InMemoryOutputPinRepository;
import me.kolpa.raspberrymclib.impl.repository.inmemory.domain.InMemorySensorInputPinRepository;

public class InMemoryUnitOfWork implements UnitOfWork
{
	private final InMemoryOutputPinRepository gpioRepository;
	private final InMemorySensorInputPinRepository inMemoryTemperatureSensorInputPinRepository;

	public InMemoryUnitOfWork(InMemoryOutputPinRepository gpioRepository, InMemorySensorInputPinRepository inMemoryTemperatureSensorInputPinRepository)
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
	public SensorInputPinRepository inputPins()
	{
		return inMemoryTemperatureSensorInputPinRepository;
	}

	@Override
	public void save()
	{
		
	}
}
