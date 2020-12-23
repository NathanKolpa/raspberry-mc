package me.kolpa.raspberrymclib.impl.repository.inmemory;

import me.kolpa.raspberrymclib.core.repository.UnitOfWork;
import me.kolpa.raspberrymclib.core.repository.UnitOfWorkFactory;
import me.kolpa.raspberrymclib.impl.repository.inmemory.domain.InMemoryOutputPinRepository;
import me.kolpa.raspberrymclib.impl.repository.inmemory.domain.InMemoryTemperatureSensorInputPinRepository;

public class InMemoryUnitOfWorkFactory implements UnitOfWorkFactory
{
	private final InMemoryOutputPinRepository gpioRepository = new InMemoryOutputPinRepository();
	private final InMemoryTemperatureSensorInputPinRepository tempRepo = new InMemoryTemperatureSensorInputPinRepository();
	
	public InMemoryOutputPinRepository getOutputPinRepository()
	{
		return gpioRepository;
	}
	
	@Override
	public UnitOfWork create()
	{
		return new InMemoryUnitOfWork(gpioRepository, tempRepo);
	}
}
