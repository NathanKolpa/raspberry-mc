package me.kolpa.raspberrymclib.core.repository;

import me.kolpa.raspberrymclib.core.repository.domain.OutputPinRepository;
import me.kolpa.raspberrymclib.core.repository.domain.SensorInputPinRepository;

public interface UnitOfWork
{
	OutputPinRepository outputPins();
	SensorInputPinRepository inputPins();
	
	void save();
}
