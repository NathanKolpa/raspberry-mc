package me.kolpa.raspberrymclib.core.repository;

import me.kolpa.raspberrymclib.core.repository.domain.OutputPinRepository;
import me.kolpa.raspberrymclib.core.repository.domain.TemperatureSensorInputPinRepository;

public interface UnitOfWork
{
	OutputPinRepository outputPins();
	TemperatureSensorInputPinRepository temperatureSensors();
	
	void save();
}
